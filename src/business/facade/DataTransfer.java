package business.facade;

import business.entities.Member;
import business.entities.Order;
import business.entities.Product;
import business.entities.Transaction;

/**
 * The DataTransfer class is used to facilitate data transfer between Store and
 * UserInterface. It is also used to support iterating over Member and Product
 * objects. The class stores copies of fields that may be sent in either
 * direction.
 * 
 * @author Brahma Dathan
 */
public abstract class DataTransfer {
    private String productId;
    private String productName;
    private String productPrice;
    private int productReorderLevel;
    private int productStockOnHand;
    private String memberId;
    private String memberName;
    private String memberAddress;
    private String memberPhone;
    private String memberDateJoined;
    private String memberFee;
    private String orderId;
    private Product orderedProduct;
    private int orderQuantity;
    private int purchaseAmount;
    private String purchaseTotal;
    private String transactionResult;
    private Transaction currentTransaction;

    /**
     * Sets all fields to either "none", 0, or null (depending on data type).
     */
    public DataTransfer() {
        reset();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductReorderLevel() {
        return productReorderLevel;
    }

    public void setProductReorderLevel(int productReorderLevel) {
        this.productReorderLevel = productReorderLevel;
    }

    public int getProductStockOnHand() {
        return productStockOnHand;
    }

    public void setProductStockOnHand(int productStockOnHand) {
        this.productStockOnHand = productStockOnHand;
    }

    /**
     * Sets all the product-related fields using the product parameter.
     * 
     * @param the product whose fields should be copied.
     */
    public void setProductFields(Product product) {
        productId = product.getId();
        productName = product.getName();
        productPrice = product.getPrice();
        productReorderLevel = product.getReorderLevel();
        productStockOnHand = product.getStockOnHand();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(String memberAddress) {
        this.memberAddress = memberAddress;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberDateJoined() {
        return memberDateJoined;
    }

    public void setMemberDateJoined(String memberDateJoined) {
        this.memberDateJoined = memberDateJoined;
    }

    public String getMemberFee() {
        return memberFee;
    }

    public void setMemberFee(String memberFee) {
        this.memberFee = memberFee;
    }

    /**
     * Sets all the member-related fields using the Member parameter.
     * 
     * @param the member whose fields should be copied.
     */
    public void setMemberFields(Member member) {
        memberId = member.getId();
        memberName = member.getName();
        memberPhone = member.getPhone();
        memberAddress = member.getAddress();
        memberDateJoined = member.getDateJoined();
        memberFee = member.getFee();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Product getOrderedProduct() {
        return orderedProduct;
    }

    public void setOrderedProduct(Product product) {
        this.orderedProduct = product;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int quantity) {
        this.orderQuantity = quantity;
    }

    /**
     * Sets all the order-related fields using the order parameter
     * 
     * @param the order whose fields should be copied
     */
    public void setOrderFields(Order order) {
        orderId = order.getId();
        orderedProduct = order.getProductOrdered();
        orderQuantity = order.getQtyOrdered();
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int amountPurchased) {
        this.purchaseAmount = amountPurchased;
    }

    public String getPurchaseTotal() {
        return purchaseTotal;
    }

    public void setPurchaseTotal(String purchaseTotal) {
        this.purchaseTotal = purchaseTotal;
    }

    public String getTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(String transactionResult) {
        this.transactionResult = transactionResult;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction newTransaction) {
        this.currentTransaction = newTransaction;
    }

    /**
     * Resets all fields to default values.
     */
    public void reset() {
        productId = "no id";
        productName = "no name";
        productPrice = "0";
        productReorderLevel = 0;
        productStockOnHand = 0;
        memberId = "no id";
        memberName = "no name";
        memberPhone = "no phone";
        memberAddress = "no address";
        memberDateJoined = "no date";
        memberFee = "no fee";
        orderId = "no id";
        orderedProduct = null;
        orderQuantity = 0;
        purchaseAmount = 0;
        purchaseTotal = "0";
        transactionResult = "null";
        currentTransaction = null;
    }
}
