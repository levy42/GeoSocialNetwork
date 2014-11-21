package managers;

import DBApi.*;
;
import org.hibernate.Session;
import tools.AvatarConfigs;
import tools.AvatarMaker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UserManager {

    Session session= HibernateUtil.getSession();

    public User getUser(int id) {
        return (User)session.get(User.class,id);
    }

    public User getUser(String username) {
        int id=(Integer)session.get(AutenteficationData.class,username);
        return (User)session.get(User.class,id);
    }

    public void addUser(User user, String username) {

    }

    public boolean registrate(User user, String username, String password) {
        session.beginTransaction();
        AutenteficationData autenteficationData=new AutenteficationData();
        autenteficationData.setPassword(password);
        autenteficationData.setUsername(username);
        try {
            session.save(autenteficationData);
            session.getTransaction().commit();
        }
        catch (Exception e){
            return false;
        }
        session.beginTransaction();
        session.save(new User(user));
        session.getTransaction().commit();
        session.beginTransaction();
        autenteficationData.setUserId(user.getUserId());
        session.update(autenteficationData);
        session.getTransaction().commit();
        return true;
    }


    public UserSession login(String username, String password) {
        UserSession userSession= ServiceManager.autentificationManager.autentification(username,password);
        if(userSession!=null) {
            User user=getUser(userSession.getUserId());
            return userSession;
        }
        else {
            return null;
        }
    }

    public boolean editUser(User newUser,String key){
        if(ServiceManager.autentificationManager.identity(newUser.getUserId(),key)){
            session.beginTransaction();
            try {
                User u=getUser(newUser.getUserId());
                u.clone(newUser);
                session.update(u);
                session.getTransaction().commit();
                return true;
            }
            catch (Exception e){
                return false;
            }
        }
        return false;
    }


    public boolean logout(int userId, String key) {
        if(ServiceManager.autentificationManager.identity(userId,key)){
            ServiceManager.locationManager.removeUser(userId);
            ServiceManager.autentificationManager.removeSession(userId);
            return true;
        }
        else
            return  false;
    }

    public boolean deleteUser(int userId){
        try {
            session.beginTransaction();
            User u = (User) session.get(User.class, userId);
            session.delete(u);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean setAvatarImage(InputStream stream, AvatarConfigs configs,int id){
        try {
            BufferedImage image=ImageIO.read(stream);
            BufferedImage normalAvatar= AvatarMaker.Normal(image, configs.Noffestx, configs.Noffsety, configs.Nw, configs.Nh);
            BufferedImage miniAvatar=AvatarMaker.Mini(image,configs.Moffestx,configs.Moffsety,configs.Mw,configs.Mh);
            String normalURL=ResourceManager.saveImage(normalAvatar,"avatar_normal"+id);
            String miniURL=ResourceManager.saveImage(miniAvatar,"avatar_mini"+id);
            String fullURL=ResourceManager.saveImage(image,"avatar_full"+id);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return  true;
    }

    public String getAvatarURL(int id,int type){
        if(type==0)
            return BaseURLs.ImagesURL+"avatar_normal"+id+".jpg";
        if(type==1)
            return BaseURLs.ImagesURL+"avatar_mini"+id+".jpg";
        else
            return BaseURLs.ImagesURL+"avatar_full"+id+".jpg";
    }

}
