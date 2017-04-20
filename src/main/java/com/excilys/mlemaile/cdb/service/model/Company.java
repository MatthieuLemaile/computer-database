package com.excilys.mlemaile.cdb.service.model;

import java.util.Objects;

/**
 * This class represent a company as in database.
 * @author Matthieu Lemaile
 *
 */
public class Company {
    private long   id;
    private String name;

    /**
     * default empty constructor.
     */
    public Company() {

    }

    /**
     * Constructeur paramétré.
     * @param id l'id de la company.
     * @param name le nom de la compagnie.
     */
    private Company(long id, String name) {
        setId(id);
        setName(name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object o) {
        boolean equal = false;
        if (o != null && o instanceof Company) {
            Company company = (Company) o;
            boolean nameEqual = false;
            if (this.name != null && this.name.equals(company.name) || (this.name == null && company.name == null)) {
                nameEqual = true;
            }
            if (this.id == company.id && nameEqual) {
                equal = true;
            }
        }
        return equal;
    }

    public static class Builder {
        private long   id;
        private String name;

        /**
         * Choisit un id pour la company à créer.
         * @param id id de la company à céer.
         * @return l'instance du builder.
         */
        public Builder id(long id) {
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
        public Company build() {
            return new Company(id, name);
        }
    }
}
