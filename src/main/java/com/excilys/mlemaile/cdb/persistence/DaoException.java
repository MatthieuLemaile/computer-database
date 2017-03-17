package com.excilys.mlemaile.cdb.persistence;

/**
 * This exception might be thrown when an exception occur in the Dao.
 * @author Matthieu Lemaile
 */
public class DaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de l'erreur.
     * @param message message de l'erreur.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructeur de l'erreur.
     * @param message message de l'erreur
     * @param e l'erreur générant cette erreur.
     */
    public DaoException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Constructeur de l'erreur.
     * @param e l'erreur générant cette erreur.
     */
    public DaoException(Throwable e) {
        super(e);
    }

}
