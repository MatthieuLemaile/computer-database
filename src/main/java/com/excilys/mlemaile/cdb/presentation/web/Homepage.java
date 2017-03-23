package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.presentation.Page;
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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Homepage() {
        super();
    }

    @Override
    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
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
            Page.numberPerPage = limit;
            Page page = new Page(numPage);
            List<Computer> computers = ServiceComputer.INSTANCE.listComputer(Page.numberPerPage,
                    (page.getPageNumber() - 1) * Page.numberPerPage);
            request.setAttribute(ATT_LIST_COMPUTERS, computers);
            request.setAttribute(ATT_PAGE, page);
            request.setAttribute(TOTAL_NUMBER_COMPUTER, ServiceComputer.INSTANCE.countComputers());
            request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request,
                    response);
        } catch (NumberFormatException | ServiceException e) {
            request.setAttribute(ATT_EXCEPTION, e.getMessage());
            request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request,
                    response);
        }
    }

}
