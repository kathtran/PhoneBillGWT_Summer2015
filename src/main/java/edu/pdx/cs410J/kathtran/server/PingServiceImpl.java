package edu.pdx.cs410J.kathtran.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.kathtran.client.PhoneBill;
import edu.pdx.cs410J.kathtran.client.PhoneCall;
import edu.pdx.cs410J.kathtran.client.PingService;

import java.lang.Override;

/**
 * The server-side implementation of the Phone Bill service
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService {
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
        return phoneBill;
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
