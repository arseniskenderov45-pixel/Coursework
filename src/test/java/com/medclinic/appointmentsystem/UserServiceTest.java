package com.medclinic.appointmentsystem;

import com.medclinic.appointmentsystem.entity.User;
import com.medclinic.appointmentsystem.repository.UserRepository;
import com.medclinic.appointmentsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void register_savesUser_whenUsernameAndEmailAreUnique() {
        when(userRepository.existsByUsername("john")).thenReturn(false);
        when(userRepository.existsByEmail("john@clinic.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("hashed");

        User saved = new User();
        saved.setId(1L);
        saved.setUsername("john");
        saved.setEmail("john@clinic.com");
        saved.setPassword("hashed");
        saved.setRole(User.Role.USER);
        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.register("john", "john@clinic.com", "password", User.Role.USER);

        assertEquals("john", result.getUsername());
        assertEquals("hashed", result.getPassword());
        assertEquals(User.Role.USER, result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_throwsException_whenUsernameAlreadyExists() {
        when(userRepository.existsByUsername("john")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.register("john", "john@clinic.com", "password", User.Role.USER));

        assertEquals("Username already taken", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void findByUsername_returnsUser_whenExists() {
        User user = new User();
        user.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        User result = userService.findByUsername("john");

        assertEquals("john", result.getUsername());
    }

    @Test
    void findByUsername_throwsException_whenNotFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findByUsername("unknown"));
    }
}
