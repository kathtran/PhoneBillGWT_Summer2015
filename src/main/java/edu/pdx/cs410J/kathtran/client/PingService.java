package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;

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

    AbstractPhoneBill getCustomerName(String name);
}
