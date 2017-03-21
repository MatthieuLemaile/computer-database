package com.excilys.mlemaile.cdb.presentation;

public class Page<T> {
    public static int numberPerPage = 50;
    private int       pageNumber;

    /**
     * Page's constructor.
     * @param page The numero of the page
     */
    public Page(int page) {
        setPageNumber(page);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getNumberPerPage() {
        return numberPerPage;
    }

    /**
     * Change the page number. It must be greater than 0.
     * @param pageNumber the new page number.
     */
    public void setPageNumber(int pageNumber) {
        if (pageNumber > 0) {
            this.pageNumber = pageNumber;
        } else {
            throw new IllegalArgumentException("A page number must be positive");
        }
    }
}
