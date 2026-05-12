package com.medclinic.appointmentsystem.service;

import com.medclinic.appointmentsystem.entity.Service;
import com.medclinic.appointmentsystem.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public Service create(String name, String description, Integer durationMinutes, Double price) {
        Service service = new Service();
        service.setName(name);
        service.setDescription(description);
        service.setDurationMinutes(durationMinutes);
        service.setPrice(price);
        return serviceRepository.save(service);
    }

    public Service findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    public List<Service> search(String name) {
        return serviceRepository.findByNameContainingIgnoreCase(name);
    }

    public Service update(Long id, String name, String description, Integer durationMinutes, Double price) {
        Service service = findById(id);
        service.setName(name);
        service.setDescription(description);
        service.setDurationMinutes(durationMinutes);
        service.setPrice(price);
        return serviceRepository.save(service);
    }

    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }
}
