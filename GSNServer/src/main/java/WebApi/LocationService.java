package WebApi;

import managers.ServiceManager;
import DBApi.Location;
import tools.LocationInfo;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/location/{userId : \\d+}")
public class LocationService {
    @PathParam("userId")int userId;

    @POST
    @Path("/sendLocation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLocation(Location l,@HeaderParam("token")String key) {
        if(ServiceManager.autentificationManager.identity(userId, key)) {
            ServiceManager.locationManager.updateLocation(l,userId);
            return Response.status(Response.Status.OK).entity(true).build();
        }
        return Response.ok().entity(false).build();
    }
    @GET
    @Path("/friendsLocations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LocationInfo> getFriends(@HeaderParam("token")String key) {
        if(ServiceManager.autentificationManager.identity(userId, key)) {
            return ServiceManager.locationManager.getFriendLocations(userId);
        }
        return null;
    }
    @POST
    @Path("/usersLocations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<LocationInfo> getUsers(Location l,@HeaderParam("token")String key) {
        if(ServiceManager.autentificationManager.identity(userId, key)) {
            return ServiceManager.locationManager.getUsersLocations(l,userId);
        }
        return null;
    }
    @POST
    @Path("/getChats")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getChats(Location l){
        return Response.status(201).entity(ServiceManager.messageManager.getChats(l)).build();
    }
}
