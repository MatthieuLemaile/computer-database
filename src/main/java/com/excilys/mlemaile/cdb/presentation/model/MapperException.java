package com.excilys.mlemaile.cdb.presentation.model;

public class MapperException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Exception constructor.
     * @param message Exception message
     */
    public MapperException(String message) {
        super(message);
    }

    /**
     * Exception constructor.
     * @param message Exception message
     * @param e root Exception
     */
    public MapperException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Exception constructor.
     * @param e root Exception
     */
    public MapperException(Throwable e) {
        super(e);
    }
}
