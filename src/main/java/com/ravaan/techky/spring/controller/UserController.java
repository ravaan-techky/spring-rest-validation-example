package com.ravaan.techky.spring.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ravaan.techky.spring.dto.User;
import com.ravaan.techky.spring.exception.ResourceNotFoundException;
import com.ravaan.techky.spring.service.UserService;

/**
 * The Class UserController.
 */
@RestController
public class UserController {

	/** The user service. */
	@Autowired
	UserService userService;

	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	@GetMapping(path = "/user")
	public CollectionModel<User> getAllUser() {
		List<User> userList = this.userService.getAllUser();
		CollectionModel<User> resource = CollectionModel.of(userList);
		WebMvcLinkBuilder userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findUser(123));
		resource.add(userLink.withRel("user-link"));
		WebMvcLinkBuilder deleteUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteUser(123));
		resource.add(deleteUserLink.withRel("delete-user-link"));
		User userObject = new User(-1, "firstName", "lastName", "address");
		WebMvcLinkBuilder saveUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).saveUser(userObject));
		resource.add(saveUserLink.withRel("save-user-link"));
		return resource;
	}


	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the entity model
	 */
	@GetMapping(path = "/user/{userId}")
	public EntityModel<User> findUser(@PathVariable(name = "userId") Integer userId) {
		User resultUser = this.userService.findUser(userId);
		if(resultUser == null) {
			throw new ResourceNotFoundException("Requested user not found. [User ID : " + userId + "]");
		}
		EntityModel<User> resource = EntityModel.of(resultUser);
		WebMvcLinkBuilder deleteUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).deleteUser(123));
		resource.add(deleteUserLink.withRel("delete-user-link"));
		WebMvcLinkBuilder allUserLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUser());
		resource.add(allUserLink.withRel("all-user"));
		return resource;
	}
	
	/**
	 * Save user.
	 *
	 * @param userObject the user object
	 * @return the response entity
	 */
	@PostMapping(path = "/user")
	public ResponseEntity<Object> saveUser(@Validated @RequestBody User userObject) {
		User newUser = this.userService.saveUser(userObject);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(newUser.getUserId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return the response entity
	 */
	@DeleteMapping(path = "/user/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable(name="userId") Integer userId) {
		User deletedUser = this.userService.deleteUser(userId);
		if(deletedUser == null) {
			throw new ResourceNotFoundException("Requested used not found. [User ID : " + userId + "]");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
