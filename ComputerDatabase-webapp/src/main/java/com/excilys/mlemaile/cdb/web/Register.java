package com.excilys.mlemaile.cdb.web;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.mlemaile.cdb.web.model.UserDto;

@Controller
public class Register {

    /**
     * Converts empty strings into null when a form is submitted.
     * @param binder The binder of parameters
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String displayView(@ModelAttribute("userDto") UserDto userDto, BindingResult result) {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap model) {
        // récupérer le user du formulaire (as homepage)
        // valider nom != empty && same pass
        // hash le pass
        // persister le user avec le rôle par défaut
        return "dashboard";
    }
}
