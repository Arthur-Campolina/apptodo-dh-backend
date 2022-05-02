package com.arthurcampolina.ToDO.services;

import com.arthurcampolina.ToDO.entities.PasswordResetToken;
import com.arthurcampolina.ToDO.entities.User;
import com.arthurcampolina.ToDO.exceptions.EmailException;
import com.arthurcampolina.ToDO.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.arthurcampolina.ToDO.repositories.PasswordResetTokenRepository;
import com.arthurcampolina.ToDO.services.impl.AuthServiceImpl;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService, AuthServiceImpl {


    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Autowired
    public AuthService(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Email não encontrado");
        }
        return user;
    }

    @Transactional
    public Boolean switchPassword(String token, String password) {
        try {
            PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
            if (validatePasswordResetToken(token)) {
                User usuario = userRepository.getById(passwordResetToken.getUser().getId());
                usuario.setPassword(passwordEncoder.encode(password));
                userRepository.save(usuario);
                return true;
            } else {
                return false;
            }
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    @Transactional
    public String createPasswordResetTokenForUser(String email) {
        User usuario = userRepository.findByEmail(email);
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        while (true) {
            PasswordResetToken tokenBase = passwordResetTokenRepository.findByToken(token);
            if (tokenBase == null) {
                break;
            } else {
                token = UUID.randomUUID().toString();
            }
        }
        PasswordResetToken myToken = new PasswordResetToken(token, usuario);
        passwordResetTokenRepository.save(myToken);
        if(emailService.sendForgotPasswodMensagem(usuario.getEmail(), token)){
            return token;
        }else{
            throw new EmailException("Não foi possível enviar o email");
        }
    }

    public boolean validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);
        return isTokenFound(passToken) && (!isTokenExpired(passToken));
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        Instant hoje = Instant.now();
        return hoje.compareTo(passToken.getExpiryDate()) > 0;
    }
}
