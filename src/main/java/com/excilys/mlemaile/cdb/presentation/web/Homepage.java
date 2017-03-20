package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.presentation.cli.Page;
import com.excilys.mlemaile.cdb.service.ServiceComputer;

/**
 * Servlet implementation class homepage.
 */
@WebServlet("/homepage")
public class Homepage extends HttpServlet {
    private static final long   serialVersionUID      = 1L;
    private static final String DASHBOARD_VIEW        = "/WEB-INF/views/dashboard.jsp";
    private static final String LIST_COMPUTERS        = "listComputers";
    private static final String PAGE                  = "page";
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
        // TODO sécuriser les input pour éviter XSS
        int numPage = 1;
        if (request.getParameter(PARAM_PAGE_NUMBER) != null) {
            numPage = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));
        }
        int limit = 50;
        if (request.getParameter(PARAM_PAGE_LIMIT) != null) {
            limit = Integer.parseInt(request.getParameter(PARAM_PAGE_LIMIT));
        }
        Page.numberPerPage = limit;
        Page<Computer> page = new Page<>(numPage);
        List<Computer> computers = ServiceComputer.INSTANCE.listComputer(Page.numberPerPage,
                (page.getPageNumber() - 1) * Page.numberPerPage);
        request.setAttribute(LIST_COMPUTERS, computers);
        request.setAttribute(PAGE, page);
        request.setAttribute(TOTAL_NUMBER_COMPUTER, ServiceComputer.INSTANCE.countComputers());
        request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request, response);
    }

}
