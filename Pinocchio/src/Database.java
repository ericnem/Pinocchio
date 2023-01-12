import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Database
{
  protected Person[] allUsers;
  protected Post[] allPosts;

  // Reads CSV user file into a list of users
	public void fetchUsers() throws FileNotFoundException
    {
        // Number of users stored in the csv
        int size = -1;

        Scanner myscanner = new Scanner(new File("users.csv"));//Creates a new File

        // Finds # of users
        while (myscanner.hasNextLine())
        {
          myscanner.nextLine();
          size += 1;
        }
        myscanner.close();

        // Makes new scanner and divides by newlines
        Scanner newScanner = new Scanner(new File("users.csv"));//Creates a new File
        newScanner.useDelimiter("\n");   //Sets the delimiter
        
        // Creates people list where users will be stored
        Person[] peopleObject = new Person[size];

        int count = -1;
        while (newScanner.hasNext()) // While there's a line in ahead
        {
            String attributesStr = newScanner.next(); // Stores next line
            if (count > -1) // If we're past the first line of the csv (first line contains titles not data)
            {
              // Stores whole line as a user in peopleObject with all the attributes
              String[] attributes = attributesStr.split(",");
              peopleObject[count] = new Person(attributes[0],attributes[1],
                                                attributes[2], attributes[3],
                                                Integer.valueOf(attributes[4]),
                                                Integer.valueOf(attributes[5]),
                                                attributes[6],attributes[7],
                                                Boolean.valueOf(attributes[8]));
            }
            
            count += 1;
        }
        newScanner.close();
        allUsers = peopleObject;
    }

  // Reads CSV posts file into a list of posts
	public void fetchPosts() throws FileNotFoundException
    {
        // Same as previous, except stores post information from a different csv
        int size = -1;

        Scanner myscanner = new Scanner(new File("posts.csv"));//Creates a new File
        while (myscanner.hasNextLine())
        {
          myscanner.nextLine();
          size += 1;
        }
        myscanner.close();

        Scanner newScanner = new Scanner(new File("posts.csv"));//Creates a new File
        newScanner.useDelimiter("\n");   //Sets the delimiter

        Post[] postObject = new Post[size];

        int count = -1;
        while (newScanner.hasNext())  //Returns a boolean(true) if this scanner has another token in its input
        {
            String attributesStr = newScanner.next();
            if (count > -1)
            {
              String[] attributes = attributesStr.split(",");
              postObject[count] = new Post(attributes[0],attributes[1], 		 
                                          Integer.valueOf(attributes[2]),
                                          Integer.valueOf(attributes[3]),
                                          Integer.valueOf(attributes[4]),
                                          Integer.valueOf(attributes[5]));
            }
            
            count += 1;
        }
        newScanner.close();
        allPosts = postObject;
    }

    // Finds all posts by a specific user in the posts list and stores them in the user's posts attribute
    public void fetchUserPosts(Post[] all_posts,Person[] all_people,String author) //Gets specific user's posts for profile screen
    {
      int post_count = 0;

      // Calculates how many posts belong to author
      for (Post post : all_posts)
      {
        if (post.author.equals(author))
        {
          post_count += 1;
        }
      }

      // List containing only posts by specific user
      Post[] userPosts = new Post[post_count];

      int indexCount = 0;
      int trueStatements = 0;
      int falseStatements = 0;

      // Iterates through all posts to find the ones by the user
      for (int i = 0; i<all_posts.length;i++)
      {
        if (all_posts[i].author.equals(author))
        {
          if (all_posts[i].verifStatus == 1)
            {
              trueStatements += 1;
            }
          else if (all_posts[i].verifStatus == 2)
          {
            falseStatements += 1;
          }
          userPosts[indexCount] = all_posts[i];
          indexCount += 1;
        }
      }
      
      // Calculates forecaster rating based on the verification statuses of specific user
      for (Person user : all_people)
      {
        if (user.userName.equals(author))
        {
          user.posts = userPosts;
          int rating;
          try
          {
            rating = (trueStatements*100) / (trueStatements+falseStatements);
          }
          catch(Exception e)
          {
            rating = -1;
          }
          user.fr = rating;
        }
      }
      

    }

    public void updatePostCSV(Post[] allPosts) // Updates CSV Post file
    {
      try (PrintWriter writer = new PrintWriter("src/posts.csv")) {

        StringBuilder sb = new StringBuilder();
        sb.append("Author,Statement,Likes,Dislikes,Interaction Status,Verif. Status");
        for (Post post : allPosts)
        {
          sb.append("\n");
          sb.append(post.author);
          sb.append(",");
          sb.append(post.statement);
          sb.append(",");
          sb.append(post.likes);
          sb.append(",");
          sb.append(post.dislikes);
          sb.append(",");
          sb.append(post.intStatus);
          sb.append(",");
          sb.append(post.verifStatus);
        }
        writer.write(sb.toString());

      } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
      }
  
    }
    public void updateUserCSV(Person[] allUsers) // Updates CSV user file
    {
      try (PrintWriter writer = new PrintWriter("src/users.csv")) {

        StringBuilder sb = new StringBuilder();
        sb.append("Name,Username,Password,Bio,Followers,Following,Profile Picture,Background Picture,FR");
        for (Person user : allUsers)
        {
          sb.append("\n");
          sb.append(user.name);
          sb.append(",");
          sb.append(user.userName);
          sb.append(",");
          sb.append(user.password);
          sb.append(",");
          sb.append(user.bio);
          sb.append(",");
          sb.append(user.followers);
          sb.append(",");
          sb.append(user.following);
          sb.append(",");
          sb.append(user.profile_pic);
          sb.append(",");
          sb.append(user.bkgImage);
          sb.append(",");
          sb.append(user.isFollowing);
        }
        writer.write(sb.toString());
  
  
      } catch (FileNotFoundException e) {
        System.out.println(e.getMessage());
      }
  
    }
}