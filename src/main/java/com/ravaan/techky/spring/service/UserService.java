package com.ravaan.techky.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ravaan.techky.spring.dto.User;
import com.ravaan.techky.spring.repository.UserRepository;

/**
 * The Class UserService.
 */
@Service("userService")
public class UserService {

	/** The user repository. */
	@Autowired
	UserRepository userRepository;

	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	public List<User> getAllUser() {
		return userRepository.getAllUser();
	}

	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User findUser(Integer userId) {
		return userRepository.findUser(userId);
	}

	/**
	 * Save user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User saveUser(User user) {
		return userRepository.saveUser(user);
	}

	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User deleteUser(Integer userId) {
		return userRepository.deleteUserById(userId);
	}
}
