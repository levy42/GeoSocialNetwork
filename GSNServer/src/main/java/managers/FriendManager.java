package managers;

import DBApi.HibernateUtil;
import DBApi.User;
import org.apache.commons.collections.MultiHashMap;
import org.hibernate.Session;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;


public class FriendManager {
    Session session= HibernateUtil.getSession();

    public void invite(int userId,int friendId){
        session.beginTransaction();
        if((Integer)(session.createSQLQuery("SELECT user FROM applicators WHERE applicator= "+userId+" AND user="+friendId).uniqueResult())==null)
            session.createSQLQuery("INSERT INTO applicators(applicator,user) VALUES("+userId+","+friendId+")").executeUpdate();
        session.getTransaction().commit();
    }

    public void rejectApplicator(int userId,int friendId){
        session.beginTransaction();
        session.createSQLQuery("DELETE FROM applicators WHERE applicator="+friendId+" AND user="+userId).executeUpdate();
        session.getTransaction().commit();
    }

    public List<Integer> getApplicators(int userId){
        return session.createSQLQuery("SELECT applicator FROM applicators WHERE user= "+userId).list();
    }

    public boolean acceptApplicator(int userId,int applicatorId){
        if((Integer)(session.createSQLQuery("SELECT user FROM applicators WHERE applicator= "+applicatorId+" AND user="+userId).uniqueResult())==userId) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM applicators WHERE applicator="+applicatorId+" AND user="+userId).executeUpdate();
            User user = (User) session.get(User.class, userId);
            user.getFriends().add(applicatorId);
            session.save(user);
            User applicator = (User) session.get(User.class, applicatorId);
            applicator.getFriends().add(userId);
            session.save(applicator);
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    public boolean deleteFriend(int userId,int applicatorId){
        session.beginTransaction();
        User user = (User) session.get(User.class, userId);
        if(!user.getFriends().remove(new Integer(applicatorId)))
            return false;
        session.save(user);
        User applicator = (User) session.get(User.class, applicatorId);
        applicator.getFriends().remove(new Integer(userId));
        session.save(applicator);
        session.getTransaction().commit();
        return true;
    }
}
