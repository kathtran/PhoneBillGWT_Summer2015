package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Map;

/**
 * A GWT remote service that returns a dummy Phone Bill
 *
 * @author Kathleen Tran
 * @version 5.0
 */
@RemoteServiceRelativePath("ping")
public interface PingService extends RemoteService {

    /**
     * Returns the a dummy Phone Bill
     */
    AbstractPhoneBill ping();

    /**
     * Adds a new phone call record to the specified customer's phone bill.
     *
     * @param customerName some name
     * @param callerNumber phone number of the person who called
     * @param calleeNumber phone number of the person who was called
     * @param startTime    time at which the call began
     * @param endTime      time at which the call ended
     * @return the phone bill containing the corresponding phone call record
     */
    AbstractPhoneBill addNewPhoneCall(String customerName, String callerNumber, String calleeNumber, String startTime, String endTime);

    /**
     * Prints out the most recently added phone call record in the specified customer's phone bill.
     *
     * @param customerName some name
     * @return the phone bill containing the corresponding phone call record
     */
    AbstractPhoneBill printMostRecentlyAddedPhoneCall(String customerName);

    /**
     * Prints out the contents of the entire phone bill for the specified customer.
     *
     * @param customerName some name
     * @return the phone bill that belongs to the specified customer
     */
    AbstractPhoneBill printPhoneBill(String customerName);

    /**
     * Prints out all phone bill records on file.
     *
     * @return the mapping that contains all customers and their phone bills.
     */
    Map printAllPhoneBills();
}
