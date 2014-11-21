package WebApi;

import DBApi.User;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import tools.AvatarConfigs;
import managers.ServiceManager;

import java.io.InputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/user/{userId : \\d+}")
public class UserService {
    public @PathParam("userId")int userId;

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User getUser() {
        return ServiceManager.userManager.getUser(userId);
    }
    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean logout(@HeaderParam("token")String token) {
        return ServiceManager.userManager.logout(userId,token)==true;
    }
    @POST
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUser(User u,@HeaderParam("token")String key){
        if(ServiceManager.userManager.editUser(u,key)) {
            return Response.status(201).build();
        }
        else return Response.status(200).build();
    }
    @POST
    @Path("/putAvatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response putAvatarImage(@HeaderParam("token")String token,@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail,@FormDataParam("configs") AvatarConfigs configs){
        if(ServiceManager.autentificationManager.identity(userId,token)) {
            if(ServiceManager.userManager.setAvatarImage(uploadedInputStream,configs,userId));
            return Response.status(201).build();
        }
        return Response.status(200).build();
    }
    @GET
    @Path("/getAvatarURL/{type}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatarImage(@PathParam("type")int type){
        return Response.status(200).entity(ServiceManager.userManager.getAvatarURL(userId,type)).build();
    }
}
