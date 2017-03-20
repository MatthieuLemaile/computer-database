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
    private static final long   serialVersionUID = 1L;
    private static final String DASHBOARD_VIEW   = "/WEB-INF/views/dashboard.jsp";
    private static final String LIST_COMPUTERS   = "listComputers";
    private static final String PAGE             = "page";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Homepage() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO sécuriser les input pour éviter XSS
        int numPage = 1;
        if (request.getParameter("page") != null) {
            numPage = Integer.parseInt(request.getParameter("page"));
        }
        int limit = 50;
        if (request.getParameter("limit") != null) {
            limit = Integer.parseInt(request.getParameter("limit"));
        }
        Page.numberPerPage = limit;
        Page<Computer> page = new Page<>(numPage);
        List<Computer> computers = ServiceComputer.INSTANCE.listComputer(Page.numberPerPage,
                (page.getPageNumber() - 1) * Page.numberPerPage);
        request.setAttribute(LIST_COMPUTERS, computers);
        request.setAttribute(PAGE, page);
        request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request, response);
    }

    @Override
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
