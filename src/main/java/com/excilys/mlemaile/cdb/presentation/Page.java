package com.excilys.mlemaile.cdb.presentation;

import com.excilys.mlemaile.cdb.persistence.FieldSort;

public class Page {
    private int       numberPerPage = 50;
    private int       pageNumber;
    private FieldSort sort;

    /**
     * Page's constructor.
     * @param page The numero of the page
     */
    public Page(int page) {
        setPageNumber(page);
        setSort(FieldSort.NAME);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getNumberPerPage() {
        return numberPerPage;
    }

    public void setNumberPerPage(int numberPerPage) {
        this.numberPerPage = numberPerPage;
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

    public FieldSort getSort() {
        return sort;
    }

    public void setSort(FieldSort sort) {
        this.sort = sort;
    }
}
