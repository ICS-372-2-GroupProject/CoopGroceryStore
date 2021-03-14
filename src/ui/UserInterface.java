package ui;

/**
 * 
 * @author Brahma Dathan and Sarnath Ramnath
 * @Copyright (c) 2010
 
 * Redistribution and use with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - the use is for academic purpose only
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Neither the name of Brahma Dathan or Sarnath Ramnath
 *     may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * The authors do not make any claims regarding the correctness of the code in this module
 * and are not responsible for any loss or damage resulting from its use.  
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

import business.entities.Transaction;
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;
import business.tests.AutomatedTester;

/**
 * RYAN TESTING. TEST MAKING A CHANGE AND COMMITING This class implements the
 * user interface for the Store project. The commands are encoded as integers
 * using a number of static final variables. A number of utility methods exist
 * to make it easier to parse the input.
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Store store;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int ADD_PRODUCT = 2;
	private static final int REMOVE_MEMBER = 3;
	private static final int GET_TRANSACTIONS = 4;
	private static final int GET_MEMBERS = 5;
	private static final int GET_PRODUCT = 6;
	private static final int PROCESS_SHIPMENT = 7;
	private static final int SAVE = 8;
	private static final int HELP = 9;

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved data.
	 * Otherwise, it gets a singleton Store object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and  use it?")) {
			retrieve();
		} else {
			store = store.instance();
			if (yesOrNo("Do you want to generate a test bed and invoke the functionality using asserts? ")) {
				new AutomatedTester().testAll();
			}

		}

	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * Gets a token after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 */
	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Gets a name after prompting
	 * 
	 * @param prompt - whatever the user wants as prompt
	 * @return - the token from the keyboard
	 */
	public String getName(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				return line;
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);

	}

	/**
	 * Queries for a yes or no and returns true for yes and false for no
	 * 
	 * @param prompt The string to be prepended to the yes/no prompt
	 * @return true for yes and false for no
	 */
	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * Converts the string to a number
	 * 
	 * @param prompt the string for prompting
	 * @return the integer corresponding to the string
	 */
	public int getNumber(String prompt) {
		do {
			try {
				String item = getToken(prompt);
				Integer number = Integer.valueOf(item);
				return number.intValue();
			} catch (NumberFormatException nfe) {
				System.out.println("Please input a number ");
			}
		} while (true);
	}

	/**
	 * Prompts for a date and gets a date object
	 * 
	 * @param prompt the prompt
	 * @return the data as a Calendar object
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception fe) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * Prompts for a command from the keyboard
	 * 
	 * @return a valid command
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Enter a number");
			}
		} while (true);
	}

	/**
	 * Displays the help screen
	 */
	public void help() {
		System.out.println("Enter a number between 0 and ???? as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(ADD_MEMBER + " to add a member");
		System.out.println(ADD_PRODUCT + " to  add product");
		System.out.println(REMOVE_MEMBER + " to  remove member");
		System.out.println(GET_TRANSACTIONS + " to  print transactions");
		System.out.println(GET_MEMBERS + " to  print all members");
		System.out.println(GET_PRODUCT + " to  print all product");
		System.out.println(PROCESS_SHIPMENT + " to process a shipment");
		System.out.println("\n ADD \n MORE \n STUFF \n");

		System.out.println(SAVE + " to  save data");
		System.out.println(HELP + " for help");
	}

	/**
	 * Method to be called for adding a member. Prompts the user for the appropriate
	 * values and uses the appropriate Library method for adding the member.
	 */
	public void addMember() {
		Request.instance().setMemberName(getName("Enter member name"));
		Request.instance().setMemberAddress(getName("Enter address"));
		Request.instance().setMemberPhone(getName("Enter phone"));
		Result result = store.addMember(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not add member");
		} else {
			System.out.println(result.getMemberName() + "'s id is " + result.getMemberId());
		}
	}

	/**
	 * Method to be called for adding a book. Prompts the user for the appropriate
	 * values and uses the appropriate Library method for adding the book.
	 */
	public void addProduct() {
		Request.instance().setProductName(getName("Enter  Product Name"));
		Request.instance().setProductId(getToken("Enter Product id"));
		Request.instance().setProductCurrentPrice(getName("Enter Product Current Price"));
		Result result = store.addProduct(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Book could not be added");
		} else {
			System.out.println("Book " + result.getProductName() + " added");
		}
	}

	/**
	 * Method to be called for removing books. Prompts the user for the appropriate
	 * values and uses the appropriate Library method for removing books.
	 */
	public void removeMember() {
		Request.instance().setMemberId(getToken("Enter member id"));
		Result result = store.removeMember(Request.instance());
		switch (result.getResultCode()) {
		case Result.NO_SUCH_MEMBER:
			System.out.println("No such member with id " + Request.instance().getMemberId() + " in store");
			break;
		case Result.OPERATION_FAILED:
			System.out.println("Member could not be removed");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.println(" Member has been removed");
			break;
		default:
			System.out.println("An error has occurred");
		}
	}

	/**
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate Library method for displaying
	 * transactions.
	 */
	public void getTransactions() {
		Request.instance().setMemberId(getToken("Enter member id"));
		Request.instance().setDate(getDate("Please enter the date for which you want records as mm/dd/yy"));
		Iterator<Transaction> result = store.getTransactions(Request.instance());
		while (result.hasNext()) {
			Transaction transaction = (Transaction) result.next();
			System.out.println(transaction.getType() + "   " + transaction.getTitle() + "\n");
		}
		System.out.println("\n End of transactions \n");
	}

	/**
	 * Displays all members
	 */
	public void getMembers() {
		Iterator<Result> iterator = store.getMembers();
		System.out.println("List of members (name, address, phone, id)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getMemberName() + " " + result.getMemberAddress() + " " + result.getMemberPhone()
					+ " " + result.getMemberId());
		}
		System.out.println("End of listing");
	}

	/**
	 * Gets and prints all books.
	 */
	public void getProduct() {
		Iterator<Result> iterator = store.getProduct();
		System.out.println("List of books (title, author, id, borrower id, due date)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getProductName() + " " + result.getProductId() + " "
					+ result.getProductCurrentPrice() + " " + result.getProductStockOnHand());
		}
		System.out.println("End of listing");
	}

	/**
	 * Method to be called for processing a shipment.
	 */
	public void processShipment() {
		Request.instance().setOrderId(getName("Enter Order ID"));
		Result result = store.processShipment(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not process shipment");
		} else {
			System.out.println(result.getProductName() + "(Product ID: " + result.getProductId() + ") updated!");
			System.out.println("New quantity in stock: " + result.getProductStockOnHand());
		}
	}

	/**
	 * Method to be called for saving the Library object. Uses the appropriate
	 * Library method for saving.
	 */
	private void save() {
		if (store.save()) {
			System.out.println(" The library has been successfully saved in the file LibraryData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Method to be called for retrieving saved data. Uses the appropriate Store
	 * method for retrieval.
	 */
	private void retrieve() {
		try {
			if (store == null) {
				store = Store.retrieve();
				if (store != null) {
					System.out.println(" The Store has been successfully retrieved from the file StoreData \n");
				} else {
					System.out.println("File doesnt exist; creating new Store");
					store = Store.instance();
				}
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}

	/**
	 * Orchestrates the whole process. Calls the appropriate method for the
	 * different functionalities.
	 */
	public void process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case ADD_MEMBER:
				addMember();
				break;
			case ADD_PRODUCT:
				addProduct();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case GET_TRANSACTIONS:
				getTransactions();
				break;
			case GET_MEMBERS:
				getMembers();
				break;
			case GET_PRODUCT:
				getProduct();
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case SAVE:
				save();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	/**
	 * The method to start the application. Simply calls process().
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}