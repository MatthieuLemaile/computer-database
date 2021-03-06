package com.excilys.mlemaile.cdb.web;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.excilys.mlemaile.cdb.dto.CompanyDto;
import com.excilys.mlemaile.cdb.dto.ComputerDto;
import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.web.model.ComputerValidator;
import com.excilys.mlemaile.cdb.web.model.MapperDtoToModel;

/**
 * Servlet implementation class AddComputer.
 */
@Controller
@RequestMapping("/addComputer")
public class AddComputer {
    private static final String ATT_COMPANIES = "companies";
    private static final String ATT_EXCEPTION = "exception";
    private static final Logger LOGGER        = LoggerFactory.getLogger(AddComputer.class);
    @Autowired
    private ServiceCompany      serviceCompany;
    @Autowired
    private ServiceComputer     serviceComputer;
    @Autowired
    private ComputerValidator   computerValidator;

    /**
     * Converts empty strings into null when a form is submitted.
     * @param binder The binder of parameters
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(computerValidator);
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
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("errors ? : " + result.hasErrors());
            LOGGER.debug("Computer : " + computerDto);
        }
        if (result.hasErrors()) {
            model.addAttribute(ATT_EXCEPTION, result.getAllErrors());
            return "addComputer";
        }
        serviceComputer.createComputer(MapperDtoToModel.computerDtoToModel(computerDto));
        return "redirect:/homepage";
    }
}
