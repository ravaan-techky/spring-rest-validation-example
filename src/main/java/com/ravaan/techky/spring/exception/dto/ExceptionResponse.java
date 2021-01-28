package com.ravaan.techky.spring.exception.dto;

import java.util.Date;

/**
 * The Class ExceptionResponse.
 */
public class ExceptionResponse {

	/** The message. */
	private String message;

	/** The details. */
	private String details;

	/** The date. */
	private Date date;

	/** The support information. */
	private String supportInformation;
	

	/**
	 * Instantiates a new exception response.
	 *
	 * @param message the message
	 * @param details the details
	 */
	public ExceptionResponse(String message, String details) {
		super();
		this.message = message;
		this.details = details;
		this.date = new Date();
		this.supportInformation = "Please contact to support team. Email - support@gmail.com OR Toll free number - 1800 100 1800";
	}

	/**
	 * Instantiates a new exception response.
	 *
	 * @param message the message
	 * @param details the details
	 * @param isSupportInformationRequired the is support information required
	 */
	public ExceptionResponse(String message, String details, Boolean isSupportInformationRequired) {
		super();
		this.message = message;
		this.details = details;
		this.date = new Date();
		if(isSupportInformationRequired) {
			this.supportInformation = "Please contact to support team. Email - support@gmail.com OR Toll free number - 1800 100 1800";
		}
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details the new details
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the support information.
	 *
	 * @return the support information
	 */
	public String getSupportInformation() {
		return supportInformation;
	}

	/**
	 * Sets the support information.
	 *
	 * @param supportInformation the new support information
	 */
	public void setSupportInformation(String supportInformation) {
		this.supportInformation = supportInformation;
	}
}
