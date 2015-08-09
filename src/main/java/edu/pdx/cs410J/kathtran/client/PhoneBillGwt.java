package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneBillGwt implements EntryPoint {
    private final RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
    private HTML pageDivider;
    private final ScrollPanel README = new ScrollPanel(readme());
    private final VerticalPanel ADD = new VerticalPanel();
    private final VerticalPanel addVPan1 = new VerticalPanel();
    private final VerticalPanel addVPan2 = new VerticalPanel();
    private final VerticalPanel addVPan3 = new VerticalPanel();
    private final VerticalPanel addVPan4 = new VerticalPanel();
    private final HorizontalPanel addHPan1 = new HorizontalPanel();
    private final HorizontalPanel addHPan2 = new HorizontalPanel();
    private final HorizontalPanel addHPan3 = new HorizontalPanel();
    private final Label addPageOutput = new Label("");
    private TextBox addPageCustomerField;
    private TextBox addPageCallerField;
    private TextBox addPageCalleeField;
    private TextBox addPageStartField;
    private TextBox addPageEndField;
    private Button addButton;
    private Button clearButton;

    private final VerticalPanel PRINT = new VerticalPanel();
    private final HorizontalPanel printHPan1 = new HorizontalPanel();
    private final HorizontalPanel printHPan2 = new HorizontalPanel();
    private final ScrollPanel printPageBillOutput = new ScrollPanel();
    private final Label printPageCallOutput = new Label("");
    private TextBox printPageCustomerField1;
    private TextBox printPageCustomerField2;
    private Button printCallButton;
    private Button printBillButton;
    private Button printAllButton;

    private String customerName;
    private String callerNumber;
    private String calleeNumber;
    private String startTime;
    private String endTime;

    private final PingServiceAsync async = GWT.create(PingService.class);

    public void onModuleLoad() {

        // <------- BUILDING ADD PAGE ------->
        loadAddPageLayout();
        ADD.getElement().getStyle().setBorderStyle(Style.BorderStyle.NONE);
        ADD.add(new HTML("<table width=\"615px\"><tr><td align=\"center\"><h1>Add a Phone Call</h1></td></tr></table>"));

        addHPan1.add(new Label("Customer Name"));
        addHPan1.add(addPageCustomerField = new TextBox());
        addPageCustomerField.setPixelSize(440, 15);
        addPageCustomerField.setMaxLength(60);
        addPageCustomerField.getElement().setAttribute("placeholder", "kathtran");

        addVPan1.add(new Label("Caller Number"));
        addVPan1.add(new Label("Call Began"));

        addVPan2.add(addPageCallerField = new TextBox());
        addPageCallerField.setMaxLength(12);
        addPageCallerField.getElement().setAttribute("placeholder", "ex. 000-000-0000");
        addVPan2.add(addPageStartField = new TextBox());
        addPageStartField.setMaxLength(19);
        addPageStartField.getElement().setAttribute("placeholder", "ex. 1/1/2015 12:00 am");

        addVPan3.add(new Label("Callee Number"));
        addVPan3.add(new Label("Call Ended"));

        addVPan4.add(addPageCalleeField = new TextBox());
        addPageCalleeField.setMaxLength(12);
        addPageCalleeField.getElement().setAttribute("placeholder", "ex. 111-111-1111");
        addVPan4.add(addPageEndField = new TextBox());
        addPageStartField.setMaxLength(19);
        addPageEndField.getElement().setAttribute("placeholder", "ex. 2/20/2015 2:00 pm");

        addHPan2.add(addVPan1);
        addHPan2.add(addVPan2);
        addHPan2.add(addVPan3);
        addHPan2.add(addVPan4);

        addHPan3.add(addButton = new Button("Add"));
        addHPan3.add(clearButton = new Button("Clear"));

        addButton.setPixelSize(250, 30);
        clearButton.setPixelSize(250, 30);

        ADD.add(addHPan1);
        ADD.add(addHPan2);
        ADD.add(addHPan3);
        ADD.add(pageDivider = new HTML("<table width=\"615\"><tr height=\"50\"><td><hr width=\"90%\"></td></tr></table>"));
        ADD.add(addPageOutput);

        addButton.addClickHandler(addNewPhoneCall());
        clearButton.addClickHandler(clearPhoneCallFields());
        // <------- END ADD PAGE ------->

        // <------- BUILDING PRINT PAGE ------->
        loadPrintPageLayout();
        PRINT.getElement().getStyle().setBorderStyle(Style.BorderStyle.NONE);
        PRINT.add(new HTML("<table width=\"615px\"><tr><td align=\"center\"><h1>Printing Phone Calls and Bills</h1></td></tr></table>"));

        printHPan1.add(new Label("Most Recently Added Phone Call"));
        printHPan1.add(printPageCustomerField1 = new TextBox());
        printPageCustomerField1.setPixelSize(300, 15);
        printPageCustomerField1.setMaxLength(60);
        printPageCustomerField1.getElement().setAttribute("placeholder", "kathtran");

        printHPan1.add(printCallButton = new Button("Print"));

        printHPan2.add(new Label("Phone Bill"));
        printHPan2.add(printPageCustomerField2 = new TextBox());
        printPageCustomerField2.setPixelSize(250, 15);
        printPageCustomerField2.setMaxLength(60);
        printPageCustomerField2.getElement().setAttribute("placeholder", "kathtran");
        printHPan2.add(printBillButton = new Button("Print One"));
        printHPan2.add(printAllButton = new Button("Print All"));

        printCallButton.setPixelSize(50, 25);
        printBillButton.setPixelSize(75, 25);
        printAllButton.setPixelSize(75, 25);

        PRINT.add(printHPan1);
        PRINT.add(pageDivider = new HTML("<table width=\"615\"><tr height=\"50\"><td><hr width=\"90%\"></td></tr></table>"));
        PRINT.add(printPageCallOutput);
        PRINT.add(printHPan2);
        PRINT.add(pageDivider = new HTML("<table width=\"615\"><tr height=\"50\"><td><hr width=\"90%\"></td></tr></table>"));
        PRINT.add(printPageBillOutput);

        printCallButton.addClickHandler(printMostRecentlyAddedPhoneCall());
        printBillButton.addClickHandler(printPhoneBill());
        printAllButton.addClickHandler(printAllPhoneBills());
//        printAllButton.addClickHandler(printAllPhoneBills());
        // <------- END PRINT PAGE ------->

        // <------- BUILDING SEARCH PAGE ------->

        // <------- END SEARCH PAGE ------->

//        Button pingServerButton = new Button("Ping Server");
//        pingServerButton.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent clickEvent) {
//                PingServiceAsync async = GWT.create(PingService.class);
//                async.ping(new AsyncCallback<AbstractPhoneBill>() {
//
//                    public void onFailure(Throwable ex) {
//                        Window.alert(ex.toString());
//                    }
//
//                    public void onSuccess(AbstractPhoneBill phonebill) {
//                        StringBuilder sb = new StringBuilder(phonebill.toString());
//                        Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
//                        for (AbstractPhoneCall call : calls) {
//                            sb.append(call);
//                            sb.append("\n");
//                        }
//                        Window.alert(sb.toString());
//                    }
//                });
//            }
//        });
//
//        ADD.add(pingServerButton);

        // Set up navigation tabs that will operate as menu interface
        TabLayoutPanel navBar = new TabLayoutPanel(2.5, Style.Unit.EM);
        navBar.add(new HTML("Welcome!"), "Home");
        navBar.add(README, "Help");
        navBar.add(ADD, "Add");
        navBar.add(PRINT, "Print");
//        navBar.add(SEARCH, "Search");

        rootLayoutPanel.add(navBar);
    }

    /**
     * <------- ADD PAGE ------->
     * Adds a phone call to the phone bill.
     *
     * @return handled click event for the Add button
     */
    private ClickHandler addNewPhoneCall() {
        return new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                customerName = addPageCustomerField.getText();
                callerNumber = addPageCallerField.getText();
                calleeNumber = addPageCalleeField.getText();
                startTime = addPageStartField.getText();
                endTime = addPageEndField.getText();

                int check = checkParams(customerName, callerNumber, calleeNumber, startTime, endTime);
                switch (check) {
                    case 1:
                        break;
                    case 0:
                        Window.alert("Please fill in the entire form!");
                        return;
                    case -1:
                        Window.alert("Please enter phone numbers in the correct format");
                    case -2:
                    case -3:
                        Window.alert("Please enter times in the correct format");
                    default:
                        return;
                }

                async.addNewPhoneCall(customerName, callerNumber, calleeNumber, startTime, endTime, new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill phoneBill) {
                        Window.alert("The call has been added!");
                        PhoneBill bill = (PhoneBill) phoneBill;
                        addPageOutput.setText(bill.callAddedMessage((PhoneCall) bill.getMostRecentPhoneCall()));
                    }
                });
            }
        };
    }

    /**
     * <------- ADD PAGE ------->
     * Clears all fields on the ADD page.
     *
     * @return handled click event for the Clear button
     */
    private ClickHandler clearPhoneCallFields() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                addPageCustomerField.getElement().setAttribute("placeholder", "");
                addPageCallerField.getElement().setAttribute("placeholder", "");
                addPageCalleeField.getElement().setAttribute("placeholder", "");
                addPageStartField.getElement().setAttribute("placeholder", "");
                addPageEndField.getElement().setAttribute("placeholder", "");

                addPageCustomerField.setText("");
                addPageCallerField.setText("");
                addPageCalleeField.setText("");
                addPageStartField.setText("");
                addPageEndField.setText("");
            }
        };
    }

    /**
     * <------- PRINT PAGE ------->
     * Prints out the most recently added phone call.
     *
     * @return handled click event for the Print button
     */
    private ClickHandler printMostRecentlyAddedPhoneCall() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                customerName = printPageCustomerField1.getText();

                if (customerName == null || customerName.equals("")) {
                    Window.alert("Please enter a customer name!");
                    return;
                }

                async.printMostRecentlyAddedPhoneCall(customerName, new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill phoneBill) {
                        if (phoneBill != null) {
                            PhoneBill bill = (PhoneBill) phoneBill;
                            printPageCallOutput.setText(bill.prettyPrintMostRecentCall());
                        } else printPageCallOutput.setText("Customer could not be found --");
                    }
                });
            }
        };
    }

    /**
     * <------- PRINT PAGE ------->
     * Prints out the phone bill for the specified customer.
     *
     * @return handled click event for the Print One button
     */
    private ClickHandler printPhoneBill() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                customerName = printPageCustomerField2.getText();

                if (customerName == null || customerName.equals("")) {
                    Window.alert("Please enter a customer name!");
                    return;
                }

                async.printPhoneBill(customerName, new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill phoneBill) {
                        if (phoneBill != null)
                            printPageBillOutput.add(new HTML(((PhoneBill) phoneBill).prettyPrint()));
                        else
                            printPageBillOutput.add(new HTML("Customer could not be found --"));
                    }
                });
            }
        };
    }

    /**
     * <------- PRINT PAGE ------->
     * Prints out all of the phone bills that have been stored for the session.
     *
     * @return handled click event for the Print All button
     */
    private ClickHandler printAllPhoneBills() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                customerName = printPageCustomerField2.getText();

                if (customerName == null || customerName.equals("")) {
                    Window.alert("Please enter a customer name!");
                    return;
                }

                async.printAllPhoneBills(new AsyncCallback<Map>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(Map map) {
                        if (!map.isEmpty()) {
                            String entireBill = new String();
                            for (Object entry : map.entrySet()) {
                                PhoneBill bill = (PhoneBill) entry;
                                entireBill += bill.prettyPrint();
                            }
                            printPageBillOutput.add(new HTML(entireBill));
                        } else printPageBillOutput.add(new HTML("No phone bills currently exist --"));
                    }
                });
            }
        };
    }

    /**
     * Perform a check on all parameters to see if all arguments have been provided
     * and are valid to build a new phone call record.
     *
     * @param customerName some name that may consist of one or more words
     * @param callerNumber the number of the person who called
     * @param calleeNumber the number of the person called
     * @param startTime    the time at which the call began
     * @param endTime      the time at which the call ended
     * @return true if there are no missing/empty parameters, otherwise false
     */
    private int checkParams(String customerName, String callerNumber, String calleeNumber, String startTime, String endTime) {
        ArrayList<String> data = new ArrayList<>();
        data.add(customerName);
        data.add(callerNumber);
        data.add(calleeNumber);
        data.add(startTime);
        data.add(endTime);

        for (String item : data) {
            if (item == null || item.equals(""))
                return 0;
        }

        if (!isValidPhoneNumber(callerNumber) || !isValidPhoneNumber(calleeNumber))
            return -1;
        String[] splitStartTime = startTime.split(" ");
        String[] splitEndTime = endTime.split(" ");
        if (!isValidTimeOfDay(splitStartTime[1]) || !isValidTimeOfDay(splitEndTime[1]))
            return -2;
        try {
            formatDate(startTime);
            formatDate(endTime);
        } catch (IllegalArgumentException ex) {
            return -3;
        }
        return 1;
    }

    /**
     * Determines whether or not some <code>String</code> is of the form
     * <code>nnn-nnn-nnnn</code> where <code>n</code> is a number <code>0-9</code>.
     *
     * @param phoneNumberInput phone number
     * @return True if the form is valid, otherwise false
     */
    private boolean isValidPhoneNumber(String phoneNumberInput) {
        RegExp regExp = RegExp.compile("\\d{3}-\\d{3}-\\d{4}");
        MatchResult numberToBeChecked = regExp.exec(phoneNumberInput);
        return numberToBeChecked != null;
    }

    /**
     * Determines whether or not the time of some <code>String</code> is
     * of the form <code>hh:mm</code> where the hour may be one digit if
     * it is less than the value of nine.
     *
     * @param dateAndTimeInput time
     * @return True if the form is valid, otherwise false
     */
    private boolean isValidTimeOfDay(String dateAndTimeInput) {
        RegExp regExp = RegExp.compile("(0?[1-9]|1[0-2]):[0-5][0-9]");
        MatchResult dateAndTimeToBeChecked = regExp.exec(dateAndTimeInput);
        return dateAndTimeToBeChecked != null;
    }

    /**
     * Determines whether or not some <code>String</code> is of the form
     * <code>MM/dd/yyyy hh:mm a</code> where the leading zero in the month,
     * day, and hour may be omitted.
     *
     * @param dateTimeInput some date, time, and marker
     * @return the date formatted properly
     * @throws IllegalArgumentException when the date cannot be parsed
     */
    private String formatDate(String dateTimeInput) throws IllegalArgumentException {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");
        return dateTimeFormat.parseStrict(dateTimeInput).toString();
    }

    private void loadAddPageLayout() {
        ADD.setSize("615px", "400px");

        addHPan1.setSize("615px", "100%");
        addHPan2.setSize("615px", "100%");
        addHPan3.setSize("615px", "100%");

        addHPan1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        addHPan2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        addHPan3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        addVPan1.setSize("100%", "70%");
        addVPan2.setSize("100%", "70%");
        addVPan3.setSize("100%", "70%");
        addVPan4.setSize("100%", "70%");

        addVPan1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        addVPan2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        addVPan3.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        addVPan4.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        addPageOutput.setPixelSize(615, 50);
    }

    private void loadPrintPageLayout() {
        PRINT.setSize("615px", "100%");

        printHPan1.setSize("615px", "100%");
        printHPan2.setSize("615px", "100%");

        printHPan1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        printHPan2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

        printPageBillOutput.getElement().getStyle().setBorderStyle(Style.BorderStyle.NONE);
        printPageBillOutput.setPixelSize(615, 250);
        printPageCallOutput.setPixelSize(615, 75);
    }

    /**
     * The README file equivalent for the CS410J Phone Bill Application.
     *
     * @return the README formatted in HTML
     */
    protected HTML readme() {
        return new HTML("<head><title>Phone Bill App - Help</title></head><body>" +
                "<p><table width=\"615\"><col width=\"150\"><tr><td colspan=\"2\" align=\"center\">" +
                "<h1><b>README - Phone Bill Application</b></h1></td></tr>" +
                "<tr><td><div id=\"introduction\"><h3><u>Introduction</u></h3></div></td></tr>" +
                "<tr><td valign=\"top\"><b>v1.0</b></td><td>Welcome to the Phone Bill Application! This is a " +
                "command-line application that allows the user to model a phone bill. In this version, the user " +
                "may associate at most one phone record per customer name. However, the information will not be " +
                "stored between each usage. A single phone record consists of the phone number of the caller, the " +
                "phone number that was called, the time at which the call began, and the time at which the call " +
                "ended.</td></tr>" +
                "<tr><td valign=\"top\"><b>v2.0</b></td><td>The program now allows the user to save his or her " +
                "phone bill to an external text file (both new and existing). Users may also load phone bill " +
                "records from existing files. Each file correlates to a single user and his or her phone " +
                "call(s).</td></tr>" +
                "<tr><td valign=\"top\"><b>v3.0</b></td><td>The newest feature that has been added is the option " +
                "to have the phone bill be printed out in a more user-friendly format, to either a separate text " +
                "file or to standard out, complete with the duration of each phone call in minutes. Phone calls " +
                "within the phone bills are now listed chronologically by their beginning time, with the phone " +
                "numbers serving as tie-breakers in appropriate cases. In addition, time stamps are no longer " +
                "recorded in 24-hour time.</td></tr>" +
                "<tr><td valign=\"top\"><b>v4.0</b></td><td>A server/client has been established for this version " +
                "using REST to incorporate a web service to the program. Users may add phone bills to the server " +
                "and search for phone calls belonging to some given phone bill between some given time " +
                "span.</td></tr>" +
                "<tr><td valign=\"top\"><b>v5.0</b></td><td>A web-based user interface using Google Web Toolkit " +
                "has been implemented to support all previous features associated with this phone bill " +
                "application.</td></tr>" +

                "<tr><td colspan=\"2\"><div id=\"functionalities\"><h3><u>Functionalities</u></h3></div></td></tr>" +
                "<tr><td valign=\"top\"><b>Add</b></td><td>Add a new phone call to some specified " +
                "customer's phone bill by supplying the customer name, caller number, callee number, call start " +
                "time, and call end time.</td></tr>" +
                "<tr><td valign=\"top\"><b>Print</b></td><td>Choose from two options: (1) print the most recently added phone " +
                "call, or (2) print all of the phone call records that belong to some specified customer.</td></tr>" +
                "<tr><td valign=\"top\"><b>Search</b></td><td>Search for all existing phone call records that fall under " +
                "some starting time and some ending time.</td></tr>" +

                "<tr><td colspan=\"2\"><div id=\"arguments\"><h3><u>Arguments</u></h3></div></td></tr>" +
                "<tr><td valign=\"top\"><b>[customer]</b></td><td valign=\"top\">Person whose phone bill we're modelling</td></tr>" +
                "<tr><td valign=\"top\"><b>[caller number]</b></td><td valign=\"top\">Phone number of the caller</td></tr>" +
                "<tr><td valign=\"top\"><b>[callee number]</b></td><td valign=\"top\">Phone number of the person called</td></tr>" +
                "<tr><td valign=\"top\"><b>[start time]</b></td><td valign=\"top\">Date and time the call began</td></tr>" +
                "<tr><td valign=\"top\"><b>[end time]</b></td><td valign=\"top\">Date and time the call ended</td></tr>" +
                "<tr><td colspan=\"2\"><br>The customer may contain symbols and consist of more than one word. " +
                "Phone numbers must be of the form nnn-nnn-nnnn where n is a number 0-9. Date and time should be " +
                "in the format: mm/dd/yyyy hh:mm [am/pm] and zeroes may be omitted where " +
                "appropriate.</td></tr>" +

                "<tr><td colspan=\"2\" align=\"center\"><h5><i>CS410J Project 5: A Rich Internet Application for a Phone Bill</i></h5></td></tr>" +
                "<tr><td colspan=\"2\" align=\"center\">&copy; Kathleen Tran Summer 2015</td></tr></table></p></body>");
    }
}
