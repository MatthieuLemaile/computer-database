package com.excilys.mlemaile.cdb.presentation.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * This class represent a computer as in the database.
 * @author Matthieu Lemaile
 */
public class ComputerDto {
    private static final String REGEX_DATE = "^(((((1[26]|2[048])00)|[12]\\d([2468][048]|[13579][26]|0[48]))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|[12]\\d))))|((([12]\\d([02468][1235679]|[13579][01345789]))|((1[1345789]|2[1235679])00))-((((0[13578]|1[02])-(0[1-9]|[12]\\d|3[01]))|((0[469]|11)-(0[1-9]|[12]\\d|30)))|(02-(0[1-9]|1\\d|2[0-8])))))$";

    @Pattern(regexp = "^[0-9]+$")
    private String id;
    @NotNull
    private String name;
    @Pattern(regexp = REGEX_DATE) // or null not after discontinued
    private String introduced;
    @Pattern(regexp = REGEX_DATE) // or null
    private String discontinued;
    private String companyName;
    @Pattern(regexp = "^[0-9]+$")
    private String companyId;

    /**
     * default empty constructor.
     */
    public ComputerDto() {
    };

    /**
     * Complete constructor of the computer.
     * @param name name of the computer.
     * @param introduced The introduced date
     * @param discontinued The discontinued data
     * @param id The id.
     * @param companyName The name of the company.
     * @param companyId The id of the company
     */
    private ComputerDto(String name, String introduced, String discontinued, String id,
            String companyName, String companyId) {
        setName(name);
        setIntroduced(introduced);
        setDiscontinued(discontinued);
        setCompanyName(companyName);
        setCompanyId(companyId);
        setId(id);
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ID : " + getId() + " name : " + getName() + " manufacturer [" + companyName + " "
                + companyId + "] introduced : " + getIntroduced() + " Discontinued : "
                + getDiscontinued();
    }

    public static class Builder {
        private String id;
        private String introduced;
        private String discontinued;
        private String name;
        private String companyName;
        private String companyId;

        /**
         * Builder constructor. The name of the computer is required.
         * @param name The name of the computer
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * Setter of the computer id.
         * @param id the id of the computer.Id
         * @return The instance of the builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * setter of the introduced date.
         * @param introduced the introduced date
         * @return The instance of the builder
         */
        public Builder introduced(String introduced) {
            this.introduced = introduced;
            return this;
        }

        /**
         * Setter of the discontinued date.
         * @param discontinued The computer's discontinued date
         * @return The instance of the builder
         */
        public Builder discontinued(String discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        /**
         * The setter of the computer's company name.
         * @param companyName The computer's company
         * @return The instance of the builder
         */
        public Builder company(String companyName) {
            this.companyName = companyName;
            return this;
        }

        /**
         * The setter of the computer's company id.
         * @param companyId the company's id
         * @return The instance of the builder
         */
        public Builder companyId(String companyId) {
            this.companyId = companyId;
            return this;
        }

        /**
         * Build the computer with given argument.
         * @return The new computer
         */
        public ComputerDto build() {

            return new ComputerDto(name, introduced, discontinued, id, companyName, companyId);
        }

    }
}