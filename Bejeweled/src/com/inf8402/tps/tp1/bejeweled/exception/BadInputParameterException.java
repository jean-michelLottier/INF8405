package com.inf8402.tps.tp1.bejeweled.exception;

public class BadInputParameterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6435685303820851229L;

	public BadInputParameterException() {
		super();
	}

	public BadInputParameterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BadInputParameterException(String arg0) {
		super(arg0);
	}

	public BadInputParameterException(Throwable arg0) {
		super(arg0);
	}
}
