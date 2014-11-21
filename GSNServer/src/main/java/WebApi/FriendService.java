package WebApi;

import managers.ServiceManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("friendsApplications/{me}")
public class FriendService {
    @PathParam("me")int userId;
    @GET
    @Path("/send/{friendId}")
    public Response sendApplication(@HeaderParam("token")String token,@PathParam("friendId")int friendId) {
        if(ServiceManager.autentificationManager.identity(userId,token)) {
            ServiceManager.friendManager.invite(userId, friendId);
            return Response.ok().build();
        }
        return Response.status(405).build();
    }
    @GET
    @Path("/accept/{friendId}")
    public Response acceptApplication(@HeaderParam("token")String token,@PathParam("friendId")int friendId) {
        if(ServiceManager.autentificationManager.identity(userId,token)) {
            ServiceManager.friendManager.acceptApplicator(userId, friendId);
            return Response.ok().build();
        }
        return Response.status(405).build();
    }
    @GET
    @Path("/reject/{friendId}")
    public Response rejectApplication(@HeaderParam("token")String token,@PathParam("friendId")int friendId) {
        if(ServiceManager.autentificationManager.identity(userId,token)) {
            ServiceManager.friendManager.rejectApplicator(userId,friendId);
            return Response.ok().build();
        }
        return Response.status(405).build();
    }
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectApplication(@HeaderParam("token")String token) {
        if(ServiceManager.autentificationManager.identity(userId,token)) {
            List<Integer> applicators=ServiceManager.friendManager.getApplicators(userId);
            return Response.ok().entity(applicators).build();
        }
        return Response.status(405).build();
    }

    @GET
    @Path("/deleteFriend/{friendId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFriend(@HeaderParam("token")String token,@PathParam("friendId")int friendId) {
        if(ServiceManager.autentificationManager.identity(userId,token)) {
            ServiceManager.friendManager.deleteFriend(userId,friendId);
            return Response.ok().build();
        }
        return Response.status(405).build();
    }
}
