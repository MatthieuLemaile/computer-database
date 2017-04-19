package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet("/homepage")
public class Homepage extends HttpServlet {
    private static final long   serialVersionUID      = 1L;
    private static final String DASHBOARD_VIEW        = "/WEB-INF/views/dashboard.jsp";
    private static final String ATT_LIST_COMPUTERS    = "listComputers";
    private static final String ATT_PAGE              = "page";
    private static final String ATT_EXCEPTION         = "exception";
    private static final String TOTAL_NUMBER_COMPUTER = "totalNumberComputers";
    private static final String PARAM_PAGE_NUMBER     = "page";
    private static final String PARAM_PAGE_LIMIT      = "limit";
    private static final String PARAM_SORT            = "sort";
    private static final String PARAM_SEARCH          = "search";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Homepage() {
        super();
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> errors = isValid(request);
        for (String err : errors.values()) {
            System.out.println(err);
        }
        if (!errors.isEmpty()) {
            request.setAttribute(ATT_EXCEPTION, errors);
        } else {
            computeResponse(request);
        }
        request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request, response);
    }

    /**
     * Validate the request made by the user
     * @param request the request issued by the client
     * @return a Map of errors
     */
    private Map<String, String> isValid(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String pageNumberValid = Validator
                .checkPageNumberPositiveOrNull(request.getParameter(PARAM_PAGE_NUMBER));
        if (pageNumberValid != null) {
            errors.put("numPage", pageNumberValid);
        }
        String pageLimitValid = Validator
                .checkPageLimitPositiveOrNull(request.getParameter(PARAM_PAGE_LIMIT));
        if (pageLimitValid != null) {
            errors.put("pageLimit", pageLimitValid);
        }
        return errors;
    }

    /**
     * Compute the response to give to the user
     * @param request the request issued by the user
     */
    private void computeResponse(HttpServletRequest request) {
        int numPage = (request.getParameter(PARAM_PAGE_NUMBER) != null) ? Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER)) : 1;
        int limit = (request.getParameter(PARAM_PAGE_LIMIT) != null)
                ? Integer.parseInt(request.getParameter(PARAM_PAGE_LIMIT)) : 50;
        String search = request.getParameter(PARAM_SEARCH);

        Page page = new Page(numPage);
        page.setNumberPerPage(limit);
        setSort(page, request);
        request.setAttribute(ATT_LIST_COMPUTERS, listPageComputers(page, search));
        request.setAttribute(ATT_PAGE, page);
        request.setAttribute(PARAM_SEARCH, search);
        request.setAttribute(TOTAL_NUMBER_COMPUTER,
                ServiceComputer.INSTANCE.countComputers(search));
    }

    /**
     * Return the computers to display for the given page and the given search
     * @param page the page of asked computers
     * @param search a String that computers name have to contains
     * @return the List of ComputerDto matching the request
     */
    private List<ComputerDto> listPageComputers(Page page, String search) {
        int startElementNumber = (page.getPageNumber() - 1) * page.getNumberPerPage();
        List<Computer> computers = ServiceComputer.INSTANCE.listSortSearchNumberComputer(
                page.getNumberPerPage(), startElementNumber, page.getSort(), search);
        return MapperDtoToModel.modelListToComputerDto(computers);
    }

    /**
     * This function set the sort parameter of the given page according to the request's parameter.
     * @param page the page to set
     * @param request the request containing data's
     */
    private void setSort(Page page, HttpServletRequest request) {
        String field = request.getParameter(PARAM_SORT);
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
