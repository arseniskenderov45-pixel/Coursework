package com.medclinic.appointmentsystem.service;

import com.medclinic.appointmentsystem.entity.Appointment;
import com.medclinic.appointmentsystem.entity.User;
import com.medclinic.appointmentsystem.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final ServiceService serviceService;

    public Appointment book(Long userId, Long serviceId, LocalDateTime dateTime) {
        Appointment appointment = new Appointment();
        appointment.setUser(userService.findById(userId));
        appointment.setService(serviceService.findById(serviceId));
        appointment.setDateTime(dateTime);
        appointment.setStatus(Appointment.Status.PENDING);
        return appointmentRepository.save(appointment);
    }

    public Appointment assignStaff(Long appointmentId, Long staffId) {
        Appointment appointment = findById(appointmentId);
        User staff = userService.findById(staffId);
        appointment.setStaff(staff);
        appointment.setStatus(Appointment.Status.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    public Appointment cancel(Long appointmentId) {
        Appointment appointment = findById(appointmentId);
        appointment.setStatus(Appointment.Status.CANCELLED);
        return appointmentRepository.save(appointment);
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findByUser(Long userId) {
        return appointmentRepository.findByUser(userService.findById(userId));
    }

    public List<Appointment> findByStatus(Appointment.Status status) {
        return appointmentRepository.findByStatus(status);
    }
}
