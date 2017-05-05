package com.excilys.mlemaile.cdb.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.excilys.mlemaile.cdb.dto.CompanyDto;
import com.excilys.mlemaile.cdb.dto.ComputerDto;

public class ConsoleUserInterface {

    /**
     * client.cre Display the menu and call the funclient.crection to do the choosen action.
     * @return a boolean which is true as long as the user don't choose to leave
     */
    public static boolean menu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String entry;
        int optionNumber = 0;
        boolean continuProgramm = true;
        do {
            System.out.println("1 : List computers");
            System.out.println("2 : List companies");
            System.out.println("3 : show computer details");
            System.out.println("4 : create a computer");
            System.out.println("5 : update a computer");
            System.out.println("6 : delete a computer");
            System.out.println("7 : delete a company");
            System.out.println("8 : get the bcrypt of a password");
            System.out.println("0 : Exit");
            try {
                entry = br.readLine();
                optionNumber = Integer.parseInt(entry);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("You must enter an integer");
            }
        } while (optionNumber < 0 || optionNumber > 8);

        switch (optionNumber) {
        case 1:
            listComputers(br);
            break;
        case 2:
            listCompanies(br);
            break;
        case 3:
            showComputerDetails(br);
            break;
        case 4:
            createComputer(br);
            break;
        case 5:
            updateComputer(br);
            break;
        case 6:
            deleteComputer(br);
            break;
        case 7:
            deleteCompany(br);
            break;
        case 8:
            bcryptPass(br);
            break;
        default:
            continuProgramm = false;

        }
        return continuProgramm;

    }

    /**
     * Display the list of computers.
     * @param br BufferedReader to read the user's entry
     */
    private static void listComputers(BufferedReader br) {
        List<ComputerDto> computers = new ArrayList<>();
        String entry;
        int pageNumber = 0;
        do {
            System.out.println("Enter a page number, 0 to leave.");
            try {
                entry = br.readLine();
                pageNumber = Integer.parseInt(entry);
                if (pageNumber > 0) {
                    computers = WebServiceAccess.INSTANCE.listComputers(pageNumber);
                    for (ComputerDto computer : computers) {
                        System.out.println(computer.toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("You must enter an integer");
            } catch (ServiceException e) {
                System.out.println("a problem occur" + e.getMessage());
            }
        } while (pageNumber > 0); // Le cas négatif n'est pa géré ici, mais
                                  // c'est un bug mineur.
    }

    /**
     * Display the list of companies.
     * @param br BufferedReader to read the user's entry
     */
    private static void listCompanies(BufferedReader br) {
        List<CompanyDto> companies = new ArrayList<>();
        int pageNumber = 0;
        do {
            try {

                companies = WebServiceAccess.INSTANCE.listCompanies();
                for (CompanyDto company : companies) {
                    System.out.println(company.toString());
                }
            } catch (NumberFormatException e) {
                System.out.println("You must enter an integer");
            } catch (ServiceException e) {
                System.out.println("a problem occur" + e.getMessage());
            }
        } while (pageNumber > 0); // Le cas négatif n'est pa géré ici, mais
                                  // c'est un bug mineur.
    }

    /**
     * Display details of a computer.
     * @param br BufferedReader to read the user's entry
     */
    private static void showComputerDetails(BufferedReader br) {
        System.out.println("enter the id of the computer of which you want to see the details");
        String entry;
        int id = 0;
        ComputerDto c = null;
        do {
            System.out.println("Enter a number greater or equal to 1");
            try {
                entry = br.readLine();
                id = Integer.parseInt(entry);
                try {
                    c = WebServiceAccess.INSTANCE.getComputer(id);
                    System.out.println("\tid : " + c.getId() + " name : " + c.getName());
                    System.out.println("\tintroduced : " + c.getIntroduced() + " discontinued : "
                            + c.getDiscontinued());
                    System.out.println("\tmanufacturer : " + c.getCompanyName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("You must enter an integer");
            } catch (ServiceException e) {
                System.out.println("a problem occur" + e.getMessage());
            }
        } while (id < 1);
    }

    /**
     * This function allow the user to create a computer.
     * @param br BufferedReader to read the user's entry
     */
    private static void createComputer(BufferedReader br) {
        System.out.println(
                "Enter the following details. You can just type enter to let a field blank, except for the name.");
        try {
            ComputerDto computerDto = new ComputerDto();
            LocalDate defaultDate = LocalDate.now();
            System.out.print("please enter the name of the computer :");
            computerDto.setName(br.readLine());
            System.out.print("date of introduction (yyyy-mm-dd)?");
            String dateIntroduced = br.readLine();
            LocalDate ldIntro = defaultDate;
            if (dateIntroduced != null && !dateIntroduced.trim().isEmpty()) {
                ldIntro = LocalDate.parse(dateIntroduced);
            }
            System.out.print("date of discontinuation (yyyy-mm-dd) ?");
            String dateDiscontinued = br.readLine();
            LocalDate ldDisco = defaultDate;
            if (dateDiscontinued != null && !dateDiscontinued.trim().isEmpty()) {
                ldDisco = LocalDate.parse(dateDiscontinued);
            }
            System.out.print("Id of the manufacturer ?");
            String strCompanyId = br.readLine();
            int companyId = 0;
            if (strCompanyId != null && !strCompanyId.trim().isEmpty()) {
                companyId = Integer.parseInt(strCompanyId);
                if (companyId < 1) {
                    System.out.println("The id of the company must be greater or equals to 1.");
                    return;
                }
                computerDto.setCompanyId(String.valueOf(companyId));
            }
            if (ldIntro.isEqual(defaultDate)) {
                computerDto.setIntroduced(ldIntro.toString());
            }
            if (ldDisco.isEqual(defaultDate)) {
                computerDto.setDiscontinued(ldDisco.toString());
            }
            WebServiceAccess.INSTANCE.createComputer(computerDto);
            System.out.println("computer successfully created !");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            System.out.println("please try again formatting your date the way indicated");
        } catch (NumberFormatException e) {
            System.out.println("please try again entering a good company id");
        } catch (ServiceException e) {
            System.out.println(
                    "Something went wrong. Ensure the order of date if entered, and eventually check log file.");
            System.out.println("a problem occur creating the computer : " + e.getMessage());
        }
    }

    /**
     * This function allow the user to update a computer.
     * @param br BufferedReader to read the user's entry
     */
    private static void updateComputer(BufferedReader br) {
        System.out.println("Type the id of the computer you want to update.");
        System.out.println(
                "Then type -[Field to change]=[new value]. fields are : name; introduced-date, discontinued-date, company-id.");
        System.out.println("A date is formatted as follow : yyyy-mm-dd");
        System.out.println("example :'214 -name=nouveau nom -introduced-date=2012-08-26'");
        String entry;
        int id = 0;
        try {
            entry = br.readLine();
            String[] args = entry.split("( -|=)");
            id = Integer.parseInt(args[0]);
            ComputerDto c = WebServiceAccess.INSTANCE.getComputer(id);
            for (int i = 1; i < args.length; i = i + 2) {
                if ("name".equals(args[i])) {
                    c.setName(args[i + 1]);
                } else if ("introduced-date".equals(args[i])) {
                    String date = args[i + 1];
                    if (date != null && !date.trim().isEmpty()) {
                        c.setIntroduced(date);
                    }
                } else if ("discontinued-date".equals(args[i])) {
                    String date = args[i + 1];
                    if (date != null && !date.trim().isEmpty()) {
                        c.setDiscontinued(date);
                    }
                } else if ("company-id".equals(args[i])) {
                    String strId = args[i + 1];
                    if (strId != null && !strId.trim().isEmpty()) {
                        long companyId = Long.parseLong(strId);
                        if (companyId > 1) {
                            c.setCompanyId(strId);
                        }
                    }
                }
            }
            WebServiceAccess.INSTANCE.updateComputer(c);
            System.out.println("computer successfully updated");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println(
                    "you must identify the computer by an id(number). The company-id parameter require an interger too.");
        } catch (ServiceException e) {
            System.out.println("a problem occur" + e.getMessage());
        }

    }

    /**
     * This function allow the user to delete a computer.
     * @param br BufferedReader to read the user's entry
     */
    private static void deleteComputer(BufferedReader br) {
        System.out.println("Enter the id of the computer you want to delete.");
        String entry;
        int id = 0;
        do {
            System.out.println("Enter a number greater or equal to 1");
            try {
                entry = br.readLine();
                id = Integer.parseInt(entry);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("You must enter an integer");
            }
        } while (id < 1);
        try {
            WebServiceAccess.INSTANCE.deleteComputer(id);
            System.out.println("Computer successfully deleted !");
        } catch (ServiceException e) {
            System.out.println("a problem occurpage.getPageNumber() - 1) * Page.numberPerPage)"
                    + e.getMessage());
        }
    }

    /**
     * This function allow the user to delete a company.
     * @param br BufferedReader to read the user's entry
     */
    private static void deleteCompany(BufferedReader br) {
        System.out.println("Enter the id of the company you want to delete.");
        String entry;
        int id = 0;
        do {
            System.out.println("Enter a number greater or equal to 1");
            try {
                entry = br.readLine();
                id = Integer.parseInt(entry);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("You must enter an integer");
            }
        } while (id < 1);
        try {
            WebServiceAccess.INSTANCE.deleteCompany(id);
            System.out.println("Company successfully deleted !");
        } catch (ServiceException e) {
            System.out.println("a problem occur" + e.getMessage());
        }
    }

    /**
     * This function print 10 hashes of the password given by the user.
     * @param br the reader allowing the user to input something
     */
    private static void bcryptPass(BufferedReader br) {
        System.out.println("Enter the password you want to get hashed");
        String entry = "";
        try {
            entry = br.readLine();
        } catch (IOException e) {
            System.out.println("a problem occur" + e.getMessage());
        }
        int i = 0;
        while (i < 10) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(entry);
            System.out.println(hashedPassword);
            i++;
        }
    }
}
