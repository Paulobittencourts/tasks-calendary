package com.br.hbs.email.services;

import com.br.hbs.email.dto.response.EmailResponse;
import com.br.hbs.email.dto.request.EmailResquest;
import com.br.hbs.email.enums.StatusEmail;
import com.br.hbs.email.model.EmailModel;
import com.br.hbs.email.respositories.EmailRepository;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private JavaMailSender mailSender;

    public Page<EmailResponse> getAll(Pageable pageable) {
        return new PageImpl<>(emailRepository.findAll(pageable)
                .stream()
                .map(email -> mapper.map(email, EmailResponse.class))
                .toList());
    }

    public EmailResponse getByIDMail(Long id) {
        EmailModel emailID = emailRepository.findById(id)
                .orElseThrow(EntityExistsException::new);

        return mapper.map(emailID, EmailResponse.class);
    }

    public EmailResponse sendEmail(EmailResquest emailResquest) {
        EmailModel email = mapper.map(emailResquest, EmailModel.class);
        email.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            mailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            email.setStatusEmail(StatusEmail.ERROR);
        }
        emailRepository.save(email);
        return mapper.map(email, EmailResponse.class);
    }
}
