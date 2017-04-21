package com.excilys.mlemaile.cdb.presentation.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.mlemaile.cdb.presentation.model.CompanyDto;
import com.excilys.mlemaile.cdb.presentation.model.ComputerDto;
import com.excilys.mlemaile.cdb.presentation.model.MapperDtoToModel;
import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.service.Validator;

/**
 * Servlet implementation class AddComputer.
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputer {
    private static final String   ATT_COMPANIES        = "companies";
    private static final String   ATT_EXCEPTION        = "exception";
    private static final String   PARAM_COMPUTER_NAME  = "computerName";
    private static final String   PARAM_COMPUTER_INTRO = "introduced";
    private static final String   PARAM_COMPUTER_DISCO = "discontinued";
    private static final String   PARAM_COMPANY_ID     = "companyId";
    @Autowired
    private ServiceCompany        serviceCompany;
    @Autowired
    private ServiceComputer       serviceComputer;

    public AddComputer() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayAddComputer(ModelMap model) {
        try {
            List<CompanyDto> companies = MapperDtoToModel
                    .modelListToCompanyDto(serviceCompany.listCompanies());
            model.addAttribute(ATT_COMPANIES, companies);
        } catch (ServiceException e) {
            model.addAttribute(ATT_EXCEPTION, e.getMessage());
        }
        return "addComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addComputer(ModelMap model,
            @RequestParam(value = PARAM_COMPUTER_NAME) String name,
            @RequestParam(value = PARAM_COMPUTER_INTRO, required = false) String introduced,
            @RequestParam(value = PARAM_COMPUTER_DISCO, required = false) String discontinued,
            @RequestParam(value = PARAM_COMPANY_ID, required = false) String companyId) {
        ComputerDto ce = new ComputerDto.Builder(name).introduced(introduced)
                .discontinued(discontinued).companyId(companyId).build();
        Map<String, String> errors = isValid(ce);
        if (errors.isEmpty()) {
            serviceComputer.createComputer(MapperDtoToModel.computerDtoToModel(ce));
            return "redirect:/homepage";
        } else {
            model.addAttribute(ATT_EXCEPTION, errors);
            return "addComputer";
        }
    }

    /**
     * Validate the request made by the user.
     * @param request the request issued by the client
     * @return a Map of errors
     */
    private Map<String, String> isValid(ComputerDto computer) {
        Map<String, String> errors = new HashMap<>();
        String nameValid = Validator.checkNameNotEmpty(computer.getName());
        if (nameValid != null) {
            errors.put(PARAM_COMPUTER_NAME, nameValid);
        }
        String companyIdValid = Validator.checkId(computer.getCompanyId());
        if (companyIdValid != null) {
            errors.put(PARAM_COMPANY_ID, companyIdValid);
        }
        String validDates = Validator.checkDateNotBeforeDate(
                computer.getDiscontinued(), computer.getIntroduced());
        if (validDates != null) {
            errors.put(PARAM_COMPUTER_DISCO, validDates);
        }
        return errors;
    }

}
