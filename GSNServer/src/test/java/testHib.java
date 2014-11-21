import DBApi.Chat;
import DBApi.HibernateUtil;
import DBApi.Location;
import managers.ChatManager;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by oleh on 15.11.2014.
 */
public class testHib {
    public static void main(String[]args){
        Session s= HibernateUtil.getSession();
        Chat c=new Chat();c.setName("name2");c.setLocation(new Location(50,30));
        new ChatManager().registrateChat(c);
        try {
            Query q=s.createQuery("from PrivateMessage where (userToId= :userToId or userToId= :userFromId) and (userFromId= :userToId or userFromId= :userFromId) order by date asc");
            q.setLong("userToId",2);
            q.setLong("userFromId",1);
            q.setMaxResults(10);
            q.setFirstResult(28);
            List<Object> l=q.list();
            l.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        };
        try {
            Query q = s.createQuery("from friends");
            List<Object> o = q.list();
        }
        catch (Exception e){};
        try {
            Query q = s.createQuery("from  User");
            List<Object> o = q.list();
        }
        catch (Exception e){};
    }
}
