package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.i18n.client.DateTimeFormat;
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
 * <p>
 * v5.0 UPDATE: Cleaned up a lot of the code and Date-related methods
 * are now implemented using the GWT DateTimeFormat class.
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {

//    @Override
//    public String getCaller() {
//        return "123-345-6789";
//    }
//
//    @Override
//    public Date getStartTime() {
//        return new Date();
//    }
//
//    public String getStartTimeString() {
//        return "START " + getStartTime();
//    }
//
//    @Override
//    public String getCallee() {
//        return "345-677-2341";
//    }
//
//    public Date getEndTime() {
//        return new Date();
//    }
//
//    public String getEndTimeString() {
//        return "END " + getEndTime();
//    }


    /**
     * The phone number of the caller
     */
    private String callerNumber;

    /**
     * The phone number of the person who was called
     */
    private String calleeNumber;

    /**
     * The time at which the call began
     */
    private String startTime;

    /**
     * The time at which the call ended
     */
    private String endTime;

    /**
     * Default constructor.
     */
    public PhoneCall() {
    }

    /**
     * Constructor that specifies all of the fields existent within a call record.
     *
     * @param callerNumber the number of the person who called
     * @param calleeNumber the number of the person who was called
     * @param startTime    the time at which the call began
     * @param endTime      the time at which the call ended
     */
    public PhoneCall(String callerNumber, String calleeNumber, String startTime, String endTime) {
        this.callerNumber = callerNumber;
        this.calleeNumber = calleeNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Parses the output from the toString method of the AbstractPhoneCall class.
     *
     * @param call some string detailing data from the phone call
     */
    public PhoneCall(String call) {
        String[] split = call.split(" ");
        callerNumber = split[3];
        calleeNumber = split[5];
        startTime = split[7] + " " + split[8] + " " + split[9];
        endTime = split[11] + " " + split[12] + " " + split[13];
    }

    /**
     * @return the phone number of the person who originated this phone call
     */
    @Override
    public String getCaller() {
        return this.callerNumber;
    }

    /**
     * @return the phone number of the person who received this phone call
     */
    @Override
    public String getCallee() {
        return this.calleeNumber;
    }

    /**
     * @return a textual representation of the time that this phone call
     * was originated
     */
    @Override
    public String getStartTimeString() {
        return getDateObject(this.startTime).toString();
    }

    /**
     * @return a textual representation of the time that this phone call
     * was completed
     */
    @Override
    public String getEndTimeString() {
        return getDateObject(this.endTime).toString();
    }

    /**
     * Creates a date object of some given date and time.
     *
     * @param dateToGet some date and time
     * @return a Date object of the provided date and time
     */
    public Date getDateObject(String dateToGet) {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yy h:mm a");
        return dateTimeFormat.parseStrict(dateToGet);
    }

    /**
     * Calculate the duration of the phone call.
     *
     * @return the duration of the call, in minutes.
     */
    public long getCallDuration() {
        Date start = getDateObject(startTime);
        Date end = getDateObject(endTime);
        long duration = end.getTime() - start.getTime();
        return (duration / (60 * 1000));
    }

    /**
     * Compares this call with the specified call for order. Returns a
     * negative integer, zero, or a positive integer as this call is less
     * than, equal to, or greater than the specified call.
     *
     * @param call the call to be compared.
     * @return a negative integer, zero, or a positive integer as this call
     * is less than, equal to, or greater than the specified call.
     * @throws NullPointerException if the specified call is null
     * @throws ClassCastException   if the specified call's type prevents it
     *                              from being compared to this call.
     */
    @Override
    public int compareTo(PhoneCall call) throws NullPointerException, ClassCastException {
        Date thisDate = getDateObject(this.getStartTimeString());
        Date thatDate = getDateObject(call.getStartTimeString());

        if (thisDate.equals(thatDate)) {
            int numberCompareResult = comparePhoneNumbers(call.getCaller());
            if (numberCompareResult == 0) {
                return 0;
            } else
                return numberCompareResult;
        } else if (thisDate.before(thatDate))
            return -1;
        else
            return 1;
    }

    /**
     * Compares this object's caller number with the specified object's caller number.
     * Returns a negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param phoneNumberToBeCompared some phone number
     * @return a negative integer, zero, or a positive integer as this object's caller
     * number is less than, equal to, or greater than the specified object's caller number.
     */
    private int comparePhoneNumbers(String phoneNumberToBeCompared) {
        try {
            String thisCaller = this.callerNumber.replaceAll("-", "");
            String thatCaller = phoneNumberToBeCompared.replaceAll("-", "");
            Long thisNumber = Long.parseLong(thisCaller);
            Long thatNumber = Long.parseLong(thatCaller);

            if (thisNumber < thatNumber)
                return -1;
            else if (thisNumber > thatNumber)
                return 1;
            else
                return 0;
        } catch (NumberFormatException ex) {
            return -2;
        }
    }

    /**
     * Creates and returns a <code>String</code> where all of the data
     * associated with the phone call has been nicely formatted.
     *
     * @return aesthetically pleasing phone call description
     */
    public String prettyPrint() {
        boolean displayOneDate = false;
        if (getJustDate(this.startTime).equals(getJustDate(this.endTime)))
            displayOneDate = true;
        String call = "\n  " + getJustDate(this.startTime);
        call += "\t";
        call += this.callerNumber + "\t" + this.calleeNumber + "\t" + getJustTime(this.startTime);
        call += "\t";
        if (getJustTime(this.startTime).length() == 7)
            call += "\t";
        call += getJustTime(this.endTime);
        call += "\t";
        if (getJustTime(this.startTime).length() == 7)
            call += "\t";
        call += getCallDuration() + "\n";
        if (!displayOneDate)
            call += "  " + getJustDate(this.endTime) + "\n";
        return call;
    }

    /**
     * Get the date from some date and time.
     *
     * @param dateToParse some date and time
     * @return the date segment
     */
    private String getJustDate(String dateToParse) {
        String[] split = dateToParse.split(" ");
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yy");
        return dateTimeFormat.parseStrict(split[0]).toString();
    }

    /**
     * Get the time from some date and time.
     *
     * @param dateToParse some date and time
     * @return the time segment
     */
    private String getJustTime(String dateToParse) {
        String[] split = dateToParse.split(" ");
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("h:mm a");
        return dateTimeFormat.parseStrict(split[1] + " " + split[2]).toString();
    }
}
