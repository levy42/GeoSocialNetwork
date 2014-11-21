package ClientAPI;

import DBApi.Chat;
import DBApi.Location;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.ClientResponse;
import tools.LocationInfo;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class LocationManager {

    public static final int LONG=360;
    public static final int LAT=360;
    private GSNClient client;
    private int listenerInterval=1000;
    private Location myLocation;
    private LocationListener locationListener=new LocationListener() {
        @Override
        public void getLocations(List<LocationInfo> users) {
            System.out.print("locations got");
        }
    };

    Thread locationListenerThread;

    public void starListening(){
        locationListenerThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(!Thread.interrupted()) {
                    List<LocationInfo> users = getUsers(myLocation);
                    locationListener.getLocations(users);
                    try {
                        Thread.sleep(getListenerInterval());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        locationListenerThread.start();
    }

    public void cencelListening(){
        if(locationListenerThread!=null)locationListenerThread.interrupt();
    }

    public void setLocationListener(LocationListener locationListener,int interval){
        setListenerInterval(interval);
        this.locationListener=locationListener;
    }

    public List<LocationInfo> getUsers(Location l){
        if(l==null)return null;
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "location/" + getClient().getId() + "/usersLocations"));
        try {
            String input= getClient().gson.toJson(l);
            ClientResponse response = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).post(ClientResponse.class,input);
            String s = response.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<LocationInfo>>() {}.getType();
            return getClient().gson.fromJson(s,typeOfObjectsList);
        }
        catch (Exception e){
            return null;
        }
    }

    public List<LocationInfo> getFriends(){
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "location/" + getClient().getId() + "/friendsLocations"));
        try {
            ClientResponse response = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).get(ClientResponse.class);
            String s = response.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<LocationInfo>>() {}.getType();
            return getClient().gson.fromJson(s,typeOfObjectsList);
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean sendLocation(Location l){
        myLocation=l;
        getClient().setWebResource(getClient().resource(GSNClient.getURL() + "location/" + getClient().getId() + "/sendLocation"));
        String input=GSNClient.gson.toJson(l);
        try {
            ClientResponse response = getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).post(ClientResponse.class, input);
            String s = response.getEntity(String.class);
            return getClient().gson.fromJson(s,Boolean.class);
        }
        catch (Exception e){
            return false;
        }
    }

    public int getListenerInterval() {
        return listenerInterval;
    }

    public void setListenerInterval(int listenerInterval) {
        if(listenerInterval<1000)return;
        this.listenerInterval = listenerInterval;
    }

    public List<Chat> getChats(Location l){
        getClient().setWebResource(getClient().resource(getClient().URL + "location/" + getClient().getId() + "/getChats"));
        ClientResponse r= getClient().getWebResource().type(MediaType.APPLICATION_JSON).header("token", getClient().getToken()).post(ClientResponse.class,GSNClient.gson.toJson(l));
        if(r.getStatus()==201) {
            String s = r.getEntity(String.class);
            Type typeOfObjectsList = new TypeToken<ArrayList<Chat>>() {}.getType();
            return getClient().gson.fromJson(s,typeOfObjectsList);
        }
        return null;
    }
    public  static int locationGroup(Location l){
        return (int)Math.floor((double)l.getLongitude())*LONG+(int)Math.floor((double)l.getLatitude());
    }

    public GSNClient getClient() {
        return client;
    }

    public void setClient(GSNClient client) {
        this.client = client;
    }
}
