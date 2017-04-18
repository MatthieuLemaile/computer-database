package com.excilys.mlemaile.cdb;

import com.excilys.mlemaile.cdb.presentation.cli.ConsoleUserInterface;

public class MainClass {

    /**
     * This is the main class.
     */
    private MainClass() {
    } // we don't want to instantiate this class

    /**
     * This is the main method.
     * @param args the argument of the main method
     */
    public static void main(String[] args) {
        System.out.println("Hello, what do you want to do ?");
        boolean keepGoing = true;
        while (keepGoing) {
            keepGoing = ConsoleUserInterface.menu();
        }
    }

}