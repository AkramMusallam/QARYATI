package com.qaryati.service;

import com.qaryati.dto.VillageRequestDto;
import com.qaryati.dto.VillageResponseDto;
import com.qaryati.entity.Village;
import com.qaryati.exception.ResourceNotFoundException;
import com.qaryati.mapper.VillageMapper;
import com.qaryati.repository.VillageRawSqlRepository;
import com.qaryati.repository.VillageRepository;
import com.qaryati.service.impl.VillageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VillageServiceTest {

    @Mock
    private VillageRepository villageRepository;

    @Mock
    private VillageRawSqlRepository villageRawSqlRepository;

    @Mock
    private GeolocationService geolocationService;

    private VillageServiceImpl villageService;

    @BeforeEach
    void setUp() {
        VillageMapper mapper = new VillageMapper();
        villageService = new VillageServiceImpl(villageRepository, villageRawSqlRepository, mapper, geolocationService);
    }

    @Test
    void createVillage_whenCoordinatesMissing_resolvesViaGeolocationService() {
        VillageRequestDto request = new VillageRequestDto();
        request.setCurrentNameAr("لفتا");
        request.setCurrentNameEn("Lifta");
        request.setGovernorate("القدس");

        when(geolocationService.getCoordinates("لفتا"))
                .thenReturn(Optional.of(new Coordinates(31.795, 35.196)));
        when(villageRepository.save(any(Village.class))).thenAnswer(inv -> {
            Village v = inv.getArgument(0);
            v.setId(1L);
            return v;
        });

        VillageResponseDto response = villageService.createVillage(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(31.795, response.getLatitude());
        assertEquals(35.196, response.getLongitude());
        verify(geolocationService, times(1)).getCoordinates("لفتا");
    }

    @Test
    void createVillage_whenCoordinatesProvided_doesNotCallGeolocationService() {
        VillageRequestDto request = new VillageRequestDto();
        request.setCurrentNameAr("بيرزيت");
        request.setGovernorate("رام الله والبيرة");
        request.setLatitude(31.970);
        request.setLongitude(35.195);

        when(villageRepository.save(any(Village.class))).thenAnswer(inv -> {
            Village v = inv.getArgument(0);
            v.setId(2L);
            return v;
        });

        VillageResponseDto response = villageService.createVillage(request);

        assertEquals(31.970, response.getLatitude());
        assertEquals(35.195, response.getLongitude());
        verifyNoInteractions(geolocationService);
    }

    @Test
    void getVillageById_whenNotFound_throwsResourceNotFoundException() {
        when(villageRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> villageService.getVillageById(999L));
    }
}
