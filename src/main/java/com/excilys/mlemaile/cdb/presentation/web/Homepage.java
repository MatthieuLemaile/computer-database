package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.util.List;

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
import com.excilys.mlemaile.cdb.service.ServiceException;

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
        try {
            int numPage = 1;
            if (request.getParameter(PARAM_PAGE_NUMBER) != null) {
                numPage = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));
            }
            int limit = 50;
            if (request.getParameter(PARAM_PAGE_LIMIT) != null) {
                limit = Integer.parseInt(request.getParameter(PARAM_PAGE_LIMIT));
            }
            String search = null;
            if (request.getParameter(PARAM_SEARCH) != null) {
                search = request.getParameter(PARAM_SEARCH);
            }
            Page.numberPerPage = limit;
            Page page = new Page(numPage);
            setSort(page, request);
            List<ComputerDto> computers = MapperDtoToModel.INSTANCE.modelListToComputerDto(
                    ServiceComputer.INSTANCE.listComputer(Page.numberPerPage,
                            (page.getPageNumber() - 1) * Page.numberPerPage, page.getSort(),
                            search));
            request.setAttribute(ATT_LIST_COMPUTERS, computers);
            request.setAttribute(ATT_PAGE, page);
            request.setAttribute(PARAM_SEARCH, search);
            request.setAttribute(TOTAL_NUMBER_COMPUTER,
                    ServiceComputer.INSTANCE.countComputers(search));
            request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request,
                    response);
        } catch (NumberFormatException | ServiceException e) {
            request.setAttribute(ATT_EXCEPTION, e.getMessage());
            request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request,
                    response);
        }
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
