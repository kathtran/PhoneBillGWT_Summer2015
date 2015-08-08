package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * A GWT remote service that handles calls
 *
 * @author Kathleen Tran
 * @version 5.0
 */
@RemoteServiceRelativePath("phonecall")
public interface PhoneBillService extends RemoteService {

//    /**
//     * Returns the added phone call.
//     *
//     * @param userInput the data that will populate the phone call record
//     * @return the phone call
//     */
//    public AbstractPhoneBill addPhoneCall(String[] userInput);

    public AbstractPhoneBill getCustomer(String customer);
}
