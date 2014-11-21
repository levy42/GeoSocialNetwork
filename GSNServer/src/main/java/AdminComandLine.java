import DBApi.Chat;
import DBApi.Location;
import ClientAPI.Admin;

import java.util.Scanner;

/**
 * Created by oleh on 18.11.2014.
 */
public class AdminComandLine {
    private static final String OPEN_CHAT="openchat";
    private static final String CLOSE_CHAT="closechat";
    private static final String DELETE_CHAT="deletechat";
    private static final String REGISTRATE_CHAT="registratechat";
    public static void main(String[] args){
        Scanner s=new Scanner(System.in);
        System.out.print("->");
        String adminname=s.next();
        String password=s.next();
        Admin admin=Admin.login(adminname,password);
        if(admin==null)return;
        System.out.println("success");
        while(true) {
            System.out.print("->");
            s.reset();
            String command = s.next();
            try {
                    if(command.equals(OPEN_CHAT)) {
                        int id = s.nextInt();
                        if (id != 0 && admin.openChat(id))
                            System.out.println("chat" + id + " -opened");
                        else
                            System.out.println("error");
                    }
                if(command.equals(REGISTRATE_CHAT)) {
                    String name = s.next();
                    float longitude = s.nextFloat();
                    float latitude = s.nextFloat();
                    Chat c = new Chat();
                    c.setLocation(new Location(longitude, latitude));
                    c.setName(name);
                    int ChatId = admin.registrateChat(c);
                    if (ChatId != -1)
                        System.out.println("registrated! chatId = " + ChatId);
                }
                if(command.equals(CLOSE_CHAT)) {
                    int id = s.nextInt();
                    if (id != 0 && admin.closeChat(id))
                        System.out.println("chat" + id + " -closed");
                }
                if(command.equals(DELETE_CHAT)) {
                    int id = s.nextInt();
                    if (id != 0 && admin.deleteChat(id))
                        System.out.println("chat" + id + " -deleted");
                }
                if(command.equals("exit")) {
                        return;
                }
            }
            catch (Exception e){
                System.out.println("syntax error");
            }
        }
    }
}
