package managers;

import DBApi.AutenteficationData;
import DBApi.HibernateUtil;
import DBApi.UserSession;
import org.hibernate.Session;

import java.util.HashMap;

public class AutentificationManager{
    private static final int adminId=1;

    private Session session=HibernateUtil.getSession();

    public UserSession autentification(String username, String password) {
        Session session= HibernateUtil.getSession();
        AutenteficationData autenteficationData=(AutenteficationData)session.get(AutenteficationData.class,username);
        if(autenteficationData!=null && autenteficationData.getPassword().equals(password))
            return addSession(autenteficationData.getUserId());
        else
            return null;
    }

    public UserSession addSession(int id){
        session.beginTransaction();
        UserSession userSession=(UserSession)session.get(UserSession.class,id);
        if(userSession!=null)
            return userSession;
        else
            userSession=new UserSession(id,genrateUserKey(id));
        session.saveOrUpdate(userSession);
        session.getTransaction().commit();
        return userSession;
    }

    public UserSession removeSession(int id){
        session.beginTransaction();
        UserSession userSession=(UserSession)session.get(UserSession.class,id);
        session.delete(userSession);
        session.getTransaction().commit();
        return userSession;
    }

    public boolean changePassword(String username, String oldpassword, String newpassword) {
        Session session= HibernateUtil.getSession();
        AutenteficationData autenteficationData=(AutenteficationData)session.get(AutenteficationData.class,username);
        if(!(autenteficationData!=null && autenteficationData.getPassword().equals(oldpassword)))
            return false;
        autenteficationData.setPassword(newpassword);
        session.beginTransaction();
        session.update(autenteficationData);
        session.getTransaction().commit();
        return true;
    }

    private String genrateUserKey(int id){
        return Integer.toString(id);
    }

    public  boolean identity(int userId, String token) {
        UserSession s=(UserSession)session.get(UserSession.class,userId);
        if(s!=null)
            return (s.getToken().equals(token));
        else return false;
    }

    public int deleteAutenteficationData(String username,String password){
        Session session= HibernateUtil.getSession();
        session.beginTransaction();
        AutenteficationData autenteficationData=(AutenteficationData)session.get(AutenteficationData.class,username);
        if(autenteficationData!=null && autenteficationData.getPassword().equals(password)){
            session.delete(autenteficationData);
            return  autenteficationData.getUserId();
        }
        return -1;
    }

    public boolean isAdmin(int id){
        return id==adminId;
    }

}

