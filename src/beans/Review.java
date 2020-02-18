package beans;

public class Review 
{
	public enum ReviewType{ Account, Deal }
	
	private String deal;
	private String seller;
	private String reviewer;
	private String header;
	private String content;
	private String imageSrc;
	private Boolean isDescTrue;
	private Boolean isDealHonored;
	private Boolean archived;
	private ReviewType type;
	
	public Review()
	{
		super();
	}
	
	public Review(String deal, String reviewer, String header, String content, String imageSrc, Boolean isDescTrue,
			Boolean isDealHonored) {
		super();
		this.deal = deal;
		this.reviewer = reviewer;
		this.header = header;
		this.content = content;
		this.imageSrc = imageSrc;
		this.isDescTrue = isDescTrue;
		this.isDealHonored = isDealHonored;
		this.archived = false;
	}
	
	public Review(Review r)
	{
		super();
		this.header = r.getHeader();
		this.content = r.getContent();
		this.imageSrc = r.getImageSrc();
		this.isDescTrue = r.getIsDescTrue();
		this.isDealHonored = r.getIsDealHonored();
		this.archived = r.isArchived();
	}
	
	public Boolean isArchived()
	{
		return archived;
	}
		
	public ReviewType getType() {
		return type;
	}

	public void setType(ReviewType type) {
		this.type = type;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getDeal() {
		return deal;
	}
	public void setDeal(String deal) {
		this.deal = deal;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
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
	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	
	public Boolean getIsDescTrue() {
		return isDescTrue;
	}

	public Boolean getIsDealHonored() {
		return isDealHonored;
	}

	public void setIsDescTrue(Boolean isDescTrue) {
		this.isDescTrue = isDescTrue;
	}
	
	public void setIsDealHonored(Boolean isDealHonored) {
		this.isDealHonored = isDealHonored;
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
