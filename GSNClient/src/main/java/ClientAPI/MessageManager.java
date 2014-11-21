package ClientAPI;

import DBApi.ChatMessage;
import DBApi.PrivateMessage;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessageManager {
    private GSNClient client;

    private MessageListener messageListener=new MessageListener() {
        @Override
        public void receive(PrivateMessage message) {
            System.out.print("user:"+message.getUserFromId()+" "+message.getMessage());
        }
    };

    public boolean send(PrivateMessage message) {
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "messages/" + getClient().getId() + "/sendMessage"));
        String input=GSNClient.gson.toJson(message);
        try{
            ClientResponse response= getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).post(ClientResponse.class, input);
            String s=response.getEntity(String.class);
            return (GSNClient.gson.fromJson(s,Boolean.class));
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean send(String message,int userId){
        PrivateMessage m=new PrivateMessage(message,userId, getClient().getId(),new Date().getTime());
        m.setMessage(message);
        return send(m);
    }

    public  ArrayList<PrivateMessage> getMessages(){
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "messages/" + getClient().getId() + "/getMessage"));
        try{
            ClientResponse response= getClient().getWebResource().header("token", getClient().getToken()).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            String s=response.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<PrivateMessage>>() {}.getType();
            return (ArrayList<PrivateMessage>)(GSNClient.gson.fromJson(s,typeOfObjectsList));
        }
        catch (Exception e){
            return null;
        }
    }

    public  ArrayList<PrivateMessage> getDialog(int userId,long dateOffset){
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "messages/" + getClient().getId() + "/getDialog/" + userId + "/" + dateOffset));
        try{
            ClientResponse response= getClient().getWebResource().header("token", getClient().getToken()).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            String s=response.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<PrivateMessage>>() {}.getType();
            return (ArrayList<PrivateMessage>)(GSNClient.gson.fromJson(s,typeOfObjectsList));
        }
        catch (Exception e){
            return null;
        }
    }

    public ArrayList<ChatMessage> getChatMessages(int chatId,long date){
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "messages/" + getClient().getId() + "/getChatMessage/" + chatId + "/" + date));
        try{
            ClientResponse response= getClient().getWebResource().header("token", getClient().getToken()).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            String s=response.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<ChatMessage>>() {}.getType();
            return (ArrayList<ChatMessage>)(GSNClient.gson.fromJson(s,typeOfObjectsList));
        }
        catch (Exception e){
            return null;
        }
    }

    public ArrayList<ChatMessage> getLastChatMessages(int chatId,long date){
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "messages/" + getClient().getId() + "/getNewChatMessage/" + chatId + "/" + date));
        try{
            ClientResponse response= getClient().getWebResource().header("token", getClient().getToken()).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            String s=response.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<ChatMessage>>() {}.getType();
            return (ArrayList<ChatMessage>)(GSNClient.gson.fromJson(s,typeOfObjectsList));
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean sendChatMessage(ChatMessage message){
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "messages/" + getClient().getId() + "/sendChatMessage/" + message.getChatId()));
        String input=GSNClient.gson.toJson(message);
        try{
            ClientResponse response= getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).post(ClientResponse.class, input);
            String s=response.getEntity(String.class);
            return response.getStatus()==201;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean sendToChat(String message,int userToId,int chatId){
        return sendChatMessage(new ChatMessage(message,userToId, getClient().getId(),new Date().getTime(),chatId));
    }

    private Thread thread;

    public void cencelListening(){
        if(thread!=null)thread.interrupt();
    }

    public void beginListening(final int interval) {
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    List<PrivateMessage>messages=getMessages();
                    if(messages!=null&&messages.size()!=0)
                        for(int i=0;i<messages.size();i++)
                            messageListener.receive(messages.get(i));
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public GSNClient getClient() {
        return client;
    }

    public void setClient(GSNClient client) {
        this.client = client;
    }
}

