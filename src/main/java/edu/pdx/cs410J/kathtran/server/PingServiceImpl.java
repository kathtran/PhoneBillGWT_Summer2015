package edu.pdx.cs410J.kathtran.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.kathtran.client.PhoneBill;
import edu.pdx.cs410J.kathtran.client.PhoneCall;
import edu.pdx.cs410J.kathtran.client.PingService;

import java.lang.Override;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService {
    private Map<String, PhoneBill> data = new HashMap<>();

    @Override
    public AbstractPhoneBill ping() {
        PhoneBill phonebill = new PhoneBill();
        phonebill.addPhoneCall(new PhoneCall());
        return phonebill;
    }

    @Override
    public AbstractPhoneBill addNewPhoneCall(String customerName, String callerNumber, String calleeNumber, String startTime, String endTime) {
        PhoneBill phoneBill = new PhoneBill(customerName);
        phoneBill.addPhoneCall(new PhoneCall(callerNumber, calleeNumber, startTime, endTime));
        data.put(customerName, phoneBill);
        return data.get(customerName);
    }

    /**
     * Prints out the most recently added phone call record in the specified customer's phone bill.
     *
     * @param customerName some name
     * @return the phone bill containing the corresponding phone call record
     */
    @Override
    public AbstractPhoneBill printMostRecentlyAddedPhoneCall(String customerName) {
        if (data.get(customerName) != null)
            return data.get(customerName);
        return null;
    }

    /**
     * Prints out the contents of the entire phone bill for the specified customer.
     *
     * @param customerName some name
     * @return the phone bill that belongs to the specified customer
     */
    @Override
    public AbstractPhoneBill printPhoneBill(String customerName) {
        if (data.get(customerName) != null)
            return data.get(customerName);
        return null;
    }

    /**
     * Prints out all phone bill records on file.
     *
     * @return the mapping that contains all customers and their phone bills.
     */
    @Override
    public Map printAllPhoneBills() {
        if (!data.isEmpty())
            return data;
        return null;
    }

    /**
     * Log unhandled exceptions to standard error
     *
     * @param unhandled The exception that wasn't handled
     */
    @Override
    protected void doUnexpectedFailure(Throwable unhandled) {
        unhandled.printStackTrace(System.err);
        super.doUnexpectedFailure(unhandled);
    }
}
