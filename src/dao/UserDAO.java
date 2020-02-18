package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Admin;
import beans.Buyer;
import beans.Deal;
import beans.Message;
import beans.Review;
import beans.Seller;
import beans.User;
import beans.User.Role;
import helpers.Filter;
import helpers.LoginRequest;

public class UserDAO 
{
	private HashMap<String,User> users = new HashMap<String,User>();
	
	private static Admin aiAdmin = new Admin("Koko(AI)","koko@gmail.com","admin","Koko(Admin)","AI","Novi Sad","011");
	
	private static Admin mainAdmin;
	
	private String contextPath;
	
	public UserDAO(String ctx)
	{
		this.contextPath = ctx;
		try {
			loadUsers();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Admin admin = new Admin("NikolaAdmin","nikola@gmail.com","admin","Nikola","Milanovic","Novi Sad","0691711271");
		users.put(admin.getEmail(), admin);
		
		mainAdmin = admin;
	}
	
	public static Admin GetAdmin()
	{
		return mainAdmin;
	}
	
	public static Admin GetKoko()
	{
		return aiAdmin;
	}
	
	public void addUser(User u)
	{
		users.put(u.getEmail(), u);
		System.out.println(users.size());
	}
	
	public User get(String email)
	{
		return users.get(email);
	}
	
	public void replace(String email, User u)
	{
		users.put(email, u);
	}
	
	public Boolean userExistsWithEmail(String email)
	{
		return users.containsKey(email);
	}
	
	public Boolean userExistsWithUsername(String username)
	{
		for(User u : users.values())
		{
			if(u.getUsername().equals(username))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public List<User> getUsers()
	{
		List<User> ret = new ArrayList<User>();
		
		for(User u : users.values())
		{
			ret.add(u);
		}
		
		return ret;
	}
	
	
	public List<User> getBuyers()
	{
		List<User> ret = new ArrayList<User>();
		
		for(User u : users.values())
		{
			if(u.getRole() == Role.Buyer)
			{
				ret.add(u);	
			}
		}
		
		return ret;
	}
	
	public List<User> getSellers()
	{
		List<User> ret = new ArrayList<User>();
		
		for(User u : users.values())
		{
			if(u.getRole() == Role.Seller)
			{
				ret.add(u);	
			}
		}
		
		return ret;
	}
	
	
	public Seller getSellerOf(Deal d)
	{
		for(User u : users.values())
		{
			if(u instanceof Seller)
			{
				Seller s = (Seller)u;
				
				if(s.contains(d))
				{
					return s;
				}
			}
		}
		
		return null;
	}
	
	public Seller getSellerOf(Review review)
	{
		for(User u : users.values())
		{
			if(u instanceof Seller)
			{
				Seller s = (Seller)u;
				
				List<Review> reviews = s.getReviews();
				
				for(Review r : reviews)
				{
					if(r.getHeader().equals(review.getHeader()) && r.getContent().equals(review.getContent()))
					{
						return s;
					}
				}
			}
		}
		
		return null;
	}
	
	public Buyer getBuyerOf(Deal d)
	{
		for(User u : users.values())
		{
			if(u instanceof Seller)
			{
				Buyer b = (Buyer)u;
				
				if(b.contains(d))
				{
					return b;
				}
			}
		}
		
		return null;
	}
	
	public List<Review> getReviewsOf(User user)
	{
		List<Review> ret = new ArrayList<Review>();
		
		for(User u : users.values())
		{
			if(u instanceof Seller)
			{
				Seller s = (Seller)u;
				
				for(Review r : s.getReviews())
				{
					if(r.getReviewer().equals(user.getEmail()))
					{
						ret.add(r);
					}
				}
			}
		}
		
		return ret;
	}
	
	public List<User> getAdmins()
	{
		List<User> ret = new ArrayList<User>();
		
		for(User u : users.values())
		{
			if(u.getRole() == Role.Admin)
			{
				ret.add(u);	
			}
		}
		
		return ret;
	}
	
	public List<User> getUsers(Filter filter)
	{
		List<User> ret = new ArrayList<User>();
		
		for(User u : users.values())
		{
			if(filterCheck(u,filter))
			{
				ret.add(u);
			}
		}
		
		return ret;
	}
	
	public Boolean userExists(LoginRequest request)
	{
		for(String name : users.keySet())
		{
			User user = users.get(name);
			LoginRequest req= new LoginRequest(user.getEmail(),user.getPassword());
			
			if(LoginRequest.Match(req, request))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private Boolean filterCheck(User u, Filter filter)
	{
		Boolean flag = true;
		
		if(filter.name != null && filter.name != "")
		{
			if(!u.getName().contains(filter.name))
			{
				flag = false;
			}
		}
		
		if(filter.city != null && filter.city != "")
		{
			if(!u.getCity().contains(filter.city))
			{
				flag = false;
			}
		}
		
		return flag;
	}
	
	private void loadUsers() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		File file = new File(this.contextPath + "data"+ java.io.File.separator +"admins.json");
		String json = ""; 
		String temp;
		
		if(file.exists())
		{
			try(BufferedReader br = new BufferedReader(new FileReader(file))){
				while ((temp = br.readLine()) != null) {
					json += temp;
				}
			}
			
			List<Admin> list = mapper.readValue(json, 
					new TypeReference<ArrayList<Admin>>() {});
			
			this.users.clear();
			for(Admin admin: list) {
				this.users.put(admin.getEmail(), admin);
			}
					
		}

		
		file = new File(this.contextPath + "data"+ java.io.File.separator +"sellers.json");
		json = "";
		
		if(file.exists())
		{
			try(BufferedReader br = new BufferedReader(new FileReader(file))){ 
				while ((temp = br.readLine()) != null) {
					json += temp;
				}
			}
			List<Seller> list2 = mapper.readValue(json, 
					new TypeReference<ArrayList<Seller>>() {});
			
			for(Seller salesman: list2) {
				this.users.put(salesman.getEmail(), salesman);
			}
				
		}
		
		
		file = new File(this.contextPath + "data"+ java.io.File.separator +"buyers.json");
		json = ""; 
		
		if(file.exists())
		{
			try(BufferedReader br = new BufferedReader(new FileReader(file))){ 
				while ((temp = br.readLine()) != null) {
					json += temp;
				}
			}
			System.out.println("Inside");
			List<Buyer> list3 = mapper.readValue(json, 
					new TypeReference<ArrayList<Buyer>>() {});
			System.out.println(list3.size());
			for(Buyer customer: list3) {
				System.out.println(customer.getEmail());
				this.users.put(customer.getEmail(), customer);
			}		
		}
		
		System.out.println("Files: Users loaded");
	}
	
	public void saveUsers() {
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayList<Admin> list = new ArrayList<Admin>();
		for (User user: this.users.values()) {
			if (user.getRole().equals(Role.Admin)) {
				list.add( (Admin)user );
			}
		}
		File file = new File(this.contextPath + "data"+ java.io.File.separator +"admins.json");
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Seller> list2 = new ArrayList<Seller>();
		for (User user: this.users.values()) {
			if (user.getRole().equals(Role.Seller)) {
				list2.add( (Seller)user );
			}
		}
		File file2 = new File(this.contextPath + "data"+ java.io.File.separator +"sellers.json");
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file2, list2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Buyer> list3 = new ArrayList<Buyer>();
		for (User user: this.users.values()) {
			if (user.getRole().equals(Role.Buyer)) {
				list3.add( (Buyer)user );
			}
		}
		System.out.println(this.contextPath + "data"+ java.io.File.separator +"buyers.json");
		File file3 = new File(this.contextPath + "data"+ java.io.File.separator +"buyers.json");
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file3, list3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
