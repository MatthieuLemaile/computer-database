package com.excilys.mlemaile.cdb.presentation.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.mlemaile.cdb.persistence.FieldSort;
import com.excilys.mlemaile.cdb.presentation.Page;
import com.excilys.mlemaile.cdb.presentation.model.ComputerDto;
import com.excilys.mlemaile.cdb.presentation.model.MapperDtoToModel;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.Validator;
import com.excilys.mlemaile.cdb.service.model.Computer;

/**
 * Servlet implementation class homepage.
 */
@Controller
@RequestMapping("/homepage")
public class Homepage {
    private static final Logger LOGGER                = LoggerFactory.getLogger(Homepage.class);
    private static final String ATT_LIST_COMPUTERS    = "listComputers";
    private static final String ATT_PAGE              = "page";
    private static final String ATT_EXCEPTION         = "exception";
    private static final String TOTAL_NUMBER_COMPUTER = "totalNumberComputers";
    private static final String PARAM_PAGE_NUMBER     = "page";
    private static final String PARAM_PAGE_LIMIT      = "limit";
    private static final String PARAM_SORT            = "sort";
    private static final String PARAM_SEARCH          = "search";
    @Autowired
    private ServiceComputer       serviceComputer;

    public Homepage() {
        super();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String displayHomepage(ModelMap model,
            @RequestParam(value = PARAM_PAGE_NUMBER, required = false) String pageNumber,
            @RequestParam(value = PARAM_PAGE_LIMIT, required = false) String pageLimit,
            @RequestParam(value = PARAM_SEARCH, required = false) String search,
            @RequestParam(value=PARAM_SORT,required=false) String sort){
        Map<String, String> errors = isValid(pageNumber, pageLimit);
        if (!errors.isEmpty()) {
            model.addAttribute(ATT_EXCEPTION, errors);
        } else {
            computeResponse(model, pageNumber, pageLimit, search, sort);
        }
        return "dashboard";
    }

    /**
     * Validate the request made by the user.
     * @param request the request issued by the client
     * @return a Map of errors
     */
    private Map<String, String> isValid(String pageNumber, String pageLimit) {
        Map<String, String> errors = new HashMap<>();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Page Number : " + pageNumber + "\t" + "Page Limit : " + pageLimit);
        }
        String pageNumberValid = Validator
                .checkPageNumberPositiveOrNull(pageNumber);
        if (pageNumberValid != null) {
            errors.put("numPage", pageNumberValid);
        }
        String pageLimitValid = Validator
                .checkPageLimitPositiveOrNull(pageLimit);
        if (pageLimitValid != null) {
            errors.put("pageLimit", pageLimitValid);
        }
        return errors;
    }

    /**
     * Compute the response to give to the user.
     * @param request the request issued by the user
     */
    private void computeResponse(ModelMap model,String pageNumber, String pageLimit,String search,String sort) {
        int numPage = (pageNumber != null) ? Integer.parseInt(pageNumber) : 1;
        int limit = (pageLimit != null) ? Integer.parseInt(pageLimit) : 50;

        Page page = new Page(numPage);
        page.setNumberPerPage(limit);
        setSort(page, sort);
        model.addAttribute(ATT_LIST_COMPUTERS, listPageComputers(page, search));
        model.addAttribute(ATT_PAGE, page);
        model.addAttribute(PARAM_SEARCH, search);
        model.addAttribute(TOTAL_NUMBER_COMPUTER, serviceComputer.countComputers(search));
    }

    /**
     * Return the computers to display for the given page and the given search.
     * @param page the page of asked computers
     * @param search a String that computers name have to contains
     * @return the List of ComputerDto matching the request
     */
    private List<ComputerDto> listPageComputers(Page page, String search) {
        int startElementNumber = (page.getPageNumber() - 1) * page.getNumberPerPage();
        List<Computer> computers = serviceComputer.listSortSearchNumberComputer(
                page.getNumberPerPage(), startElementNumber, page.getSort(), search);
        return MapperDtoToModel.modelListToComputerDto(computers);
    }

    /**
     * This function set the sort parameter of the given page according to the request's parameter.
     * @param page the page to set
     * @param request the request containing data's
     */
    private void setSort(Page page, String field) {
        if (field != null && !field.trim().isEmpty()) {
            switch (field) {
            case "name":
                page.setSort(FieldSort.NAME);
                break;
            case "introduced":
                page.setSort(FieldSort.INTRODUCED);
                break;
            case "discontinued":
                page.setSort(FieldSort.DISCONTINUED);
                break;
            case "companyName":
                page.setSort(FieldSort.COMPANY_NAME);
                break;
            }
        }
    }
}
