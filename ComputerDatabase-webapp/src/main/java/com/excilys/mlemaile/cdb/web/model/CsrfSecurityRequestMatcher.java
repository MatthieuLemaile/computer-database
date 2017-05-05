package com.excilys.mlemaile.cdb.web.model;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class CsrfSecurityRequestMatcher implements RequestMatcher {
    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    private Pattern allowedUrls    = Pattern
            .compile("^/ComputerDatabase/(computers|companies)(/)?(.)*");

    @Override
    public boolean matches(HttpServletRequest request) {
        if (allowedMethods.matcher(request.getMethod()).matches()) {
            return false;
        }
        return !allowedUrls.matcher(request.getRequestURI()).matches();
    }
}