package edu.pdx.cs410J.kathtran.client;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.lang.Override;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class PhoneCall extends AbstractPhoneCall implements Comparable {

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
        this.callerNumber = null;
        this.calleeNumber = null;
        this.startTime = null;
        this.endTime = null;
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
     * Creates a phone call from an array of arguments.
     *
     * @param call some string detailing data form the phone call
     */
    public PhoneCall(String[] call) {
        callerNumber = call[1];
        calleeNumber = call[2];
        startTime = call[3];
        endTime = call[4];
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
        return dateFormatter(this.startTime);
    }

    /**
     * @return a textual representation of the time that this phone call
     * was completed
     */
    @Override
    public String getEndTimeString() {
        return dateFormatter(this.endTime);
    }

    /**
     * Formats the date and time to reflect the requirements of Project 3,
     * where the date remains formatted as MM/dd/yyyy, while the time is
     * in a 12-hour format and includes AM/PM.
     *
     * @param dateToFormat some date and time
     * @return date and time formatted using java.text.DateFormat.SHORT
     */
    private String dateFormatter(String dateToFormat) {
        Date date = null;
        DateFormat parseDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            date = parseDate.parse(dateToFormat);
        } catch (ParseException ex) {
            System.err.println("Something went wrong whilst attempting to parse the date");
            System.exit(1);
        }
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
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
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param object the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Object object) throws NullPointerException, ClassCastException {
        PhoneCall comparison = (PhoneCall) object;

        Date thisDate = getDateObject(this.getStartTimeString());
        Date thatDate = getDateObject(comparison.getStartTimeString());

        if (thisDate.equals(thatDate)) {
            int numberCompareResult = comparePhoneNumbers(comparison.getCaller());
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
     * Compares the time between two <code>String</code> objects and returns
     * some value based on their relation.
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
     * Creates a date object of some given date and time.
     *
     * @param dateToGet some date and time
     * @return a Date object of the provided date and time
     */
    public Date getDateObject(String dateToGet) {
        Date date = null;
        DateFormat parseDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            date = parseDate.parse(dateToGet);
        } catch (ParseException ex) {
            System.err.println("Something went wrong whilst attempting to parse the date");
            System.exit(1);
        }
        return date;
    }

    /**
     * Format some date into the SHORT format and return it as a <code>String</code>.
     *
     * @param dateToFormat some date
     * @return date in SHORT format
     */
    public String getShortDateFormat(String dateToFormat) {
        Date date = null;
        DateFormat parseDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        try {
            date = parseDate.parse(dateToFormat);
        } catch (ParseException ex) {
            System.err.println("Something went wrong whilst attempting to parse the date");
            System.exit(1);
        }
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(date);
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
            System.err.println("Something went wrong whilst attempting to parse the phone numbers");
            System.exit(1);
        }
        return 1;
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
        Date date = null;
        DateFormat parseDate = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = parseDate.parse(dateToParse);
        } catch (ParseException ex) {
            System.err.println("Something went wrong whilst attempting to parse the date");
            System.exit(1);
        }
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }

    /**
     * Get the time from some date and time.
     *
     * @param dateToParse some date and time
     * @return the time segment
     */
    private String getJustTime(String dateToParse) {
        String[] split = dateToParse.split(" ");
        Date date = null;
        DateFormat parseDate = new SimpleDateFormat("hh:mm a");
        try {
            date = parseDate.parse(split[1] + " " + split[2]);
        } catch (ParseException ex) {
            System.err.println("Something went wrong whilst attempting to parse the time");
            System.exit(1);
        }
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }

    /**
     * Corrects the casing of some <code>String</code> that is the customer's name.
     *
     * @param nameInput some name provided by the user
     * @return a String where the first letter of each name is capitalized while
     * the remaining letters are lower cased. Each part of the name is separated
     * by a single whitespace.
     */
    public String correctNameCasing(String nameInput) {
        @SuppressWarnings("all")
        String correctedName = new String();
        String[] fullName = nameInput.split(" ");
        for (String name : fullName) {
            char firstLetter = Character.toUpperCase(name.charAt(0));
            String remainingLetters = name.substring(1).toLowerCase();
            correctedName = correctedName.concat(firstLetter + remainingLetters + " ");
        }
        return correctedName.substring(0, correctedName.length() - 1);
    }

    /**
     * Determines whether or not some <code>String</code> is of the form
     * <code>nnn-nnn-nnnn</code> where <code>n</code> is a number <code>0-9</code>.
     *
     * @param phoneNumberInput some phone number provided by the user
     * @return True if the form is valid, otherwise false
     */
    public boolean isValidPhoneNumber(String phoneNumberInput) {
        Pattern phoneNumberFormat = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher numberToBeChecked = phoneNumberFormat.matcher(phoneNumberInput);
        return numberToBeChecked.matches();
    }

    /**
     * Determines the validity of some <code>String</code> representative of the date
     * and time both in regards to the values provided and to their formatting.
     *
     * @param dateInput the month, day, and year
     * @param timeInput the hour and minute(s)
     * @param timeMark  am/pm marker
     * @return True if the both the date and formatting are valid, otherwise false
     * @throws NumberFormatException when the argument cannot be parsed into an Integer
     * @throws ParseException        when the date is invalid
     */
    public boolean isValidDateAndTime(String dateInput, String timeInput, String timeMark) throws
            NumberFormatException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        dateFormat.parse(dateInput);
        return isValidTimeOfDay(timeInput) && (timeMark.equals("AM") || timeMark.equals("PM"));
    }

    /**
     * Determines whether or not the time of some <code>String</code> is
     * of the form <code>hh:mm</code> where the hour may be one digit if
     * it is less than the value of nine.
     *
     * @param timeToCheck time
     * @return True if the form is valid, otherwise false
     */
    public boolean isValidTimeOfDay(String timeToCheck) {
        Pattern timeFormat = Pattern.compile("(0?[1-9]|1[0-2]):[0-5][0-9]");
        Matcher timeToBeChecked = timeFormat.matcher(timeToCheck);
        return timeToBeChecked.matches();
    }
}
