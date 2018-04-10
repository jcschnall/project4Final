package com.example.usersapi.controllers;

import com.example.usersapi.models.User;
import com.example.usersapi.repositories.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UsersController {

	@Autowired
	private UserRepository userRepository;


	@GetMapping("/users")
	public Iterable<User> findAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/test")
	public String findTest() {
		return "test";
	}    //for debugging purposes

	@GetMapping("users/{userId}")
	public User findUserById(@PathVariable Long userId) throws NotFoundException {

		User foundUser = userRepository.findOne(userId);

		if (foundUser == null) {
			throw new NotFoundException("not found!");
		}

		return foundUser;
	}



	@PostMapping("/users")
	public User createNewUser(@RequestBody User newUser) {
		return userRepository.save(newUser);
	}



	@DeleteMapping("users/{userId}")
	public HttpStatus deleteUserById(@PathVariable Long userId) throws EmptyResultDataAccessException {

		userRepository.delete(userId);
		return HttpStatus.OK;
	}



	@PatchMapping("users/{userId}")
	public User updateUserById(@PathVariable Long userId, @RequestBody User userRequest) throws NotFoundException {
		User userFromDb = userRepository.findOne(userId);

		if (userFromDb == null) {
			throw new NotFoundException("not found!");
		}

		userFromDb.setUserName(userRequest.getUserName());
		userFromDb.setFirstName(userRequest.getFirstName());
		userFromDb.setLastName(userRequest.getLastName());

		return userRepository.save(userFromDb);
	}




	// Add exceptions for testing purposes

	@ExceptionHandler
	void handleUserNotFound(
		NotFoundException exception,
		HttpServletResponse response) throws IOException {

		response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}

	@ExceptionHandler
	void handleDeleteNotFoundException(
		EmptyResultDataAccessException exception,
		HttpServletResponse response) throws IOException {

		response.sendError(HttpStatus.NOT_FOUND.value());
	}
}
