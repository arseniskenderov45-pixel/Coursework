package com.medclinic.appointmentsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequest {
    private String name;
    private String description;
    private Integer durationMinutes;
    private Double price;
}
