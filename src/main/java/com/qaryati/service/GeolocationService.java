package com.qaryati.service;

import java.util.Optional;

public interface GeolocationService {

    Optional<Coordinates> getCoordinates(String placeName);
}
