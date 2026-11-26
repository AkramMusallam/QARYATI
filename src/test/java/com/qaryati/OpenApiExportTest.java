package com.qaryati;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OpenApiExportTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void exportOpenApiYamlSpec() throws Exception {
        MvcResult result = mockMvc.perform(get("http://localhost:8080/v3/api-docs.yaml"))
                .andExpect(status().isOk())
                .andReturn();

        String yaml = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(yaml)
                .contains("openapi: 3.")
                .contains("/api/v1/villages");

        Path outputPath = Path.of("docs", "openapi.yaml");
        Files.createDirectories(outputPath.getParent());
        Files.writeString(outputPath, yaml, StandardCharsets.UTF_8);
    }
}
