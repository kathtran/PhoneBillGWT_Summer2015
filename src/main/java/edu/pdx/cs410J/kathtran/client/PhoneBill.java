package edu.pdx.cs410J.kathtran.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Implements the abstract methods that can be found within the
 * {@link AbstractPhoneBill} in addition to new methods that support
 * the construction of the phone bill skeleton. The customer's
 * name as well as their collection of phone call records are
 * maintained here.
 * <p>
 * v3.0 UPDATE: A pretty print method has been implemented. It
 * nicely formats the phone bill and its corresponding phone calls.
 * <p>
 * v4.0 UPDATE: Minor edits to the formatting of the pretty print method.
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneBill extends AbstractPhoneBill {
    private Collection<AbstractPhoneCall> calls = new ArrayList<AbstractPhoneCall>();

    @Override
    public String getCustomer() {
        return "CS410J";
    }

    @Override
    public void addPhoneCall(AbstractPhoneCall call) {
        this.calls.add(call);
    }

    @Override
    public Collection getPhoneCalls() {
        return this.calls;
    }
}
