public class Person {
  protected String name;
  protected String userName;
  protected String password;
  protected String bio;
  protected int followers;
  protected int following;
  protected String bkgImage;
  protected String profile_pic;
  protected Boolean isFollowing;
  protected int fr; // Forecaster rating
  protected Post[] posts;

  Person(String name,String userName,String password, String bio,int followers, int following, String profile_pic, String bkgImage, Boolean isFollowing)
  {
    this.name = name;
    this.userName = userName;
    this.password = password;
    this.bio = bio;
    this.bkgImage = bkgImage;
    this.profile_pic = profile_pic;
    this.followers = followers;
    this.following = following;
    this.isFollowing = isFollowing;

  }
}
