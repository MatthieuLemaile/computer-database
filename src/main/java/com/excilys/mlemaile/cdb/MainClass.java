package com.excilys.mlemaile.cdb;

import com.excilys.mlemaile.cdb.presentation.ConsoleUserInterface;

public class MainClass {

	private MainClass(){} //we don't want to instantiate this class
	
	public static void main(String [] args){
		System.out.println("Hello, what do you want to do ?");
		while(ConsoleUserInterface.menu());
		
	}
	
	
}
