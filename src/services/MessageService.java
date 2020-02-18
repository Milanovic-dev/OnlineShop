package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Message;
import beans.User;
import dao.UserDAO;
import helpers.Mailbox;

@Path("messages")
public class MessageService 
{
	@Context
	ServletContext ctx;
	
	
	public MessageService()
	{
		super();
	}
	
	
	@POST
	@Path("/sendMessage/{recieverID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendMessage(Message m, @PathParam("recieverID")String Id,@Context HttpServletRequest rq)
	{
		User user = (User) rq.getSession().getAttribute("user");
		
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		
		if(dao == null) return Response.status(500).build();
				
		//Mailbox.send(m, dao.get(Id));
		
		Mailbox.send("", m.getHeader(), m.getContent(), user.getEmail(), dao.get(Id));
		
		return Response.ok().build();
	}
	
	
	@DELETE
	@Path("/deleteMessage/{messageHeader}/{messageContent}")
	public Response deleteMessage(@PathParam("messageHeader") String header,@PathParam("messageContent") String content,@Context HttpServletRequest rq)
	{
		User user = (User) rq.getSession().getAttribute("user");
		
		
		user.deleteMessage(header, content);
		
		return Response.ok().build();
	}
	
	
}
