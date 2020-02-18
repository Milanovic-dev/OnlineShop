package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Deal;
import beans.Deal.Status;
import beans.Review;
import beans.User;
import helpers.DealFilter;

public class DealDAO 
{
	private HashMap<String,Deal> deals = new HashMap<String,Deal>();
	private String contextPath;
	
	public DealDAO(String ctx)
	{
		super();
		contextPath = ctx;
		
		try {
			loadDeals();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void addDeal(Deal d)
	{
		deals.put(d.getName(),d);
	}
	
	public void remove(String id)
	{
		deals.get(id).archive();
		deals.get(id).setActive(false);
	}
	
	public Deal get(String id)
	{
		return deals.get(id);
	}
	
	public Boolean dealExists(String id)
	{
		return deals.containsKey(id);
	}
	
	public List<Deal> getActiveDeals()
	{
		List<Deal> ret = new ArrayList<Deal>();
		
		for(Deal d : deals.values())
		{
			if(d.isActive() && !d.isArchived() && d.getStatus() == Status.Active)
			{
				ret.add(d);
			}
		}
		
		return ret;
	}
	
	public List<String> getAllCities()
	{
		List<String> ret = new ArrayList<String>();
		
		for(Deal d : deals.values())
		{
			String city = d.getCity();
			if(city != "" && !ret.contains(city))
			{
				ret.add(city);
			}
		}
		
		return ret;
	}
	
	public List<Deal> getActiveDeals(DealFilter filter)
	{
		List<Deal> ret = new ArrayList<Deal>();
		
		
		for(Deal d : deals.values())
		{
			if(d.isActive() && !d.isArchived() && d.getStatus() == Status.Active)
			{
				if(filterPassCheck(d,filter))
				{
					ret.add(d);
				}
			}
		}
		
		
		return ret;
	}
	
	public List<Deal> getPopularDeals()
	{
		List<Deal> ret = new ArrayList<Deal>();
		
		List<Deal> temp = new ArrayList<>(deals.values());
		
		for(int i = 0 ; i < 9 ; i++)
		{
			for(Deal d : temp)
			{
				if(d.isActive() && !d.isArchived() && d.getStatus() == Status.Active)
				{
					
				}
			}			
		}
		
		
		return ret;
	}
	
	
	public void deleteReview(Review review)
	{
		for(Deal d: deals.values())
		{
			for(Review r : d.getReviews())
			{
				if(r.getHeader().equals(review.getHeader()) && r.getContent().equals(review.getContent()))
				{
					r.archive();
				}
			}
		}
	}
	
	public void replaceReview(Review review,String header,String content)
	{
		for(Deal d: deals.values())
		{
			for(Review r : d.getReviews())
			{
				if(r.getHeader().equals(header) && r.getContent().equals(content))
				{
					r.setHeader(review.getHeader());
					r.setContent(review.getContent());
					r.setDeal(review.getDeal());
					r.setImageSrc(review.getImageSrc());
					r.setIsDealHonored(review.getIsDealHonored());
					r.setIsDescTrue(review.getIsDescTrue());
				}
			}
		}
	}
	
	public List<Review> getReviewsOf(User user)
	{
		List<Review> ret = new ArrayList<Review>();
		
		for(Deal d : deals.values())
		{
			for(Review r : d.getReviews())
			{
				if(r.getReviewer().equals(user.getEmail()))
				{
					ret.add(r);
				}
			}
		}
		
		return ret;
	}
	
	public List<Deal> getAllDeals(DealFilter filter)
	{
		List<Deal> ret = new ArrayList<Deal>();
		
		for(Deal d: deals.values())
		{
			if(filterPassCheck(d,filter))
			{
				ret.add(d);				
			}
		}
		
		return ret;
	}
	
	
	private Boolean filterPassCheck(Deal d,DealFilter filter)
	{
		Boolean flag = true;
		
		if(filter.name != null && filter.name != "")
		{
			if(!d.getName().contains(filter.name))
			{
				flag = false;
			}
		}
		
		if(filter.minPrice != null && filter.minPrice != "")
		{
			try
			{
				float min = Float.valueOf(filter.minPrice);	
				float price = Float.valueOf(d.getPrice());
				
				if(price < min)
				{
					flag = false;
				}
				
			}
			catch(NumberFormatException e)
			{
				
			}
		}
		
		if(filter.maxPrice != null && filter.maxPrice != "")
		{
			try
			{
				float max = Float.valueOf(filter.maxPrice);	
				float price = Float.valueOf(d.getPrice());
				
				if(price > max)
				{
					flag = false;
				}
				
			}
			catch(NumberFormatException e)
			{
				
			}
		}
		
		if(filter.minLikes != null && filter.minLikes != "") 
		{
			try
			{
				int min = Integer.valueOf(filter.minLikes);	
				int likes = Integer.valueOf(d.getLikes());
				
				if(likes < min)
				{
					flag = false;
				}
				
			}
			catch(NumberFormatException e)
			{
				
			}
		}
		
		if(filter.minLikes != null && filter.minLikes != "") 
		{
			try
			{
				int max = Integer.valueOf(filter.maxLikes);	
				int likes = Integer.valueOf(d.getLikes());
				
				if(likes > max)
				{
					flag = false;
				}
				
			}
			catch(NumberFormatException e)
			{
				
			}
		}
				
		if(filter.minDate != null && filter.minDate != "") 
		{
			String fullDate = d.getStartDate();
			
			String date = fullDate.split("T")[0];
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Date dealDate = sdf.parse(d.getStartDate().split("T")[0]);
				Date filterDate = sdf.parse(filter.minDate);
				
				if(dealDate.compareTo(filterDate) < 0)
				{
					flag = false;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//TODO: Check dates
		}
		
		if(filter.maxDate != null && filter.maxDate != "") 
		{
			//TODO: Check dates
			String fullDate = d.getEndDate();
			
			String date = fullDate.split("T")[0];
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				Date dealDate = sdf.parse(d.getStartDate().split("T")[0]);
				Date filterDate = sdf.parse(filter.maxDate);
				
				if(dealDate.compareTo(filterDate) > 0)
				{
					flag = false;
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(filter.city != null && filter.city != "")
		{
			if(!d.getCity().contains(filter.city))
			{
				flag = false;
			}
		}
		
		if(filter.status != null && filter.status != "")
		{
			if(!d.getStatus().toString().equals(filter.status))
			{
				flag = false;
			}
		}
		
		
		if(filter.category != null && filter.category != "")
		{
			if(!d.getCategory().equals(filter.category))
			{
				flag = false;
			}
		}
		
		
		return flag;
	}
	
	
	public void saveDeals()
	{
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(this.contextPath + "data"+ java.io.File.separator +"deals.json");
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, this.deals.values());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadDeals() throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();

		File file = new File(this.contextPath + "data"+ java.io.File.separator +"deals.json");
		String json = ""; 
		String temp;
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			while ((temp = br.readLine()) != null) {
				json += temp;
			}
		}
		List<Deal> list = mapper.readValue(json, 
			    new TypeReference<ArrayList<Deal>>() {});
		
		this.deals.clear();
		for(Deal ad: list) {
			this.deals.put(ad.getName(), ad);
		}
		
		System.out.println("Files: Deals loaded");

		
	}
	
}
