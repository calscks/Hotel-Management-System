package application.assets;

public class LoginData {
    private static String uname;
    private static String auth;

    //use static method then enough alrd. Just need to pass values from login to slidemenu
    public static String getUname() {
        return uname;
    }

    public static String getAuth() {
        return auth;
    }

    public void setUname(String uname) {
        LoginData.uname = uname;
    }

    public void setAuth(String auth) {
        LoginData.auth = auth;
    }
}
