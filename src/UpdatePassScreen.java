import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdatePassScreen {

    public static Stage Window;

    public UpdatePassScreen() {
        
        // Declering textfield for update password screen
        TextField OldPass = new TextField();
        TextField Pass = new TextField();
        TextField RPass = new TextField();
        Button UpdateBtn = new Button("Update Password");
        // Creating database object
        Database db = new Database();

        // Creating a alert function
        Alert a = new Alert(Alert.AlertType.INFORMATION); 
        // Creating event for alert function
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // Pause and wait for user to repond on alert message
                a.showAndWait();
            }
        };

        // Click function for update btn on update password screen
        UpdateBtn.setOnAction(e -> {
            try {
                // Update password and check old password
                db.PreStatement = db.GetConnection().prepareStatement("UPDATE MasterUsers SET Password=? WHERE Username=? AND Password=?");
                // Seding new password in query
                db.PreStatement.setString(1, Pass.getText());
                // Seding username in query from session
                db.PreStatement.setString(2, Driver.EmailStore);
                // Sending old password
                db.PreStatement.setString(3, OldPass.getText());
                // Checking if password in both fields match
                if(Pass.getText().equals(RPass.getText())){
                    // Checking if the password has been changed
                    if (db.PreStatement.executeUpdate() > 0) {
                        a.setContentText("Master password has been updated");
                        event.handle(new ActionEvent());
                        // Showing home after changing the password
                        new Home();
                    } else {
                        // Error in case of wrong old password
                        a.setContentText("Invalid old password");
                        event.handle(new ActionEvent());
                    }
                }
                else{
                    // Show error in case match does not match in both fields
                        a.setContentText("Passwords does not match");
                        event.handle(new ActionEvent());
                }
            } catch (SQLException e1) {
                // Showing error from sql if any
                System.out.println(e1.getMessage());
            }
        });

        // Delcraing labels for password screen
        Label OldLabel = new Label("Old Password");
        Label PassLabel = new Label("Password");
        Label RPassLabel = new Label("Re Enter Password");

        // Creating a box to hold all the labels and fields
        VBox Center = new VBox();
        // Applying styles on update password screen
        Center.getStyleClass().add("hbox");
        // Adding all the labels and fields in box
        Center.getChildren().addAll(
                OldLabel, OldPass,
                PassLabel, Pass,
                RPassLabel, RPass,
                UpdateBtn);
        // Setting width for box
        Center.setMaxWidth(400);
        // Setting height for box
        Center.setMaxHeight(400);
        // Setting spaces for box
        Center.setSpacing(10);
        // Adding menubar in update password screen
        Menu m = new Menu();
        Driver.Layout.setTop(m.Menu());
        // Adding box in screen
        Driver.Layout.setCenter(Center);
    }
}
