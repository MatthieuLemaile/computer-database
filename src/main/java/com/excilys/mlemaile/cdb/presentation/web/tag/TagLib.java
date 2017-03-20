package com.excilys.mlemaile.cdb.presentation.web.tag;

import javax.servlet.jsp.tagext.TagSupport;

public class TagLib extends TagSupport {
    private static final long serialVersionUID = 1L;

    /**
     * Cette fonction retourne l'url vers la page demandé avec le nombre d'élément demandé.
     * @param target l'url de la page demandé
     * @param numPage le numéro de la page
     * @param limit la nombre d'élément sur la page
     * @return l'url vers la page
     */
    public static String link(String target, int numPage, int limit) {
        return "/" + target + "?page=" + numPage + "&limit=" + limit;
    }
}
