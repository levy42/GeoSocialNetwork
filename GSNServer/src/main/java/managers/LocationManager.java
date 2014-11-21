package managers;

import DBApi.User;
import DBApi.Location;
import tools.LocationInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by oleh on 08.11.2014.
 */
public class LocationManager {

    protected static final int LONG=360;
    protected static final int LAT=360;
    public HashMap<Integer,User>users=new HashMap<Integer,User>();
    public HashMap<Integer, LocationInfo> usersLoc =new HashMap<Integer, LocationInfo>();
    public HashMap<Integer, LocationInfo>[][] usersMap=new HashMap[LONG][LAT];
    public HashMap<Integer, LocationInfo> getUsersLoc(int i,int j){
        if(usersMap[i][j]==null)
            usersMap[i][j]=new HashMap<Integer, LocationInfo>();
        return usersMap[i][j];
    }

    public void startLocationMode(int userId){
        User u=ServiceManager.userManager.getUser(userId);
        users.put(userId,u);
        addUser(u);
    }

    public void finishLocationMode(int userId){
        users.remove(userId);
    }
    public List<LocationInfo> getUsersLocations(Location l,int userId){
        int i=(int)Math.floor(l.getLongitude());
        int j=(int)Math.floor(l.getLatitude());
        List<LocationInfo>userLocationInfos=new ArrayList<LocationInfo>();
        userLocationInfos.addAll(getUsersLoc(i,j).values());
        userLocationInfos.addAll(getUsersLoc(i+1,j).values());
        userLocationInfos.addAll(getUsersLoc(i-1,j).values());
        userLocationInfos.addAll(getUsersLoc(i,j+1).values());
        userLocationInfos.addAll(getUsersLoc(i,j-1).values());
        userLocationInfos.addAll(getUsersLoc(i+1,j+1).values());
        userLocationInfos.addAll(getUsersLoc(i-1,j-1).values());
        userLocationInfos.addAll(getUsersLoc(i+1,j-1).values());
        userLocationInfos.addAll(getUsersLoc(i-1,j+1).values());
        LocationInfo userLoc=usersLoc.get(userId);
        userLocationInfos.remove(userLoc);
        return userLocationInfos;
    }

    public List<LocationInfo> getUsersLocations(Set<Integer> ids){
        List<LocationInfo>userLocationInfos=new ArrayList<LocationInfo>();
        for(int i:ids){
            LocationInfo info= usersLoc.get(i);
            if(info!=null)
                userLocationInfos.add(info);
        }
        return  userLocationInfos;
    }

    public List<LocationInfo> getFriendLocations(int userId){
        return getUsersLocations(users.get(userId).getFriends());
    }

    public void removeUser(int id){
        LocationInfo info= usersLoc.remove(id);
        if(info==null)return;
        Location l=info.getLocation();
        int i=(int)Math.floor(l.getLongitude());
        int j=(int)Math.floor(l.getLatitude());
        if(usersMap[i][j]==null)return;
        getUsersLoc(i,j).remove(id);
    }

    public void addUser(User user){
        if(user.getMode()!= User.PRIVATE_MODE) {
            LocationInfo info = new LocationInfo();
            info.setId(user.getUserId());
            usersLoc.put(user.getUserId(), info);
        }
    }

    public void updateLocation(Location l,int id){
        if(users.get(id)==null)startLocationMode(id);
        Location prev= usersLoc.get(id).getLocation();
        if(prev==null){
            int i=(int)Math.floor(l.getLongitude());
            int j=(int)Math.floor(l.getLatitude());
            usersLoc.get(id).setLocation(l);
            if(users.get(id).getMode()==User.PUBLIC_MODE)
                getUsersLoc(i,j).put(id, usersLoc.get(id));
        }
        else {
            int i0 = (int) Math.floor(prev.getLongitude());
            int j0 = (int) Math.floor(prev.getLatitude());
            int i = (int) Math.floor(l.getLongitude());
            int j = (int) Math.floor(l.getLatitude());
            if (i0 == i && j0 == j) return;
            usersLoc.get(id).setLocation(l);
            LocationInfo info = getUsersLoc(i0,j0).remove(id);
            info.setLocation(l);
            getUsersLoc(i,j).put(id, info);
        }
    }

    public  static int locationGroup(Location l){
        return (int)Math.floor((double)l.getLongitude())*LONG+(int)Math.floor((double)l.getLatitude());
    }
}
