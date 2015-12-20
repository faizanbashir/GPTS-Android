package api;

public class Resource {

    public static final String VALIDATE_LOGIN = "http://godpowerturbo.com/app/webservice/validate_login/";
    public static final String REGISTER = "http://godpowerturbo.com/app/webservice/register_user/";
    public static final String FETCH_HOT_PRODUCTS = "http://godpowerturbo.com/app/webservice/get_product_nos/";
    //Image resource url: http://godpowerturbo.com/app/uploads/product_parts/HOT_PRODUCT_ID.png
    public static final String FETCH_HOT_PRODUCT_IMAGES = "http://godpowerturbo.com/app/uploads/product_parts/";
    public static final String FETCH_EVENTS = "http://godpowerturbo.com/app/webservice/get_events/";
    //Image resource url: http://godpowerturbo.com/app/uploads/events/EVENT_ID.png
    public static final String FETCH_EVENT_IMAGES = "http://godpowerturbo.com/app/uploads/events/";
    public static final String LOGOUT = "http://godpowerturbo.com/app/webservice/do_logout/";

    //File Names
    public static final String CSV_PARTNUMBERS = "newpartnumbers.csv";
    public static final String CSV_COMPANY = "company.csv";
    public static final String CSV_SYMPTOMS = "troubleshoot_parts.csv";
    public static final String CSV_CAUSES = "troubleshoot_causes.csv";
    public static final String CSV_POTENTIAL_CAUSES = "troubleshoot_potential_cause.csv";


    //Color Resources
    public static final String STACKED_BACKGROUND = "#80000000";
    public static final String BACKGROUND_DRAWABLE = "#59000000";

    //Vibration Time
    public static final int VIBR_TIME = 200; //200 Miliseconds = 0.2 Seconds

    //Retry Policy
    public static final int SOCKET_TIMEOUT = 3000; //3000 Miliseconds = 3 Seconds

    //TAGS
    public static final String TAG_SUCCESS = "Success";
    public static final String TAG_MOBILE = "mobile";
    public static final String TAG_IP = "ip_address";

    //PID
    public static final int PID = android.os.Process.myPid();

    public static final String HTML = "<font color=\"red\">No.1 Potential causes</font><br>\n" +
            "<font color=\"red\">A. Potential causes</font><br/>\n" +
            "Air pipe leakage<br/>\n" +
            "<font color=\"red\">B. Vehicle / Engine Diagnostic checks</font><br/>\n" +
            "Check air supply line leakage<br/>\n" +
            "<font color=\"red\">C. Turbocharger condition and Effects</font><br/>\n" +
            "Overspeed burst compresore wheel, small dent on rear of compressore wheel<br/>\n" +
            "<font color=\"red\">D. Recommended Action</font><br>\n" +
            "Replace or repair leakage from air pipe";

}
