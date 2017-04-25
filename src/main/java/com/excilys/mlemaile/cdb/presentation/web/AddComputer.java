package com.excilys.mlemaile.cdb.presentation.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    private static final String   PARAM_COMPUTER_DISCO = "discontinued";
    private static final String   PARAM_COMPANY_ID     = "companyId";
    @Autowired
    private ServiceCompany        serviceCompany;
    @Autowired
    private ServiceComputer       serviceComputer;

    @InitBinder /* Converts empty strings into null when a form is submitted */
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayAddComputer(@ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult result, ModelMap model) {
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
    public String addComputer(@Valid @ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult result, ModelMap model) {
        System.out.println(computerDto);
        if (result.hasErrors()) {
            model.addAttribute(ATT_EXCEPTION, result.getAllErrors());
            return "addComputer";
        }
        // Map<String, String> errors = isValid(computerDto);
        // if (!errors.isEmpty()) {
        // model.addAttribute(ATT_EXCEPTION, errors);
        // return "addComputer";
        // }
        serviceComputer.createComputer(MapperDtoToModel.computerDtoToModel(computerDto));
        return "redirect:/homepage";
    }

    /**
     * Validate the computer sent by the user.
     * @param computer the computer to test
     * @return a Map of errors
     */
    public static Map<String, String> isValid(ComputerDto computer) {
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
