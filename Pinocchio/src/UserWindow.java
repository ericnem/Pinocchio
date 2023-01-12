import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.geom.Ellipse2D;
import java.awt.event.*; 
public class UserWindow
{
    protected JFrame frame = new JFrame("Home Page");
    protected JScrollPane scrollPane = new JScrollPane();
    protected JPanel parentPanel, childOne;

    public void showUserScreen(Person user, Database data) 
    {
        JButton button = new JButton("My Account");
        parentPanel = new JPanel() 
        {
            @Override
            public Dimension getPreferredSize() 
            {
                return new Dimension(new Dimension(415, 1500));
            }
        };

        //Child panels to hold individual posts
        childOne = new JPanel() 
        {
            @Override
            public Dimension getPreferredSize() 
            {
                return new Dimension(new Dimension(415, 460));
            }
        };
        
        // Colour setup
        parentPanel.setBackground(Color.WHITE);
        parentPanel.setBounds(-2,-2,400,2000);
        childOne.setBackground(Color.WHITE);
        childOne.setOpaque(true);



        // Gives frame a box layout for the child panels
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        // Gets rid of layout for first panel so that we can specify positions by pixels
        childOne.setLayout(null);

        // App name at the top
        JLabel nameText = new JLabel("Pinocchio",SwingConstants.LEFT);
        nameText.setFont(new Font("Futura", Font.PLAIN, 25));
        nameText.setBounds(20,10,415,40);
        childOne.add(nameText);

        // Search bar
        final JTextField searchText = new JTextField();
        searchText.setOpaque(false);
        searchText.setBackground(new Color(250,250,250));
        searchText.setBounds(215,19,150,21);
        searchText.setBorder(BorderFactory.createLineBorder(new Color(244,244,244)));
        childOne.add(searchText);

        searchText.addActionListener(new ActionListener() 
          {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
              String userInptString = searchText.getText(); //Reads what's in the field when enter is pressed
              try
              {
                for (Person user : data.allUsers)
                {
                    if (user.userName.equals(userInptString) | user.name.equals(userInptString))
                    {
                        frame.setVisible(false);
                        data.fetchUserPosts(data.allPosts,data.allUsers,user.userName);
                        UserWindow page = new UserWindow();
                        page.showUserScreen(user,data);
                    }
                }
              } catch(Exception e)
              {
              }
            }
          });

        
        // Puts searchbar picture behind the textfield so that it's as if you're writing in the bar
        try
        {
            BufferedImage sBarImage = ImageIO.read(new File("Pictures/searchbar.png")); // Get image
            
            Image tmp = sBarImage.getScaledInstance(191, 24, Image.SCALE_SMOOTH); // Scales it down
            BufferedImage dimg = new BufferedImage(191, 24, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            JLabel sBarLabel = new JLabel( new ImageIcon(dimg) );
            sBarLabel.setLayout( new BorderLayout() );
            sBarLabel.setBounds(206,18,191,23);
        
            childOne.add(sBarLabel);
        } catch(Exception e){}
        
        // Displays profile picture as circle
        try{
            BufferedImage myPicture = ImageIO.read(new File(user.profile_pic));
            Image tmp = myPicture.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            
            int width = 80;
            BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new Ellipse2D.Float(0, 0, width, width));
            g2.drawImage(tmp, 0, 0, width, width, null);
            g2.dispose();

            JLabel picLabel = new JLabel(new ImageIcon(circleBuffer));
            picLabel.setBounds(20,181,80,80);
            childOne.add(picLabel);

        } catch(Exception e){}
        
        // Displays slightly bigger white circle behind the profile picture, as if outline
        try{
            BufferedImage myPicture = ImageIO.read(new File("Pictures/white.png"));
            Image tmp = myPicture.getScaledInstance(88, 88, Image.SCALE_SMOOTH);
            int width = 88;
            BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2.setClip(new Ellipse2D.Float(0, 0, width, width));
            g2.drawImage(tmp, 0, 0, width, width, null);
            g2.dispose();

            JLabel picLabel = new JLabel(new ImageIcon(circleBuffer));
            picLabel.setBounds(16,177,88,88);
            childOne.add(picLabel);

        } catch(Exception e){}

