package com.qaryati.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RequestTracingAndRateLimitFilter extends OncePerRequestFilter {

    public static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    public static final String MDC_CORRELATION_ID_KEY = "correlationId";

    @Value("${qaryati.rate-limit.enabled:true}")
    private boolean rateLimitEnabled;

    @Value("${qaryati.rate-limit.requests-per-minute:300}")
    private int maxRequestsPerMinute;

    private final Map<String, WindowCounter> clientCounters = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        MDC.put(MDC_CORRELATION_ID_KEY, correlationId);
        response.setHeader(CORRELATION_ID_HEADER, correlationId);

        try {
            if (rateLimitEnabled && request.getRequestURI().startsWith("/api/")) {
                String clientIp = resolveClientIp(request);
                WindowCounter counter = clientCounters.computeIfAbsent(clientIp, k -> new WindowCounter());
                int currentCount = counter.incrementAndGet();
                response.setHeader("X-RateLimit-Limit", String.valueOf(maxRequestsPerMinute));
                response.setHeader("X-RateLimit-Remaining", String.valueOf(Math.max(0, maxRequestsPerMinute - currentCount)));

                if (currentCount > maxRequestsPerMinute) {
                    log.warn("Rate limit exceeded for IP={} on URI={}", clientIp, request.getRequestURI());
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    String body = String.format(
                            "{\"timestamp\":\"%s\",\"status\":429,\"message\":\"Rate limit exceeded. Maximum %d requests per minute allowed.\",\"correlationId\":\"%s\"}",
                            LocalDateTime.now(), maxRequestsPerMinute, correlationId);
                    response.getWriter().write(body);
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_CORRELATION_ID_KEY);
        }
    }

    private String resolveClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr() != null ? request.getRemoteAddr() : "unknown";
    }

    private static class WindowCounter {
        private volatile long windowStartMillis = System.currentTimeMillis();
        private final AtomicInteger count = new AtomicInteger(0);

        int incrementAndGet() {
            long now = System.currentTimeMillis();
            if (now - windowStartMillis >= 60_000L) {
                synchronized (this) {
                    if (now - windowStartMillis >= 60_000L) {
                        windowStartMillis = now;
                        count.set(0);
                    }
                }
            }
            return count.incrementAndGet();
        }
    }
}
