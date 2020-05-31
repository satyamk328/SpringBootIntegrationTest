package com.test.exception;

/**
 * @author Sandeep
 *
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public BusinessException() {
	}

	/**
	 * @param arg0
	 */
	public BusinessException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public BusinessException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public BusinessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
