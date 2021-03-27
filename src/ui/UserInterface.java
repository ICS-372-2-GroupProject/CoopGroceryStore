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

import business.entities.Order;
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
	private static final int ENROLL_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int ADD_PRODUCT = 3;
	private static final int CHECK_OUT_ITEMS = 4;
	private static final int PROCESS_SHIPMENT = 5;
	private static final int CHANGE_PRICE = 6;
	private static final int GET_PRODUCT_INFO = 7;
	private static final int GET_MEMBER_INFO = 8;
	private static final int GET_TRANSACTIONS = 9;
	private static final int GET_OUTSTANDING_ORDERS = 10;
	private static final int GET_MEMBERS = 11;
	private static final int GET_PRODUCT = 12;
	private static final int SAVE = 13;
	private static final int HELP = 14;

	/**
	 * Made private for singleton pattern. Conditionally looks for any saved data.
	 * Otherwise, it gets a singleton Store object.
	 */
	private UserInterface() {
		if (yesOrNo("Look for saved data and use it?")) {
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
	 * @param prompt
	 *            - whatever the user wants as prompt
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
	 * @param prompt
	 *            - whatever the user wants as prompt
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
	 * @param prompt
	 *            The string to be prepended to the yes/no prompt
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
	 * @param prompt
	 *            the string for prompting
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
	 * @param prompt
	 *            the prompt
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
		System.out.println("\n" + EXIT + " to Exit");
		System.out.println(ENROLL_MEMBER + " to enroll a member");
		System.out.println(REMOVE_MEMBER + " to remove a member");
		System.out.println(ADD_PRODUCT + " to add a product");
		System.out.println(CHECK_OUT_ITEMS + " to check out items for purchase");
		System.out.println(PROCESS_SHIPMENT + " to process a shipment");
		System.out.println(CHANGE_PRICE + " to change the price of a product");
		System.out.println(GET_PRODUCT_INFO + " to list details of a product");
		System.out.println(GET_MEMBER_INFO + " to list details of a member");
		System.out.println(GET_TRANSACTIONS + " ???");
		System.out.println(GET_OUTSTANDING_ORDERS + " to print all outstanding orders");
		System.out.println(GET_MEMBERS + " to print all members");
		System.out.println(GET_PRODUCT + " to print all product");
		System.out.println(SAVE + " to  save data");
		System.out.println(HELP + " for help");
	}

	/**
	 * code updated by Richard Fritz Method to be called for enrolling a member.
	 * Prompts the user for the appropriate values and uses the Store method
	 * enrollMember() for adding the member.
	 */
	public void enrollMember() {
		Request.instance().setMemberName(getName("Enter member name"));
		Request.instance().setMemberAddress(getName("Enter address"));
		Request.instance().setMemberPhone(getName("Enter phone"));
		Result result = store.enrollMember(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not add member");
		} else {
			System.out.println(result.getMemberName() + "'s id is " + result.getMemberId());
		}
	}

	/**
	 * code updated by Richard Fritz Method to be called for adding a product.
	 * Prompts the user for the appropriate values and uses the appropriate Store
	 * method for adding the product.
	 */
	public void addProduct() {
		Request.instance().setProductName(getName("Enter  Product Name"));
		Request.instance().setProductId(getToken("Enter Product id"));
		Request.instance().setProductStockOnHand(getNumber("Enter Stock on Hand"));
		Request.instance().setProductPrice(getName("Enter Current Price"));
		Request.instance().setProductReorderLevel(getNumber("Enter Reorder Level"));
		Result result = store.addProduct(Request.instance());
		switch (result.getResultCode()) {
		case Result.PRODUCT_EXISTS:
			System.out.println("Product with id " + Request.instance().getProductId() + " already exists");
			break;
		case Result.NAME_IN_USE:
			System.out.println("Product with name " + Request.instance().getProductName() + " already exists");
			break;
		case Result.OPERATION_FAILED:
			System.out.println("Product failed to be added");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.println("Product " + result.getProductName() + " added");
			break;
		default:
			System.out.println("An error has occurred");
		}
	}

	/**
	 * Modified from instructional code.
	 * 
	 * @author G.D. Ponsness
	 * 
	 */
	public void changePrice() {
		Request.instance().setProductId(getToken("Enter product id"));
		Request.instance().setProductPrice(getToken("Enter new price"));
		Result result = store.changePrice(Request.instance());
		switch (result.getResultCode()) {
		case Result.PRODUCT_NOT_FOUND:
			System.out.println("Product with id " + Request.instance().getProductId() + " cannot be found");
			break;
		case Result.NOT_DECIMAL:
			System.out
					.println("Price " + Request.instance().getProductPrice() + " cannot be converted to dollar value");
			break;
		case Result.OPERATION_COMPLETED:
			System.out.println("Price changed. \n" + result.getProductName() + "\t" + result.getProductPrice());
			break;
		default:
			System.out.println("An error has occurred");
		}
	}

	/**
	 * Modified from instructional code.
	 * 
	 * @author G.D. Ponsness
	 * 
	 */
	public void checkoutItems() {

	}

	/**
	 * code updated by Richard Fritz Method to be called for removing a member.
	 * Prompts the user for the appropriate values and uses Store method
	 * removeMember() for removing member.
	 */
	public void removeMember() {
		Request.instance().setMemberId(getToken("Enter member id"));
		Result result = store.removeMember(Request.instance());
		switch (result.getResultCode()) {
		case Result.NO_SUCH_MEMBER:
			System.out.println("No such member with id " + Request.instance().getMemberId() + " in member list");
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
	 * Prompts user for product name (partial name is acceptable). Lists all
	 * products that begin with input string provided by user.
	 */
	public void getProductInfo() {

	}

	/**
	 * Prompts user for member name (partial name is acceptable). Lists all members
	 * whose name begins with input string provided by user.
	 */
	public void getMemberInfo() {

	}

	/**
	 * Method to be called for displaying transactions. Prompts the user for the
	 * appropriate values and uses the appropriate store method for displaying
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
	 * Method to be called for displaying outstanding orders.
	 */
	public void getOutstandingOrders() {
		Iterator<Order> result = store.getOutstandingOrders();
		while (result.hasNext()) {
			Order order = (Order) result.next();
			System.out.println(order.getId() + ", " + order.getProductOrdered().getName() + ", "
					+ order.getDateOrdered() + ", " + order.getQtyOrdered());
		}
		System.out.println("\nEnd of Transactions\n");
	}

	/**
	 * code updated by Richard Fritz Displays all members
	 */
	public void getMembers() {
		Iterator<Result> iterator = store.getMembers();
		System.out.println("List of members (Member Name, address, phone, date joined, fee paid, Member Id)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getMemberName() + " " + result.getMemberAddress() + " " + result.getMemberPhone()
					+ " " + result.getMemberDateJoined() + " " + result.getMemberFee() + " " + result.getMemberId());
		}
		System.out.println("End of listing");
	}

	/**
	 * code updated by Richard Fritz Gets and prints all product.
	 */
	public void getProduct() {
		Iterator<Result> iterator = store.getProduct();
		System.out.println("List of product (Product Name, Stock on Hand, Current Price, Reorder Level, Product Id)");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println(result.getProductName() + " " + result.getProductStockOnHand() + " "
					+ result.getProductPrice() + " " + result.getProductReorderLevel() + " " + result.getProductId());
		}
		System.out.println("End of listing");
	}

	/**
	 * Method to be called for processing a shipment.
	 */
	public void processShipment() {
		do {
			Request.instance().setOrderId(getToken("Enter Order ID"));
			Result result = store.processShipment(Request.instance());
			if (result.getResultCode() == Result.OPERATION_COMPLETED) {
				System.out.println(result.getProductName() + "(Product ID: " + result.getProductId() + ") updated!");
				System.out.println("New quantity in stock: " + result.getProductStockOnHand());
			} else {
				System.out.println(result.getResultCode()); // Line added for testing. Remove from final program
				System.out.println("Could not process shipment");
			}
			if (!yesOrNo("Process more orders?")) {
				break;
			}
		} while (true);
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
			case ENROLL_MEMBER:
				enrollMember();
				break;
			case REMOVE_MEMBER:
				removeMember();
				break;
			case ADD_PRODUCT:
				addProduct();
				break;
			case CHECK_OUT_ITEMS:
				checkoutItems();
				break;
			case PROCESS_SHIPMENT:
				processShipment();
				break;
			case CHANGE_PRICE:
				changePrice();
				break;
			case GET_PRODUCT_INFO:
				getProductInfo();
				break;
			case GET_MEMBER_INFO:
				getMemberInfo();
				break;
			case GET_TRANSACTIONS:
				getTransactions();
				break;
			case GET_OUTSTANDING_ORDERS:
				getOutstandingOrders();
				break;
			case GET_MEMBERS:
				getMembers();
				break;
			case GET_PRODUCT:
				getProduct();
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
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		UserInterface.instance().process();
	}
}