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
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;
import business.tests.AutomatedTester;

/**
 * This class implements the user interface for the Store project. The commands
 * are encoded as integers using a number of static final variables. A number of
 * utility methods exist to make it easier to parse the input.
 */
public class UserInterface {
    private static UserInterface userInterface;
    private BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
    private static Store store;
    private static final int EXIT = 0;
    private static final int ENROLL_MEMBER = 1;
    private static final int REMOVE_MEMBER = 2;
    private static final int ADD_PRODUCT = 3;
    private static final int CHECK_OUT_ITEMS = 4;
    private static final int PROCESS_SHIPMENT = 5;
    private static final int CHANGE_PRICE = 6;
    private static final int RETRIEVE_PRODUCT_INFO = 7;
    private static final int RETRIEVE_MEMBER_INFO = 8;
    private static final int GET_TRANSACTIONS = 9;
    private static final int GET_OUTSTANDING_ORDERS = 10;
    private static final int GET_MEMBERS = 11;
    private static final int GET_PRODUCT = 12;
    private static final int SAVE = 13;
    private static final int HELP = 14;

    /**
     * Made private for singleton pattern. Conditionally looks for any saved
     * data. Otherwise, it gets a singleton Store object.
     */
    private UserInterface() {
        if (yesOrNo("Look for saved data and use it?")) {
            retrieve();
        } else {
            store = store.instance();
            if (yesOrNo(
                    "Do you want to generate a test bed and invoke the functionality using asserts? ")) {
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
                System.out.println("Please input a number");
            }
        } while (true);
    }

    /**
     * Converts the string to a decimal number
     * 
     * @param prompt the string for prompting
     * @return the decimal corresponding to the string
     * @author Richard Fritz - Modified from instructional code.
     */
    public String getDecimalForm(String prompt) {
        do {
            try {
                String item = getToken(prompt);
                @SuppressWarnings("unused") // this is only used to generate an
                                            // error if a non decimal string
                Double number = Double.valueOf(item);
                return item;
            } catch (NumberFormatException nfe) {
                System.out.println("Please input a decimal number");
            }
        } while (true);
    }

    /**
     * Prompts for a date and gets a date object
     * 
     * 
     * @param prompt the prompt
     * 
     * @return the data as a Calendar object
     */
    public Calendar getDate(String prompt) {
        do {
            try {
                Calendar date = new GregorianCalendar();
                String item = getToken(prompt);
                DateFormat dateFormat = SimpleDateFormat
                        .getDateInstance(DateFormat.SHORT);
                date.setTime(dateFormat.parse(item));
                return date;
            } catch (Exception fe) {
                System.out.println("Please input a date as mm/dd/yy");
            }
        } while (true);
    }

    /**
     * Prompts for get Begin And End Date.
     * 
     * @param beginDate - Calendar
     * @param endDate   - Calendar
     * 
     * @return array of Calendar that include begin and end dates.
     */
    public Calendar[] getBeginAndEndDate(Calendar beginDate_,
            Calendar endDate_) {
        Calendar beginDate = beginDate_;
        Calendar endDate = endDate_;
        do {
            try {
                if (beginDate.after(endDate)) {
                    throw new Exception();
                }

                return new Calendar[] { beginDate, endDate };

            } catch (Exception fe) {
                System.out.println(
                        "Please input a begin date before end date correctly!");
                beginDate = getDate(
                        "Please enter the begin date for which you want records as mm/dd/yy");
                endDate = getDate(
                        "Please enter the end date for which you want records as mm/dd/yy");
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
                int value = Integer.parseInt(
                        getToken("Enter command (" + HELP + " for help): "));
                if (value >= EXIT && value <= HELP) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number: ");
            }
        } while (true);
    }

    /**
     * Displays the help screen
     */
    public void help() {
        System.out
                .println("Enter a number between 0 and 14 as explained below:");
        System.out.println("\n" + EXIT + " to Exit");
        System.out.println(ENROLL_MEMBER + " to enroll a member");
        System.out.println(REMOVE_MEMBER + " to remove a member");
        System.out.println(ADD_PRODUCT + " to add a product");
        System.out
                .println(CHECK_OUT_ITEMS + " to check out items for purchase");
        System.out.println(PROCESS_SHIPMENT + " to process a shipment");
        System.out.println(CHANGE_PRICE + " to change the price of a product");
        System.out.println(
                RETRIEVE_PRODUCT_INFO + " to list details of a product");
        System.out
                .println(RETRIEVE_MEMBER_INFO + " to list details of a member");
        System.out.println(
                GET_TRANSACTIONS + " to print all transactions of a member");
        System.out.println(
                GET_OUTSTANDING_ORDERS + " to print all outstanding orders");
        System.out.println(GET_MEMBERS + " to print all members");
        System.out.println(GET_PRODUCT + " to print all product");
        System.out.println(SAVE + " to  save data");
        System.out.println(HELP + " for help");
    }

