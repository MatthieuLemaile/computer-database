package com.excilys.mlemaile.cdb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.mlemaile.cdb.dto.CompanyDto;
import com.excilys.mlemaile.cdb.model.Company;
import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.web.model.MapperDtoToModel;

@RestController
public class RestEndPointControllerCompany {

    @Autowired
    private ServiceCompany serviceCompany;

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDto>> listCompanies() {
        List<Company> companies = serviceCompany.listCompanies();
        if (companies.isEmpty()) {
            return new ResponseEntity<List<CompanyDto>>(HttpStatus.NO_CONTENT);
        }
        List<CompanyDto> companiesDto = MapperDtoToModel.modelListToCompanyDto(companies);
        return new ResponseEntity<List<CompanyDto>>(companiesDto, HttpStatus.OK);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable("id") long id) {
        Company company = serviceCompany.getCompanyById(id);
        if (company != null) {
            return new ResponseEntity<CompanyDto>(MapperDtoToModel.modelToCompanyDto(company),
                    HttpStatus.OK);
        }
        return new ResponseEntity<CompanyDto>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Long> deleteCompany(@PathVariable Long id) {
        try {
            serviceCompany.deleteCompany(id);
            return new ResponseEntity<Long>(id, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
        }
    }
}