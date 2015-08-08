package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * A GWT remote service that handles calls
 *
 * @author Kathleen Tran
 * @version 5.0
 */
@RemoteServiceRelativePath("phoneCall")
public interface PhoneBillService extends RemoteService {

    /**
     * Returns the a dummy Phone Bill
     */
    public AbstractPhoneBill ping();

}
