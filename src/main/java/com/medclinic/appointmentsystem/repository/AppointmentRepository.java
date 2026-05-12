package com.medclinic.appointmentsystem.repository;

import com.medclinic.appointmentsystem.entity.Appointment;
import com.medclinic.appointmentsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
    List<Appointment> findByStaff(User staff);
    List<Appointment> findByStatus(Appointment.Status status);
}
