package business.facade;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.Member;
import business.entities.Order;
import business.entities.Product;
import business.entities.Transaction;
import business.entities.iterators.FilteredOrderIterator;
import business.entities.iterators.SafeIterator;

/**
 * The facade class handling all requests from users.
 * 
 * @author Brahma Dathan
 */
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
    private ProductList products = new ProductList();
    private MemberList members = new MemberList();
    private OrderList orders = new OrderList();
    private static Store store;

    /**
     * @author Brahma Dathan and Sarnath Ramnath -
     * @Copyright (c) 2010 Redistribution and use with or without modification,
     *            are permitted provided that the following conditions are met:
     *            - the use is for academic purpose only - Redistributions of
     *            source code must retain the above copyright notice, this list
     *            of conditions and the following disclaimer. - Neither the name
     *            of Brahma Dathan or Sarnath Ramnath may be used to endorse or
     *            promote products derived from this software without specific
     *            prior written permission. The authors do not make any claims
     *            regarding the correctness of the code in this module and are
     *            not responsible for any loss or damage resulting from its use.
     */

    /**
     * The collection class for Product objects
     * 
     * @author Brahma Dathan and Sarnath Ramnath
     */
    private class ProductList implements Iterable<Product>, Serializable {
        private static final long serialVersionUID = 1L;
        private List<Product> products = new LinkedList<Product>();

        /**
         * Checks whether a product with a given product id exists.
         * 
         * @param productId the name of the product
         * @return true if the product exists
         */
        public Product search(String productId) {
            for (Iterator<Product> iterator = products.iterator(); iterator
                    .hasNext();) {
                Product product = (Product) iterator.next();
                if (product.getId().equals(productId)) {
                    return product;
                }
            }
            return null;
        }

        /**
         * Checks whether a product with a given product name exists.
         * 
         * @param productName the name of the product
         * @return true if the product exists
         */
        public Product searchName(String productName) {
            for (Iterator<Product> iterator = products.iterator(); iterator
                    .hasNext();) {
                Product product = (Product) iterator.next();
                if (product.getName().equals(productName)) {
                    return product;
                }
            }
            return null;
        }

        /**
         * Inserts a product into the collection
         * 
         * @param product the product to be inserted
         * @return true if the product could be inserted. Currently always true
         */
        public boolean insertProduct(Product product) {
            products.add(product);
            return true;
        }

        /**
         * Returns an iterator to all products
         * 
         * @return iterator to the collection
         */
        public Iterator<Product> iterator() {
            return products.iterator();
        }

        /**
         * String form of the collection
         */
        public String toString() {
            return products.toString();
        }
    }

    /**
     * The collection class for Member objects
     */
    private class MemberList implements Iterable<Member>, Serializable {
        private static final long serialVersionUID = 1L;
        private List<Member> members = new LinkedList<Member>();

        /**
         * Checks whether a member with a given member id exists.
         * 
         * @param memberId the id of the member
         * @return true if member exists
         */
        public Member search(String memberId) {
            for (Iterator<Member> iterator = members.iterator(); iterator
                    .hasNext();) {
                Member member = iterator.next();
                if (member.getId().equals(memberId)) {
                    return member;
                }
            }
            return null;
        }

        /**
         * Removes a Member from the MemberList
         * 
         * @author Richard
         * @param memberId
         * @return true if Member could be removed
         */
        public boolean removeMember(String memberId) {
            Member member = search(memberId);
            if (member == null) {
                return false;
            } else {
                return members.remove(member);
            }
        }

        /**
         * Inserts a member into the collection
         * 
         * @author Richard
         * @param member the member to be inserted
         * @return true if the member could be inserted. Will always be true
         */
        public boolean insertMember(Member member) {
            members.add(member);
            return true;
        }

        public Iterator<Member> iterator() {
            return members.iterator();
        }

        /**
         * String form of the collection
         */
        @Override
        public String toString() {
            return members.toString();
        }
    }

    /**
     * The collection class for Order objects
     * 
     * @author Brahma Dathan and Sarnath Ramnath
     */
    private class OrderList implements Iterable<Order>, Serializable {
        private static final long serialVersionUID = 1L;
        private List<Order> orders = new LinkedList<Order>();

        /**
         * Checks whether an order with a given order id exists.
         * 
         * @param orderId the id of the order
         * @return true if the order exists
         */
        public Order search(String orderId) {
            for (Iterator<Order> iterator = orders.iterator(); iterator
                    .hasNext();) {
                Order order = (Order) iterator.next();
                if (order.getId().equals(orderId)) {
                    return order;
                }
            }
            return null;
        }

        /**
         * Inserts an order into the collection
         * 
         * @param order the order to be inserted
         * @return true if the order could be inserted. Currently always true
         */
        public boolean insertOrder(Order order) {
            orders.add(order);
            return true;
        }

        public Iterator<Order> getOutstandingOrders() {
            return new FilteredOrderIterator(orders.iterator(),
                    order -> order.isOutstanding());
        }

        /**
         * Returns an iterator to all orders
         * 
         * @return iterator to the collection
         */
        public Iterator<Order> iterator() {
            return orders.iterator();
        }

        /**
         * String form of the collection
         */
        public String toString() {
            return orders.toString();
        }
    }

    /**
     * Private for the singleton pattern Creates the product collection and
     * member collection objects
     */
    private Store() {
    }

    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static Store instance() {
        if (store == null) {
            return store = new Store();
        } else {
            return store;
        }
    }

    /**
     * Adds a product to product list - code updates by Richard Fritz
     * 
     * @param request - request is of type Request that extends DataTransfer.
     *                The fields in DataTransfer that are used in addProduct
     *                include productName, productCurrentPrice,
     *                productReorderLevel
     * @return result - result is of type Result and condition returned will be
     *         OPERATION_COMPLETED or OPERATION_FAILED
     */
    public Result addProduct(Request request) {
        Result result = new Result();
        Product product = new Product(request.getProductName(),
                request.getProductId(), request.getProductPrice(),
                request.getProductReorderLevel(),
                request.getProductStockOnHand());
        Product checkExists = products.search(request.getProductId());
        if (checkExists != null) {
            result.setResultCode(Result.PRODUCT_EXISTS);
            return result;
        }
        checkExists = products.searchName(request.getProductName());
        if (checkExists != null) {
            result.setResultCode(Result.NAME_IN_USE);
            return result;
        }
        if (products.insertProduct(product)) {
            result.setResultCode(Result.OPERATION_COMPLETED);
            result.setProductFields(product);
            Order newOrder = new Order(product,
                    request.getProductReorderLevel() * 2);
            orders.insertOrder(newOrder);
            return result;
        }
        result.setResultCode(Result.OPERATION_FAILED);
        return result;
    }

    /**
     * Modified from instructional code.
     * 
     * @author G.D. Ponsness
     * @param request
     * @return
     */
    public Result changePrice(Request request) {
        Result result = new Result();
        try {
            Double.parseDouble(request.getProductPrice());
        } catch (Error NumberExceptionError) {
            result.setResultCode(Result.NOT_DECIMAL);
            return result;
        }
        Product product = products.search(request.getProductId());
        if (product == null) {
            result.setResultCode(Result.PRODUCT_NOT_FOUND);
            return result;
        }
        product.setPrice(request.getProductPrice());
        result.setResultCode(Result.OPERATION_COMPLETED);
        result.setProductFields(product);
        return result;
    }

    /**
     * Organizes the operations for enrolling a member - code updates by Richard
     * Fritz
     * 
     * @param request - request is of type Request that extends DataTransfer.
     *                the fields in DataTransfer that relate to enrollMember
     *                include memberName, memberPhone, memberFee
     * @return result - result is of type Result and condition returned will be
     *         OPERATION_COMPLETED or OPERATION_FAILED
     */
    public Result enrollMember(Request request) {
        Result result = new Result();
        Member member = new Member(request.getMemberName(),
                request.getMemberAddress(), request.getMemberPhone(),
                request.getMemberFee());
        if (members.insertMember(member)) {
            result.setResultCode(Result.OPERATION_COMPLETED);
            result.setMemberFields(member);
            return result;
        }
        result.setResultCode(Result.OPERATION_FAILED);
        return result;
    }

    /**
     * Removes a member from the member list - code updates by Richard Fritz
     * 
     * @param request - request is of type Request that extends DataTransfer.
     *                the field in DataTransfer that relate to removeMember is
     *                memberId
     * @return result - result is of type Result and condition returned will be
     *         NO_SUCH_MEMBER, OPERATION_COMPLETED or OPERATION_FAILED
     */
    public Result removeMember(Request request) {
        Result result = new Result();
        Member member = members.search(request.getMemberId());
        if (member == null) {
            result.setResultCode(Result.NO_SUCH_MEMBER);
            return result;
        }
        result.setMemberFields(member);

        if (members.removeMember(request.getMemberId())) {
            result.setResultCode(Result.OPERATION_COMPLETED);
            return result;
        }
        result.setResultCode(Result.OPERATION_FAILED);
        return result;
    }

    /**
     * Searches for a given member
     * 
     * @param memberId id of the member
     * @return true iff the member is in the member list collection
     */
    public Result searchMembership(Request request) {
        Result result = new Result();
        Member member = members.search(request.getMemberId());
        if (member == null) {
            result.setResultCode(Result.NO_SUCH_MEMBER);
        } else {
            result.setResultCode(Result.OPERATION_COMPLETED);
            result.setMemberFields(member);
        }
        return result;
    }

    /**
     * Method to process a shipment when it arrives at the store. There is one
     * product per order. Quantity in stock is updated based on the quantity on
     * hand summed with the quantity received in the order.
     * 
     * @param request
     * @return a code representing the outcome
     */
    public Result processShipment(Request request) {
        Result result = new Result();
        Order order = orders.search(request.getOrderId());
        if (order == null) {
            result.setResultCode(Result.NO_ORDER_FOUND);
            return result;
        }
        result.setOrderFields(order);
        Request.instance().setProductId(order.getProductOrdered().getId());
        Product product = products.search(request.getProductId());
        if (product == null) {
            result.setResultCode(Result.PRODUCT_NOT_FOUND);
            return result;
        }
        product.setStockOnHand(
                order.getQtyOrdered() + product.getStockOnHand());
        order.updateStatus(false);
        result.setProductFields(product);
        result.setResultCode(Result.OPERATION_COMPLETED);
        return result;
    }

    /**
     * Returns an iterator to the transactions for a specific member on a
     * certain date
     * 
     * @param memberId member id
     * @param date     date of issue
     * @return iterator to the collection
     */
    public Iterator<Transaction> getTransactions(Request request) {
        Member member = members.search(request.getMemberId());
        if (member == null) {
            return new LinkedList<Transaction>().iterator();
        }
        return member.getTransactionsOnDate(request.getDate());
    }

    /**
     * Retrieves a deserialized version of the store from disk
     * 
     * @return a Store object
     */
    public static Store retrieve() {
        try {
            FileInputStream file = new FileInputStream("StoreData");
            ObjectInputStream input = new ObjectInputStream(file);
            store = (Store) input.readObject();
            Member.retrieve(input);
            return store;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return null;
        }
    }

    /**
     * Serializes the Store object
     * 
     * @return true if the data could be saved
     */
    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("StoreData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(store);
            Member.save(output);
            file.close();
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

    public Iterator<Order> getOutstandingOrders() {
        Iterator<Order> iterator = orders.getOutstandingOrders();
        return iterator;
    }

    /**
     * Returns an iterator to Member info. The Iterator returned is a safe one,
     * in the sense that only copies of the Member fields are assembled into the
     * objects returned via next().
     * 
     * @return an Iterator to Result - only the Member fields are valid.
     */
    public Iterator<Result> getMembers() {
        return new SafeIterator<Member>(members.iterator(),
                SafeIterator.MEMBER);
    }

    /**
     * Returns an iterator to Book info. The Iterator returned is a safe one, in
     * the sense that only copies of the Book fields are assembled into the
     * objects returned via next().
     * 
     * @return an Iterator to Result - only the Book fields are valid.
     */
    public Iterator<Result> getProduct() {
        return new SafeIterator<Product>(products.iterator(),
                SafeIterator.PRODUCT);
    }

    /**
     * String form of the library
     */
    @Override
    public String toString() {
        return products + "\n" + members;
    }
}