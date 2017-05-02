package com.excilys.mlemaile.cdb.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.web.model.CompanyDto;
import com.excilys.mlemaile.cdb.web.model.ComputerDto;
import com.excilys.mlemaile.cdb.web.model.MapperDtoToModel;

/**
 * Servlet implementation class EditComputer.
 */
@Controller
@RequestMapping("/editComputer")
public class EditComputer {
    private static final String PARAM_COMPUTER_ID    = "computerId";
    private static final String ATT_COMPUTER      = "computerDto";
    private static final String ATT_COMPANIES        = "companies";
    private static final String ATT_EXCEPTION        = "exception";
    @Autowired
    private ServiceCompany        serviceCompany;
    @Autowired
    private ServiceComputer       serviceComputer;
    @Autowired
    private ComputerValidator   computerValidator;

    /**
     * Initialise the mapping of datas.
     * @param binder The binder of parameters
     */
    @InitBinder /* Converts empty strings into null when a form is submitted */
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.addValidators(computerValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayEditComputer(ModelMap model,
            @ModelAttribute("computerDto") ComputerDto computerDto,
            @RequestParam(value = PARAM_COMPUTER_ID, required = false) String computerIdStr) {
        Map<String, String> errors = new HashMap<>();
        try {
            long computerId = 0;
            if (computerIdStr != null) {
                computerId = Long.parseLong(computerIdStr);
            }
            Optional<Computer> optComputer = serviceComputer.getComputerById(computerId);
            if (optComputer.isPresent()) {
                ComputerDto c = MapperDtoToModel.modelToComputerDto(optComputer.get());
                computerDto = c;
                model.addAttribute(ATT_COMPUTER, c);
            }
            List<CompanyDto> companies = MapperDtoToModel
                    .modelListToCompanyDto(serviceCompany.listCompanies());
            model.addAttribute(ATT_COMPANIES, companies);
        } catch (NumberFormatException | ServiceException e) {
            errors.put("Error", e.getMessage());
            model.addAttribute(ATT_EXCEPTION, errors);
        }
        return "editComputer";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(@Valid @ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute(ATT_EXCEPTION, result.getAllErrors());
            return "editComputer";
        }
        serviceComputer.updatecomputer(MapperDtoToModel.computerDtoToModel(computerDto));
        return "redirect:/homepage";
    }
}
