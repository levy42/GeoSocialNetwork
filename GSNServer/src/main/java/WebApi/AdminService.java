package WebApi;

import DBApi.Chat;
import managers.ServiceManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/admin/{id}")
public class AdminService {
    @PathParam("id")int adminId;

    @GET
    @Path("/openChat/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response openChat(@PathParam("id") int chatId,@HeaderParam("token")String token){
        if(ServiceManager.autentificationManager.isAdmin(adminId)&&ServiceManager.autentificationManager.identity(adminId,token)){
            ServiceManager.adminManager.openChat(chatId);
            return Response.ok().build();
        }
        return Response.status(405).build();
    }

    @POST
    @Path("/registrateChat")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrateChat(Chat chat,@HeaderParam("token")String token){
        if(ServiceManager.autentificationManager.isAdmin(adminId)&&ServiceManager.autentificationManager.identity(adminId,token)){
            return Response.ok().entity(ServiceManager.adminManager.registrateChat(chat)).build();
        }
        return Response.status(405).build();
    }
    @GET
    @Path("/closeChat/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response closeChat(@PathParam("chatId")int chatId,@HeaderParam("token")String token){
        if(ServiceManager.autentificationManager.isAdmin(adminId)&&ServiceManager.autentificationManager.identity(adminId,token)){
            ServiceManager.adminManager.closeChat(chatId);
            return Response.ok().build();
        }
        return Response.status(405).build();
    }

    @GET
    @Path("/deleteChat/{chatId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteChat(@PathParam("chatId")int chatId,@HeaderParam("token")String token){
        if(ServiceManager.autentificationManager.isAdmin(adminId)&&ServiceManager.autentificationManager.identity(adminId,token)){
            ServiceManager.adminManager.deleteChat(chatId);
            return Response.ok().build();
        }
        return Response.status(405).build();
    }
}
