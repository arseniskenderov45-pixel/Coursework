package com.medclinic.appointmentsystem;

import com.medclinic.appointmentsystem.entity.Service;
import com.medclinic.appointmentsystem.repository.ServiceRepository;
import com.medclinic.appointmentsystem.service.ServiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceService serviceService;

    @Test
    void create_savesAndReturnsService() {
        Service saved = new Service();
        saved.setId(1L);
        saved.setName("Терапевт");
        saved.setDescription("Осмотр");
        saved.setDurationMinutes(30);
        saved.setPrice(5000.0);
        when(serviceRepository.save(any(Service.class))).thenReturn(saved);

        Service result = serviceService.create("Терапевт", "Осмотр", 30, 5000.0);

        assertEquals("Терапевт", result.getName());
        assertEquals(30, result.getDurationMinutes());
        verify(serviceRepository).save(any(Service.class));
    }

    @Test
    void findById_returnsService_whenExists() {
        Service service = new Service();
        service.setId(1L);
        service.setName("Терапевт");
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        Service result = serviceService.findById(1L);

        assertEquals("Терапевт", result.getName());
    }

    @Test
    void findById_throwsException_whenNotFound() {
        when(serviceRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serviceService.findById(99L));
    }

    @Test
    void delete_callsRepository() {
        serviceService.delete(1L);
        verify(serviceRepository).deleteById(1L);
    }

    @Test
    void search_returnsMatchingServices() {
        Service service = new Service();
        service.setName("Терапевт");
        when(serviceRepository.findByNameContainingIgnoreCase("терап")).thenReturn(List.of(service));

        List<Service> result = serviceService.search("терап");

        assertEquals(1, result.size());
        assertEquals("Терапевт", result.get(0).getName());
    }
}
