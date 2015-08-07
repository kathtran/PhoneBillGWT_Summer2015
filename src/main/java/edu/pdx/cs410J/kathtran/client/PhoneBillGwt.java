package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.awt.*;
import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneBillGwt implements EntryPoint {
    private static RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
    private static ScrollPanel _README = new ScrollPanel();
    private static ScrollPanel _ADD = new ScrollPanel();
    private static ScrollPanel _PRINT = new ScrollPanel();
    private static ScrollPanel _SEARCH = new ScrollPanel();
    private final Button addCall = new Button("Add");
    private final Button clear = new Button("Clear");
    private final Button quickAdd = new Button("Quick Add");
    private final Button clearQuickAdd = new Button("Clear");
    private final TextBox customerNameBox = new TextBox();
    private final TextBox callerNumberBox = new TextBox();
    private final TextBox calleeNumberBox = new TextBox();
    private final TextBox startTimeBox = new TextBox();
    private final TextBox endTimeBox = new TextBox();
    private final TextBox quickAddBox = new TextBox();
    private final Button printRecent = new Button("Print");
    private final Button printOneBill = new Button("Print One");
    private final Button printAllBills = new Button("Print All");
    private final Button search = new Button("Search");
    private final Button pingServerButton = new Button("Ping Server");

    public void onModuleLoad() {

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

        _README.add(readme());
        _ADD.add(addCallPage());
        _PRINT.add(printPage());
        _SEARCH.add(searchPage());

        // Set up navigation tabs that will operate as menu interface
        TabLayoutPanel navBar = new TabLayoutPanel(2.5, Style.Unit.EM);
        navBar.add(new HTML("Welcome!"), "Home");
        navBar.add(_README, "Help");
        navBar.add(_ADD, "Add");
        navBar.add(_PRINT, "Print");
        navBar.add(_SEARCH, "Search");

        rootLayoutPanel.add(navBar);
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

    protected HTML addCallPage() {
        customerNameBox.setPixelSize(413, 15);
        quickAddBox.setPixelSize(413, 15);

        addCall.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                PingServiceAsync async = GWT.create(PingService.class);

            }
        });

        return new HTML(pingServerButton + "<head><title>Phone Bill App - Add</title></head><body>" +
                "<p><table width=\"600\"><col width=\"150\"><tr><td colspan=\"4\" align=\"center\">" +
                "<h1><b>Add a Phone Call</b></h1></td></tr>" +
                "<tr><td colspan=\"1\" align=\"left\"><b>Customer Name</b></td><td colspan=\"3\">" + customerNameBox + "</td></tr>" +
                "<tr><td><b>Caller Number</b></td><td>" + callerNumberBox + "</td><td><b>Callee Number</b></td><td>" + calleeNumberBox + "</td></tr>" +
                "<tr><td><b>Call Began</b></td><td>" + startTimeBox + "</td><td><b>Call Ended</b></td><td>" + endTimeBox + "</td></tr>" +
                "<tr></tr><tr><td colspan=\"1\"></td><td colspan=\"1\"></td><td colspan=\"1\"></td>" +
                "<td colspan=\"1\" align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + addCall + "&nbsp;&nbsp;" + clear + "</td></tr>" +
                "<tr style=\"height:20px\"></tr>" +
                "<tr><td colspan=\"1\" align=\"left\"><b>Phone Call</b></td><td colspan=\"3\">" + quickAddBox + "</td></tr>" +
                "<tr></tr><tr><td colspan=\"1\"></td><td colspan=\"1\"></td><td colspan=\"1\"></td>" +
                "<td align=\"left\">&nbsp;" + quickAdd + "&nbsp;&nbsp;" + clearQuickAdd + "</td></tr>" +
                "<tr style=\"height:75px\"><td colspan=\"4\"><hr width=\"90%\"></td></tr>" +
                "</table></p></body>");
    }

    protected HTML printPage() {
        customerNameBox.setPixelSize(275, 15);

        return new HTML("<head><title>Phone Bill App - Print</title></head><body>" +
                "<p><table width=\"600\"><col width=\"150\"><tr><td colspan=\"4\" align=\"center\">" +
                "<h1><b>Printing Phone Calls and Bills</b></h1></td></tr>" +
                "<tr><td colspan=\"3\"><b>Most Recent Phone Call</b></td><td colspan=\"1\" align=\"right\">" + printRecent + "</td><tr>" +
                "<tr style=\"height:75px\"><td colspan=\"4\"><hr width=\"90%\"></td></tr>" +
                "<tr style=\"height:90px\"></tr>" +
                "<tr><td colspan=\"1\"><b>Phone Bill</b></td><td colspan=\"2\">" + customerNameBox + "</td>" +
                "<td colspan=\"1\" align=\"right\">" + printOneBill + "&nbsp;&nbsp;" + printAllBills + "</td><tr>" +
                "<tr style=\"height:75px\"><td colspan=\"4\"><hr width=\"90%\"></td></tr>" +
                "</table></p></body>");
    }

    protected HTML searchPage() {

        return new HTML("<head><title>Phone Bill App - Search</title></head><body>" +
                "<p><table width=\"600\">" +
                "<colgroup><col style=\"width:20%\"><col style=\"width:20%\"><col style=\"width:20%\"><col style=\"width:20%\"><col style=\"width:20%\"></colgroup>" +
                "<tr><td colspan=\"5\" align=\"center\">" +
                "<h1><b>Search for Calls</b></h1></td></tr>" +
                "<tr><td><b>Between</b></td><td align=\"left\">" + startTimeBox + "</td><td align=\"center\"><b>and</b></td>" +
                "<td>" + endTimeBox + "</td><td align=\"center\">" + search + "</td><tr>" +
                "<tr style=\"height:75px\"><td colspan=\"5\"><hr width=\"90%\"></td></tr>" +
                "</table></p></body>");
    }
}
