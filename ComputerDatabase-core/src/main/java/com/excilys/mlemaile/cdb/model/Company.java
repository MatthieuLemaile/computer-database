package com.excilys.mlemaile.cdb.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represent a company as in database.
 * @author Matthieu Lemaile
 *
 */
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public Company(long id, String name) {
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
}