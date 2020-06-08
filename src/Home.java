import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class Home {

    // Constructor
    public Home() throws SQLException {
        
        // Delcaring buttons
        Button EditUser = new Button("Users");
        Button UpdatePassword = new Button("Password");
        Button LogoutBtn = new Button("Logout");

        // Checking if admin is logged in or simple user logged in
        if (Driver.RoleStore.equals("1")) {
            // Applying styles on home screen
            EditUser.getStyleClass().addAll("HomeBtn", "LightGreen");
            // Applying styles on home screen
            Image AUImg = new Image(getClass().getResourceAsStream("assets/AddUser.png"));
            // Applying styles on home screen
            ImageView AUIV = new ImageView(AUImg);
            // Applying styles on home screen
            AUIV.setFitWidth(50);
            // Applying styles on home screen
            AUIV.setFitHeight(50);
            // Applying styles on home screen
            EditUser.setGraphic(AUIV);
            // Applying styles on home screen
            EditUser.setTooltip(new Tooltip("Add User"));
            // Adding on click function
            EditUser.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        // Open new screen on clicking on update password
                        new AddUsers();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
            // Applying styles on home screen
        UpdatePassword.getStyleClass().addAll("HomeBtn", "LightGreen");
            // Applying styles on home screen
        Image UPImg = new Image(getClass().getResourceAsStream("assets/UpdatePassword.png"));
            // Applying styles on home screen
        ImageView UPIV = new ImageView(UPImg);
            // Applying styles on home screen
        UPIV.setFitWidth(50);
            // Applying styles on home screen
        UPIV.setFitHeight(50);
            // Applying styles on home screen
        UpdatePassword.setGraphic(UPIV);
            // Applying styles on home screen
        UpdatePassword.setTooltip(new Tooltip("Password"));
            // Adding on click fucntion for update password
        UpdatePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // open update password screen
                    new UpdatePassScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
            // Applying styles on home screen
        LogoutBtn.getStyleClass().addAll("HomeBtn", "LightGreen");
            // Applying styles on home screen
        Image LgImg = new Image(getClass().getResourceAsStream("assets/Logout.png"));
            // Applying styles on home screen
        ImageView LgIV = new ImageView(LgImg);
            // Applying styles on home screen
        LgIV.setFitWidth(50);
            // Applying styles on home screen
        LgIV.setFitHeight(50);
            // Applying styles on home screen
        LogoutBtn.setGraphic(LgIV);
            // Applying styles on home screen
        LogoutBtn.setTooltip(new Tooltip("Logout"));
            // Adding on click fucntion on logout button
        LogoutBtn.setOnAction(e -> {
            // Killing the session
            Driver d = new Driver();
            // Killing the session
            Driver.EmailStore = null;
            // Killing the session
            d.isLogin = false;
            // Showing the login screen again
            d.Login();
        });
        // Getting screen sizes
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Creating a row to add home button
        HBox RowOne = new HBox();
        // Setting spaces between buttons
        RowOne.setSpacing(20);
        // Checking the user is admin
        if (Driver.RoleStore.equals("1")) {
            RowOne.getChildren().addAll(EditUser, UpdatePassword, LogoutBtn);
        } else {
            RowOne.getChildren().addAll(UpdatePassword, LogoutBtn);
        }

        // Creating a main box to add all the fields and labels
        VBox Center = new VBox();
        // Applying the styles
        Center.setAlignment(Pos.CENTER);
        // Adding the rows to box
        Center.getChildren().addAll(RowOne);
        // Applying the styles
        Center.getStyleClass().addAll("HomeH" + "box");
        // Applying the styles
        Center.setMaxWidth(screenBounds.getWidth() * 0.75);
        // Applying the styles
        Center.setMaxHeight(screenBounds.getHeight() * 0.75);
        // Applying the styles
        Center.setSpacing(20);
        Menu m = new Menu();
        // Adding menu bar on home screen
        Driver.Layout.setTop(m.Menu());
        // Adding main box on home screen
        Driver.Layout.setCenter(Center);
    }
}
