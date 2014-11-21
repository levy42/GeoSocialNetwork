import managers.ChatManager;
import managers.ServiceManager;

/**
 * Created by oleh on 16.11.2014.
 */
public class test3 {
    public static void main(String[] args) {
        ChatManager m = ServiceManager.adminManager;
        m.openChat(1);
        System.out.print("how!!!");
    }
}

