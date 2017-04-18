package com.excilys.mlemaile.cdb.presentation.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.model.Company;
import com.excilys.mlemaile.cdb.service.model.Computer;

/**
 * This class contains mapping functions for dto <-> model conversion.
 * @author Matthieu Lemaile
 *
 */
public class MapperDtoToModel {

    /**
     * Mapp a computer to a ComputerDto.
     * @param c the computer to map
     * @return the computerDto mapped
     */
    public static ComputerDto modelToComputerDto(Computer c) {
        String introStr = "";
        String discoStr = "";
        if (c.getIntroduced() != null) {
            introStr = c.getIntroduced().toString();
        }
        if (c.getDiscontinued() != null) {
            discoStr = c.getDiscontinued().toString();
        }

        return new ComputerDto.Builder(c.getName()).company(c.getCompany().getName())
                .companyId(Long.toString(c.getCompany().getId())).introduced(introStr)
                .discontinued(discoStr).id(Long.toString(c.getId())).build();
    }

    /**
     * map a ComputerDto to a Computer.
     * @param ce the ComputerDto to map
     * @return the computer mapped
     */
    public static Computer computerDtoToModel(ComputerDto ce) {
        LocalDate introduced = null;
        LocalDate discontinued = null;
        long companyId = 0;
        long id = 0;
        try {
            if (ce.getIntroduced() != null && !ce.getIntroduced().trim().isEmpty()) {
                introduced = LocalDate.parse(ce.getIntroduced());
            }
            if (ce.getDiscontinued() != null && !ce.getDiscontinued().trim().isEmpty()) {
                discontinued = LocalDate.parse(ce.getDiscontinued());
            }
            if (ce.getCompanyId() != null) {
                companyId = Long.parseLong(ce.getCompanyId());
            }
            if (ce.getId() != null) {
                id = Long.parseLong(ce.getId());
            }
        } catch (NumberFormatException e) {
            throw new MapperException(e.getMessage());
        } catch (DateTimeParseException e) {
            throw new MapperException("The date must follow the pattern : yyyy-mm-dd");
        }
        Company company = null;
        if (companyId > 0) {
            company = ServiceCompany.INSTANCE.getCompanyById(companyId);
        }

        return new Computer.Builder(ce.getName()).company(company).id(id).introduced(introduced)
                .discontinued(discontinued).build();
    }

    /**
     * Map a list of Computer to a List of ComputerDto.
     * @param computers the List to map
     * @return The List mapped
     */
    public static List<ComputerDto> modelListToComputerDto(List<Computer> computers) {
        List<ComputerDto> computersEdit = new ArrayList<>();
        for (Computer c : computers) {
            computersEdit.add(modelToComputerDto(c));
        }
        return computersEdit;
    }

    /**
     * Map a Company to a CompanyDto.
     * @param c the Company to map
     * @return the CompanyDto mapped
     */
    public static CompanyDto modelToCompanyDto(Company c) {
        return new CompanyDto.Builder().id(Long.toString(c.getId())).name(c.getName()).build();
    }

    /**
     * Map a list of Company to a List of CompanyDto.
     * @param companies the List of Company to map
     * @return The List of CompanyDto mapped
     */
    public static List<CompanyDto> modelListToCompanyDto(List<Company> companies) {
        List<CompanyDto> companiesDto = new ArrayList<>();
        for (Company c : companies) {
            companiesDto.add(modelToCompanyDto(c));
        }
        return companiesDto;
    }
}
