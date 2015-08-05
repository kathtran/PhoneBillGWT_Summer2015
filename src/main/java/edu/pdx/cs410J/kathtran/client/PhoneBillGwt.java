package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
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
    public void onModuleLoad() {

        Button helpButton = new Button("Help");

        ScrollPanel README = new ScrollPanel();
        README.add(readme());

        // Set up navigation tabs that will operate as menu interface
        TabLayoutPanel navBar = new TabLayoutPanel(2.5, Style.Unit.EM);
        navBar.add(new HTML("Welcome"), "Welcome");
        navBar.add(README, "Help");
        navBar.add(new HTML("Add"), "Add");
        navBar.add(new HTML("Print"), "Print");
        navBar.add(new HTML("Search"), "Search");
        RootLayoutPanel.get().add(navBar);
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
        RootPanel rootPanel = RootPanel.get();
        //rootPanel.add(pingServerButton);
        rootPanel.add(navBar);
    }

    private HTML readme() {
        return new HTML("<h1><u>README - Phone Bill Application</u></h1>" +
                "<h3><u>Introduction</u></h3>" +
                "<p><b>v1.0</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome to the Phone Bill Application! This is " +
                "a command-line application that allows the user to model a phone bill. In this version, the user may " +
                "associate at most one phone record per customer name. However, the information will not be stored " +
                "between each usage. A single phone record consists of the phone umber of the caller, the phone number " +
                "that was called, the time at which the call began, and the time at which the call ended.<br>" +
                "<b>v2.0</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The program now allows the user to save his or her phone bill " +
                "to an external text file (both new and existing). Users may also load phone bill records from existing " +
                "files. Each file correlates to a single user and his or her phone call(s).<br>" +
                "<b>v3.0</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The newest feature that has been added is the option to have " +
                "the phone bill be printed out in a more user-friendly format, to either a separate text file or to " +
                "standard out, complete with the duration of each phone call in minutes. Phone calls within the phone " +
                "bills are now listed chronologically by their beginning time, with the phone numbers serving as tie-" +
                "breakers in appropriate cases. In addition, time stamps are no longer recordered in 24-hour time.<br>" +
                "<b>v4.0</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A server/client has been established for this version using " +
                "REST to incorporate a web service to the program. Users may add phone bills to the server and search " +
                "for phone calls belonging to some given phone bill between some given time span.<br>" +
                "<b>v5.0</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A web-based user interface using Google Web Toolkit has been " +
                "implemented to support all previous features associated with this phone bill application.<br></p>" +
                "<h3><u>Functionalities</u></h3>" +
                "<p><b>Add</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Add a new phone call to some specified customer's phone bill " +
                "by supplying the customer name, caller number, callee number, call start time, and call end time.<br>" +
                "<b>Print</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Choose from two options: (1) print the most recently added " +
                "phone call, or (2) print all of the phone call records that belong to some specified customer.<br>" +
                "<b>Search</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Search for all existing phone call records that fall under " +
                "some starting time and some ending time.</p>" +
                "<h3><u>Arguments</u></h3>" +
                "<p><table style=\"width:100%\"><tr><td><b>[customer]</b></td><td>Person whose phone bill we're modelling</td></tr>" +
                "<tr><td><b>[caller number]</b></td><td>Phone number of the caller</td></tr>" +
                "<tr><td><b>[callee number]</b></td><td>Phone number of the person called</td></tr>" +
                "<tr><td><b>[start time]</b></td><td>Date and time the call began</td></tr>" +
                "<tr><td><b>[end time]</b></td><td>Date and time the call ended</td></tr>" +
                "<tr><td colspan=\"2\">The customer may contain symbols and consist of more than one word. Phone numbers must be of the form " +
                "nnn-nnn-nnnn where n is a number 0-9. Date and time should be in the format: mm/dd/yyyy hh:mm [am/pm] " +
                "and zeroes may be omitted where appropriate.</td></tr></table></p>" +
                "<h5><i>CS410J Project 5: A Rich Internet Application for a Phone Bill<br>&copy; Kathleen Tran 2015</i></h5>");
    }
}
