package com.excilys.mlemaile.cdb.presentation.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.mlemaile.cdb.presentation.model.CompanyDto;
import com.excilys.mlemaile.cdb.presentation.model.ComputerDto;
import com.excilys.mlemaile.cdb.presentation.model.MapperDtoToModel;
import com.excilys.mlemaile.cdb.service.ServiceCompany;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.service.Validator;

/**
 * Servlet implementation class AddComputer.
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
    private static final long     serialVersionUID     = 1L;
    private static final String   ADD_COMPUTER_VIEW    = "/WEB-INF/views/addComputer.jsp";
    private static final String   ATT_COMPANIES        = "companies";
    private static final String   ATT_EXCEPTION        = "exception";
    private static final String   PARAM_COMPUTER_NAME  = "computerName";
    private static final String   PARAM_COMPUTER_INTRO = "introduced";
    private static final String   PARAM_COMPUTER_DISCO = "discontinued";
    private static final String   PARAM_COMPANY_ID     = "companyId";
    private WebApplicationContext ctx;
    private ServiceCompany        serviceCompany;
    private ServiceComputer       serviceComputer;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        serviceCompany = ctx.getBean("serviceCompany", ServiceCompany.class);
        serviceComputer = ctx.getBean("serviceComputer", ServiceComputer.class);
    }

    @Override
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<CompanyDto> companies = MapperDtoToModel
                    .modelListToCompanyDto(serviceCompany.listCompanies());
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

        Map<String, String> errors = isValid(request);
        if (errors.isEmpty()) {
            ComputerDto ce = new ComputerDto.Builder(request.getParameter(PARAM_COMPUTER_NAME))
                    .introduced(request.getParameter(PARAM_COMPUTER_INTRO))
                    .discontinued(request.getParameter(PARAM_COMPUTER_DISCO))
                    .companyId(request.getParameter(PARAM_COMPANY_ID)).build();
            serviceComputer.createComputer(MapperDtoToModel.computerDtoToModel(ce));
            response.sendRedirect(getServletContext().getContextPath() + "/homepage");
        } else {
            request.setAttribute(ATT_EXCEPTION, errors);
            request.getServletContext().getRequestDispatcher(ADD_COMPUTER_VIEW).forward(request,
                    response);
        }
    }

    /**
     * Validate the request made by the user.
     * @param request the request issued by the client
     * @return a Map of errors
     */
    private Map<String, String> isValid(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        String nameValid = Validator.checkNameNotEmpty(request.getParameter(PARAM_COMPUTER_NAME));
        if (nameValid != null) {
            errors.put(PARAM_COMPUTER_NAME, nameValid);
        }
        String companyIdValid = Validator.checkId(request.getParameter(PARAM_COMPANY_ID));
        if (companyIdValid != null) {
            errors.put(PARAM_COMPANY_ID, companyIdValid);
        }
        String validDates = Validator.checkDateNotBeforeDate(
                request.getParameter(PARAM_COMPUTER_DISCO),
                request.getParameter(PARAM_COMPUTER_INTRO));
        if (validDates != null) {
            errors.put(PARAM_COMPUTER_DISCO, validDates);
        }
        return errors;
    }

}
