package com.excilys.mlemaile.cdb.cli;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.excilys.mlemaile.cdb.dto.CompanyDto;
import com.excilys.mlemaile.cdb.dto.ComputerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum WebServiceAccess {
    INSTANCE;
    private static final int    NUMBER_PER_PAGE = 50;
    private static final String API_URL = "http://localhost:8080/ComputerDatabase";

    public ComputerDto getComputer(long id) {
        WebTarget target = ClientBuilder.newClient().target(API_URL);
        Invocation.Builder invocationBuilder = target.path("computers/" + String.valueOf(id))
                .request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get(ComputerDto.class);
    }

    public List<ComputerDto> listComputers(int pageNumber){
        
        WebTarget target = ClientBuilder.newClient().target(API_URL);
        Invocation.Builder invocationBuilder = target
                .path("computers").queryParam("page", pageNumber)
                .queryParam("limit", NUMBER_PER_PAGE)
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (response.getStatus() != 200) {
            System.out.println(response.getStatus());
            return null;
        } else {
            try {
                return new ObjectMapper().readValue(response.readEntity(String.class),
                        new TypeReference<List<ComputerDto>>() {
                        });
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
    }
    
    public List<CompanyDto> listCompanies() {
        WebTarget target = ClientBuilder.newClient().target(API_URL);
        Invocation.Builder invocationBuilder = target.path("companies/")
                .request(MediaType.APPLICATION_JSON);
        try {
            Response response = invocationBuilder.get();
            return new ObjectMapper().readValue(response.readEntity(String.class),
                    new TypeReference<List<CompanyDto>>() {
                    });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void createComputer(ComputerDto computerDto) {
        WebTarget target = ClientBuilder.newClient().target(API_URL);
        Invocation.Builder invocationBuilder = target.path("computers")
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder
                .post(Entity.entity(computerDto, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            System.out.println(response.getStatus());
        }
    }

    public void updateComputer(ComputerDto computerDto) {
        WebTarget target = ClientBuilder.newClient().target(API_URL);
        Invocation.Builder invocationBuilder = target.path("computers")
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder
                .put(Entity.entity(computerDto, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            System.out.println(response.getStatus());
        }
    }

    public void deleteComputer(long id) {
        WebTarget target = ClientBuilder.newClient().target(API_URL);
        Invocation.Builder invocationBuilder = target.path("computers/" + String.valueOf(id))
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.delete();
        if (response.getStatus() != 200) {
            System.out.println(response.getStatus());
        }
    }

    public void deleteCompany(long id) {
        WebTarget target = ClientBuilder.newClient().target(API_URL);
        Invocation.Builder invocationBuilder = target.path("companies/" + String.valueOf(id))
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.delete();
        if (response.getStatus() != 200) {
            System.out.println(response.getStatus());
        }
    }
}
