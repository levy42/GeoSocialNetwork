package ClientAPI;

import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FriendManager {
    private GSNClient client;

    public boolean invite(int userId){
        getClient().setWebResource(getClient().resource(getClient().getURL() + "friendsApplications/" + getClient().getId() + "/send/" + userId));
        try {
            ClientResponse r = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).get(ClientResponse.class);
            return r.getStatus()== 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean accept(int userId){
        getClient().setWebResource(getClient().resource(getClient().getURL() + "friendsApplications/" + getClient().getId() + "/accept/" + userId));
        try {
            ClientResponse r = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).get(ClientResponse.class);
            return r.getStatus()== 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean reject(int userId){
        getClient().setWebResource(getClient().resource(getClient().getURL() + "friendsApplications/" + getClient().getId() + "/reject/" + userId));
        try {
            ClientResponse r = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).get(ClientResponse.class);
            return r.getStatus()== 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int userId){
        getClient().setWebResource(getClient().resource(getClient().getURL() + "friendsApplications/" + getClient().getId() + "/deleteFriend/" + userId));
        try {
            ClientResponse r = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).get(ClientResponse.class);
            return r.getStatus()== 200;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Integer> getApplicators(){
        getClient().setWebResource(getClient().resource(getClient().getURL() + "friendsApplications/" + getClient().getId() + "/get"));
        try {
            ClientResponse r = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).get(ClientResponse.class);
            String s=r.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<Integer>>() {}.getType();
            return getClient().gson.fromJson(s,typeOfObjectsList);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public GSNClient getClient() {
        return client;
    }

    public void setClient(GSNClient client) {
        this.client = client;
    }
}
