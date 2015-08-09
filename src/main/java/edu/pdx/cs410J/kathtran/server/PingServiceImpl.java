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
 * The server-side implementation of the Phone Bill service.
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService {
    private Map<String, PhoneBill> data = new HashMap<>();

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
    public String printAllPhoneBills() {
        String allPhoneBills = "";
        if (!data.isEmpty()) {
            for (Map.Entry<String, PhoneBill> phoneBill : data.entrySet()) {
                PhoneBill bill = (PhoneBill) phoneBill;
                allPhoneBills += bill.prettyPrint();
            }
            return allPhoneBills;
        }
        return null;
    }

    /**
     * Prints out all calls that began within the specified time frame for the specified customer.
     *
     * @param customerName some name
     * @param searchAfter  the lower bound to search for calls in
     * @param searchBefore the upper bound to search for calls in
     * @return a String containing all qualifying phone calls, pretty printed
     */
    @Override
    public String searchForCalls(String customerName, String searchAfter, String searchBefore) {
        String searchResults = "";
        PhoneCall call;
        int after;
        int before;
        boolean atLeastOneExists = false;
        for (Map.Entry<String, PhoneBill> phoneBill : data.entrySet()) {
            if (phoneBill.getKey().equals(customerName)) {
                searchResults += phoneBill.getKey();
                for (Object phoneCall : phoneBill.getValue().getPhoneCalls()) {
                    call = (PhoneCall) phoneCall;
                    after = call.compareTime(call.getStartTimeString(), call.getShortDateFormat(searchAfter));
                    before = call.compareTime(call.getEndTimeString(), call.getShortDateFormat(searchBefore));
                    if ((after == 0 || after == 1) && (before == 0 || before == -1)) {
                        searchResults += call.prettyPrint();
                        atLeastOneExists = true;
                    }
                }
            }
        }
        if (!atLeastOneExists)
            return searchResults;
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
