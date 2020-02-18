package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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
import beans.Deal;
import beans.Deal.Status;
import beans.Review;
import beans.Review.ReviewType;
import beans.Seller;
import beans.User;
import beans.User.Role;
import dao.DealDAO;
import dao.UserDAO;
import helpers.DealFilter;
import helpers.Mailbox;

@Path("deals")
public class DealService 
{
	
	@Context
	ServletContext ctx;
	
	public DealService()
	{
		super();
	}
	
	@PostConstruct
	public void init() 
	{
		if (ctx.getAttribute("dealDAO") == null) 
		{
			ctx.setAttribute("dealDAO", new DealDAO(ctx.getRealPath("/")));
		}
	}
	
	@POST
	@Path("/getActive")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getActiveDeals(DealFilter filter,@Context HttpServletRequest rq)
	{			
		DealDAO dao = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(dao == null) return null;
			
		User user = (User)rq.getSession().getAttribute("user");
		
		if(user != null)
		{
			if(user.getRole() == Role.Admin)
			{
				return dao.getAllDeals(filter);
			}
		}
		
		return dao.getActiveDeals(filter);
	}
	
	
	@POST
	@Path("/addDeal/{userID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDeal(@PathParam("userID") String userID, Deal d,@Context HttpServletRequest rq)
	{
				
		Deal newDeal = new Deal(d);
		
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(userDAO == null || dealDAO == null) return Response.status(500).build();
		
		//TODO: add deal to user published(Seller)
		Seller seller = (Seller)rq.getSession().getAttribute("user");
		
		seller.addToPublished(newDeal);	
		dealDAO.addDeal(newDeal);
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
			
		return Response.ok().build();
	}
		
	@PUT
	@Path("/updateDeal/{dealID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDeal(@PathParam("dealID") String dealID,Deal d,@Context HttpServletRequest rq)
	{
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		Deal deal = dealDAO.get(dealID);
		
		User user = (User)rq.getSession().getAttribute("user");
		
		
		if(deal == null || user == null) return Response.status(500).build();
		
		if(user.getRole() == Role.Seller)
		{
			Seller seller = (Seller)rq.getSession().getAttribute("user");			
			Mailbox.sendAutomatic(seller, "Your deal has been updated", "Hello, "+seller.getName()+" Your deal "+deal.getName()+" has been changed", deal.getName());
		}
		else if(user.getRole() == Role.Admin)
		{
			Seller seller = (Seller)userDAO.get(deal.getSeller());
			
			if(seller == null) return Response.status(500).entity("Error 404:Could not find the seller of this deal.").build();
			Mailbox.sendAutomatic(seller, "Your deal has been updated", "Hello, "+seller.getName()+" Your deal "+deal.getName()+" has been changed by Admin ("+user.getEmail()+")", deal.getName());
			
			if(deal.getStatus() == Status.Delivered || deal.getStatus() == Status.InProcess)
			{
				Buyer buyer = userDAO.getBuyerOf(deal);
				Mailbox.sendAutomatic(buyer, "Deal you ordered has been updated", "Hello, "+buyer.getName()+" deal"+deal.getName()+" you ordered has been changed by Admin", deal.getName());
			}
		}
		
		
		deal.setName(d.getName());
		deal.setPrice(d.getPrice());
		deal.setDescription(d.getDescription());
		deal.setCity(d.getCity());
		deal.setCategory(d.getCategory());
		
		if(d.getImage() != "")
		{
			deal.setImage(d.getImage());		
		}
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/deleteDeal/{dealID}")
	public Response deleteDealAdmin(@PathParam("dealID") String dealID,@Context HttpServletRequest rq)
	{
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");

		User user = (User)rq.getSession().getAttribute("user");
		
		if(dealDAO == null) return Response.status(500).build();
		
		Deal deal = dealDAO.get(dealID);
		
		if(deal == null) return Response.status(500).entity("Error 404:Could not find this deal in the database.").build();
			
		Seller seller = (Seller)userDAO.get(deal.getSeller());
		//get from DEALDAO
		
		if(seller == null)
		{
			seller = userDAO.getSellerOf(deal);
		}
		
		if(seller == null) return Response.status(500).entity("Error 404:Could not find the seller of this deal.").build();
		
		deal.archive();
				
		if(user.getRole() == Role.Admin)
		{
			Mailbox.sendAutomatic(seller, "Deal has been deleted by the admin.", "Admin has deleted your deal: "+deal.getName() + ".Sorry for inconvinience", deal.getName());		
			
			if(deal.getStatus() != Status.Active)
			{
				Buyer buyer = userDAO.getBuyerOf(deal);
				
				Mailbox.sendAutomatic(buyer, "Deal has been deleted by the admin.", "Admin has delete a deal "+deal.getName()+" you ordered.Sorry for inconvinience.", deal.getName());
			}
		}
		else
		{
			Mailbox.sendAutomatic(UserDAO.GetAdmin(), "Deal has been deleted by the seller.", "Seller "+seller.getEmail()+ " has deleted his deal "+deal.getName(), deal.getName());
		}
			
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("/restoreDeal/{dealID}")
	public Response restoreDeal(@PathParam("dealID") String dealID)
	{
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(dealDAO == null) return Response.status(500).build();
		
		Deal deal = dealDAO.get(dealID);
		
		if(deal == null) return Response.status(500).entity("Error 404:Could not find this deal in the database.").build();
		
		deal.unarchive();
		
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	
	@PUT
	@Path("/setDelivered/{dealID}")
	public Response setDealDelivered(@PathParam("dealID") String dealID,@Context HttpServletRequest rq)
	{
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(userDAO == null) return Response.status(500).build();
		
		
		Buyer buyer = (Buyer)rq.getSession().getAttribute("user");
		//get from DEALDAO
		
		Deal deal = dealDAO.get(dealID);
		
	
		if(deal == null)
		{
			deal = buyer.getFromOrdered(dealID);			
		}
		
		if(deal == null) return Response.status(500).entity("Error 404:Could not find this deal in the database.").build();
		
		deal.setStatus(Status.Delivered);
		
		if(buyer != null)
		{
			buyer.deleteFromOrdered(deal);
			buyer.addToDelivered(deal);			
		}
		else
		{
			if(buyer == null) return Response.status(500).entity("Error 404:Could not find the buyer of this deal.").build();
		}
		
		Seller seller = (Seller)userDAO.get(deal.getSeller());
		
		if(seller != null)
		{
			seller.deleteFromPublished(deal);
			seller.addToDelivered(deal);	
			Mailbox.sendAutomatic(seller, deal.getName()+" Delivery", "Your deal " + deal.getName() + " has been delivered!",deal.getName());
		}
		else
		{
			if(seller == null) return Response.status(500).entity("Error 404:Could not find the seller of this deal.").build();
		}
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/addToFavorite/{dealID}")
	public Response addToFavorite(@PathParam("dealID") String dealID,@Context HttpServletRequest rq)
	{
		Buyer buyer = (Buyer)rq.getSession().getAttribute("user");
		
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		Deal deal = dealDAO.get(dealID);
		
		if(deal == null) return Response.status(500).entity("Error 404:Could not find this deal in the database.").build();

		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		
		if(!buyer.getFavorites().contains(deal))
		{
			buyer.addToFavorites(deal);	
			deal.setPopularity(deal.getPopularity()+1);
		}
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/addReviewDeal/{dealID}/{rating}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReview(@PathParam("dealID") String dealID,@PathParam("rating") String rating,Review review,@Context HttpServletRequest rq)
	{
		dealID.replace("%20", " ");
		
		System.out.println(dealID);
		Buyer buyer = (Buyer)rq.getSession().getAttribute("user");
		
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		
		if(dealDAO == null || userDAO == null) return Response.status(500).build();
		
		Deal deal = dealDAO.get(dealID);
		
		if(deal == null)
		{
			deal = buyer.getFromDelivered(dealID);
		}
		
		if(deal == null) return Response.status(500).entity("Error 404:Could not find this deal in the database.").build();
		
		if(deal.containsReview(review.getHeader()))
		{
			return Response.status(500).entity("Error 300: Already contains review with same name!").build();
		}
		
	
		Review newReview = new Review(review);
		newReview.setDeal(deal.getName());
		newReview.setReviewer(buyer.getEmail());
		newReview.setType(ReviewType.Deal);
		
		if(rating.equals("Like"))
		{
			deal.addLike();
		}
		else if(rating.equals("Dislike"))
		{
			deal.addDislike();
		}
		
		Seller seller = (Seller)userDAO.get(deal.getSeller());
		
		if(seller == null)
		{
			seller = userDAO.getSellerOf(deal);
		}
		
		
		if(seller == null) return Response.status(500).entity("Error 404:Could not find the seller of this deal.").build();
		
		Mailbox.sendAutomatic(seller, "Your deal has been reviewed!", "Hello "+seller.getName()+"! Your deal: "+deal.getName()+" has been reviwed by "+buyer.getEmail(), deal.getName());
		
		deal.setSeller(seller.getEmail());
		deal.addReview(newReview);		
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/addReviewSeller/{userID}/{rating}")
	public Response addReviewSeller(@PathParam("userID") String userID,@PathParam("rating") String rating,Review review,@Context HttpServletRequest rq)
	{
		Buyer buyer = (Buyer)rq.getSession().getAttribute("user");
		
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		
		Seller seller =(Seller) userDAO.get(userID);
		
		if(seller == null) return Response.status(500).entity("Error 404:Could not find the seller of this deal.").build();
	
		Review newReview = new Review(review);
		newReview.setSeller(seller.getEmail());
		newReview.setDeal("");
		newReview.setReviewer(buyer.getEmail());
		newReview.setType(ReviewType.Account);
		
		seller.addReview(newReview);
		
		if(rating.equals("Like"))
		{
			seller.addLike();
		}
		else if(rating.equals("Dislike"))
		{
			seller.addDislike();
		}
			
		Mailbox.sendAutomatic(seller, "Your account has been reviwed!", "Hello "+seller.getName()+"! Your account has been reviewed by "+ buyer.getName(), "");
		
		userDAO.saveUsers();
		
		return Response.ok().build();
	}
		
	@DELETE
	@Path("/deleteReview")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteReview(Review review, @Context HttpServletRequest rq)
	{	
		User user = (User)rq.getSession().getAttribute("user");
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(userDAO == null || dealDAO == null) return Response.status(500).build();
		
		Seller seller = userDAO.getSellerOf(review);
			
		if(seller != null)
		{
			seller.deleteReview(review);	
			Mailbox.sendAutomatic(seller, "Review on your account has been deleted","The review \""+review.getHeader()+"\" made by "+ user.getEmail()+" has deleted his review for your account." , "");
		}
		else
		{		
			dealDAO.deleteReview(review);
			Mailbox.sendAutomatic(seller, "Review on your deal has been deleted","The review \""+review.getHeader()+"\" made by "+ user.getEmail()+" has deleted his review for your deal "+review.getDeal()+"." , "");
		}
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();

	}
	
	@PUT
	@Path("updateReview/{header}/{content}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReview(@PathParam("header") String header,@PathParam("content") String content,Review review,@Context HttpServletRequest rq)
	{
		User user = (User)rq.getSession().getAttribute("user");
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(userDAO == null || dealDAO == null) return Response.status(500).build();
		
		Seller seller = (Seller) userDAO.get(review.getSeller());
		
		
		if(review.getType().equals(ReviewType.Account))
		{
			System.out.println("First");
			seller.replaceReview(review, header, content);
			Mailbox.sendAutomatic(seller, "Review on your account has been changed","The review \""+review.getHeader()+"\" made by "+ user.getEmail()+" has changed his review for your account." , "");
		}
		else
		{
			System.out.println("Second");
			dealDAO.replaceReview(review, header, content);
			Mailbox.sendAutomatic(seller, "Review on your deal has been changed","The review \""+review.getHeader()+"\" made by "+ user.getEmail()+" has changed his review for your deal "+review.getDeal()+"." , "");
		}
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/getBuyerReviews")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Review> getBuyerReviews(@Context HttpServletRequest rq)
	{
		User user = (User)rq.getSession().getAttribute("user");
		
		List<Review> ret = new ArrayList<Review>();
		
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(userDAO == null || dealDAO == null) return null;
		
		List<Review> sellerReviews = userDAO.getReviewsOf(user);
		List<Review> dealReviews = dealDAO.getReviewsOf(user);
		
		ret.addAll(sellerReviews);
		ret.addAll(dealReviews);
		
		return ret;
	}
	
	
	@PUT
	@Path("/order/{dealID}")
	public Response orderDeal(@PathParam("dealID") String dealID,@Context HttpServletRequest rq)
	{
		UserDAO userDAO = (UserDAO)ctx.getAttribute("userDAO");
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(userDAO == null || dealDAO == null) return Response.status(500).build();
		
		Deal deal = dealDAO.get(dealID);
		
		if(deal == null) return Response.status(404).entity("Error 404:Could not find this deal in the database.").build();
		
		Buyer b = (Buyer)rq.getSession().getAttribute("user");
			
		if(b == null) return Response.status(404).entity("Error 404:Could not find the buyer of this deal.").build();

		b.addToOrdered(deal);
		
		deal.setStatus(Status.InProcess);
		
		userDAO.saveUsers();
		dealDAO.saveDeals();
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/uploadImage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadImage(@FormDataParam("fileToUpload") InputStream uploadedInputStream,
			@FormDataParam("name") String name)
	{
		String uploadedFileLocation = ctx.getRealPath("/data/images/" + name);
		
		System.out.println(uploadedFileLocation);
			
		try {
			//FileInputStream stream = new FileInputStream(new File("C:\\Users\\Nikola\\Desktop\\RaceCarTop.png"));
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
					
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return uploadedFileLocation;
	}
	

	@GET
	@Path("/getPopular")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Deal> getPopular()
	{	
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		List<Deal> deals = dealDAO.getActiveDeals();
		
		deals.sort((i,j) -> Integer.compare(i.getPopularity(), j.getPopularity()));
		
		if(deals.size() > 9)
		{
			return deals.subList(0, 9);
		}
		else
		{
			return deals;
		}
	}
	
	@GET
	@Path("/get/{dealID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Deal getDeal(@PathParam("dealID") String dealID)
	{
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		return dealDAO.get(dealID);
	}
	
	@GET
	@Path("/getCities")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCities()
	{
		DealDAO dealDAO = (DealDAO)ctx.getAttribute("dealDAO");
		
		if(dealDAO == null) return null;
		
		return dealDAO.getAllCities();
	}
		
}
