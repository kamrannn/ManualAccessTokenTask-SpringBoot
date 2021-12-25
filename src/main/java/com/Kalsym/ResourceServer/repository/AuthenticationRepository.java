/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Kalsym.ResourceServer.repository;

import com.Kalsym.ResourceServer.model.AuthenticationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author kamran
 */
@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationRequest, String> {
    Optional<AuthenticationRequest> findAuthenticationRequestByUsernameAndPassword(String username, String password);
}
