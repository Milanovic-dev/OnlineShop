package helpers;

import beans.Message;
import beans.User;
import dao.UserDAO;

public class Mailbox 
{
	public static void send(String dealName, String header, String content, String sender, User reciever)
	{
		if(reciever != null)
			reciever.addMessage(new Message(dealName,sender,header,content));
	}
	
	public static void sendAutomatic(User reciever, String header, String content,String dealName)
	{
		if(reciever != null)
			reciever.addMessage(new Message(dealName,UserDAO.GetKoko().getEmail(),header,content));
	}
	
	public static String getWelcomeText()
	{
		return "\nThank you for joining Shopify!";
	}
}
