package top.sclab.android;

public class Constant {

    private static final String PROTOCOL = "http";
    private static final String HOST = "192.168.2.27:8080";

    public static final String APPLICATION_URL = PROTOCOL + "://" + HOST;

    public static final String WEB_SOCKET_URL = "ws" + PROTOCOL.substring(4) + "://" + HOST + "/ws";

    public static final int EXIT_APPLICATION_CODE = 0;
}
