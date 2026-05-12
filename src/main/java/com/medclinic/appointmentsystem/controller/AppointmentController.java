package com.medclinic.appointmentsystem.controller;

import com.medclinic.appointmentsystem.dto.AppointmentRequest;
import com.medclinic.appointmentsystem.entity.Appointment;
import com.medclinic.appointmentsystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> book(@RequestBody AppointmentRequest request) {
        LocalDateTime dateTime = LocalDateTime.parse(request.getDateTime());
        Appointment appointment = appointmentService.book(request.getUserId(), request.getServiceId(), dateTime);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> findAll(@RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(appointmentService.findByStatus(Appointment.Status.valueOf(status.toUpperCase())));
        }
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> findByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(appointmentService.findByUser(userId));
    }

    @PatchMapping("/{id}/assign-staff/{staffId}")
    public ResponseEntity<Appointment> assignStaff(@PathVariable Long id, @PathVariable Long staffId) {
        return ResponseEntity.ok(appointmentService.assignStaff(id, staffId));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Appointment> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancel(id));
    }
}
