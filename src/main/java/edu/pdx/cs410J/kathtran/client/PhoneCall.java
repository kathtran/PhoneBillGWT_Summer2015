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
 * are now implemented using the GWT DateTimeFormat class. Pretty Print
 * is no longer used, instead it has been replaced with Simple Print.
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {

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
        return DateTimeFormat.getFormat("M/d/yy h:mm a").format(getDateObject(this.startTime));
    }

    /**
     * @return a textual representation of the time that this phone call
     * was completed
     */
    @Override
    public String getEndTimeString() {
        return DateTimeFormat.getFormat("M/d/yy h:mm a").format(getDateObject(this.endTime));
    }

    /**
     * Gets the SHORT date format equivalent for some date <code>String</code>.
     *
     * @param date some date, time, and marker
     * @return the date properly formatted to SHORT standards
     */
    public String getShortDateFormat(String date) {
        return DateTimeFormat.getFormat("M/d/yy h:mm a").format(getDateObject(date));
    }

    /**
     * Creates a date object of some given date and time.
     *
     * @param dateToGet some date and time
     * @return a Date object of the provided date and time
     */
    public Date getDateObject(String dateToGet) {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("M/d/yy h:mm a");
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
     * Compares the time between two <code>String</code> objects and returns
     * some value based on their relation. This is used for the Search feature.
     *
     * @param thisTime some time String
     * @param thatTime some time String
     * @return a negative integer, zero, or a positive integer as this thatTime
     * is less than, equal to, or greater than the specified thatTime.
     * @throws NullPointerException if the specified thatTime is null
     */
    public int compareTime(String thisTime, String thatTime) throws NullPointerException {
        Date thisDate = getDateObject(thisTime);
        Date thatDate = getDateObject(thatTime);

        if (thisDate.equals(thatDate))
            return 0;
        if (thisDate.before(thatDate))
            return -1;
        if (thisDate.after(thatDate))
            return 1;
        return 2;
    }

    /**
     * Creates and returns a <code>String</code> where all of the data
     * associated with the phone call has been nicely formatted.
     *
     * @return aesthetically pleasing phone call description
     */
    @Deprecated
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

    public String getDateInterval() {
        if (getJustDate(this.startTime).equals(getJustDate(this.endTime)))
            return getJustDate(this.startTime);
        return getJustDate(this.startTime) + "-" + getJustDate(this.endTime);
    }

    /**
     * Get the date from some date and time.
     *
     * @param dateToParse some date and time
     * @return the date segment
     */
    private String getJustDate(String dateToParse) {
        String[] split = dateToParse.split(" ");
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("M/d/yy");
        Date date = dateTimeFormat.parseStrict(split[0]);
        return DateTimeFormat.getFormat("M/d/yy").format(date);
    }

    /**
     * Get the time from some date and time.
     *
     * @param timeToParse some date and time
     * @return the time segment
     */
    public String getJustTime(String timeToParse) {
        String[] split = timeToParse.split(" ");
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("h:mm a");
        Date time = dateTimeFormat.parseStrict(split[1] + split[2]);
        return DateTimeFormat.getFormat("h:mm a").format(time);
    }

    /**
     * Revised and condensed version of Pretty Print for GWT.
     *
     * @return phone call record, formatted simply
     */
    public String toSimple() {
        return "@<b>" + this.getStartTimeString() + "</b> - " + getJustDate(this.getEndTimeString()) + " call from " +
                callerNumber + " to " + calleeNumber + " that lasted " + getCallDuration() + " minutes.<br>";
    }
}
