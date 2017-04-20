package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;

/**
 * Servlet implementation class DeleteComputer.
 */
@WebServlet("/deleteComputer")
public class DeleteComputer extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String DASHBOARD        = "/homepage";
    private static final String DASHBOARD_VIEW   = "/WEB-INF/views/dashboard.jsp";
    private static final String PARAM_ID_LIST    = "selection";
    private static final String ATT_EXCEPTION    = "exception";
    private WebApplicationContext ctx;
    private ServiceComputer       serviceComputer;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        serviceComputer = ctx.getBean("serviceComputer", ServiceComputer.class);
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
                serviceComputer.deleteComputer(id);
            }
            response.sendRedirect(getServletContext().getContextPath() + DASHBOARD);
        } catch (NumberFormatException | ServiceException e) {
            request.setAttribute(ATT_EXCEPTION, e.getMessage());
            request.getServletContext().getRequestDispatcher(DASHBOARD_VIEW).forward(request,
                    response);
        }
    }

}
