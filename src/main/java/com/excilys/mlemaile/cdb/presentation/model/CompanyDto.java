package com.excilys.mlemaile.cdb.presentation.model;

/**
 * This class represent a company as in database.
 * @author Matthieu Lemaile
 *
 */
public class CompanyDto {
    private String   id;
    private String name;

    /**
     * Constructeur par défaut.
     */
    private CompanyDto() {
    }

    /**
     * Constructeur paramétré.
     * @param id l'id de la company.
     * @param name le nom de la compagnie.
     */
    private CompanyDto(String id, String name) {
        setId(id);
        setName(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID : " + getId() + " name : " + getName();
    }

    public static class Builder {
        private String   id;
        private String name;

        /**
         * Choisit un id pour la company à créer.
         * @param id id de la company à céer.
         * @return l'instance du builder.
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * choisit un nom pour la company à créer.
         * @param name nom de la company à créer.
         * @return retourne l'instance du builder.
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Construit et retourne la Company avec les paramètres entrés
         * précédemment.
         * @return l'instance de la company
         */
        public CompanyDto build() {
            return new CompanyDto(id, name);
        }
    }
}
