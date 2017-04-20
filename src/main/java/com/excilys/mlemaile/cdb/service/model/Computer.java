package com.excilys.mlemaile.cdb.service.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * This class represent a computer as in the database.
 * @author Matthieu Lemaile
 */
public class Computer {
    private long      id;
    private String    name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company   company;

    /**
     * default empty constructor.
     */
    public Computer() {

    }

    /**
     * Complete constructor of the computer.
     * @param name name of the computer.
     * @param introduced The introduced date
     * @param discontinued The discontinued data
     * @param id The id.
     * @param company Sa company.
     */
    private Computer(String name, LocalDate introduced, LocalDate discontinued, long id,
            Company company) {
        this(name, introduced, discontinued, company);
        setId(id);
    }

    /**
     * Constructeur du computer.
     * @param name le nom du computer
     * @param introduced Sa date de mise sur le marché
     * @param discontinued Sa date d'arrêt
     * @param company The company
     */
    private Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
        setName(name);
        setIntroduced(introduced);
        setDiscontinued(discontinued);
        setCompany(company);
    }

    public long getId() {
        return id;
    }

    /**
     * setter, which check if it's greater than 0.
     * @param id The computer id
     */
    public void setId(long id) {
        if (id > 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException(
                    "The Id of a computer must be an integer greater than or equal to 1.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    /**
     * Setter of the discontinued date. Must be after the introduced date if set.
     * @param discontinued The new discontinued date
     */
    public void setDiscontinued(LocalDate discontinued) {
        // Pour pouvoir remettre à zéro cette propriété
        if (discontinued == null) {
            this.discontinued = discontinued;
        } else if (introduced != null
                && (discontinued.isAfter(introduced) || discontinued.isEqual(introduced))) {
            this.discontinued = discontinued;
        } else if (introduced == null) {
            this.discontinued = discontinued;
        } else {
            throw new IllegalArgumentException(
                    "The discontinued date must be after the introduced date.");
        }
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        if (getCompany() != null) {
            return "ID : " + getId() + " name : " + getName() + " manufacturer ["
                    + getCompany().toString() + "] introduced : " + getIntroduced()
                    + " Discontinued : " + getDiscontinued();
        } else {
            return "ID : " + getId() + " name : " + getName()
                    + " manufacturer [unknow] introduced : " + getIntroduced() + " Discontinued : "
                    + getDiscontinued();
        }
    }

    @Override
    public boolean equals(Object computer) {
        boolean equal = false;
        if (computer != null && computer instanceof Computer) {
            Computer c = (Computer) computer;
            boolean nameEqual = false;
            boolean introEqual = false;
            boolean discoEqual = false;
            boolean companyEqual = false;
            if ((name != null && name.equals(c.getName())) || name == null && c.getName() == null) {
                nameEqual = true;
            }
            if ((this.getIntroduced() != null && this.getIntroduced().equals(c.getIntroduced()))
                    || this.getIntroduced() == null && c.getIntroduced() == null) {
                introEqual = true;
            }
            if ((this.getDiscontinued() != null
                    && this.getDiscontinued().equals(c.getDiscontinued()))
                    || this.getDiscontinued() == null && c.getDiscontinued() == null) {
                discoEqual = true;
            }
            if ((this.getCompany() != null && this.getCompany().equals(c.getCompany())
                    || this.getCompany() == null && c.getCompany() == null)) {
                companyEqual = true;
            }
            if (nameEqual && introEqual && discoEqual && id == c.getId() && companyEqual) {
                equal = true;
            }
        }
        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, introduced, discontinued, company);
    }

    public static class Builder {
        private long      id;
        private LocalDate introduced;
        private LocalDate discontinued;
        private String    name;
        private Company   company;

        /**
         * Builder constructor. The name of the computer is required.
         * @param name The name of the computer
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * Setter of the computer id.
         * @param id the id of the computer.
         * @return The instance of the builder
         */
        public Builder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * setter of the introduced date.
         * @param introduced the introduced date
         * @return The instance of the builder
         */
        public Builder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Setter of the discontinued date.
         * @param discontinued The computer's discontinued date
         * @return The instance of the builder
         */
        public Builder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * The setter of the computer's company.
         * @param company The computer's company
         * @return The instance of the builder
         */
        public Builder company(Company company) {
            this.company = company;
            return this;
        }

        /**
         * Build the computer with given argument.
         * @return The new computer
         */
        public Computer build() {
            if (id != 0) {
                return new Computer(name, introduced, discontinued, id, company);
            } else {
                return new Computer(name, introduced, discontinued, company);
            }
        }
    }
}