package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Admin;
import beans.Buyer;
import beans.Message;
import beans.Seller;
import beans.User;
import dao.UserDAO;
import helpers.Filter;
import helpers.LoginRequest;
import helpers.Mailbox;

@Path("account")
public class AccountService 
{
	@Context
	ServletContext ctx;
	
	public AccountService()
	{
		super();
	}
	
	@PostConstruct
	public void init() 
	{
		if (ctx.getAttribute("userDAO") == null) 
		{
			ctx.setAttribute("userDAO", new UserDAO(ctx.getRealPath("/")));
		}
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginRequest logRq,@Context HttpServletRequest rq)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		
		if(dao == null) return Response.status(500).build();
		
		
		if(!dao.userExists(logRq))
		{
			return Response.status(400).build();
		}
		
		if (rq.getSession().getAttribute("user") != null)
			rq.getSession().invalidate();
		
		User user = dao.get(logRq.getEmail());
		
		rq.getSession().setAttribute("user", user);
				
		return Response.ok().build();
	}
	
	@GET
	@Path("/getUnreadMessages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getUnreadMessages(@Context HttpServletRequest rq)
	{	
		User user = (User) rq.getSession().getAttribute("user");
		
		return user.getUnreadMessages();
	}
	
	@PUT
	@Path("/markMessagesAsRead")
	public Response markMessagesAsRead(@Context HttpServletRequest rq)
	{
		User user = (User) rq.getSession().getAttribute("user");
		
		user.readAllMessages();
		
		UserDAO dao = (UserDAO)ctx.getAttribute("userDAO");
		dao.saveUsers();
		
		return Response.ok().build();
	}
	
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User u, @Context HttpServletRequest rq)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		
		if(dao == null) return Response.status(500).build();
				
		if(dao.userExistsWithEmail(u.getEmail()))
		{
			return Response.status(400).entity("Email").build();
		}
		
		if(dao.userExistsWithUsername(u.getUsername()))
		{
			return Response.status(400).entity("Username").build();
		}
		
		Buyer newUser = new Buyer(u);		
		dao.addUser(newUser);
		
		Mailbox.sendAutomatic(newUser, "Welcome to Shopify!", "Hello "+ newUser.getName()+", " + Mailbox.getWelcomeText(),"");
		
		dao.saveUsers();
		
		return Response.ok().build();
	}
	
	
	
	@GET
	@Path("/currentUser")
	@Produces(MediaType.APPLICATION_JSON)
	public User currUser(@Context HttpServletRequest rq) {
		
		if(rq.getSession(false) == null)
		{
			System.out.println("Session false");
			return null;			
		}
		
		User user = (User) rq.getSession(false).getAttribute("user");
							
		return user;
	}
	
		
	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers()
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		
		if(dao == null) return null;
		
		
		return dao.getUsers();
	}
	
	@POST
	@Path("getAllFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<User> getAllUsersFilter(Filter f)
	{
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		
		if(dao == null) return null;
		
		return dao.getUsers(f);
	}
	
	
	@PUT
	@Path("updateRole/{userID}/{role}")
	public Response updateRole(@PathParam("userID") String userID,@PathParam("role") String role, String email)
	{
		
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		
		if(dao == null) return Response.status(400).build();
		
		User user = dao.get(userID);
		
		if(role.equals("Admin"))
		{
			//Parse to admin
			Admin a = Admin.Parse(user);
			dao.replace(a.getEmail(), a);
		}
		else if(role.equals("Seller"))
		{
			//Parse to seller
			Seller s = Seller.Parse(user);
			dao.replace(s.getEmail(), s);
		}
		else if(role.equals("Buyer"))
		{
			//Parse to buyer
			Buyer b = Buyer.Parse(user);
			dao.replace(b.getEmail(), b);
		}
		
		dao.saveUsers();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/logout")
	public Response logout(@Context HttpServletRequest rq)
	{
		rq.getSession().invalidate();
		return Response.ok().build();
	}
		
}
