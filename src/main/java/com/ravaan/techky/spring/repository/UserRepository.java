package com.ravaan.techky.spring.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.ravaan.techky.spring.dto.User;

/**
 * The Class UserRepository.
 */
@Component
public class UserRepository {

	/** The Constant userList. */
	private static final List<User> userList = new ArrayList<>();

	/** The Constant userIncrementVar. */
	private static final AtomicInteger userIncrementVar = new AtomicInteger(0);

	static {
		userList.addAll(Arrays.asList(new User(userIncrementVar.incrementAndGet(), "Bhushan", "Patil", "Pune"),
				new User(userIncrementVar.incrementAndGet(), "Sandip", "Patil", "USA"),
				new User(userIncrementVar.incrementAndGet(), "Harshal", "Patil", "Mumbai"),
				new User(userIncrementVar.incrementAndGet(), "Amol", "Kale", "Jalgaon")));
	}

	/**
	 * Gets the all user.
	 *
	 * @return the all user
	 */
	public List<User> getAllUser() {
		return UserRepository.userList;
	}

	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User findUser(Integer userId) {
		User resultUser = null;
		Optional<User> userOptional = UserRepository.userList.stream().filter(user -> user.getUserId() == userId)
				.findFirst();
		if (userOptional.isPresent()) {
			resultUser = userOptional.get();
		}
		return resultUser;
	}

	/**
	 * Save user.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User saveUser(User user) {
		if(UserRepository.userIncrementVar.get() == 5) {
			throw new RuntimeException("Failed to create User");
		}
		user.setUserId(UserRepository.userIncrementVar.incrementAndGet());
		UserRepository.userList.add(user);
		return user;
	}
	
	/**
	 * Delete user by id.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	public User deleteUserById(Integer userId) {
		User deletedUser = null;
		Iterator<User> userIterator = UserRepository.userList.iterator();
		while(userIterator.hasNext()) {
			User user = userIterator.next();
			if(user.getUserId() == userId) {
				deletedUser = user;
				userIterator.remove();
				break;
			}
		}
		return deletedUser;
	}
}
