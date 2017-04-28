package com.excilys.mlemaile.cdb.service;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Exception constructor.
     * @param message Exception message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Exception constructor.
     * @param message Exception message
     * @param e root Exception
     */
    public ServiceException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Exception constructor.
     * @param e root Exception
     */
    public ServiceException(Throwable e) {
        super(e);
    }
}
