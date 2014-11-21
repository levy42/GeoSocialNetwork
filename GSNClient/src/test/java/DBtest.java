import DBApi.Chat;
import DBApi.ChatMessage;
import DBApi.Location;
import DBApi.PrivateMessage;
import AndminClient.Admin;
import ClientAPI.GSNClient;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DBtest {
    public static void main(String[] args) throws SQLException {
        GSNClient vitaliyClient=null,vadimClient=null,vitaliyCopy=null;
       try {
            GSNClient.setURL("http://192.168.1.123:8090/GSNbackend/rest/");
            vitaliyCopy = GSNClient.login("vitaliy25", "12345");
            vadimClient=GSNClient.login("lalka", "54321");
            vitaliyClient=Admin.login("vitaliy24","12345");
            Chat c=new Chat();
            c.setLocation(new Location(50,30));
            c.setName("chat3");
            ((Admin)vitaliyClient).openChat(13);
            ((Admin)vitaliyClient).openChat(9);
            ((Admin)vitaliyClient).openChat(8);
            Thread.sleep(1000);
            vitaliyClient.getLocationManager().getChats(new Location(50,30));
            List<ChatMessage>mess=vadimClient.getMessageManager().getLastChatMessages(1,0);
            List<ChatMessage>mess2=vadimClient.getMessageManager().getChatMessages(1, new Date().getTime());
            List<PrivateMessage>mess4=vadimClient.getMessageManager().getMessages();
            List<PrivateMessage>mess3=vadimClient.getMessageManager().getDialog(vitaliyClient.getId(),new Date().getTime());
            vitaliyClient.logout();
            vadimClient.logout();
            vitaliyCopy.logout();
            System.out.println("fuck");
        }
        catch (Exception e){
            e.printStackTrace();
            vitaliyClient.logout();
            vadimClient.logout();
            vitaliyCopy.logout();
        }
    }
}