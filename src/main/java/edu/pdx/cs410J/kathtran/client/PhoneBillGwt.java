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
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 *
 * @author Kathleen Tran
 * @version 5.0
 */
public class PhoneBillGwt implements EntryPoint {
    private final RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
    private final ScrollPanel _README = new ScrollPanel();
    private final VerticalPanel _ADD = new VerticalPanel();
    private final FlowPanel _PRINT = new FlowPanel();
    private final FlowPanel _SEARCH = new FlowPanel();
    private final Button addButton = new Button("Add");
    private final Button clearButton = new Button("Clear");
    private final Button quickAddButton = new Button("Quick Add");
    private final Button clearQuickAddButton = new Button("Clear");
    private final Button printRecentButton = new Button("Print");
    private final Button printOneBillButton = new Button("Print One");
    private final Button printAllBillsButton = new Button("Print All");
    private final Button searchButton = new Button("Search");
    private final TextBox customerNameBox = new TextBox();
    private final TextBox callerNumberBox = new TextBox();
    private final TextBox calleeNumberBox = new TextBox();
    private final TextBox startTimeBox = new TextBox();
    private final TextBox endTimeBox = new TextBox();
    private final TextBox quickAddBox = new TextBox();
    private final HTML addPageTitle = new HTML("<table width=\"600\" ><tr><td align=\"center\"><h1>Add a Phone Call</h1></td></tr></table>");
    private final HTML printPageTitle = new HTML("<table width=\"600\" ><tr><td align=\"center\"><h1>Printing Phone Calls and Bills</h1></td></tr></table>");
    private final HTML searchPageTitle = new HTML("<table width=\"600\" ><tr><td align=\"center\"><h1>Search for Calls</h1></td></tr></table>");
    private final HTML pageDivider = new HTML("<table width=\"600\"><tr style=\"height:25px\"><td><hr width=\"90%\"></td></tr></table>");
    private final InlineLabel customerNameLabel = new InlineLabel("Customer Name");
    private final InlineLabel callerNumberLabel = new InlineLabel("Caller Number");
    private final InlineLabel calleeNumberLabel = new InlineLabel("Callee Number");
    private final InlineLabel startTimeLabel = new InlineLabel("Call Began");
    private final InlineLabel endTimeLabel = new InlineLabel("Call Ended");
    private final InlineLabel quickAddLabel = new InlineLabel("Phone Call");
    private final InlineLabel printMostRecentLabel = new InlineLabel("Most Recent Phone Call");
    private final InlineLabel phoneBillLabel = new InlineLabel("Phone Bill");
    private final InlineLabel searchStartLabel = new InlineLabel("Between");
    private final InlineLabel searchEndLabel = new InlineLabel("and");

    private String customer;
    private String caller;
    private String callee;
    private String startTime;
    private String endTime;
    private String[] userInputs = new String[5];

