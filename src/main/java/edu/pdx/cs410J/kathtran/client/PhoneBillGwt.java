package edu.pdx.cs410J.kathtran.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
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
        Button button = new Button("Ping Server");
        button.addClickHandler(new ClickHandler() {
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
        RootPanel rootPanel = RootPanel.get();
        rootPanel.add(button);
    }

    /**
     * Prints out a brief description of what the Phone Bill Application is and how it operates.
     */
    public void readme() {
        System.out.print("\n\t\tREADME - PHONE BILL APPLICATION\n" +
                "\t\t===============================\n" +
                "Introduction\n" +
                "------------\n\n" +
                "Welcome to the Phone Bill Application! This is a command-line\n" +
                "application that allows the user to model a phone bill. In version\n" +
                "1.0, the user may associate at most one phone record per customer\n" +
                "name. However, the information will not be stored between each usage.\n" +
                "A single phone record consists of the phone number of the caller, the\n" +
                "phone number that was called, the time at which the call began, and\n" +
                "the time at which the call ended.\n\n" +

                "Updates\n" +
                "-------\n" +
                "v2.0\tThe program now allows the user to save their phone bill\n" +
                "\t\tto an external text file (both new and existing). Users may\n" +
                "\t\talso load phone bill records from existing files. Each file\n" +
                "\t\tcorrelates to a single user and their phone call(s).\n\n" +
                "v3.0\tThe newest feature that has been added is the option to have\n" +
                "\t\tthe phone bill be printed out in a more user-friendly format,\n" +
                "\t\tto either a separate text file or to standard out, complete\n" +
                "\t\twith the duration of each phone call in minutes. Phone calls\n" +
                "\t\twithin the phone bills are now listed chronologically by their\n" +
                "\t\tbeginning time, with the phone numbers serving as tie-breakers\n" +
                "\t\tin appropriate cases. In addition, time stamps are no longer\n" +
                "\t\trecorded in 24-hour time.\n\n" +
                "v4.0\tA server/client has now been established using REST to incorporate\n" +
                "\t\ta web service to the program. Users may add phone bills to the\n" +
                "\t\tserver and search for phone calls belonging to some given phone bill\n" +
                "\t\tbetween some given time span.\n\n" +
                "v5.0\tA web-based user interface using Google Web Toolkit has been\n" +
                "\t\timplemented to support all previous features associated with this\n" +
                "\t\tphone bill application.\n\n" +

                "Commands\n" +
                "--------\n\n" +
                "-README\t\t\t\tThe project description. Entering this option at\n" +
                "\t\t\t\t\tthe command line will display this page.\n" +
                "-print\t\t\t\tA description of some phone call. Entering this\n" +
                "\t\t\t\t\toption at the command line will display the\n" +
                "\t\t\t\t\tdescription of the most recently added phone call.\n" +
                "-search\t\t\t\tSupplementing this option with a customer name,\n" +
                "\t\t\t\t\tsome starting time and some ending time will return\n" +
                "\t\t\t\t\tall of the calls started between those times.\n" +
                "-host <hostname>\tThe host computer on which the server runs.\n" +
                "-port <port>\t\tThe port on which the server is listening.\n" +
                "To add a calling event, the following arguments must be provided\n" +
                "in the order listed below, separated by a single white space.\n\n" +
                "<customer>\t\t\tPerson whose phone bill we're modelling\n" +
                "<caller number>\t\tPhone number of the caller\n" +
                "<callee number>\t\tPhone number of the person called\n" +
                "<start time>\t\tDate and time the call began\n" +
                "<end time>\t\t\tDate and time the call ended\n\n" +
                "If the customer name contains more than one word, it must be\n" +
                "delimited by double quotes. Phone numbers must be of the form\n" +
                "nnn-nnn-nnnn where n is a number 0-9. Date and time should be\n" +
                "in the format: mm/dd/yyyy hh:mm and zeros may be omitted where\n" +
                "appropriate.\n\n" +
                "Options are to be entered before arguments, and only the customer\n" +
                "name may be delimited by double quotes.\n" +
                "\n" +
                "----------------------------------------------------------\n" +
                "CS410J PROJECT 5: A RICH INTERNET APPLICATION FOR A PHONE BILL\n\n" +
                "AUTHOR: KATHLEEN TRAN\nLAST MODIFIED: 8/4/2015\n\n");
        System.exit(1);
    }
}
