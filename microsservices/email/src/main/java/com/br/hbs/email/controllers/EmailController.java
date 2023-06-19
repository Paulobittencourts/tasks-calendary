package com.br.hbs.email.controllers;

import com.br.hbs.email.dto.response.EmailResponse;
import com.br.hbs.email.dto.request.EmailResquest;
import com.br.hbs.email.services.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailResponse> sendingEmail(@RequestBody @Valid EmailResquest emailResquest) {
        return new ResponseEntity<>(emailService.sendEmail(emailResquest), HttpStatus.CREATED);
    }
    @GetMapping("/emails")
    public Page<EmailResponse> getMailAll(Pageable pageable) {
        return emailService.getAll(pageable);
    }
    @GetMapping("/emails/{id}")
    public EmailResponse getMailByID(@PathVariable @NotNull Long id){
        return emailService.getByIDMail(id);
    }

}
