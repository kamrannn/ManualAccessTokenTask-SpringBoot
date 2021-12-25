/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Kalsym.ResourceServer.service;

import com.Kalsym.ResourceServer.model.AuthenticationRequest;
import com.Kalsym.ResourceServer.repository.AuthenticationRepository;
import com.Kalsym.ResourceServer.utility.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * @author kamran
 */
@Service
public class AuthenticationRequestService {
    public static String accessToken;
    public static Date TokenExpireTime;
    private final AuthenticationRepository repo;

    @Autowired
    public AuthenticationRequestService(AuthenticationRepository repo) {
        this.repo = repo;
    }

    public ResponseEntity<Object> getUserNameAndPassword(String username, String password, final HttpServletResponse headerResponse) throws ParseException {
        HashMap<String, Object> response = new HashMap<>();
        headerResponse.setHeader("Cache-Control", "no-store");
        headerResponse.setHeader("Pragma", "no-cache");
        Optional<AuthenticationRequest> user = repo.findAuthenticationRequestByUsernameAndPassword(username, password);
        if (user.isPresent()) {
            UUID uuid = UUID.randomUUID();
            accessToken = uuid.toString();
            response.put("access_token", accessToken);
            response.put("token_type", "bearer");
            TokenExpireTime = DateTime.getExpireTime();
            response.put("expires_in", TokenExpireTime);
            response.put("scope", "create");
            System.out.println(accessToken);
            System.out.println(TokenExpireTime.toString());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("error", "invalid request");
            response.put("error_description", "Request was missing the 'password' parameter.");
            response.put("error_uri", "See the full API docs at http://authorization-server.com/docs/access_token");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
