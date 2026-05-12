package com.medclinic.appointmentsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentRequest {
    private Long userId;
    private Long serviceId;
    private String dateTime;
}
