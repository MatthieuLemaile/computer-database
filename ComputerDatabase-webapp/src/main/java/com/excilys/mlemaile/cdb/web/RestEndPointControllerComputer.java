package com.excilys.mlemaile.cdb.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.mlemaile.cdb.dto.ComputerDto;
import com.excilys.mlemaile.cdb.model.Computer;
import com.excilys.mlemaile.cdb.persistence.FieldSort;
import com.excilys.mlemaile.cdb.service.ServiceComputer;
import com.excilys.mlemaile.cdb.service.ServiceException;
import com.excilys.mlemaile.cdb.web.model.ComputerValidator;
import com.excilys.mlemaile.cdb.web.model.MapperDtoToModel;

@RestController
public class RestEndPointControllerComputer {
    private static final String PARAM_PAGE_NUMBER = "page";
    private static final String PARAM_PAGE_LIMIT  = "limit";
    private static final String PARAM_SORT        = "sort";
    private static final String PARAM_SEARCH      = "search";
    private static final Logger LOGGER            = LoggerFactory
            .getLogger(RestEndPointControllerComputer.class);

    @Autowired
    private ServiceComputer     serviceComputer;
    @Autowired
    private ComputerValidator   computerValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(computerValidator);
    }

    @GetMapping("/computers")
    public ResponseEntity<List<ComputerDto>> listComputers(
            @RequestParam(value = PARAM_PAGE_NUMBER, required = false) String pageNumber,
            @RequestParam(value = PARAM_PAGE_LIMIT, required = false) String pageLimit,
            @RequestParam(value = PARAM_SEARCH, required = false) String search,
            @RequestParam(value = PARAM_SORT, required = false) String sort) {
        int numPage = (pageNumber != null) ? Integer.parseInt(pageNumber) : 1;
        int limit = (pageLimit != null) ? Integer.parseInt(pageLimit) : 50;
        int startElementNumber = (numPage - 1) * limit;
        List<Computer> computers = serviceComputer.listSortSearchNumberComputer(limit,
                Integer.toUnsignedLong(startElementNumber), getSort(sort), search);
        if (computers.isEmpty()) {
            return new ResponseEntity<List<ComputerDto>>(HttpStatus.NO_CONTENT);
        }
        List<ComputerDto> computersDto = MapperDtoToModel.modelListToComputerDto(computers);
        return new ResponseEntity<List<ComputerDto>>(computersDto, HttpStatus.OK);
    }

    private FieldSort getSort(String field) {
        if (field != null && !field.trim().isEmpty()) {
            switch (field) {
            case "name":
                return FieldSort.NAME;
            case "introduced":
                return FieldSort.INTRODUCED;
            case "discontinued":
                return FieldSort.DISCONTINUED;
            case "companyName":
                return FieldSort.COMPANY_NAME;
            }
        }
        return null;
    }

    @GetMapping("/computers/{id}")
    public ResponseEntity<ComputerDto> getComputer(@PathVariable("id") long id) {
        Optional<Computer> computer = serviceComputer.getComputerById(id);
        if (computer.isPresent()) {
            return new ResponseEntity<ComputerDto>(
                    MapperDtoToModel.modelToComputerDto(computer.get()), HttpStatus.OK);
        }
        return new ResponseEntity<ComputerDto>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/computers")
    public ResponseEntity<ComputerDto> createComputer(@Valid @RequestBody ComputerDto computerDto,
            BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors().get(0));
            return new ResponseEntity(result.getAllErrors().stream().map(values -> values.getCode())
                    .collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        Computer computer = MapperDtoToModel.computerDtoToModel(computerDto);
        serviceComputer.createComputer(computer);
        return new ResponseEntity<ComputerDto>(computerDto, HttpStatus.OK);
    }

    @DeleteMapping("/computers/{id}")
    public ResponseEntity<Long> deleteComputer(@PathVariable Long id) {
        try {
            serviceComputer.deleteComputer(id);
            return new ResponseEntity<Long>(id, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/computers")
    public ResponseEntity updateComputer(@Valid @RequestBody ComputerDto computerDto,
            BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors().get(0));
            return new ResponseEntity(result.getAllErrors().stream().map(values -> values.getCode())
                    .collect(Collectors.toList()), HttpStatus.BAD_REQUEST);
        }
        Computer computer = MapperDtoToModel.computerDtoToModel(computerDto);
        serviceComputer.updatecomputer(computer);
        return new ResponseEntity<ComputerDto>(computerDto, HttpStatus.OK);
    }

}
