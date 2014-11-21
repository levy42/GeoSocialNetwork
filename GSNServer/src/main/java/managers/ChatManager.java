package managers;

import DBApi.Chat;

/**
 * Created by oleh on 11.11.2014.
 */
public class ChatManager extends UserManager {
    public boolean openChat(int id){
        session.beginTransaction();
        Chat c=(Chat)session.get(Chat.class,id);
        if(c==null)
            return false;
        else {
            c.setOpen(true);
            session.update(c);
            session.getTransaction().commit();
            ServiceManager.messageManager.addChat(c);
            return true;
        }
    }

    public int registrateChat(Chat c){
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
        return c.getId();
    }

    public boolean deleteChat(int id){
        session.beginTransaction();
        Chat c=(Chat)session.get(Chat.class,id);
        if(c==null)return false;
        session.delete(c);
        session.getTransaction().commit();
        return true;
    }

    public boolean closeChat(int id){
        session.beginTransaction();
        Chat c=(Chat)session.get(Chat.class,id);
        if(c==null)return false;
        c.setOpen(false);
        session.update(c);
        session.getTransaction().commit();
        return (ServiceManager.messageManager.removeChat(id)!=null);
    }
}
