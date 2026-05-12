package com.medclinic.appointmentsystem.controller;

import com.medclinic.appointmentsystem.dto.RegisterRequest;
import com.medclinic.appointmentsystem.entity.User;
import com.medclinic.appointmentsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User.Role role = User.Role.valueOf(request.getRole().toUpperCase());
        User user = userService.register(request.getUsername(), request.getEmail(), request.getPassword(), role);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
