package org.psionicgeek.uber_backend.dto;

import lombok.Data;

@Data
public class PointDto {

    private Double[] coordinates;
    private String type="Point";

    public PointDto(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}
