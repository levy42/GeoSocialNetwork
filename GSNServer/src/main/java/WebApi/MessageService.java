package WebApi;

import DBApi.ChatMessage;
import DBApi.PrivateMessage;
import managers.ServiceManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/messages/{userId : \\d+}")
public class MessageService {
    @PathParam("userId")int userId;

    @POST
    @Path("/sendChatMessage/{chatId : \\d+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendChatMessage(ChatMessage m,@PathParam("chatId")int chatId,@HeaderParam("token")String key){
        if(ServiceManager.autentificationManager.identity(userId,key)
                &&ServiceManager.messageManager.sendMessageToChat(m))
            return Response.status(201).entity(true).build();
        return Response.status(200).entity(false).build();
    }

    @GET
    @Path("/getNewChatMessage/{chatId}/{date}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatMessage(@PathParam("chatId")int chatId,@PathParam("date")long date,@HeaderParam("token")String token){
        if(ServiceManager.autentificationManager.identity(userId,token))
        {
            List<ChatMessage> messages=ServiceManager.messageManager.getLastChatMessages(chatId,date);
            return Response.status(201).entity(messages).build();
        }
        return Response.status(201).build();
    }

    @GET
    @Path("/getChatMessage/{chatId}/{date}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getChatMessage(@PathParam("date")long date,@HeaderParam("token")String token,@PathParam("chatId")int chatId){
        if(ServiceManager.autentificationManager.identity(userId,token))
        {
            List<ChatMessage> messages=ServiceManager.messageManager.getChatMessages(chatId,date);
            return Response.status(201).entity(messages).build();
        }
        return Response.status(201).build();
    }

    @POST
    @Path("/sendMessage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(PrivateMessage m,@HeaderParam("token")String token){
        if(ServiceManager.autentificationManager.identity(userId,token)
                &&ServiceManager.messageManager.sendPrivateMessage(m))
            return Response.status(201).entity(true).build();
        return Response.status(200).entity(false).build();
    }
    @GET
    @Path("/getMessage")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewMessage(@HeaderParam("token")String key){
        if(ServiceManager.autentificationManager.identity(userId,key))
        {
            List<PrivateMessage>messages=ServiceManager.messageManager.getNewPrivateMessages(userId);
            return Response.status(201).entity(messages).build();
        }
        return Response.status(201).build();
    }

    @GET
    @Path("/getDialog/{userWith}/{offset}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDialog(@HeaderParam("token")String key,@PathParam("userWith")int userWithId,@PathParam("offset")long dateOffset){
        if(ServiceManager.autentificationManager.identity(userId,key))
        {
            List<PrivateMessage>messages=ServiceManager.messageManager.getDialogMessages(userId,userWithId,dateOffset);
            return Response.status(201).entity(messages).build();
        }
        return Response.status(201).build();
    }
}