    public void onModuleLoad() {

        // Set up navigation tabs that will operate as menu interface
        TabLayoutPanel navBar = new TabLayoutPanel(2.5, Style.Unit.EM);

        // Build ADD page
        customerNameBox.setMaxLength(30);
        callerNumberBox.setMaxLength(12);
        calleeNumberBox.setMaxLength(12);
        startTimeBox.setMaxLength(19);
        endTimeBox.setMaxLength(19);
        quickAddBox.setMaxLength(128);

        customerNameBox.setPixelSize(275, 15);
        callerNumberBox.setPixelSize(275, 15);
        calleeNumberBox.setPixelSize(275, 15);
        startTimeBox.setPixelSize(275, 15);
        endTimeBox.setPixelSize(275, 15);
        quickAddBox.setPixelSize(350, 15);

        _ADD.setSize("600px", "375px");
        _ADD.getElement().getStyle().setMargin(2, Style.Unit.EM);
        _ADD.getElement().getStyle().setBorderStyle(Style.BorderStyle.NONE);
        _ADD.add(addPageTitle);
        HorizontalPanel addPageSectionOne = new HorizontalPanel();
        addPageSectionOne.setSize("600px", "100%");
        VerticalPanel addPageSectionOneLabels = new VerticalPanel();
        addPageSectionOneLabels.setSize("100%", "100%");
        addPageSectionOneLabels.getElement().getStyle().setLineHeight(1.7, Style.Unit.EM);
        addPageSectionOneLabels.add(customerNameLabel);
        addPageSectionOneLabels.add(callerNumberLabel);
        addPageSectionOneLabels.add(calleeNumberLabel);
        addPageSectionOneLabels.add(startTimeLabel);
        addPageSectionOneLabels.add(endTimeLabel);
        VerticalPanel addPageSectionOneFields = new VerticalPanel();
        addPageSectionOneFields.setSize("100%", "100%");
        addPageSectionOneFields.add(customerNameBox);
        addPageSectionOneFields.add(callerNumberBox);
        addPageSectionOneFields.add(calleeNumberBox);
        addPageSectionOneFields.add(startTimeBox);
        addPageSectionOneFields.add(endTimeBox);
        addPageSectionOne.add(addPageSectionOneLabels);
        addPageSectionOne.add(addPageSectionOneFields);
        _ADD.add(addPageSectionOne);
        HorizontalPanel addPageButtonSet = new HorizontalPanel();
        addPageButtonSet.setSize("110px", "100%");
        addPageButtonSet.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        addPageButtonSet.add(addButton);
        addPageButtonSet.add(clearButton);
        _ADD.add(addPageButtonSet);
        _ADD.add(pageDivider);
        HorizontalPanel quickAddSection = new HorizontalPanel();
        quickAddSection.setSize("600px", "100%");
        quickAddSection.add(quickAddLabel);
        quickAddSection.add(quickAddBox);
        quickAddSection.add(quickAddButton);
        quickAddSection.add(clearQuickAddButton);
        _ADD.add(quickAddSection);

        addButton.addClickHandler(getCustomer());

        navBar.add(new HTML("Welcome!"), "Home");
        navBar.add(readme(), "Help");
        navBar.add(_ADD, "Add");
        navBar.add(printPage(), "Print");
        navBar.add(searchPage(), "Search");

        rootLayoutPanel.add(navBar);
    }

    private ClickHandler getCustomer() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                String customer = customerNameBox.getText();
                Window.alert("CUSTOMER IS: " + customer);

//                PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
//                async.getCustomer(customer, display());
            }

//            private AsyncCallback<AbstractPhoneBill> display() {
//                return new AsyncCallback<AbstractPhoneBill>() {
//                    @Override
//                    public void onFailure(Throwable throwable) {
//                        Window.alert(throwable.toString());
//                    }
//
//                    @Override
//                    public void onSuccess(AbstractPhoneBill phoneBill) {
//                        Window.alert(phoneBill.getCustomer());
//                    }
//                };
//            }
        };
    }



//    protected ClickHandler addPhoneCallToServer() {
//        return new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent clickEvent) {
//                userInputs[0] = customerNameBox.getText();
//                userInputs[1] = callerNumberBox.getText();
//                userInputs[2] = calleeNumberBox.getText();
//                userInputs[3] = startTimeBox.getText();
//                userInputs[4] = endTimeBox.getText();
//
//                for (String input : userInputs) {
//                    if (input == null || input.equals("")) {
//                        Window.alert("One or more fields are empty!");
//                        break;
//                    }
//                }
//                PhoneBillServiceAsync async = GWT.create(PhoneBillService.class);
//                async.addPhoneCall(userInputs, displayAddedCall());
//            }
//        };
//    }

    /**
     * The contents of the Add tab. Consists of widgets that support adding a phone call.
     *
     * @return the ADD page as a Widget
     */
