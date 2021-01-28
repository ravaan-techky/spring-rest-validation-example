package com.ravaan.techky.spring.dto;

import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {
	
	/** The user id. */
	private Integer userId;
	
	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/** The address. */
	private String address;

	/** The enroll date. */
	private Date enrollDate;
		
	/**
	 * Instantiates a new user.
	 *
	 * @param userId the user Id
	 * @param firstName the first name
	 * @param lastName  the last name
	 * @param address   the address
	 */
	public User(Integer userId, String firstName, String lastName, String address) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.enrollDate = new Date();
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
		
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
		
	/**
	 * Gets the enroll date.
	 *
	 * @return the enroll date
	 */
	public Date getEnrollDate() {
		return enrollDate;
	}

	/**
	 * Sets the enroll date.
	 *
	 * @param enrollDate the new enroll date
	 */
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + "]";
	}

}
