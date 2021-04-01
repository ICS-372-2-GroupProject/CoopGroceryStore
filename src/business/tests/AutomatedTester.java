package business.tests;

import java.util.Calendar;

import business.entities.Member;
import business.entities.Product;
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;

/**
 * This class generates sample automated tests for the Store system using
 * asserts.
 * 
 * @author Brahma Dathan
 */
public class AutomatedTester {
	private Store store;
	private String[] memberNames = { "Rich Fritz", "Ryan Kinsella", "Nalongsone Danddank", "Marc Wedo",
			"Gilbert Ponsness" };
	private String[] addresses = { "123 4th street", "567 8th Street", "910 11th Ave", "1213 14th Ave",
			"1516 17th Ln" };
	private String[] phones = { "123-4567", "234-5678", "345-7890", "987-6543", "876-5432" };
	private String[] dateJoined = { "01-JAN-2021", "02-FEB-2021", "03-MAR-2021", "04-APR-2021", "05-MAY-2021" };
	private String[] fee = { "20", "20", "20", "20", "20" };
	private Member[] members = new Member[5];
	private String[] productName = { "Soda 12pk", "Corn Chips", "Apples 2lb", "Cookies", "Bread", "Eggs 12pk",
			"Watermelon", "Bananas 1lb", "Sliced Ham", "CerealA", "Pasta", "Marinara Sauce", "Salsa", "Canned Tuna",
			"Ground Beef 1lb", "Ribeye 1lb", "Chicken 1lb", "Almonds 1lb", "Frozen Pizza", "Apple Juice" };
	private String[] productId = { "P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10", "P11", "P12",
			"P13", "P14", "P15", "P16", "P17", "P18", "P19", "P20" };
	private int[] stockOnHand = { 10, 12, 14, 16, 18, 10, 12, 14, 16, 18, 10, 12, 14, 16, 18, 10, 12, 14, 16, 18 };
	private String[] currentPrice = { "4.50", "3.25", "5.00", "1.75", "2.75", "4.25", "2.00", "0.95", "7.75", "5.75",
			"1.50", "5.95", "4.85", "1.99", "3.85", "14.50", "5.15", "9.00", "6.75", "2.99" };
	private int[] reorderLevel = { 5, 6, 7, 8, 9, 5, 6, 7, 8, 9, 5, 6, 7, 8, 9, 5, 6, 7, 8, 9 };
	private Product[] products = new Product[20];

	/**
	 * Tests Member creation.
	 */
	public void testEnrollMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberName(memberNames[count]);
			Request.instance().setMemberAddress(addresses[count]);
			Request.instance().setMemberPhone(phones[count]);
			Request.instance().setMemberDateJoined(dateJoined[count]);
			Request.instance().setMemberFee(fee[count]);

			Result result = Store.instance().enrollMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(memberNames[count]);
			assert result.getMemberPhone().equals(phones[count]);
		}
	}

	/**
	 * Tests Member Removal.
	 */
	public void testRemoveMember() {
		Request.instance().setMemberName("Member Beingremoved");
		Request.instance().setMemberAddress("34567 Removed Ave");
		Request.instance().setMemberPhone("555-5555");
		Request.instance().setMemberDateJoined("01-JAN-1999");
		Request.instance().setMemberFee("20");
		// Check that member to remove was successfully added
		Result result = Store.instance().enrollMember(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		assert result.getMemberName().equals("Member Beingremoved");
		assert result.getMemberPhone().equals("555-5555");
		Request.instance().setMemberId(result.getMemberId());
		result = Store.instance().removeMember(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED : result.getResultCode();
		result = Store.instance().removeMember(Request.instance());
		assert result.getResultCode() == Result.NO_SUCH_MEMBER : result.getResultCode();
	}

	/**
	 * Test addProduct
	 */
	public void testAddProduct() {
		for (int count = 0; count < products.length; count++) {
			Request.instance().setProductName(productName[count]);
			Request.instance().setProductId(productId[count]);
			Request.instance().setProductStockOnHand(stockOnHand[count]);
			Request.instance().setProductPrice(currentPrice[count]);
			Request.instance().setProductReorderLevel(reorderLevel[count]);
			Result result = Store.instance().addProduct(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getProductName().equals(productName[count]);
			assert result.getProductId().equals(productId[count]);
		}
	}

	public void testCheckOutItems() {
		Request.instance().setMemberId("M1");
		Result result = Store.instance().searchMembership(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		result = Store.instance().beginTransaction(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		for (int index = 0; index < 3; index++) {
			Request.instance().setProductId(productId[index]);
			Request.instance().setPurchaseAmount(6);
			result = Store.instance().checkOutItem(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
		}
		result = Store.instance().displayPurchases(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		result = Store.instance().finalizeTransaction(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
	}

	public void testProcessShipment() {
		Request.instance().setOrderId("O1");
		Result result = Store.instance().processShipment(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		Request.instance().setOrderId("O20");
		result = Store.instance().processShipment(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		Request.instance().setOrderId("O200");
		result = Store.instance().processShipment(Request.instance());
		assert result.getResultCode() == Result.NO_ORDER_FOUND;
	}

	public void testGetTransactions() {
		Calendar beginDate = Calendar.getInstance();
		beginDate.set(2012, Calendar.JULY, 1, 0, 0, 0);

		Calendar endDate = Calendar.getInstance();
		// Request.instance().setMemberName(memberNames[0]);
		// Request.instance().setMemberAddress(addresses[0]);
		// Request.instance().setMemberPhone(phones[0]);
		// Request.instance().setMemberDateJoined(dateJoined[0]);
		// Request.instance().setMemberFee(fee[0]);
		// endDate.set(2020, Calendar.JULY, 1, 0, 0, 0);
		// members[0] =
		Request.instance().setMemberId("M1");
		Request.instance().setBeginDate(beginDate);
		Request.instance().setEndDate(endDate);
		// Iterator<Result> results = Store.instance()
		// .getTransactions(Request.instance());
		// while (results.hasNext()) {
		// Result result = (Result) results.next();
		// // TODO: Transactions do not have types or titles.
		// // System.out.println(result.getCurrentTransaction().getType() + " "
		// // + result.getCurrentTransaction().getTitle() + "\n");
		// }

	}

	/*
	 * public void testSearchMembership() { Request.instance().setMemberId("M1");
	 * assert Store.instance().searchMembership(Request.instance()).getMemberId().
	 * equals( "M1"); Request.instance().setMemberId("M6"); assert
	 * Store.instance().searchMembership(Request.instance()) == null; }
	 */
	public void testAll() {
		testEnrollMember();
		testRemoveMember();
		testAddProduct();
		testCheckOutItems();
		// testSearchMembership();
		testProcessShipment();
		// testGetTransactions();
	}

	public static void main(String[] args) {
		new AutomatedTester().testAll();
	}
}