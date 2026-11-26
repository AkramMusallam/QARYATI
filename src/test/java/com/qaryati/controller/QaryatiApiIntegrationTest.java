package com.qaryati.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qaryati.dto.*;
import com.qaryati.entity.User;
import com.qaryati.repository.UserRepository;
import com.qaryati.service.Coordinates;
import com.qaryati.service.GeolocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QaryatiApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private GeolocationService geolocationService;

    private String adminToken;

    @BeforeEach
    void setUp() throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
        }

        when(geolocationService.getCoordinates(anyString()))
                .thenReturn(Optional.of(new Coordinates(31.795, 35.196)));

        AuthRequestDto loginReq = new AuthRequestDto();
        loginReq.setUsername("admin");
        loginReq.setPassword("admin123");

        MvcResult loginResult = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andReturn();

        AuthResponseDto authResponse = objectMapper.readValue(
                loginResult.getResponse().getContentAsString(), AuthResponseDto.class);
        adminToken = authResponse.getToken();
    }

    @Test
    void fullLifecycle_villageAndKnowledgeEntitiesAndRawSqlAnalytics() throws Exception {
        VillageRequestDto villageReq = new VillageRequestDto();
        villageReq.setCurrentNameAr("دير ياسين");
        villageReq.setCurrentNameEn("Deir Yassin");
        villageReq.setGovernorate("القدس");
        villageReq.setHistoricalOverview("قرية فلسطينية تاريخية غربي القدس.");
        villageReq.setCulturalCharacteristics("اشتهرت بقلع الحجارة وزراعة الزيتون.");

        mockMvc.perform(post("/api/v1/villages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(villageReq)))
                .andExpect(status().isUnauthorized());

        MvcResult createResult = mockMvc.perform(post("/api/v1/villages")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(villageReq)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("X-Correlation-Id"))
                .andExpect(jsonPath("$.currentNameAr").value("دير ياسين"))
                .andExpect(jsonPath("$.createdBy").value("admin"))
                .andExpect(jsonPath("$.latitude").value(31.795))
                .andReturn();

        VillageResponseDto createdVillage = objectMapper.readValue(
                createResult.getResponse().getContentAsString(), VillageResponseDto.class);
        Long villageId = createdVillage.getId();

        PopulationRecordRequestDto popReq = new PopulationRecordRequestDto();
        popReq.setVillageId(villageId);
        popReq.setPopulationValue(610);
        popReq.setReferenceYear(1945);
        popReq.setSourceInformation("Village Statistics 1945");

        mockMvc.perform(post("/api/v1/population-records")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(popReq)))
                .andExpect(status().isCreated());

        LandmarkRequestDto landmarkReq = new LandmarkRequestDto();
        landmarkReq.setVillageId(villageId);
        landmarkReq.setName("عين البلد");
        landmarkReq.setCategory("SPRING");
        landmarkReq.setDescription("نبع مياه تاريخي في القرية");
        landmarkReq.setSourceReference("كي لا ننسى - وليد الخالدي");

        mockMvc.perform(post("/api/v1/landmarks")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(landmarkReq)))
                .andExpect(status().isCreated());

        HistoricalEventRequestDto eventReq = new HistoricalEventRequestDto();
        eventReq.setVillageId(villageId);
        eventReq.setTitle("توثيق القرية في الدفاتر العثمانية");
        eventReq.setEventYear(1596);
        eventReq.setPeriod("العهد العثماني");
        eventReq.setSourceReference("سجلات الضرائب العثمانية 1596");

        mockMvc.perform(post("/api/v1/historical-events")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventReq)))
                .andExpect(status().isCreated());

        EconomyItemRequestDto ecoReq = new EconomyItemRequestDto();
        ecoReq.setVillageId(villageId);
        ecoReq.setName("الحجر المقدسي وزيت الزيتون");
        ecoReq.setCategory("INDUSTRY");

        mockMvc.perform(post("/api/v1/economy-items")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ecoReq)))
                .andExpect(status().isCreated());

        PublicFacilityRequestDto facReq = new PublicFacilityRequestDto();
        facReq.setVillageId(villageId);
        facReq.setName("مدرسة القرية للبنين");
        facReq.setFacilityType("SCHOOL");

        mockMvc.perform(post("/api/v1/public-facilities")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facReq)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/villages/" + villageId + "/analytics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.villageId").value(villageId))
                .andExpect(jsonPath("$.latestPopulation").value(610))
                .andExpect(jsonPath("$.latestPopulationYear").value(1945))
                .andExpect(jsonPath("$.landmarksCount").value(1))
                .andExpect(jsonPath("$.historicalEventsCount").value(1))
                .andExpect(jsonPath("$.economyItemsCount").value(1))
                .andExpect(jsonPath("$.publicFacilitiesCount").value(1));

        mockMvc.perform(get("/api/v1/villages/stats/governorates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].governorate").value("القدس"));
    }
}
