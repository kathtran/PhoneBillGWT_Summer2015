package edu.pdx.cs410J.kathtran.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Implements the abstract methods that can be found within the
 * {@link AbstractPhoneBill} in addition to new methods that support
 * the construction of the phone bill skeleton. The customer's
 * name as well as their collection of phone call records are
 * maintained here.
 * <p>
 * v3.0 UPDATE: A pretty print method has been implemented. It
 * nicely formats the phone bill and its corresponding phone calls.
 * <p>
 * v4.0 UPDATE: Minor edits to the formatting of the pretty print method.
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneBill extends AbstractPhoneBill {
//    private String customer;
//    private Collection<AbstractPhoneCall> calls = new ArrayList<>();
//
//    public PhoneBill() {}
//
//    public PhoneBill(String name) {
//        this.customer = name;
//    }
//
//    @Override
//    public String getCustomer() {
//        return customer;
//    }
//
//    @Override
//    public void addPhoneCall(AbstractPhoneCall call) {
//        this.calls.add(call);
//    }
//
//    @Override
//    public Collection getPhoneCalls() {
//        return this.calls;
//    }

    /**
     * The customer's name. May consist of one or more words,
     * and be comprised of any character, symbol, or number.
     */
    private String customer;

    /**
     * All phone call records that are associated with the
     * customer. Each record, or item, is an instance of the
     * {@link PhoneCall} class.
     */
    private ArrayList<PhoneCall> phoneCalls = new ArrayList<>();

    /**
     * Default constructor.
     */
    public PhoneBill() {
    }

    /**
     * Constructor that specifies the customer's name.
     *
     * @param customer a name that may consist of one or more
     *                 words, as some String
     */
    public PhoneBill(String customer) {
        this.customer = customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return this.customer;
    }

    /**
     * Adds a phone call record to this phone bill.
     *
     * @param call an instance of the {@link PhoneCall} class that
     *             contains the caller's phone number, callee's phone
     *             number, and start and end times of the call
     */
    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        this.phoneCalls.add((PhoneCall) call);
    }

    /**
     * @return all of the phone calls (as instances of {@link
     * AbstractPhoneCall}) in this phone bill
     */
    @Override
    public Collection getPhoneCalls() {
        return this.phoneCalls;
    }

    /**
     * Gets the call record for the most recent phone call made.
     *
     * @return the call record at the end of the list
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     */
    public Object getMostRecentPhoneCall() throws ArrayIndexOutOfBoundsException {
        return this.phoneCalls.get(phoneCalls.size() - 1);
    }

    /**
     * Sorts the phone calls in the phone bill by starting time. Ties are
     * broken by comparing the callers' phone numbers.
     */
    public void sortPhoneCalls() {
        Collections.sort(this.phoneCalls);
    }

    /**
     * Prints out the phone bill and all of its call records in
     * a user-friendly format.
     *
     * @return the entire phone bill in its new pretty format
     */
    public String prettyPrint() {
        sortPhoneCalls();
        String divider = "  ====================";
        int count = 0;
        while (count < customer.length()) {
            divider += "=";
            count += 1;
        }
        String entireBill = "CS410J Phone Bill\n" + divider +
                "\n  No. of Calls on Record: " + this.phoneCalls.size() +
                "\n\n  Date(s)\tCaller\t\tCallee\t\tCall Began\tCall Ended\tDuration (mins)";
        for (Object call : getPhoneCalls()) {
            PhoneCall phoneCall = (PhoneCall) call;
            entireBill += phoneCall.prettyPrint();
        }
        return entireBill;
    }

    /**
     * Prints out the most recently added phone call record in the
     * phone bill in a user-friendly format.
     *
     * @return the phone bill with only the most recently added phone call record displayed
     */
    public String prettyPrintMostRecentCall() {
        sortPhoneCalls();
        String divider = "  ====================";
        int count = 0;
        while (count < customer.length()) {
            divider += "=";
            count += 1;
        }
        String entireBill = "CS410J Phone Bill\n" + divider +
                "\n  No. of Calls on Record: " + this.phoneCalls.size() +
                "\n\n  Date(s)\tCaller\t\tCallee\t\tCall Began\tCall Ended\tDuration (mins)";
        entireBill += this.phoneCalls.get(phoneCalls.size() - 1).prettyPrint();
        return entireBill;
    }

    /**
     * An output message for the client to display when a phone call has been added.
     *
     * @param call some phone call
     * @return the message notifying that the specified phone call has been added to
     * its respective customer's phone bill
     */
    public String callAddedMessage(PhoneCall call) {
        return call.toString() + " has been added to " + customer + "'s phone bill!";
    }
}
