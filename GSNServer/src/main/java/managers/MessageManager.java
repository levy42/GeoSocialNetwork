package managers;

import DBApi.Chat;
import DBApi.ChatMessage;
import DBApi.HibernateUtil;
import DBApi.PrivateMessage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import DBApi.Location;
import org.hibernate.impl.CriteriaImpl;
import tools.LocationInfo;
import tools.MessagePool;

import java.util.*;

public class MessageManager {
    public static final int CHAT_CLEAR_TIME=1000*60*1;
    public static final int LOT=10;
    public static final int chatKesh=20;
    Session session=HibernateUtil.getSession();
    public MessageManager(){

    }

    public MessagePool privatemessagePool=new MessagePool();
    public HashMap<Integer,Chat> chats =new HashMap<Integer,Chat>();

    public void addChat(Chat chat){
        Location l=chat.getLocation();
        if(l==null)return;
        int i=(int)Math.floor(l.getLongitude());
        int j=(int)Math.floor(l.getLatitude());
        chats.put(chat.getId(),chat);
    }
    public Chat removeChat(int id){
        return chats.remove(id);
    }

    public boolean sendPrivateMessage(PrivateMessage m){
        session.beginTransaction();
        session.save(m);
        session.getTransaction().commit();
        return  privatemessagePool.addMessage(m);
    }

    public boolean sendMessageToChat(ChatMessage m){
        Chat c= chats.get(m.getChatId());
        Session s=HibernateUtil.getSession();
        if(c!=null) {
            c.addMessage(m);
            s.beginTransaction();
            s.save(m);
            s.getTransaction().commit();
            if(c.getMessages().size()>chatKesh)
                c.getMessages().remove(c.getMessages().size()-1);
            return true;
        }
        else
            return false;
    }

    public List<PrivateMessage>getNewPrivateMessages(int userId){
        return privatemessagePool.getMessages(userId);
    }

    public List<PrivateMessage>getDialogMessages(int user1Id,int user2Id,long dateOffset){
        org.hibernate.Query q=session.createQuery("from PrivateMessage where (userToId= :userToId or userToId= :userFromId) and (userFromId= :userToId or userFromId= :userFromId) and date< :date order by date asc");
        q.setLong("userToId", 2);
        q.setLong("userFromId", 1);
        q.setLong("date", dateOffset);
        q.setMaxResults(10);
        return q.list();
    }

    public List<ChatMessage>getLastChatMessages(int chatId,long date){
        List<ChatMessage> messages=new ArrayList<ChatMessage>();
        Chat c= chats.get(chatId);
        if(c==null)return null;
        int n=0;
        try {
            for (ChatMessage m : c.getMessages()) {
                if (m.getDate() < date || n > 20)
                    break;
                messages.add(m);
                n++;
            }
        }
        catch (Exception e){}
        return messages;
    }

    public List<ChatMessage>getChatMessages(int chatId,long date){
        org.hibernate.Query q = session.createQuery("from ChatMessage where chatId =:chatId and date< :date order by date asc");
        q.setLong("chatId",chatId);
        q.setLong("date",date);
        q.setMaxResults(20);
        return q.list();
    }

    public List<Chat> getChats(Location l){
        org.hibernate.Query q = session.createQuery("from Chat where locationGroup =:group");
        q.setInteger("group",LocationManager.locationGroup(l));
        return q.list();
    }
}
