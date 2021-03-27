package com.epf.rentmanager.exception;

public class DaoException extends Exception {
	private static final long serialVersionUID = 2L;
	//le constructeur
	public DaoException() {
	}
	    public DaoException(String message) {
			super(message);	
	}

}
