package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * The client-side interface to the ping service
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public interface PingServiceAsync {

    /**
     * Return the current date/time on the server
     */
    void ping(AsyncCallback<AbstractPhoneBill> async);

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
     * @param customerName some name
     * @param asyncCallback the phone bill that belongs to the specified customer
     */
    void printPhoneBill(String customerName, AsyncCallback<AbstractPhoneBill> asyncCallback);
}
