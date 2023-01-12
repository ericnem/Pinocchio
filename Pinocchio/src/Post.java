public class Post {

	protected String author;
	protected String statement;
  protected int likes;
  protected int dislikes;
  protected int intStatus; // Whether user liked it, disliked it, or neither
	protected int verifStatus; // Whether the prediction is true, false or unverified
	
	Post(String author, String statement, int likes, int dislikes, int status, int verifStatus)
	{
		this.author = author;
		this.statement = statement;
		this.likes = likes;
		this.dislikes = dislikes;
		this.intStatus = status;
		this.verifStatus = verifStatus;
	}
}
