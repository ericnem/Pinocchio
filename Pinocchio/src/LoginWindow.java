import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.Font;
import java.awt.*;
public class LoginWindow
{
  // Welcome text
  protected JLabel welcome = new JLabel("<html>Welcome to<br>&nbsp;&nbsp;Pinocchio</html>", SwingConstants.CENTER);
  

  // Sign-in text
  protected JLabel signIn = new JLabel("Sign in to continue.", SwingConstants.CENTER);
  

  // Username text
  protected JLabel nameText = new JLabel("USERNAME");
  

  // Username text field
  protected JPanel userNamePanel = new JPanel();
  protected final JTextField text = new JTextField();  
  

  // Password text
  protected JLabel passText = new JLabel("PASSWORD");
  

  // Password text field
  protected JPanel passwordPanel = new JPanel();
  protected final JPasswordField userPass = new JPasswordField(); // Special password field  
   

  // Login button
  protected JButton loginButton = new JButton("log in");

  // Wrong info text
  protected JLabel warning = new JLabel("Username or password is wrong.", SwingConstants.CENTER);
  
  
  public void show(JFrame f, Database data)
  {
    welcome.setFont(new Font("Futura", Font.PLAIN, 45));
    welcome.setBounds(0,112,450,100);

    signIn.setFont(new Font("Apple Casual", Font.PLAIN, 18));
    signIn.setBounds(0,230,450,30);

    nameText.setFont(new Font("Apple Casual", Font.PLAIN, 20));
    nameText.setBounds(30,275,450,40);

    userNamePanel.setBounds(30,335, 390,60);
    userNamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
    userNamePanel.setBackground(Color.WHITE);

    
    text.setBounds(40,337, 370,56);
    text.setFont(new Font("Apple Casual", Font.PLAIN, 20));
    text.setBorder(new LineBorder(Color.WHITE, 1));

    passText.setFont(new Font("Apple Casual", Font.PLAIN, 20));
    passText.setBounds(30,405,450,40);

    passwordPanel.setBounds(30,460, 390,60);
    passwordPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    passwordPanel.setBackground(Color.WHITE);

    userPass.setBounds(40,462,370,56);
    userPass.setFont(new Font("Apple Casual", Font.PLAIN, 20));
    userPass.setBorder(new LineBorder(Color.WHITE, 1));

    loginButton.setOpaque(true);  
    loginButton.setBackground(Color.BLACK);
    loginButton.setForeground(Color.WHITE);
    loginButton.setBorderPainted(false);
    loginButton.setFocusPainted(false);
    loginButton.setBounds(30,560, 390,60);  
    loginButton.setFont(new Font("Apple Gothic", Font.BOLD, 20)); 
    
    warning.setFont(new Font("Apple Casual", Font.PLAIN, 18));
    warning.setBounds(0,640,450,30);
    warning.setVisible(false);

    // Adding components
    f.add(welcome);
    f.add(signIn);
    f.add(nameText);
    f.add(passText);
    f.add(userPass); 
    f.add(loginButton);
    f.add(warning);  
    f.add(text);
    f.add(userNamePanel);
    f.add(passwordPanel);
    f.setSize(450,900); 
    f.getContentPane().setBackground(new java.awt.Color(122, 143, 222));   
    f.setLayout(null);    
    f.setVisible(true); 
    f.setResizable(false); 
    
    loginButton.addActionListener(new ActionListener()  
        {    
            public void actionPerformed(ActionEvent e) 
            {
                // Password is stored as a list of characters, converts to a string to validate it.
                char[] userPassword = userPass.getPassword();
                StringBuilder sb = new StringBuilder();
                for (Character ch: userPassword)
                {
                    sb.append(ch);
                } 
                String string = sb.toString();

                // 
                for (Person profile : data.allUsers)
                    if (profile.password.equals(string) && text.getText().equals(profile.userName))
                    {
                        f.setVisible(false); 
                        data.fetchUserPosts(data.allPosts,data.allUsers,profile.userName);

                        UserWindow page = new UserWindow();
                        page.showUserScreen(profile,data); 
                    }
                else
                {
                    warning.setVisible(true);
                }
            }
        });
    }
}