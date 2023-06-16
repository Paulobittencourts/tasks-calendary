package com.br.hbs.customer.controllers;

import com.br.hbs.customer.dto.UserDTO;
import com.br.hbs.customer.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<UserDTO> getAll(@PageableDefault(size = 5) Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO getByID(@PathVariable @Valid Long id) {
        return userService.getUsersById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUsers(@RequestBody @Valid @NotNull UserDTO dto){
        if(userService.existsUsername(dto.getUsername())){
            throw new DataIntegrityViolationException("The username already exists: " + dto.getUsername());
        }
        if (userService.existsEmail(dto.getEmail())){
            throw new DataIntegrityViolationException("The email already exists: " + dto.getEmail());
        }
        userService.createUsers(dto);
        return ResponseEntity.ok().body("Creating with success");
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsers(@PathVariable @NotNull Long id) {
        userService.delete(id);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> updateUsers(@RequestBody @Valid @NotNull UserDTO dto, @PathVariable Long id) {
        if(userService.existsUsername(dto.getUsername())){
            throw new DataIntegrityViolationException("The username already exists: " + dto.getUsername());
        }
        if (userService.existsEmail(dto.getEmail())){
            throw new DataIntegrityViolationException("The email already exists: " + dto.getEmail());
        }
        UserDTO userUpdated = userService.updatedUsers(id, dto);
        return ResponseEntity.ok(userUpdated);
    }

    @PutMapping("/users/tasks/{tasksId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> updatedStatus(@RequestParam String status, @PathVariable Long tasksId){
        userService.updatedStatus(status, tasksId);
        return ResponseEntity.ok().body("Updating with success");
    }
}
