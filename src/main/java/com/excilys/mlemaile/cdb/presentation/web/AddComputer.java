package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mlemaile.cdb.presentation.model.CompanyDto;
import com.excilys.mlemaile.cdb.presentation.model.ComputerDto;
import com.excilys.mlemaile.cdb.presentation.model.MapperDtoToModel;
import com.excilys.mlemaile.cdb.presentation.model.MapperException;
import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.service.Validator;

/**
 * Servlet implementation class AddComputer.
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
    private static final long   serialVersionUID     = 1L;
    private static final String ADD_COMPUTER_VIEW    = "/WEB-INF/views/addComputer.jsp";
    private static final String ATT_COMPANIES        = "companies";
    private static final String ATT_EXCEPTION        = "exception";
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
        try {
            List<CompanyDto> companies = MapperDtoToModel.INSTANCE
                    .modelListToCompanyDto(ServiceCompany.INSTANCE.listCompanies());
            request.setAttribute(ATT_COMPANIES, companies);
        } catch (ServiceException e) {
            request.setAttribute(ATT_EXCEPTION, e.getMessage());
        } finally {
            request.getServletContext().getRequestDispatcher(ADD_COMPUTER_VIEW).forward(request,
                    response);
        }
    }

    @Override
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String companyId = request.getParameter(PARAM_COMPANY_ID);
        String introduced = request.getParameter(PARAM_COMPUTER_INTRO);
        String discontinued = request.getParameter(PARAM_COMPUTER_DISCO);
        try {
            Validator.INSTANCE.checkId(companyId);
            Validator.INSTANCE.checkDate(introduced);
            Validator.INSTANCE.checkDate(discontinued);
            Validator.INSTANCE.checkDateNotBeforeDate(discontinued, introduced);
            ComputerDto ce = new ComputerDto.Builder(request.getParameter(PARAM_COMPUTER_NAME))
                    .introduced(introduced).discontinued(discontinued).companyId(companyId).build();
            ServiceComputer.INSTANCE
                    .createComputer(MapperDtoToModel.INSTANCE.computerDtoToModel(ce));
            response.sendRedirect(getServletContext().getContextPath() + "/homepage");
        } catch (MapperException | ServiceException e) {
            request.setAttribute(ATT_EXCEPTION, e.getMessage());
            request.getServletContext().getRequestDispatcher(ADD_COMPUTER_VIEW).forward(request,
                    response);
        }

    }

}
