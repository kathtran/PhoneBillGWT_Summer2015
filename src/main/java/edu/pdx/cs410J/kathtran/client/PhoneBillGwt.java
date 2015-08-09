package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneBillGwt implements EntryPoint {
    private final RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
    private final ScrollPanel README = new ScrollPanel(readme());
    private final VerticalPanel ADD = new VerticalPanel();
    private final HorizontalPanel addHPan1 = new HorizontalPanel();
    private final HorizontalPanel addHPan2 = new HorizontalPanel();
    private final HorizontalPanel addHPan3 = new HorizontalPanel();
    private final VerticalPanel addVPan1 = new VerticalPanel();
    private final VerticalPanel addVPan2 = new VerticalPanel();
    private final VerticalPanel addVPan3 = new VerticalPanel();
    private final VerticalPanel addVPan4 = new VerticalPanel();
    private TextBox customerField;
    private TextBox callerField;
    private TextBox calleeField;
    private TextBox startField;
    private TextBox endField;
    private Button addButton;
    private Button clearButton;

    public void onModuleLoad() {

        // <------- BUILDING ADD PAGE ------->
        loadAddPageLayout();
        ADD.getElement().getStyle().setBorderStyle(Style.BorderStyle.NONE);
        ADD.add(new HTML("<table width=\"600px\"><tr><td align=\"center\"><h1>Add a Phone Call</h1></td></tr></table>"));

        addHPan1.add(new Label("Customer Name"));
        addHPan1.add(customerField = new TextBox());
        customerField.setPixelSize(435, 15);
        customerField.setMaxLength(30);
        customerField.getElement().setAttribute("placeholder", "Jane Doe");

        addVPan1.add(new Label("Caller Number"));
        addVPan1.add(new Label("Call Began"));

        addVPan2.add(callerField = new TextBox());
        callerField.setMaxLength(12);
        addVPan2.add(startField = new TextBox());
        startField.setMaxLength(19);

        addVPan3.add(new Label("Callee Number"));
        addVPan3.add(new Label("Call Ended"));

        addVPan4.add(calleeField = new TextBox());
        calleeField.setMaxLength(12);
        addVPan4.add(endField = new TextBox());
        startField.setMaxLength(19);

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

        addButton.addClickHandler(addNewPhoneCall());
        clearButton.addClickHandler(clearPhoneCallFields());
        // <------- END ADD PAGE ------->

        Button pingServerButton = new Button("Ping Server");
        pingServerButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                PingServiceAsync async = GWT.create(PingService.class);
                async.ping(new AsyncCallback<AbstractPhoneBill>() {

                    public void onFailure(Throwable ex) {
                        Window.alert(ex.toString());
                    }

                    public void onSuccess(AbstractPhoneBill phonebill) {
                        StringBuilder sb = new StringBuilder(phonebill.toString());
                        Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
                        for (AbstractPhoneCall call : calls) {
                            sb.append(call);
                            sb.append("\n");
                        }
                        Window.alert(sb.toString());
                    }
                });
            }
        });

        ADD.add(pingServerButton);

        // Set up navigation tabs that will operate as menu interface
        TabLayoutPanel navBar = new TabLayoutPanel(2.5, Style.Unit.EM);
        navBar.add(new HTML("Welcome!"), "Home");
        navBar.add(README, "Help");
        navBar.add(ADD, "Add");

        rootLayoutPanel.add(navBar);
    }

    /**
     * Adding a phone call to the phone bill.
     *
     * @return handled click event for the Add button
     */
    private ClickHandler addNewPhoneCall() {
        return new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                String phoneCall = customerField.getText();

                if (phoneCall.isEmpty() || phoneCall.equals("")) {
                    Window.alert("The form is blank!");
                    return;
                }

                Window.alert("CUSTOMER IS: " + phoneCall);
                PingServiceAsync async = GWT.create(PingService.class);
                async.getCustomerName(phoneCall, new AsyncCallback<AbstractPhoneBill>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert(throwable.toString());
                    }

                    @Override
                    public void onSuccess(AbstractPhoneBill phoneBill) {
                        Window.alert(phoneBill.getCustomer());
                    }
                });
            }
        };
    }

    /**
     * Clears all fields on the ADD page.
     *
     * @return handled click event for the Clear button
     */
    private ClickHandler clearPhoneCallFields() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                customerField.getElement().setAttribute("placeholder", "");
                callerField.getElement().setAttribute("placeholder", "");
                calleeField.getElement().setAttribute("placeholder", "");
                startField.getElement().setAttribute("placeholder", "");
                endField.getElement().setAttribute("placeholder", "");

                customerField.setText("");
                callerField.setText("");
                calleeField.setText("");
                startField.setText("");
                endField.setText("");
            }
        };
    }

    /**
     * The README file equivalent for the CS410J Phone Bill Application.
     *
     * @return the README formatted in HTML
     */
    protected HTML readme() {
        return new HTML("<head><title>Phone Bill App - Help</title></head><body>" +
                "<p><table width=\"600\"><col width=\"150\"><tr><td colspan=\"2\" align=\"center\">" +
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

    /**
     * Determines whether or not some <code>String</code> is of the form
     * <code>nnn-nnn-nnnn</code> where <code>n</code> is a number <code>0-9</code>.
     *
     * @param phoneNumberInput some phone number
     * @return True if the form is valid, otherwise false
     */
    private boolean isValidPhoneNumber(String phoneNumberInput) {
        RegExp regExp = RegExp.compile("\\d{3}-\\d{3}-\\d{4}");
        MatchResult numberToBeChecked = regExp.exec(phoneNumberInput);
        boolean match = numberToBeChecked != null;
        return match;
    }

    private void loadAddPageLayout() {
        ADD.setSize("600px", "300px");

        addHPan1.setSize("600px", "100%");
        addHPan2.setSize("600px", "100%");
        addHPan3.setSize("600px", "100%");

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
    }
}