//    protected Widget addCallPage() {
//        customerNameBox.setPixelSize(413, 15);
//        quickAddBox.setPixelSize(413, 15);
//
//        _ADD.add(new HTML("<head><title>Phone Bill App - Add</title></head><body>" +
//                "<p><table width=\"600\" ><col width=\"150\"><tr><td colspan=\"4\" align=\"center\">" +
//                "<h1><b>Add a Phone Call</b></h1></td></tr>" +
//                "<tr><td colspan=\"1\" align=\"left\">" + customerNameLabel + "</td><td colspan=\"3\">" + customerNameBox + "</td></tr>" +
//                "<tr><td>" + callerNumberLabel + "</td><td>" + callerNumberBox + "</td><td>" + calleeNumberLabel + "</td><td>" + calleeNumberBox + "</td></tr>" +
//                "<tr><td>" + startTimeLabel + "</td><td>" + startTimeBox + "</td><td>" + endTimeLabel + "</td><td>" + endTimeBox + "</td></tr>" +
//                "<tr></tr><tr><td colspan=\"1\"></td><td colspan=\"1\"></td><td colspan=\"1\"></td></table></p></body>"));
//        _ADD.add(addButton);
//        _ADD.add(clearButton);
//        _ADD.add(new HTML("<p><table width=\"600\"><col width=\"150\">" +
//                "<tr style=\"height:20px\"></tr>" +
//                "<tr><td colspan=\"1\" align=\"left\">" + quickAddLabel + "</td><td colspan=\"3\">" + quickAddBox + "</td></tr>" +
//                "<tr></tr><tr><td colspan=\"1\"></td><td colspan=\"1\"></td><td colspan=\"1\"></td>" +
//                "</table></p></body>"));
//        _ADD.add(quickAddButton);
//        _ADD.add(clearQuickAddButton);
//        _ADD.add(new HTML("<p><table width=\"600\"><col width=\"150\">" +
//                "<tr style=\"height:50px\"><td colspan=\"4\"><hr width=\"90%\"></td></tr>" +
//                "</table></p>"));
//        return _ADD.asWidget();
//    }

//    private AsyncCallback<AbstractPhoneBill> displayAddedCall() {
//        return new AsyncCallback<AbstractPhoneBill>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                Window.alert(throwable.toString());
//            }
//
//            @Override
//            public void onSuccess(AbstractPhoneBill phoneBill) {
//                StringBuilder sb = new StringBuilder(phoneBill.toString());
//                Collection<AbstractPhoneCall> calls = phoneBill.getPhoneCalls();
//                for (AbstractPhoneCall call : calls) {
//                    sb.append(call);
//                    sb.append("\n");
//                }
//                Window.alert(sb.toString());
//            }
//        };
//    }

    protected Widget printPage() {
        customerNameBox.setPixelSize(275, 15);

        _PRINT.add(new HTML("<head><title>Phone Bill App - Print</title></head><body>" +
                "<p><table width=\"600\"><col width=\"150\"><tr><td colspan=\"4\" align=\"center\">" +
                "<h1><b>Printing Phone Calls and Bills</b></h1></td></tr>" +
                "<tr><td colspan=\"3\"><b>Most Recent Phone Call</b></td><td colspan=\"1\" align=\"right\">" + printRecentButton + "</td><tr>" +
                "<tr style=\"height:75px\"><td colspan=\"4\"><hr width=\"90%\"></td></tr>" +
                "<tr style=\"height:90px\"></tr>" +
                "<tr><td colspan=\"1\"><b>Phone Bill</b></td><td colspan=\"2\">" + customerNameBox + "</td>" +
                "<td colspan=\"1\" align=\"right\">" + printOneBillButton + "&nbsp;&nbsp;" + printAllBillsButton + "</td><tr>" +
                "<tr style=\"height:75px\"><td colspan=\"4\"><hr width=\"90%\"></td></tr>" +
                "</table></p></body>"));
        return _PRINT.asWidget();
    }

    protected Widget searchPage() {

        _SEARCH.add(new HTML("<head><title>Phone Bill App - Search</title></head><body>" +
                "<p><table width=\"600\">" +
                "<colgroup><col style=\"width:20%\"><col style=\"width:20%\"><col style=\"width:20%\"><col style=\"width:20%\"><col style=\"width:20%\"></colgroup>" +
                "<tr><td colspan=\"5\" align=\"center\">" +
                "<h1><b>Search for Calls</b></h1></td></tr>" +
                "<tr><td><b>Between</b></td><td align=\"left\">" + startTimeBox + "</td><td align=\"center\"><b>and</b></td>" +
                "<td>" + endTimeBox + "</td><td align=\"center\">" + searchButton + "</td><tr>" +
                "<tr style=\"height:75px\"><td colspan=\"5\"><hr width=\"90%\"></td></tr>" +
                "</table></p></body>"));
        return _SEARCH.asWidget();
    }

    /**
     * The README file equivalent for the CS410J Phone Bill Application.
     *
     * @return the README as a Widget
     */
    protected Widget readme() {
        _README.add(new HTML("<head><title>Phone Bill App - Help</title></head><body>" +
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
                "and searchButton for phone calls belonging to some given phone bill between some given time " +
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
                "<tr><td colspan=\"2\" align=\"center\">&copy; Kathleen Tran Summer 2015</td></tr></table></p></body>"));
        return _README.asWidget();
    }
}
