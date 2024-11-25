package org.psionicgeek.uber_backend.services.Impl;

import org.locationtech.jts.geom.Point;
import org.psionicgeek.uber_backend.services.DistanceService;
import org.springframework.stereotype.Service;

@Service
public class DistanceServiceOSRMImpl implements DistanceService {
    @Override
    public double calculateDistance(Point src, Point dest) {
        return 0;
    }
}
