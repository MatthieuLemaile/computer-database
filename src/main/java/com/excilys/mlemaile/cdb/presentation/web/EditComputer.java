package com.excilys.mlemaile.cdb.presentation.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.excilys.mlemaile.cdb.service.model.Computer;

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
    public String doPost(@ModelAttribute("computerDto") ComputerDto computerDto,
            BindingResult result, ModelMap model) {
        Map<String, String> errors = isValid(computerDto);
        if (!errors.isEmpty()) {
            model.addAttribute(ATT_EXCEPTION, errors);
            return "editComputer";
        }
        serviceComputer.updatecomputer(MapperDtoToModel.computerDtoToModel(computerDto));
        return "redirect:/homepage";
    }

    /**
     * validate the computer sent by the user.
     * @param computer the computer to test
     * @return A Map containing errors
     */
    private Map<String, String> isValid(ComputerDto computer) {
        Map<String, String> errors = AddComputer.isValid(computer);
        String idValid = Validator.checkId(computer.getId());
        if (idValid != null) {
            errors.put(PARAM_COMPUTER_ID, idValid);
        }
        return errors;
    }

}
