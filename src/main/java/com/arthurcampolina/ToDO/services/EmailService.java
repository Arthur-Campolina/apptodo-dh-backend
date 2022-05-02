package com.arthurcampolina.ToDO.services;

import com.arthurcampolina.ToDO.entities.User;
import com.arthurcampolina.ToDO.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service("emailService")
@Slf4j
public class EmailService {

    @Value("${front.url}")
    private String urlFront;

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine,
                        UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean sendForgotPasswodMensagem(String to, String token) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            User user = userRepository.findByEmail(to);

            helper.setFrom("noreply@multitecnica.com.br");
            helper.setTo(to);
            helper.setSubject("[Multitécnica] Recuperação de senha");

            Context context = new Context();
            context.setVariable("link", urlFront + token);
            context.setVariable("nome", user.getEmail());

            String html = templateEngine.process("esqueci-a-senha.html", context);

            helper.setText(html, true);

            javaMailSender.send(message);
            log.info("Email para troca de senha enviado para: " + to);
            return true;
        } catch (MessagingException e) {
            log.error("Erro ao enviar email para troca de senha para: " + to);
            return false;
        }
    }

    @Transactional
    public String sendNewPassword(String to, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            User user = userRepository.findByEmail(to);

            helper.setFrom("noreply@pontoini.com.br");
            helper.setTo(to);
            helper.setSubject("[.Ini] Conta criada, sua nova senha chegou.");

            Context context = new Context();
            context.setVariable("senha", password);
            context.setVariable("nome", user.getEmail());

            String html = templateEngine.process("new-password.html", context);
            helper.setText(html, true);

            javaMailSender.send(message);

            log.info("Email nova senha enviado para: " + to);
            return "Email enviado com sucesso!";
        } catch (MessagingException e) {
            log.error("Erro ao enviar email nova senha para: " + to);
            return "Erro ao enviar email.";
        }
    }

}

