package com.excilys.mlemaile.cdb.persistence;

/**
 * This exception might be thrown when an exception occur in the Dao
 * @author Matthieu Lemaile
 *
 */
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DaoException(String message){
		super(message);
	}
	
	public DaoException(String message,Throwable e){
		super(message,e);
	}
	
	public DaoException(Throwable e){
		super(e);
	}

}
