package com.onerivet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onerivet.model.payload.AuthRequest;
import com.onerivet.model.payload.UsersDto;
import com.onerivet.model.response.GenericResponse;
import com.onerivet.service.JwtService;
import com.onerivet.service.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;

//	@Autowired
//	private AuthRequest authRequest;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/add-users")
	public GenericResponse<String> addUsers(@RequestBody UsersDto usersDto) {
		GenericResponse<String> genericResponse = new GenericResponse<>(this.usersService.addAllUsers(usersDto), null);
		return genericResponse;
	}

	@GetMapping("/get-all-users")
	public GenericResponse<List<UsersDto>> getAll() {
		GenericResponse<List<UsersDto>> genericResponse = new GenericResponse<>(this.usersService.getAllusers(), null);
		return genericResponse;
	}

	@GetMapping("/get-by-id/{id}")
	public GenericResponse<UsersDto> getById(@PathVariable int id) {
		GenericResponse<UsersDto> genericResponse = new GenericResponse<>(this.usersService.getUsersById(id), null);
		return genericResponse;

	}

	@PutMapping("/update-users/{id}")
	public GenericResponse<String> updateUsers(@RequestBody UsersDto usersDto, @PathVariable int id) {
		GenericResponse<String> genericResponse = new GenericResponse<>(this.usersService.updateUsersById(usersDto, id),
				null);
		return genericResponse;
	}

	@DeleteMapping("/delete-user")
	public GenericResponse<String> deleteUsers() {
		GenericResponse<String> genericResponse = new GenericResponse<>(this.usersService.deleteAll(), null);
		return genericResponse;
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if (authenticate.isAuthenticated()) {

			//GenericResponse<String> genericResponse = new GenericResponse<>(
					String generatedTokens = this.jwtService.generatedTokens(authRequest.getUserName());
					return generatedTokens;
			//return genericResponse;
		}

		else {

			throw new UsernameNotFoundException("Invaild users");
		}
		

	}

}
