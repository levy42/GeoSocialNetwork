package ClientAPI;

import DBApi.User;
import DBApi.UserSession;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;
import tools.*;
import tools.AvatarConfigs;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;


public class GSNClient extends Client{
    protected static String URL ="http://localhost:8090/GSNbackend/rest/";
    protected static String ImagesURL="https://localhost:8090/GSNbackend/resources/images/";
    protected static String ServerURL="http://localhost:8090/GSNbackend/";

    public static final int NORMAL_AVATAR=0;
    public static final int MINI_AVATAR=1;
    public static final int FULL_AVATAR=2;

    protected static Gson gson=new Gson();
    protected WebResource webResource;

    protected User user;
    protected String username;
    protected int id;
    protected String token;

    protected MessageManager messageManager=new MessageManager();
    protected LocationManager locationManager=new LocationManager();
    protected FriendManager friendManager=new FriendManager();

    public  User getUser(int userId){
        this.setWebResource(this.resource(getURL() + "user/" + userId+"/get"));
        try {
            ClientResponse response=webResource.type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
            if(response.getStatus()!=200)return null;
            String s=response.getEntity(String.class);
            User user = gson.fromJson(s, User.class);
            return user;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static GSNClient login(String username,String password) {
        GSNClient client=new GSNClient();
        client.setWebResource(client.resource(getURL() +"login/"+username));
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

    public boolean logout(){
        this.setWebResource(this.resource(getURL() +"user/"+id+"/logout"));
        try {
            ClientResponse r = webResource.type(MediaType.APPLICATION_JSON).header("token",getToken()).get(ClientResponse.class);
            return Boolean.valueOf(r.getEntity(String.class));
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(String oldpassword,String newpassword){
        MultivaluedMap<String,String>data=new MultivaluedMapImpl();
        data.add("oldPassword", oldpassword);
        data.add("newPassword", newpassword);
        this.setWebResource(this.resource(getURL() + "changePassword/" + getUsername()));
        ClientResponse response=webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, data);
        return response.getStatus()==201;
    }

    public static boolean registration(User user,String username,String password){
        RegistrationData registrateData=new RegistrationData();
        registrateData.setUserName(username);
        registrateData.setPassword(password);
        registrateData.setUser(user);
        WebResource webResource1;
        Client client=new Client();
        webResource1=client.resource(getURL() +"registration");
        String input=gson.toJson(registrateData);
        try{
            ClientResponse response=webResource1.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,input);
            String s=response.getEntity(String.class);
            return (gson.fromJson(s, Boolean.class));
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean editUser(User newUser){
        newUser.setUserId(getId());
        setWebResource(this.resource(getURL() +"user/"+getId()+"/edit"));
        String input=gson.toJson(newUser);
        try{
            ClientResponse response=webResource.type(MediaType.APPLICATION_JSON).header("token", getToken()).post(ClientResponse.class, input);
            return (response.getStatus()==201);
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean setAvatar(File f, AvatarConfigs configs){
        setWebResource(resource(URL+"user/"+id+"/"+"putAvatar"));
        WebResource webResource = getWebResource();
        final FormDataMultiPart multiPart = new FormDataMultiPart();
        if (f != null) {
            multiPart.bodyPart(new FileDataBodyPart("file", f, MediaType.valueOf("image/*")));
            String configsJson=gson.toJson(configs);
            multiPart.field("configs",configsJson,MediaType.APPLICATION_JSON_TYPE);
        }
        try {
            final ClientResponse clientResp = webResource.header("token",getToken()).type(MediaType.MULTIPART_FORM_DATA_TYPE).post(
            ClientResponse.class, multiPart);
            return clientResp.getStatus()==201;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public BufferedImage getAvatar(int type){
        setWebResource(resource(URL+"user/"+id+"/"+"getAvatarURL/"+type));
        ClientResponse response=getWebResource().get(ClientResponse.class);
        String imageURL=ServerURL+response.getEntity(String.class);
        return getImage(imageURL);
    }

    protected BufferedImage getImage(String imageURL){
        setWebResource(resource(imageURL));
        ClientResponse response1=getWebResource().type("image/*").get(ClientResponse.class);
        byte[] bytes=response1.getEntity(byte[].class);
        BufferedImage img=null;
        try {
            img = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        GSNClient.URL = URL;
    }

    public WebResource getWebResource() {
        return webResource;
    }

    public void setWebResource(WebResource webResource) {
        this.webResource = webResource;
    }

    public User getUser() {
        if(user==null) {
            return getUser(getId());
        }
        else
            return user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public void setFriendManager(FriendManager friendManager) {
        this.friendManager = friendManager;
    }
}