    /**
     * called for enrolling a member. Prompts the user for the appropriate
     * values and uses the Store method enrollMember() for adding the member.
     * 
     * @author Richard Fritz - Modified from instructional code.
     */
    public void enrollMember() {
        Request.instance().setMemberName(getName("Enter member name"));
        Request.instance().setMemberAddress(getName("Enter address"));
        Request.instance().setMemberPhone(getName("Enter phone"));
        Request.instance().setMemberFee(getDecimalForm("Enter Fee Paid"));
        Result result = store.enrollMember(Request.instance());
        if (result.getResultCode() != Result.OPERATION_COMPLETED) {
            System.out.println("Could not add member");
        } else {
            System.out.println(result.getMemberName() + "'s id is "
                    + result.getMemberId());
        }
    }

    /**
     * Method to be called for adding a product. Prompts the user for the
     * appropriate values and uses the appropriate Store method for adding the
     * product.
     * 
     * @author Richard Fritz - Modified from instructional code.
     */
    public void addProduct() {
        Request.instance().setProductName(getName("Enter Product Name"));
        Request.instance().setProductId(getToken("Enter Product id"));
        Request.instance()
                .setProductStockOnHand(getNumber("Enter Stock on Hand"));
        Request.instance()
                .setProductPrice(getDecimalForm("Enter Current Price"));
        Request.instance()
                .setProductReorderLevel(getNumber("Enter Reorder Level"));
        Result result = store.addProduct(Request.instance());
        switch (result.getResultCode()) {
        case Result.PRODUCT_EXISTS:
            System.out.println("Product with id "
                    + Request.instance().getProductId() + " already exists");
            break;
        case Result.NAME_IN_USE:
            System.out.println("Product with name "
                    + Request.instance().getProductName() + " already exists");
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
     * Gives price and product id to Store and informs user of result.
     * 
     * @author G.D.Ponsness (Modified from instructional code).
     * 
     */
    public void changePrice() {
        Request.instance().setProductId(getToken("Enter product id"));
        Request.instance().setProductPrice(getDecimalForm("Enter new price"));
        Result result = store.changePrice(Request.instance());
        switch (result.getResultCode()) {
        case Result.PRODUCT_NOT_FOUND:
            System.out.println("Product with id "
                    + Request.instance().getProductId() + " cannot be found");
            break;
        case Result.OPERATION_COMPLETED:
            System.out.println("Price changed. \n" + result.getProductName()
                    + "\t" + result.getProductPrice());
            break;
        default:
            System.out.println("An error has occurred");
        }
    }

    /**
     * Initiates and handles checkout process between User and Store instance.
     * 
     * @author G.D.Ponsness
     */
    public void checkOutItems() {
        Request.instance().setMemberId(getToken("Enter member id"));
        Result result = store.searchMembership(Request.instance());
        if (result.getResultCode() != Result.OPERATION_COMPLETED) {
            System.out.println(
                    "No member with id " + Request.instance().getMemberId());
            return;
        }
        result = store.beginTransaction(Request.instance());
        if (result.getResultCode() != Result.OPERATION_COMPLETED) {
            System.out.println("Transaction could not be created");
            return;
        }
        do {
            Request.instance().setProductId(getToken("Enter product id"));
            Request.instance()
                    .setPurchaseAmount(getNumber("Enter amount purchased"));
            result = store.checkOutItem(Request.instance());
            switch (result.getResultCode()) {
            case Result.PRODUCT_NOT_FOUND:
                System.out.println("No product with id "
                        + Request.instance().getProductId());
                break;
            case Result.OPERATION_COMPLETED:
                System.out.println(
                        result.getProductName() + " scanned for checkout.");
                break;
            default:
                System.out.println("An error has occurred. Please rescan item");
            }
        } while (yesOrNo("Check out another item?"));
        result = store.displayPurchases(Request.instance());
        if (result.getResultCode() != Result.OPERATION_COMPLETED) {
            System.out.println(
                    "An error has occurred. Please restart transaction");
            return;
        }
        System.out.println(result.getTransactionResult());
        if (!yesOrNo("Has the member paid total amount due (with cash)? \n")) {
            System.out.println("The transaction has been cancelled");
            return;
        }
        result = store.finalizeTransaction(Request.instance());
        switch (result.getResultCode()) {
        case Result.OPERATION_FAILED:
            System.out
                    .println("CRITCAL ERROR: Transaction could not be saved ");
            break;
        case Result.OPERATION_COMPLETED:
            System.out.println(
                    "Transaction complete for member " + result.getMemberId());
            System.out.println(result.getTransactionResult());
            break;
        default:
            System.out
                    .println("CRITCAL ERROR: Inventory has not been adjusted");
        }
    }

    /**
     * Method to be called for removing a member. Prompts the user for the
     * appropriate values and uses Store method removeMember() for removing
     * member.
     * 
     * @author Richard Fritz - Modified from instructional code.
     */
    public void removeMember() {
        Request.instance().setMemberId(getToken("Enter member id"));
        Result result = store.removeMember(Request.instance());
        switch (result.getResultCode()) {
        case Result.NO_SUCH_MEMBER:
            System.out.println("No such member with id "
                    + Request.instance().getMemberId() + " in member list");
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
     * 
     * @author Ryan Kinsella
     */
    public void retrieveProductInfo() {
        String name = getName("Product name starts with");
        Boolean productFound = false;
        Iterator<Result> iterator = store.getProduct();
        while (iterator.hasNext()) {
            Result result = iterator.next();
            if (result.getProductName().toUpperCase()
                    .startsWith(name.toUpperCase())) {
                System.out.println(
                        result.getProductName() + "\t" + result.getProductId()
                                + "\t" + result.getProductPrice() + "\t"
                                + result.getProductStockOnHand() + "\t"
                                + result.getProductReorderLevel());
                productFound = true;
            }
        }
        if (productFound == false) {
            System.out.println("Product name not found.\n");
        } else {
            System.out.println();
        }

    }

    /**
     * Prompts user for member name (partial name is acceptable). Lists all
     * members whose name begins with input string provided by user.
     * 
     * @author Ryan Kinsella
     */
    public void retrieveMemberInfo() {
        String name = getName("Member name starts with");
        Boolean memberFound = false;
        Iterator<Result> iterator = store.getMembers();
        while (iterator.hasNext()) {
            Result result = iterator.next();
            if (result.getMemberName().toUpperCase()
                    .startsWith(name.toUpperCase())) {
                System.out.println(result.getMemberName() + "\t"
                        + result.getMemberAddress() + "\t"
                        + result.getMemberFee() + "\t" + result.getMemberId());
                memberFound = true;
            }
        }
        if (memberFound == false) {
            System.out.println("Member name not found.\n");
        } else {
            System.out.println();
        }

    }

    /**
     * Method to be called for displaying transactions. Prompts the user for the
     * appropriate memberID, beginning date and ending date for using the
     * appropriate store method for displaying transactions.
     * 
     * @author Nalongsone Danddank
     * 
     */
    public void getTransactions() {
        Request.instance().setMemberId(getToken("Enter member id"));
        Result resultOfId = store.searchMembership(Request.instance());
        if (resultOfId.getResultCode() != Result.OPERATION_COMPLETED) {
            System.out.println(
                    "No member with id: " + Request.instance().getMemberId());
            return;
        }
        Calendar beginAndEndDate[] = getBeginAndEndDate(getDate(
                "Please enter the begin date for which you want records as mm/dd/yy"),
                getDate("Please enter the end date for which you want records as mm/dd/yy"));
        Request.instance().setBeginDate(beginAndEndDate[0]);
        Request.instance().setEndDate(beginAndEndDate[1]);
        Iterator<Result> results = store.getTransactions(Request.instance());
        System.out.println("Previous transactions for member "
                + Request.instance().getMemberId() + ":\n");
        while (results.hasNext()) {
            Result result = (Result) results.next();
            System.out.println(result.getCurrentTransaction());
        }
        System.out.println("\nEnd of listing\n");
    }

    /**
     * Method iterates through OrderList and returns a list of orders that are
     * currently outstanding (not yet received and processed).
     * 
     * @author Marc Wedo
     * @param None
     * @return List of outstanding order (printed on console)
     */
    public void getOutstandingOrders() {
        Iterator<Order> result = store.getOutstandingOrders();
        System.out.println("List of Outstanding Orders\n"
                + "(Order Number, Product Name, Order Date/Time, Quantity Ordered)\n");
        while (result.hasNext()) {
            Order order = (Order) result.next();
            System.out.println(order.getId() + "\t"
                    + order.getProductOrdered().getName() + "\t"
                    + order.getDateOrdered() + "\t" + order.getQtyOrdered());
        }
        System.out.println("\nEnd of listing\n");
    }

    /**
     * Displays all members
     * 
     * @author Richard Fritz - Modified from instructional code.
     */
    public void getMembers() {
        Iterator<Result> iterator = store.getMembers();
        System.out.println("List of members\n" + padString("Member Name", 20)
                + padString("address", 20) + padString("phone", 12)
                + padString("date joined", 30) + padString("fee", 5)
                + padString("Member Id", 10));
        while (iterator.hasNext()) {
            Result result = iterator.next();
            System.out.println(padString(result.getMemberName(), 20)
                    + padString(result.getMemberAddress(), 20)
                    + padString(result.getMemberPhone(), 12)
                    + padString(result.getMemberDateJoined(), 30)
                    + padString(result.getMemberFee(), 10)
                    + padString(result.getMemberId(), 10));
        }

        System.out.println("End of listing");
    }

    /**
     * Pads the string with spaces for displaying output
     * 
     * @author Richard Fritz - Modified from instructional code.
     */
    public String padString(String stringToPad, int width) {
        int stringLength = stringToPad.length();
        int pad = width - stringLength;
        String padding = "";
        for (int i = 0; i < pad; i++) {
            padding = padding + " ";
        }
        return stringToPad + padding;
    }

    /**
     * prints all product.
     * 
     * @author Richard Fritz - Modified from instructional code.
     */
    public void getProduct() {
        Iterator<Result> iterator = store.getProduct();
        System.out.println("List of products\n" + padString("Product Name", 20)
                + padString("Stock on Hand", 16)
                + padString("Current Price", 15)
                + padString("Reorder Level", 15) + padString("Product Id", 10));
        while (iterator.hasNext()) {
            Result result = iterator.next();
            System.out
                    .println(
                            padString(result.getProductName(), 20)
                                    + padString(
                                            Integer.toString(result
                                                    .getProductStockOnHand()),
                                            16)
                                    + padString(result.getProductPrice(), 15)
                                    + padString(
                                            Integer.toString(result
                                                    .getProductReorderLevel()),
                                            15)
                                    + padString(result.getProductId(), 10));
        }

        System.out.println("End of listing");
    }

    /**
     * Method for processing a shipment that has been received at the store.
     * This updates the quantity in stock based on the quantity received in the
     * shipment, then displays the product that was updated and the new quantity
     * in stock. Processed shipments are marked as no longer outstanding using
     * the orderOutstanding field in Order.
     * 
     * @author Marc Wedo
     * @param None
     * @return Product ID, Product Name, and current quantity in stock (printed
     *         on console)
     */
    public void processShipment() {
        do {
            Request.instance().setOrderId(getToken("Enter Order ID"));
            Result result = store.processShipment(Request.instance());
            if (result.getResultCode() == Result.OPERATION_COMPLETED) {
                System.out.println(result.getProductName() + "(Product ID: "
                        + result.getProductId() + ") updated!");
                System.out.println("New quantity in stock: "
                        + result.getProductStockOnHand());
            } else {
                System.out.println("Could not process shipment");
            }
            if (!yesOrNo("Process more orders?")) {
                break;
            }
        } while (true);
    }

    /**
     * Method to be called for saving the Store object. Uses the appropriate
     * Store method for saving.
     */
    private void save() {
        if (store.save()) {
            System.out.println(
                    " The store has been successfully saved in the file StoreData \n");
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
                    System.out.println(
                            " The Store has been successfully retrieved from the file StoreData \n");
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
                checkOutItems();
                break;
            case PROCESS_SHIPMENT:
                processShipment();
                break;
            case CHANGE_PRICE:
                changePrice();
                break;
            case RETRIEVE_PRODUCT_INFO:
                retrieveProductInfo();
                break;
            case RETRIEVE_MEMBER_INFO:
                retrieveMemberInfo();
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
     * @param args not used
     */
    public static void main(String[] args) {
        UserInterface.instance().process();
    }
}