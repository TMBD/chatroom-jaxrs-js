package chatroom;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("room")
@Produces(MediaType.APPLICATION_JSON)
public class ChatRoomResource {
	
	@Inject
	ChatRoom chatroom;
	
	@PUT
    @Path("{pseudo}")
    public Response subscribeToRoom(@PathParam("pseudo") String pseudo) {
		if(chatroom.subscribe(pseudo))
			return Response.ok().build();
		else 
			return Response.serverError().build();
    }
	
	@DELETE
    @Path("{pseudo}")
    public Response unsubscribeToRoom(@PathParam("pseudo") String pseudo) {
		if(chatroom.unsubscribe(pseudo))
			return Response.ok().build();
		else 
			return Response.serverError().build();
    }
	
	@GET
    @Path("{pseudo}")
    public ArrayList<Message> getMessageFromRoom(@PathParam("pseudo") String pseudo) {
		return chatroom.getMessage(pseudo);
	}
	
	@POST
    @Path("{pseudo}")
    public Response postMessageToRoom(@PathParam("pseudo") String pseudo, String body) {
		JSONObject jsonBody = new JSONObject(body);
    	String content = jsonBody.getString("content");
		if(chatroom.postMessage(pseudo, content))
			return Response.ok().build();
		else 
			return Response.serverError().build();
	}
}
