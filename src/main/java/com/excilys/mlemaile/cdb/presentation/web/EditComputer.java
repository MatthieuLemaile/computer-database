package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.service.ServiceComputer;

/**
 * Servlet implementation class EditComputer.
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
    private static final long   serialVersionUID     = 1L;
    private static final String PARAM_COMPUTER_ID    = "computerId";
    private static final String ATT_COMPUTER_ID      = "computer";
    private static final String EDIT_COMPUTER_VIEW   = "/WEB-INF/views/editComputer.jsp";
    private static final String PARAM_COMPUTER_NAME  = "computerName";
    private static final String PARAM_COMPUTER_INTRO = "introduced";
    private static final String PARAM_COMPUTER_DISCO = "discontinued";
    private static final String PARAM_COMPANY_ID     = "companyId";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long computerId = 0;
        if (request.getParameter(PARAM_COMPUTER_ID) != null) {
            computerId = Long.parseLong(request.getParameter(PARAM_COMPUTER_ID));
        }
        Computer c = ServiceComputer.INSTANCE.getComputer(computerId);
        request.setAttribute(ATT_COMPUTER_ID, c);
        request.getServletContext().getRequestDispatcher(EDIT_COMPUTER_VIEW).forward(request,
                response);
    }

    @Override
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = 0;
        if (request.getParameter(PARAM_COMPUTER_ID) != null) {
            id = Long.parseLong(request.getParameter(PARAM_COMPUTER_ID));
        }
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
        if (ServiceComputer.INSTANCE.updatecomputer(id, name, introduced, discontinued,
                companyId)) {
            response.sendRedirect(getServletContext().getContextPath() + "/homepage");
        } else {
            request.getServletContext().getRequestDispatcher(EDIT_COMPUTER_VIEW).forward(request,
                    response);
            // TODO Warn the user of the error
        }
    }

}
