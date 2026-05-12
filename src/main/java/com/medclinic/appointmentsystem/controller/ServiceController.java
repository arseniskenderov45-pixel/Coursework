package com.medclinic.appointmentsystem.controller;

import com.medclinic.appointmentsystem.dto.ServiceRequest;
import com.medclinic.appointmentsystem.entity.Service;
import com.medclinic.appointmentsystem.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<Service> create(@RequestBody ServiceRequest request) {
        Service service = serviceService.create(request.getName(), request.getDescription(),
                request.getDurationMinutes(), request.getPrice());
        return ResponseEntity.ok(service);
    }

    @GetMapping
    public ResponseEntity<List<Service>> findAll(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(serviceService.search(search));
        }
        return ResponseEntity.ok(serviceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> findById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> update(@PathVariable Long id, @RequestBody ServiceRequest request) {
        Service service = serviceService.update(id, request.getName(), request.getDescription(),
                request.getDurationMinutes(), request.getPrice());
        return ResponseEntity.ok(service);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
