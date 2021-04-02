package business.tests;

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
	private String[] memberNames = { "Rich Fritz", "Ryan Kinsella", "Nalongsone Danddank", "Marc Wedo",
			"Gilbert Ponsness" };
	private String[] addresses = { "123 4th street", "567 8th Street", "910 11th Ave", "1213 14th Ave",
			"1516 17th Ln" };
	private String[] phones = { "123-4567", "234-5678", "345-7890", "987-6543", "876-5432" };
	private String[] fee = { "20", "20", "20", "20", "20" };
	private Member[] members = new Member[5];
	private String[] productName = { "Eggs 12pk", "Corn Chips", "Apples 2lb", "Cookies", "Bread", "Soda 12pk",
			"Watermelon", "Bananas 1lb", "Salad Mix", "CerealA", "Pasta", "Marinara Sauce", "Salsa", "Canned Tuna",
			"Ground Beef 1lb", "Ribeye 1lb", "Chicken 1lb", "Almonds 1lb", "Frozen Pizza", "Apple Juice" };
	private String[] productId = { "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8", "P9", "P10", "P11", "P12", "P13",
			"P14", "P15", "P16", "P17", "P18", "P19", "P20" };
	private int[] stockOnHand = { 10, 12, 14, 16, 18, 10, 12, 14, 16, 18, 10, 12, 14, 16, 18, 10, 12, 14, 16, 18 };
	private String[] currentPrice = { "4.50", "3.25", "5.00", "1.75", "2.75", "4.25", "2.00", "0.95", "4.75", ".35",
			"1.50", "5.95", "4.85", "1.99", "7.85", "14.50", "5.15", "9.00", "6.75", "299" };
	private int[] reorderLevel = { 5, 6, 7, 8, 9, 5, 6, 7, 8, 9, 5, 6, 7, 8, 9, 5, 6, 7, 8, 9 };
	private Product[] products = new Product[20];

	/**
	 * Tests Member creation.
	 * 
	 * @author Richard Fritz - Modified from instructional code.
	 */
	public void testEnrollMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberName(memberNames[count]);
			Request.instance().setMemberAddress(addresses[count]);
			Request.instance().setMemberPhone(phones[count]);
			Request.instance().setMemberFee(fee[count]);

			Result result = Store.instance().enrollMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(memberNames[count]);
			assert result.getMemberPhone().equals(phones[count]);
		}
	}

	/**
	 * Tests Member Removal.
	 * 
	 * @author Richard Fritz - Modified from instructional code.
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
	 * 
	 * @author Richard Fritz - Modified from instructional code.
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
		Request.instance().setProductName("Soda 12pk");
		Request.instance().setProductId("P1");
		Request.instance().setProductStockOnHand(5);
		Request.instance().setProductPrice("5.00");
		Request.instance().setProductReorderLevel(10);
		Result result = Store.instance().addProduct(Request.instance());
		assert result.getResultCode() == Result.PRODUCT_EXISTS : result.getResultCode();
		Request.instance().setProductId("P21");
		result = Store.instance().addProduct(Request.instance());
		assert result.getResultCode() == Result.NAME_IN_USE : result.getResultCode();
	}

	/**
	 * Test checkOutItems
	 */
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

	/**
	 * Automated test method to test the processShipment() functionality. This test
	 * will process order #O1. The product is "Eggs 12pk" and it begins with an
	 * inventory of 10 units. The testCheckOutItems() method, which runs prior to
	 * this test then purchases 6 units, bringing the stock on hand down to 4. This
	 * test runs processShipment() and shows that the OPERATION_COMPLETE code was
	 * received. It then shows that the Product's stock on hand has been increased
	 * to 14 units. (Reorder level for this product is 5, so a new order will always
	 * be 10 units.) Finally, the test runs with order #O200, which does not exist,
	 * to show that the NO_ORDER_FOUND code was received.
	 * 
	 * @author Marc Wedo
	 */
	public void testProcessShipment() {
		Request.instance().setOrderId("O1");
		Result result = Store.instance().processShipment(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		assert result.getProductStockOnHand() == 14;

		Request.instance().setOrderId("O200");
		result = Store.instance().processShipment(Request.instance());
		assert result.getResultCode() == Result.NO_ORDER_FOUND;
	}

	/**
	 * Test changePrice
	 */
	public void testChangePrice() {
		Request.instance().setProductId("P10");
		Request.instance().setProductPrice("3.50");
		assert Request.instance().getProductPrice().equals("3.50");
		Request.instance().setProductId("P20");
		Request.instance().setProductPrice("2.99");
		assert Request.instance().getProductPrice().equals("2.99");
		Result result = Store.instance().changePrice(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		Request.instance().setProductId("P50");
		result = Store.instance().changePrice(Request.instance());
		assert result.getResultCode() == Result.PRODUCT_NOT_FOUND;
	}

	public void testAll() {
		testEnrollMember();
		testRemoveMember();
		testAddProduct();
		testCheckOutItems();
		testProcessShipment();
		testChangePrice();
	}

	public static void main(String[] args) {
		new AutomatedTester().testAll();
	}
}