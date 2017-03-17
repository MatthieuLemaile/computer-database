package com.excilys.mlemaile.cdb.presentation.cli;

public class Page<T> {
    public static int numberPerPage = 50;
    private int       pageNumber;

    /**
     * Constructeur de page, avec le numero de page.
     * @param page numero de la page créé.
     */
    public Page(int page) {
        setPageNumber(page);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Change le numéro de page. Ce dernier doit être supérieur à 0.
     * @param pageNumber le numéro de page.
     */
    public void setPageNumber(int pageNumber) {
        if (pageNumber > 0) {
            this.pageNumber = pageNumber;
        } else {
            throw new IllegalArgumentException("A page number must be positive");
        }
    }
}
