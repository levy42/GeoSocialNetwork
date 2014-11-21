package AndminClient;

import ClientAPI.GSNClient;
import DBApi.Chat;
import DBApi.UserSession;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MediaType;


public class Admin extends GSNClient {
    public int registrateChat(Chat c){
        setWebResource(resource(URL + "admin/" + id + "/registrateChat"));
        String input=gson.toJson(c);
        try {
            ClientResponse response = getWebResource().type(MediaType.APPLICATION_JSON).header("token", getToken()).post(ClientResponse.class, input);
            if (response.getStatus() == ClientResponse.Status.OK.getStatusCode()) {
                String s = response.getEntity(String.class);
                return gson.fromJson(s,Integer.class);
            }
        }
        catch (Exception e){
            return -1;
        }
        return -1;
    }

    public boolean openChat(int id){
        setWebResource(resource(URL + "admin/" + getId() + "/openChat/"+id));
        try {
            ClientResponse response = getWebResource().header("token", getToken()).get(ClientResponse.class);
            if (response.getStatus() == ClientResponse.Status.OK.getStatusCode())
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean closeChat(int id){
        setWebResource(resource(URL + "admin/" + getId() + "/closeChat/"+id));
        try {
            ClientResponse response = getWebResource().header("token", getToken()).get(ClientResponse.class);
            if (response.getStatus() == ClientResponse.Status.OK.getStatusCode())
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean deleteChat(int id){
        setWebResource(resource(URL + "admin/" + getId() + "/deleteChat/"+id));
        try {
            ClientResponse response = getWebResource().header("token", getToken()).get(ClientResponse.class);
            if (response.getStatus() == ClientResponse.Status.OK.getStatusCode())
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public static Admin login(String username,String password) {
        Admin client=new Admin();
        client.setWebResource(client.resource(getURL() + "login/" + username));
        String input=password;
        ClientResponse response=null;
        try {
            response = client.getWebResource().type("application/json")
                    .post(ClientResponse.class, input);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            String str = response.getEntity(String.class);
            UserSession s = gson.fromJson(str, UserSession.class);
            client.setId(s.getUserId());
            client.setToken(s.getToken());
            client.setUsername(username);
            client.locationManager.setClient(client);
            client.messageManager.setClient(client);
            client.getFriendManager().setClient(client);
            return client;
        }
        catch (Exception e) {
            return null;
        }
    }
}
