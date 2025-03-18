package org.psionicgeek.uber_backend.services.impl;

import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.psionicgeek.uber_backend.services.DistanceService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";
    @Override
    public double calculateDistance(Point src, Point dest) {

        try {
            OSRMResponseDto responseDto = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(
                            src.getX() + "," + src.getY() + ";" + dest.getX() + "," + dest.getY()
                    )
                    .retrieve()
                    .body(OSRMResponseDto.class);

            return responseDto != null ? responseDto.getRoutes().getFirst().getDistance() / 1000.0 : -1;
        }
        catch (Exception e) {
            throw new RuntimeException("Error while calculating distance "+e.getMessage(), e);
        }
    }
}

@Data
class OSRMResponseDto {
    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute {
    private double distance;
}
