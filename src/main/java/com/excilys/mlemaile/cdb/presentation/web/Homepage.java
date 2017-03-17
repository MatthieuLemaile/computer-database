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
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Homepage() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Page<Computer> page = new Page<Computer>(1);
        List<Computer> computers = ServiceComputer.INSTANCE.listComputer(Page.numberPerPage, 0);
        request.setAttribute("listComputers", computers);
        request.setAttribute("coyote", "bip");
        request.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
    }

    @Override
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
