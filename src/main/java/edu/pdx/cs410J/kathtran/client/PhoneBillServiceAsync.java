package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * The client-side interface to the phone bill service
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public interface PhoneBillServiceAsync {

    /**
     * Return the added phone call.
     */
    void addPhoneCall(String[] userInput, AsyncCallback<AbstractPhoneBill> async);
}
