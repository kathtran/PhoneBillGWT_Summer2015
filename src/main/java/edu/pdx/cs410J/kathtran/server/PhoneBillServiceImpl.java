package edu.pdx.cs410J.kathtran.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.kathtran.client.PhoneBill;
import edu.pdx.cs410J.kathtran.client.PhoneCall;
import edu.pdx.cs410J.kathtran.client.PhoneBillService;

import java.lang.Override;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService {
    private Map<String, PhoneBill> data = new HashMap<>();

    /**
     * Returns the added phone call.
     *
     * @param userInput the data that will populate the phone call record
     * @return the phone call
     */
    @Override
    public AbstractPhoneBill addPhoneCall(String[] userInput) {
        PhoneBill phoneBill = new PhoneBill(userInput[0]);
        PhoneCall phoneCall = new PhoneCall(userInput);
        phoneBill.addPhoneCall(phoneCall);
        this.data.put(phoneBill.getCustomer(), phoneBill);
        return this.data.get(phoneBill.getCustomer());
    }
}
