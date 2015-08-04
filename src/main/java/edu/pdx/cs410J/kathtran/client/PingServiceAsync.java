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
}
