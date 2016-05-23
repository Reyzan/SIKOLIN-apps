package hukum2016.sikolin.app;

/**
 * Created by Ari on 4/10/2016.
 */
public class AppConfig {
    // Server url
    public static final String URL_ROOT = "http://sikolin.cloudapp.net:8080/Sikolin/api/";
    // Server user login url
    public static String URL_LOGIN = URL_ROOT + "auth.jsp";

    // Server user register url
    public static String URL_REGISTER = URL_ROOT + "reg.jsp";

    public static String URL_GET_MENU = URL_ROOT + "listmenu.jsp";

    public static String URL_SUBMIT_ORDER = URL_ROOT + "requestmenu.jsp";

}
