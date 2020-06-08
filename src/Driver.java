import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Driver extends Application {

    // Declraing Variables
    public static Boolean isLogin = false;
    public static Stage Window;
    public static String EmailStore, RoleStore;
    public static Scene Scn;
    public static BorderPane Layout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Setting primaryStage to window variable
        Window = primaryStage;
        // Setting title of application
        Window.setTitle("Password Manager");
        // Calling the login function
        Login();
    }

    // This is the function which is called when we run the program
    public static void main(String[] args) {
        // This function is calling the start function
        launch(args);
    }

    // This function will show the login screen
    public void Login() {
        
        // This will show the login status in case of error
        Text Status = new Text();

        // This will create textfield of email
        TextField Email = new TextField();
        // Setting hint text
        Email.setPromptText("Email Address");
        // Applying styles on email field
        Email.getStyleClass().add("EmailAddress");

        // This will create textfield of password
        PasswordField Password = new PasswordField();
        // Setting hint text
        Password.setPromptText("Password");
        // Applying styles
        Password.getStyleClass().add("Password");

        // Creating login button
        Button LoginBtn = new Button("Login");
        // Applying styles on button
        LoginBtn.getStyleClass().addAll("LoginBtn", "WhiteTextColor");
        // Setting error text to null
        Status.setText("");

        // Setting on enter event in login screen
        Password.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // Click loggin button on pressing enter
                LoginBtn.fire();
            }
        });
        
        // Setting on enter event in login screen
        Email.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // Click loggin button on pressing enter
                LoginBtn.fire();
            }
        });

        // Setting on enter event in login screen
        LoginBtn.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // Click loggin button on pressing enter
                LoginBtn.fire();
            }
        });

        // Login button event
        LoginBtn.setOnAction(e -> {
            try {
                // Creating database object
                Database db = new Database();
                // Getting all the users from database
                db.PreStatement = db.GetConnection().prepareStatement("SELECT * FROM Users");
                // Executing query
                db.Result_Set = db.PreStatement.executeQuery();
                // Searching all the user
                while (db.Result_Set.next()) {
                    // If the user matches our record
                    if (Email.getText().equals(db.Result_Set.getObject(1)) && Password.getText().equals(db.Result_Set.getObject(5))) {
                        // Storing email as session in program
                        EmailStore = Email.getText();
                        // Storing users role (admin / user)
                        RoleStore = db.Result_Set.getString(6);
                        // Session for login
                        isLogin = true;
                        // Showing home page after logging in
                       new Home();
                       // Breaking loop
                        break;
                    }
                }
                // If record not found show error on login screen
                if (!isLogin) {
                    // Setting red color for error
                    Status.setFill(Color.RED);
                    // Showing error msg
                    Status.setText("Invalid email or password");
                }

            } catch (SQLException e1) {
                // Showing sql Error 
                System.out.println(e1.getMessage());
            }
        });

        // Creating a box for company information
        VBox CompanyInformation = new VBox();
        // Adding company name
        Label CName = new Label("Free Password Manager.");
        // Applying styles
        CName.getStyleClass().add("CName");
        // Applying styles
        CName.getStyleClass().add("WhiteTextColor");

        // Login heading on login screen
        Label LoginLbl = new Label("Login");
        // Applying styles on login heading
        LoginLbl.getStyleClass().addAll("LoginHeading");

        // Adding a horizontal line in login screen
        Line Hr = new Line(0, 0, 100, 0);
        // Setting stroke of line
        Hr.setStrokeWidth(5);
        // Setting stroke color
        Hr.setStroke(Color.WHITE);
        // Applying styles on line
        Hr.getStyleClass().add("Hr");

        // Adding company information description
        Label CDesc = new Label("This is a password manager for\n" + "Milo\'s BINUS project."
                + "Hi, sir Jude!\n");

        // Creating right login box
        VBox LoginBox = new VBox();
        // Applying login styles
        LoginBox.getStyleClass().add("WhiteVbox");
        // Adding fields on login box
        LoginBox.getChildren().addAll(LoginLbl, Status, Email, Password, LoginBtn);
        // Setting space between fields
        LoginBox.setSpacing(20);

        // Sub layout
        HBox CenterBox = new HBox();
        // Applying styles
        CenterBox.getStyleClass().add("CenterHbox");
        // Adding both boxess
        CenterBox.getChildren().addAll(CompanyInformation, LoginBox);

        // Main layout
        Layout = new BorderPane();
        // Adding menu bar
        Menu m = new Menu();
        Layout.setTop(m.Menu());
        // Adding center boxes
        Layout.setCenter(CenterBox);

        // Styles for description
        CDesc.getStyleClass().add("WhiteTextColor");

        // Add labels in company information box
        CompanyInformation.getChildren().addAll(CName, Hr, CDesc);
        // Applying styles on company information
        CompanyInformation.getStyleClass().add("BlueVbox");
        // Setting spaces on fields
        CompanyInformation.setSpacing(20);

        // Getting screen sizes
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        // Creating scene
        Scn = new Scene(Layout, screenBounds.getWidth(), screenBounds.getHeight());
        // Applying styles to main screen
        Scn.getStylesheets().add(getClass().getResource("assets/application.css").toExternalForm());
        // Setting scene
        Window.setScene(Scn);
        // Maximizing the screen
        Window.setMaximized(true);
        // Showing the screen
        Window.show();
    }
}
