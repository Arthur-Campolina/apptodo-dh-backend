package com.arthurcampolina.ToDO.services.impl;

public interface AuthServiceImpl {

    String createPasswordResetTokenForUser(String email);
    boolean validatePasswordResetToken(String token);
    Boolean switchPassword(String token, String password);
}