        // Displays big background picture
        try
        {
            BufferedImage myPicture = ImageIO.read(new File(user.bkgImage));
            Image tmp = myPicture.getScaledInstance(415, 173, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(415, 173, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            JLabel picLabel = new JLabel(new ImageIcon(dimg));
            picLabel.setBounds(-1,52,420,173);
            childOne.add(picLabel);

        } catch(IOException e){}

        // Displays name
        JLabel userNameText = new JLabel(user.name,SwingConstants.LEFT);
        userNameText.setFont(new Font("Apple Casual", Font.BOLD, 20));
        userNameText.setBounds(20,282,415,40);
        childOne.add(userNameText);

        // Displays username
        JLabel userNameHandle = new JLabel(user.userName,SwingConstants.LEFT);
        userNameHandle.setFont(new Font("Apple Casual", Font.PLAIN, 16));
        userNameHandle.setForeground(Color.GRAY);
        userNameHandle.setBounds(20,320,415,30);
        childOne.add(userNameHandle);

        // Dispalys forecaster rating on the side
        if (user.fr != -1)
        {   
            JLabel rating = new JLabel(user.name + " • "+user.fr + "%",SwingConstants.LEFT);
            rating.setFont(new Font("Apple Casual", Font.BOLD, 20));
            rating.setForeground(Color.GRAY);
            rating.setBounds(20,282,415,40);
            childOne.add(rating);
        }
        

        // Displays follow button, by setting the follow image as icon of jButton
        try
        {
            JButton follow;

            BufferedImage myPicture = ImageIO.read(new File("Pictures/following.png"));
            Image tmp = myPicture.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            follow = new JButton(new ImageIcon(dimg));
            BufferedImage myPicture2 = ImageIO.read(new File("Pictures/follow.png"));
            Image tmp2 = myPicture2.getScaledInstance(180, 130, Image.SCALE_SMOOTH);
            BufferedImage dimg2 = new BufferedImage(180, 130, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d2 = dimg2.createGraphics();
            g2d2.drawImage(tmp2, 0, 0, null);
            g2d2.dispose();

            if (user.isFollowing == true)
            {
                follow = new JButton(new ImageIcon(dimg));
            }
            else
            {
                follow = new JButton(new ImageIcon(dimg2));
            }

        follow.setOpaque(true);
        follow.setContentAreaFilled(false);
        follow.setBorderPainted(false);
        follow.setFocusPainted(false);
        follow.setBounds(300,285,100,40);

        if (user != data.allUsers[0])
        {
            childOne.add(follow);
        }


        follow.addActionListener(new ActionListener() 
          {
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                if (user.isFollowing == true)
                {
                    user.isFollowing = false;
                    frame.setVisible(false);
                    data.updateUserCSV(data.allUsers);
                    data.fetchUserPosts(data.allPosts,data.allUsers,user.userName);
                    UserWindow page = new UserWindow();
                    page.showUserScreen(user,data);
                }
                else
                {   
                    user.isFollowing = true;
                    frame.setVisible(false);
                    data.updateUserCSV(data.allUsers);
                    data.fetchUserPosts(data.allPosts,data.allUsers,user.userName);
                    UserWindow page = new UserWindow();
                    page.showUserScreen(user,data);
                } 
            }
          });
        } catch(Exception e){}

        // Displays bio
        JLabel bio = new JLabel(user.bio,SwingConstants.LEFT);
        bio.setFont(new Font("Apple Casual", Font.PLAIN, 16));
        bio.setBounds(20,345,415,40);
        childOne.add(bio);
    
        // Displays # of posts
        String postString = "<html><b>" + String.valueOf(user.posts.length) + "</b> Posts </html>";
        JLabel posts = new JLabel(postString,SwingConstants.LEFT);
        posts.setFont(new Font("Apple Casual", Font.PLAIN, 16));
        posts.setBounds(20,375,415,40);
        childOne.add(posts);

        // Displays # of followers
        String followersString;
        if (user.followers > 1000000)
        {
            followersString = "<html><b>" + (user.followers/1000000) + "M</b> following </html>";
        }
        else if (user.followers > 1000)
        {
            followersString = "<html><b>" + (user.followers/1000) + "K</b> following </html>";
        }
        else
        {
            followersString = "<html><b>" + user.followers + "K</b> following </html>";
        }
        JLabel followers = new JLabel(followersString,SwingConstants.LEFT);
        followers.setFont(new Font("Apple Casual", Font.PLAIN, 16));
        followers.setBounds(110,375,415,40);
        childOne.add(followers);

        // Displays # following
        
        String followingString;
        if (user.following > 1000000)
        {
            followingString = "<html><b>" + (user.following/1000000) + "M</b> following </html>";
        }
        else if (user.following > 1000)
        {
            followingString = "<html><b>" + (user.following/1000) + "K</b> following </html>";
        }
        else
        {
            followingString = "<html><b>" + user.following + "</b> following </html>";
        }

        JLabel following = new JLabel(followingString,SwingConstants.LEFT);
        following.setFont(new Font("Apple Casual", Font.PLAIN, 16));
        following.setBounds(250,375,415,40);
        childOne.add(following);

        // Displays tab title called "Predictions"
        JLabel tab = new JLabel("Predictions",SwingConstants.LEFT);
        tab.setFont(new Font("Apple Casual", Font.BOLD, 20));
        tab.setBounds(20,410,415,40);
        tab.setForeground(new Color (0,164,249));
        childOne.add(tab);

        JLabel tabLine = new JLabel();
        tabLine.setBounds(20,454,115,4);
        tabLine.setBorder((BorderFactory.createLineBorder(new Color (0,164,249),4)));
        childOne.add(tabLine);

        JLabel Line = new JLabel();
        Line.setBounds(-5,458,415,1);
        Line.setBorder((BorderFactory.createLineBorder(new Color(200, 200, 200),2)));
        childOne.add(Line);

        // Adds children panels to the main panel
        parentPanel.add(childOne);

        JPanel[] pnlArray = new JPanel[user.posts.length];

        for (int i = 0; i < user.posts.length; i++)
        {
            pnlArray[i] = new JPanel()
            {
                @Override
                public Dimension getPreferredSize() 
                {
                    return new Dimension(new Dimension(400, 200));
                }
                
                @Override
                public void paintComponent(Graphics g) 
                {
                    Dimension dim = getSize();
                    super.paintComponent(g);
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, dim.width, dim.height);
                }
            };
            pnlArray[i].setLayout(null);

            if (i>0)
            {
                JLabel sepLine = new JLabel();
                sepLine.setBounds(-5,0,415,1);
                sepLine.setBorder((BorderFactory.createLineBorder(new Color(200, 200, 200),2)));
                pnlArray[i].add(sepLine);
            }


            Person[] currentUsers = data.allUsers;
            data.fetchUserPosts(data.allPosts, data.allUsers, user.userName);
            for (int j = 0; j < currentUsers.length; j++)
            {

                String usernm1 = data.allUsers[j].userName;
                String author1 = data.allPosts[i].author;


                if(String.valueOf(usernm1).equals(String.valueOf(author1)) )
                {
                  try{
                    BufferedImage myPicture = ImageIO.read(new File(user.profile_pic));
                    Image tmp = myPicture.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    
                    int width = 80;
                    BufferedImage circleBuffer = new BufferedImage(width, width, BufferedImage.TYPE_INT_ARGB);
        
                    Graphics2D g2 = circleBuffer.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                  RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setClip(new Ellipse2D.Float(0, 0, width, width));
                    g2.drawImage(tmp, 0, 0, width, width, null);
                    g2.dispose();
        
                    JLabel picLabel = new JLabel(new ImageIcon(circleBuffer));
                    picLabel.setBounds(20,28,80,80);
                    pnlArray[i].add(picLabel);
        
                } catch(Exception e){}
                }
            }
            try
            {
                BufferedImage myPicture = ImageIO.read(new File("Pictures/like.png"));
                Image tmp = myPicture.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                BufferedImage dimg = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = dimg.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();
                
                JButton likeButton = new JButton(new ImageIcon(dimg));
                likeButton.setOpaque(true);
                likeButton.setContentAreaFilled(false);
                likeButton.setBorderPainted(false);
                likeButton.setFocusPainted(false);
                likeButton.setBounds(135,160,25,25);
                pnlArray[i].add(likeButton);
    
                likeButton.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent e) 
                    {
                    }  
                });
            } catch(Exception e){}
            try
            {

                BufferedImage myPicture = ImageIO.read(new File("Pictures/dislike.png"));
                Image tmp = myPicture.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                BufferedImage dimg = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = dimg.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();
                
                JButton dislike = new JButton(new ImageIcon(dimg));
                dislike.setOpaque(true);
                dislike.setContentAreaFilled(false);
                dislike.setBorderPainted(false);
                dislike.setFocusPainted(false);
                dislike.setBounds(250,170,25,25);
                pnlArray[i].add(dislike);
    
                dislike.addActionListener(new ActionListener() 
                {
                    public void actionPerformed(ActionEvent e) 
                    {
                    }  
                });
    
            } 
            catch(Exception e)
            {
    
            }        
            String likesString;
            if (user.posts[i].likes > 1000000)
            {
                likesString = (user.posts[i].likes/1000000) + "M";
            }
            else if (user.posts[i].likes > 1000)
            {
                likesString = (user.posts[i].likes/1000) + "K";
            }
            else
            {
                likesString = user.posts[i].likes + "";
            }
            JLabel likes = new JLabel(likesString);
            
            String dislikesString;
            if (user.posts[i].dislikes > 1000000)
            {
                dislikesString = (user.posts[i].dislikes/1000000) + "M";
            }
            else if (user.posts[i].dislikes > 1000)
            {
                dislikesString = (user.posts[i].dislikes/1000) + "K";
            }
            else
            {
                dislikesString = user.posts[i].dislikes + "";
            }
            JLabel dislikes = new JLabel(dislikesString);


            if (user.posts[i].verifStatus == 0)
            {
                likes.setForeground(Color.black);
                dislikes.setForeground(Color.black);
            }
            else if (user.posts[i].verifStatus == 1)
            {
                likes.setForeground(Color.blue);
                dislikes.setForeground(Color.black);
            }
            else if (user.posts[i].verifStatus == 2)
            {
                likes.setForeground(Color.black);
                dislikes.setForeground(Color.red);
            }
        
            JTextArea text1 = new JTextArea();
            text1.setEditable(false);
            String author = user.posts[i].author;
            JButton button1 = new JButton();
            Font newButtonFont=new Font(button.getFont().getName(),Font.BOLD,17);
            button1.setFont(newButtonFont);
            button1.setBorderPainted(false);
            button1.setFocusPainted(false);
            for (Person profile : currentUsers)
            {
              if (profile.userName.equals(author))
              {
                button1.setText(profile.name);

              }
            }

            String str = user.posts[i].statement;

            text1.setText(str);

            // Wrap the lines of the JTextArea
            text1.setLineWrap(true);
            text1.setWrapStyleWord(true);
            Font contentFont = new Font(button.getFont().getName(),Font.PLAIN,15);
            text1.setFont(contentFont);

            button1.setHorizontalAlignment(SwingConstants.LEFT);
            button1.setBounds(110,35,300,25);
            text1.setBounds(130,80,230,80);
            likes.setBounds(170,165,80,20);
            likes.setFont(contentFont);
            dislikes.setFont(contentFont);
            dislikes.setBounds(285,165,80,20);

            pnlArray[i].add(likes);
            pnlArray[i].add(dislikes);
            pnlArray[i].add(button1);
            pnlArray[i].add(text1);

            parentPanel.add(pnlArray[i]);
        }
        // Settings for scroll
        scrollPane.setViewportView(parentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disables horizontal scrolling
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0)); // Makes vertical scrollbar's dimensions 0 to make it invisible

        // Frame settings
        frame.add(scrollPane);
        frame.getContentPane().setBackground(Color.WHITE); 
        frame.setSize(415,900); 
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); 
    }  
}