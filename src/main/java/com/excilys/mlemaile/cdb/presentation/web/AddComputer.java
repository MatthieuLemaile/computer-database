package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mlemaile.cdb.service.ServiceComputer;

/**
 * Servlet implementation class AddComputer.
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
    private static final long   serialVersionUID     = 1L;
    private static final String ADD_COMPUTER_VIEW    = "/WEB-INF/views/addComputer.jsp";
    private static final String PARAM_COMPUTER_NAME  = "computerName";
    private static final String PARAM_COMPUTER_INTRO = "introduced";
    private static final String PARAM_COMPUTER_DISCO = "discontinued";
    private static final String PARAM_COMPANY_ID     = "companyId";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher(ADD_COMPUTER_VIEW).forward(request,
                response);
    }

    @Override
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = "";
        if (request.getParameter(PARAM_COMPUTER_NAME) != null) {
            name = request.getParameter(PARAM_COMPUTER_NAME);
        }
        LocalDate introduced = null;
        if (request.getParameter(PARAM_COMPUTER_INTRO) != null
                && !request.getParameter(PARAM_COMPUTER_INTRO).trim().isEmpty()) {
            introduced = LocalDate.parse(request.getParameter(PARAM_COMPUTER_INTRO));
        }
        LocalDate discontinued = null;
        if (request.getParameter(PARAM_COMPUTER_DISCO) != null
                && !request.getParameter(PARAM_COMPUTER_DISCO).trim().isEmpty()) {
            discontinued = LocalDate.parse(request.getParameter(PARAM_COMPUTER_DISCO));
        }
        long companyId = 0;
        if (request.getParameter(PARAM_COMPANY_ID) != null) {
            companyId = Long.parseLong(request.getParameter(PARAM_COMPANY_ID));
        }
        if (ServiceComputer.INSTANCE.createComputer(name, introduced, discontinued, companyId)) {
            response.sendRedirect(getServletContext().getContextPath() + "/homepage");
        } else {
            request.getServletContext().getRequestDispatcher(ADD_COMPUTER_VIEW).forward(request,
                    response);
            //TODO Warn the user of the error
        }

    }

}
