package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Category;
import dao.CategoryDAO;
import dao.DealDAO;

@Path("categories")
public class CategoryService 
{
	@Context
	ServletContext ctx;
	
	public CategoryService ()
	{
		super();
	}
	
	@PostConstruct
	public void init() 
	{
		if (ctx.getAttribute("categoryDAO") == null) 
		{
			ctx.setAttribute("categoryDAO", new CategoryDAO(ctx.getRealPath("/")));
		}
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addCategory(Category c)
	{
		CategoryDAO dao = (CategoryDAO)ctx.getAttribute("categoryDAO");
		
		if(dao == null) return Response.status(400).build();
		
		dao.add(c);
		
		dao.saveCategories();
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/delete/{name}")
	public Response deleteCategory(@PathParam("name") String name)
	{
		CategoryDAO dao = (CategoryDAO)ctx.getAttribute("categoryDAO");
		
		if(dao == null) return Response.status(400).build();
		
		dao.remove(name);
		
		dao.saveCategories();
		
		return Response.ok().build();
	}
	
	
	@PUT
	@Path("/update/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCategory(@PathParam("name") String name, Category c)
	{
		CategoryDAO dao = (CategoryDAO)ctx.getAttribute("categoryDAO");
		
		if(dao == null) return Response.status(400).build();
		
		dao.replace(name, c);
		
		dao.saveCategories();
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories()
	{
		CategoryDAO dao = (CategoryDAO)ctx.getAttribute("categoryDAO");
		
		if(dao == null) return null;
		
		return dao.getAll();
	}
}
