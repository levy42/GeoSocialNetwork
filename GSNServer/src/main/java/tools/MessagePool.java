package tools;

import DBApi.PrivateMessage;
import org.apache.commons.collections.MultiHashMap;

import java.util.List;

/**
 * Created by oleh on 02.11.2014.
 */
public class MessagePool {
    MultiHashMap messages=new MultiHashMap();

    public List<PrivateMessage> getMessages(int userId){
        return (List<PrivateMessage>)(messages.remove(userId));
        //return (List<Message>)messages.getCollection(userId);
    }

    public synchronized boolean addMessage(PrivateMessage m){
        try {
            messages.put(m.getUserToId(), m);
            return true;
        }
        catch (Exception e) {
            return  false;
        }
    }
}
