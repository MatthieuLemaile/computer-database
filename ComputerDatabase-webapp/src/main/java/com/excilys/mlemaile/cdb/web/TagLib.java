package com.excilys.mlemaile.cdb.web;

import javax.servlet.jsp.tagext.TagSupport;

public class TagLib extends TagSupport {
    private static final long serialVersionUID = 1L;

    /**
     * This function return the url to the asked page with limit number of element per page.
     * @param target The url of the asked page
     * @param pageNumber The numero of the page
     * @param limit The number of element per page
     * @return The url of the page
     */
    public static String link(String target, int pageNumber, int limit) {
        return "/" + target + "?page=" + pageNumber + "&limit=" + limit;
    }

    /**
     * This function return the url to the page.
     * @param pageNumber the numero of the page
     * @param limit the number of element per page
     * @return The url to the asked url
     */
    public static String pagination(int pageNumber, int limit) {
        return "/homepage?page=" + pageNumber + "&limit=" + limit;
    }
}
