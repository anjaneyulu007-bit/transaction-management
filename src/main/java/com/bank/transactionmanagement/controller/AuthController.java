package com.bank.transactionmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.transactionmanagement.dto.AuthRequestDTO;
import com.bank.transactionmanagement.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequestDTO loginRequest) {
		String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
		return ResponseEntity.ok(token);
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody AuthRequestDTO registerRequest) {
		authService.register(registerRequest.getUsername(), registerRequest.getPassword());
		return ResponseEntity.ok().build();
	}
}