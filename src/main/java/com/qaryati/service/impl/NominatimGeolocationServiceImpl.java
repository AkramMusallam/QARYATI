package com.qaryati.service.impl;

import com.qaryati.service.Coordinates;
import com.qaryati.service.GeolocationService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NominatimGeolocationServiceImpl implements GeolocationService {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";
    private static final String USER_AGENT = "QaryatiProject/1.0 (Student Project - Advanced SE)";

    private final RestTemplate restTemplate;

    @Override
    @Cacheable(value = "geolocations", key = "#placeName", unless = "#result == null")
    @Retry(name = "nominatimApi", fallbackMethod = "fallbackCoordinates")
    public Optional<Coordinates> getCoordinates(String placeName) {
        String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_API_URL)
                .queryParam("q", placeName + ", Palestine")
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> body = response.getBody();
        if (body == null || body.isEmpty()) {
            log.info("No coordinates found for '{}'", placeName);
            return Optional.empty();
        }

        Map<String, Object> firstResult = body.get(0);
        Object lat = firstResult.get("lat");
        Object lon = firstResult.get("lon");
        if (lat == null || lon == null) {
            log.warn("Unexpected Nominatim response for '{}': {}", placeName, firstResult);
            return Optional.empty();
        }

        try {
            return Optional.of(new Coordinates(Double.parseDouble(lat.toString()), Double.parseDouble(lon.toString())));
        } catch (NumberFormatException e) {
            log.warn("Invalid coordinates from Nominatim for '{}': lat={}, lon={}", placeName, lat, lon);
            return Optional.empty();
        }
    }

    public Optional<Coordinates> fallbackCoordinates(String placeName, Throwable t) {
        log.warn("Geolocation API failed for '{}' after retries: {}", placeName, t.getMessage());
        return Optional.empty();
    }
}
