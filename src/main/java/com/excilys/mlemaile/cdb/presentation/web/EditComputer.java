package com.excilys.mlemaile.cdb.presentation.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.mlemaile.cdb.presentation.model.CompanyDto;
import com.excilys.mlemaile.cdb.presentation.model.ComputerDto;
import com.excilys.mlemaile.cdb.presentation.model.MapperDtoToModel;
import com.excilys.mlemaile.cdb.presentation.model.MapperException;
import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.service.Validator;
import com.excilys.mlemaile.cdb.service.model.Computer;

/**
 * Servlet implementation class EditComputer.
 */
@Controller
@RequestMapping("/editComputer")
public class EditComputer {
    private static final String PARAM_COMPUTER_ID    = "computerId";
    private static final String ATT_COMPUTER         = "computer";
    private static final String ATT_COMPANIES        = "companies";
    private static final String ATT_EXCEPTION        = "exception";
    private static final String PARAM_COMPUTER_NAME  = "computerName";
    private static final String PARAM_COMPUTER_INTRO = "introduced";
    private static final String PARAM_COMPUTER_DISCO = "discontinued";
    private static final String PARAM_COMPANY_ID     = "companyId";
    @Autowired
    private ServiceCompany        serviceCompany;
    @Autowired
    private ServiceComputer       serviceComputer;

    public EditComputer() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayEditComputer(ModelMap model,
            @RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerIdStr) {
        try {
            long computerId = 0;
            if (computerIdStr != null) {
                computerId = Long.parseLong(computerIdStr);
            }
            Optional<Computer> optComputer = serviceComputer.getComputerById(computerId);
            if (optComputer.isPresent()) {
                ComputerDto c = MapperDtoToModel.modelToComputerDto(optComputer.get());
                model.addAttribute(ATT_COMPUTER, c);
            }
            List<CompanyDto> companies = MapperDtoToModel
                    .modelListToCompanyDto(serviceCompany.listCompanies());
            model.addAttribute(ATT_COMPANIES, companies);
        } catch (NumberFormatException | ServiceException e) {
            model.addAttribute(ATT_EXCEPTION, e.getMessage());
        }
        return "editComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(ModelMap model, @RequestParam(value = PARAM_COMPUTER_ID) String id,
            @RequestParam(value = PARAM_COMPUTER_NAME) String name,
            @RequestParam(value = PARAM_COMPANY_ID, required = false) String companyId,
            @RequestParam(value = PARAM_COMPUTER_INTRO, required = false) String introduced,
            @RequestParam(value = PARAM_COMPUTER_DISCO, required = false) String discontinued) {
        Validator.checkId(id);
        Validator.checkId(companyId);
        Validator.checkDate(introduced);
        Validator.checkDate(discontinued);
        Validator.checkDateNotBeforeDate(discontinued, introduced);
        ComputerDto ce = new ComputerDto.Builder(name).introduced(introduced)
                .discontinued(discontinued).companyId(companyId).id(id).build();
        try {
            serviceComputer.updatecomputer(MapperDtoToModel.computerDtoToModel(ce));
            return "redirect:/homepage";
        } catch (MapperException | ServiceException e) {
            model.addAttribute(ATT_EXCEPTION, e.getMessage());
            return "editComputer";
        }
    }

}
