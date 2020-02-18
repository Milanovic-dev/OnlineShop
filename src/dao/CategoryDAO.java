package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Category;
import beans.Deal;

public class CategoryDAO 
{
	private List<Category> categories = new ArrayList<Category>();
	private String contextPath;
	
	public CategoryDAO(String ctx)
	{
		super();
		contextPath = ctx;
		
		try {
			loadCategories();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void add(Category c)
	{
		Category cat = get(c.getName());
		
		if(cat != null)
		{
			cat.unarchive();
			return;
		}
		
		categories.add(c);
	}
	
	public Category get(String name)
	{
		for(int i = 0 ; i < categories.size() ; i++)
		{
			Category c = categories.get(i);
			if(c.getName().equals(name))
			{
				return categories.get(i);				
			}
		}
		
		return null;
	}
	
	public void replace(String name, Category c)
	{
		if(categories == null) return;
		
		for(int i = 0 ; i < categories.size() ; i++)
		{
			if(categories.get(i).getName().equals(name))
			{
				categories.set(i, c);			
			}
		}
	}
	
	public List<Category> getAll()
	{
		List<Category> ret = new ArrayList<Category>();
		
		for(Category c : categories)
		{
			if(!c.isArchived())
			{
				ret.add(c);
			}
		}
		
		
		return ret;
	}
	
	public void remove(Category c)
	{
		c.archive();
	}
	
	public void remove(String name)
	{
		if(categories == null) return;
		
		for(Category c : categories)
		{
			if(c.getName().equals(name))
			{
				c.archive();
			}
		}
	}
	
	private void loadCategories() throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		File file = new File(this.contextPath + "data"+ java.io.File.separator +"categories.json");
		String json = ""; 
		String temp;
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			while ((temp = br.readLine()) != null) {
				json += temp;
			}
		}
		List<Category> list = mapper.readValue(json, 
			    new TypeReference<ArrayList<Category>>() {});
		
		this.categories.clear();
		for(Category cat: list) {
			//TODO fill list of ads 
			this.categories.add(cat);
		}
		
		System.out.println("Files: Categories loaded");
	
	}
	
	public void saveCategories() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(this.contextPath + "data"+ java.io.File.separator +"categories.json");
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, this.categories);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
