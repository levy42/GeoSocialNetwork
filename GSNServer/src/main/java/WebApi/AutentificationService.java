package WebApi;

import DBApi.UserSession;
import managers.ServiceManager;
import tools.RegistrationData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class AutentificationService {
    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registration(RegistrationData registrationData) {
        boolean bool=false;
        try {
            bool = ServiceManager.userManager.registrate(registrationData.getUser(), registrationData.getUserName(), registrationData.getPassword());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(bool) {
            return Response.status(201).entity(bool).build();
        }
        else
            return Response.status(200).entity(bool).build();
    }

    @GET
    @Path("/delete/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("login")String name,String password){
        int userId=ServiceManager.autentificationManager.deleteAutenteficationData(name,password);
        if(userId!=-1) {
            ServiceManager.userManager.deleteUser(userId);
        }
        return Response.ok().build();
    }
    @POST
    @Path("/login/{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserSession login(@PathParam("login")String name,String password) {
        UserSession userSession=ServiceManager.userManager.login(name,password);
        return userSession;
    }
    @POST
    @Path("/changePassword/{login}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response changePassword(@PathParam("login")String userName,@FormParam("oldPassword")String oldPassword,@FormParam("newPassword")String newPassword) {
        if(ServiceManager.autentificationManager.changePassword(userName,oldPassword,newPassword))
            return Response.status(201).build();
        else
            return Response.status(200).build();
    }
}
