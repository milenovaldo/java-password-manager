
import java.sql.SQLException;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu {

    // Declearing the variables
    public static Stage Window;

    public VBox Menu() {
        // Creating a new menu bar
        MenuBar myMenu = new MenuBar();
        // Creating menu inside a menubar
        javafx.scene.control.Menu Action = new javafx.scene.control.Menu("_Action");
        // Creating a menu item
        MenuItem Exit = new MenuItem("_Exit");
        // Exiting on clicking on exit button
        Exit.setOnAction(e -> System.exit(0));
        // Creating a page to contain the whole menu
        VBox PageTop = new VBox();
        // Creating driver object
        Driver d = new Driver();
        // Checking if the user is logged in
        if (Driver.isLogin) {
            // Creating a menu  for manager
            javafx.scene.control.Menu Manage = new javafx.scene.control.Menu("_Manage");

            // Creating a menu item
            MenuItem AddUser = new MenuItem("_Add User");
            // On click function for add user
            AddUser.setOnAction(e -> {
                try {
                    // Creating add user screen
                    new AddUsers();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            // Creating a menu item for update password
            MenuItem UpdatePass = new MenuItem("_Update Password");
            // Adding on click function
            UpdatePass.setOnAction(e -> {
                try {
                    // Creating screen for update password
                    new UpdatePassScreen();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            // Creating a menu item for home
            MenuItem Home = new MenuItem("_Home");
            // Adding a click function on home button
            Home.setOnAction(e -> {
                try {
                    // Showing the home screen on click
                    new Home();
                } catch (SQLException e1) {
                    System.out.println("SQL Error");
                }
            });
            // Creating logout button
            MenuItem Logout = new MenuItem("_Logout");
            // Creating a click function for logout
            Logout.setOnAction(e -> {
                // Killing session
                Driver.EmailStore = null;
                d.isLogin = false;
                d.Login();

            });
            // Add items to action menu
            Action.getItems().addAll(Home, Logout, Exit);
            if (Driver.RoleStore.equals("1")) {
                Manage.getItems().addAll(AddUser, UpdatePass);
            } else {
                Manage.getItems().addAll(UpdatePass);
            }
            // Add menus to menubar
            myMenu.getMenus().addAll(Action, Manage);
            // Adding menu on layout
            PageTop.getChildren().add(myMenu);
        } else {
            // Adding and creating menuitems for guest users
            MenuItem Login = new MenuItem("_Login");
            // Adding login screen on click on login button
            Login.setOnAction(e -> d.Login());
            Action.getItems().addAll(Login, Exit);
            // Adding menuitem
            myMenu.getMenus().add(Action);
            // Adding menu
            PageTop.getChildren().add(myMenu);
        }
        // returing menu layout
        return PageTop;
    }

}
