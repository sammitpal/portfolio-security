package com.security.controller;

import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.security.dto.TokenResponse;
import com.security.dto.UserModel;
import com.security.dto.UserResponse;
import com.security.exception.BadCredentials;
import com.security.exception.ErrorResponse;
import com.security.exception.RegistrationException;
import com.security.model.User;
import com.security.repository.UserRepo;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController implements ErrorController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TokenResponse tokenResponse;

	@Autowired
	private ErrorResponse errorResponse;

	@Autowired
	private PasswordEncoder encoder;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody UserModel userModel) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
			String token = JWT.create().withSubject(userModel.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 60 * 1000 * 30))
					.sign(Algorithm.HMAC512("${tokensecret}"));

			tokenResponse.setToken(token);
			return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
		} catch (BadCredentialsException e) {
			throw new BadCredentials(e.getMessage());
		}

	}

	@PostMapping("/register")
	public Map<String, String> save(@RequestBody User user) throws RegistrationException {
		
		Map<String, String> map = new HashMap<>();

		if (user.getUsername() == null || user.getEmail() == null && user.getPassword() == null) {
			map.put("error", "PLease enter the credentials");
		} else {
			User userFound = userRepo.findByUsername(user.getUsername()).orElse(null);
			if (userFound != null) {
				throw new RegistrationException("User Exists");
			} else {
				user.setPassword(encoder.encode(user.getPassword()));
				userRepo.save(user);
				map.put("message", "User registration Successful!");
			}
		}
		return map;
	}

	@GetMapping("/profile")
	public UserResponse profile(Principal principal) throws NullPointerException{
		UserResponse userResponse = new UserResponse();
		try {			
			User user = userRepo.findByUsername(principal.getName()).orElse(null);
			userResponse.setUsername(user.getUsername());
			userResponse.setEmail(user.getEmail());
			userResponse.setActive(user.isActive());
			userResponse.setRoles(user.getRoles());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return userResponse;
	}
	
	@GetMapping("/error")
	public ResponseEntity<?> errorResponse(HttpServletResponse response){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Unauthorized");
		errorResponse.setHttpStatus(HttpStatus.valueOf(response.getStatus()));
		errorResponse.setTimestamp(Instant.now().toString());
		return new ResponseEntity(errorResponse,HttpStatus.valueOf(response.getStatus()));
	}
}
