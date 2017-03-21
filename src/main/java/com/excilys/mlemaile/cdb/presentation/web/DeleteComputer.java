package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mlemaile.cdb.service.ServiceComputer;

/**
 * Servlet implementation class DeleteComputer.
 */
@WebServlet("/deleteComputer")
public class DeleteComputer extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String DASHBOARD        = "/homepage";
    private static final String PARAM_ID_LIST    = "selection";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    @Override
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idList = "";
        if (request.getParameter(PARAM_ID_LIST) != null) {
            idList = request.getParameter(PARAM_ID_LIST);
        }
        String[] ids = idList.split(",");
        try {
            for (String idStr : ids) {
                long id = Long.parseLong(idStr);
                ServiceComputer.INSTANCE.deleteComputer(id);
            }
        } catch (NumberFormatException e) {
            // TODO WARN BAD REQUEST
        }
        response.sendRedirect(getServletContext().getContextPath() + DASHBOARD);
    }

}
