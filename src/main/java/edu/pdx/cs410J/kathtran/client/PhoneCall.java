package edu.pdx.cs410J.kathtran.client;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Override;
import java.util.Date;

/**
 * Implements the abstract methods that can be found within
 * the {@link AbstractPhoneCall}. It represents a single
 * phone call record, in which there exists a caller number,
 * callee number, start time, and end time.
 * <p>
 * v3.0 UPDATE: Various methods have been implemented to support
 * better formatted dates and times, and to pretty print the
 * details of the phone call.
 * <p>
 * v4.0 UPDATE: An additional constructor has been implemented to
 * parse a single String to create a new PhoneCall object.
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneCall extends AbstractPhoneCall {

    @Override
    public String getCaller() {
        return "123-345-6789";
    }

    @Override
    public Date getStartTime() {
        return new Date();
    }

    public String getStartTimeString() {
        return "START " + getStartTime();
    }

    @Override
    public String getCallee() {
        return "345-677-2341";
    }

    public Date getEndTime() {
        return new Date();
    }

    public String getEndTimeString() {
        return "END " + getEndTime();
    }

}
