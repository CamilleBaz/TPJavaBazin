package com.epf.rentmanager.exception;

public class ServiceException extends Exception { 
    private static final long serialVersionUID = 2L;
    public ServiceException() {    
    }
    public ServiceException(String message) {
        super(message);
    }
}