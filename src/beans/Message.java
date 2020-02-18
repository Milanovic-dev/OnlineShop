 package beans;

public class Message 
{
	private String dealName;
	private String sender;
	private String header;
	private String content;
	private String dateSent;
	private Boolean isRead;
	private Boolean archived;
	
	public Message()
	{
		super();
	}
	
	public Message(String dealName, String sender, String header, String content) 
	{
		super();
		this.dealName = dealName;
		this.sender = sender;
		this.header = header;
		this.content = content;
		this.isRead = false;
		this.archived = false;
		dateSent = java.time.LocalDateTime.now().toString();
	}
	
	
	public Message(Message m)
	{
		super();
		this.dealName = m.dealName;
		this.sender = m.sender;
		this.header = m.header;
		this.content = m.content;
		this.isRead = false;
		this.dateSent = java.time.LocalDateTime.now().toString();
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDateSent() {
		return dateSent;
	}

	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}
	
	public Boolean isRead()
	{
		return this.isRead;
	}
	
	public void setRead(Boolean read)
	{
		this.isRead = read;
	}
	
	public Boolean isArchived()
	{
		return this.archived;
	}
	
	public void archive()
	{
		archived = true;
	}
	
	public void unarchive()
	{
		archived = false;
	}
	
}
