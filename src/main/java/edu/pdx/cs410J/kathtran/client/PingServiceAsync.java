package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * The client-side interface to the ping service.
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public interface PingServiceAsync {

    /**
     * Return the phone bill containing the newly added phone call record.
     *
     * @param customerName  some name
     * @param callerNumber  phone number of the person who called
     * @param calleeNumber  phone number of the person who was called
     * @param startTime     time at which the call began
     * @param endTime       time at which the call ended
     * @param asyncCallback the phone bill containing the newly added phone call record
     */
    void addNewPhoneCall(String customerName, String callerNumber, String calleeNumber, String startTime, String endTime, AsyncCallback<AbstractPhoneBill> asyncCallback);

    /**
     * Return the phone bill for the specified customer.
     *
     * @param customerName  some name
     * @param asyncCallback the phone bill that belongs to the specified customer
     */
    void printMostRecentlyAddedPhoneCall(String customerName, AsyncCallback<AbstractPhoneBill> asyncCallback);

    /**
     * Return the phone bill for the specified customer.
     *
     * @param customerName  some name
     * @param asyncCallback the phone bill that belongs to the specified customer
     */
    void printPhoneBill(String customerName, AsyncCallback<AbstractPhoneBill> asyncCallback);

    /**
     * Return all phone bills.
     *
     * @param asyncCallback a String containing all phone bills on record, pretty printed
     */
    void printAllPhoneBills(AsyncCallback<String> asyncCallback);

    /**
     * Return all phone calls that began during the specified time frame for the specified customer.
     *
     * @param customerName  some name
     * @param searchAfter   the lower bound to search for calls in
     * @param searchBefore  the upper bound to search for calls in
     * @param asyncCallback a String containing all qualifying phone call records, pretty printed
     */
    void searchForCalls(String customerName, String searchAfter, String searchBefore, AsyncCallback<String> asyncCallback);
}
