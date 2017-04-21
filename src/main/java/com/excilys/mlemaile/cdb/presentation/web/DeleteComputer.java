package com.excilys.mlemaile.cdb.presentation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;

/**
 * Servlet implementation class DeleteComputer.
 */
@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputer {
    private static final String PARAM_ID_LIST    = "selection";
    private static final String ATT_EXCEPTION    = "exception";
    @Autowired
    private ServiceComputer       serviceComputer;

    public DeleteComputer() {
        super();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String deleteComputer(ModelMap model,
            @RequestParam(value = PARAM_ID_LIST) String idList) {
        String[] ids = idList.split(",");
        try {
            for (String idStr : ids) {
                long id = Long.parseLong(idStr);
                serviceComputer.deleteComputer(id);
            }
            return "redirect:/homepage";
        } catch (NumberFormatException | ServiceException e) {
            model.addAttribute(ATT_EXCEPTION, e.getMessage());
            return "dashboard";
        }
    }

}
