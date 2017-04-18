package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
import com.excilys.mlemaile.cdb.service.model.Computer;

/**
 * Servlet implementation class EditComputer.
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
    private static final long   serialVersionUID     = 1L;
    private static final String PARAM_COMPUTER_ID    = "computerId";
    private static final String ATT_COMPUTER         = "computer";
    private static final String ATT_COMPANIES        = "companies";
    private static final String ATT_EXCEPTION        = "exception";
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
        try {
            long computerId = 0;
            if (request.getParameter(PARAM_COMPUTER_ID) != null) {
                computerId = Long.parseLong(request.getParameter(PARAM_COMPUTER_ID));
            }
            Optional<Computer> optComputer = ServiceComputer.INSTANCE.getComputerById(computerId);
            if (optComputer.isPresent()) {
                ComputerDto c = MapperDtoToModel.modelToComputerDto(optComputer.get());
                request.setAttribute(ATT_COMPUTER, c);
            }
            List<CompanyDto> companies = MapperDtoToModel
                    .modelListToCompanyDto(ServiceCompany.INSTANCE.listCompanies());
            request.setAttribute(ATT_COMPANIES, companies);
        } catch (NumberFormatException | ServiceException e) {
            request.setAttribute(ATT_EXCEPTION, e.getMessage());
        } finally {
            request.getServletContext().getRequestDispatcher(EDIT_COMPUTER_VIEW).forward(request,
                    response);
        }
    }

    @Override
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter(PARAM_COMPUTER_ID);
        String companyId = request.getParameter(PARAM_COMPANY_ID);
        String introduced = request.getParameter(PARAM_COMPUTER_INTRO);
        String discontinued = request.getParameter(PARAM_COMPUTER_DISCO);
        Validator.INSTANCE.checkId(id);
        Validator.INSTANCE.checkId(companyId);
        Validator.INSTANCE.checkDate(introduced);
        Validator.INSTANCE.checkDate(discontinued);
        Validator.INSTANCE.checkDateNotBeforeDate(discontinued, introduced);
        ComputerDto ce = new ComputerDto.Builder(request.getParameter(PARAM_COMPUTER_NAME))
                .introduced(introduced).discontinued(discontinued).companyId(companyId).id(id)
                .build();
        try {
            ServiceComputer.INSTANCE
                    .updatecomputer(MapperDtoToModel.computerDtoToModel(ce));
            response.sendRedirect(getServletContext().getContextPath() + "/homepage");
        } catch (MapperException | ServiceException e) {
            request.setAttribute(ATT_EXCEPTION, e.getMessage());
            request.getServletContext().getRequestDispatcher(EDIT_COMPUTER_VIEW).forward(request,
                    response);
        }
    }

}
