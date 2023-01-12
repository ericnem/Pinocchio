import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        // Setup
		Database data = new Database(); 
        data.fetchUsers(); // Stores user info in list of users
		data.fetchPosts(); // Stores post info in list of posts


        // Login screen
        JFrame f=new JFrame("Pinocchio");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initiates login window
        LoginWindow newWindow = new LoginWindow();
        newWindow.show(f,data);
    }
}
