import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AddUsers {

    // Declaring the variables
    public static Stage Window;
    Database db = new Database();

    // Constructor
    public AddUsers() {

        // Creating a new list to add users
        ObservableList<Users> DataList = FXCollections.observableArrayList();
        // Creating a table to show users
        TableView<Users> UsersTable = new TableView<Users>(DataList);

        // Creating table column
        TableColumn<Users, String> FirstName = new TableColumn<Users, String>("First Name");
        // Creating table column
        TableColumn<Users, String> LastName = new TableColumn<Users, String>("Last Name");
        // Creating table column
        TableColumn<Users, String> Username = new TableColumn<Users, String>("Username");
        // Creating table column
        TableColumn<Users, String> Phone = new TableColumn<Users, String>("Phone");
        // Creating table column
        TableColumn<Users, String> Password = new TableColumn<Users, String>("Password");

        // Setting the value types
        FirstName.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        // Setting the value types
        LastName.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        // Setting the value types
        Username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        // Setting the value types
        Phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        // Setting the value types
        Password.setCellValueFactory(new PropertyValueFactory<>("Password"));

        // Setting width to each column of table
        FirstName.setPrefWidth(135);
        // Setting width to each column of table
        LastName.setPrefWidth(135);
        // Setting width to each column of table
        Username.setPrefWidth(135);
        // Setting width to each column of table
        Phone.setPrefWidth(135);
        // Setting width to each column of table
        Password.setPrefWidth(135);
        // Setting width to each column of table

        try {
            // Getting all the normal users except admin user
            db.PreStatement = db.GetConnection().prepareStatement("SELECT * FROM Users WHERE Role=?");
            db.PreStatement.setString(1, "0");
            // Executing query to get all the requried users
            db.Result_Set = db.PreStatement.executeQuery();
            // Looping to get all the users and storing it in our users list
            while (db.Result_Set.next()) {
                DataList.add(new Users(db.Result_Set.getString(1), db.Result_Set.getString(2), db.Result_Set.getString(3), db.Result_Set.getString(4), db.Result_Set.getString(5)));
            }
        } catch (SQLException e1) {
            // In case of sql error
            System.out.println(e1.getMessage());
        }

        // Adding all the column to table
        UsersTable.getColumns().addAll(FirstName, LastName, Username, Phone, Password);

        // Creating a new box to add our table
        VBox TableVB = new VBox();
        // Adding table
        TableVB.getChildren().add(UsersTable);
        // Setting width
        TableVB.setMinWidth(320);

        // Creating a new box to add buttons
        HBox ButtonB = new HBox();

        // Creating new button
        Button New = new Button("New");
        // Click function for new button
        New.setOnAction(e -> NewUsers());

        // Creating edit button
        Button Edit = new Button("Edit");
        // Click function for edit buttion
        Edit.setOnAction(e -> UpdateUsers(UsersTable.getSelectionModel().getSelectedItem()));

        // Creating delete button
        Button Delete = new Button("Delete");
        // Click function for delete button
        Delete.setOnAction(e -> DeleteUsers(UsersTable.getSelectionModel().getSelectedItem()));

        // Adding buttons to button boxes
        ButtonB.getChildren().addAll(New, Edit, Delete);
        // Adding spaces between buttons
        ButtonB.setSpacing(10);

        // Creating main box to add all the boxes
        VBox Center = new VBox();
        // Applying styles
        Center.getStyleClass().add("hbox");
        // Adding boxes in main box
        Center.getChildren().addAll(TableVB, ButtonB);
        // Applying styles
        Center.setMaxHeight(800);
        // Applying styles
        Center.setMaxWidth(800);
        // Applying styles
        Center.setSpacing(20);
        // Showing main box
        Driver.Layout.setCenter(Center);
    }

    // Creating a sub screen to add users
    public void NewUsers() {
        // Creating a small stage
        Stage DialogStage = new Stage();
        // Declaring all the labels
        Label FirstNameLabel = new Label("First Name");
        Label LastNameLabel = new Label("Last Name");
        Label UsernameLabel = new Label("Username");
        Label PhoneLabel = new Label("Phone");
        Label PasswordLabel = new Label("Password");

        // Declaring all the textfields
        TextField FirstNameField = new TextField();
        TextField LastNameField = new TextField();
        TextField UsernameField = new TextField();
        TextField PhoneField = new TextField();
        
        TextField PasswordField = new TextField();
        PasswordField.setMinWidth(325);
        
        // Creating a generate password button
        Button Generate = new Button("Generate");
        // Creating a function to generate a random password
        Generate.setOnAction(e->{
            // Creating a technique for password
            Caesar C = new Caesar(1);
            // Encrypting a password and showing
            PasswordField.setText(C.encrypt(Database.generateRandomPassword(8)));
        });
        
        // Creating a box to add password button and field
        HBox PassBox = new HBox();
        PassBox.getChildren().addAll(PasswordField, Generate);

        // Creating main sub box
        VBox DialogNewUsers = new VBox();
        // Adding all the labels and textfields
        DialogNewUsers.getChildren().addAll(
                FirstNameLabel,  FirstNameField,
                LastNameLabel,  LastNameField,
                UsernameLabel,  UsernameField,
                PhoneLabel,  PhoneField,
                PasswordLabel,  PassBox
        );

        // Creating a box to add all the buttons
        HBox ButtonBox = new HBox();

        // Creating enter button
        Button EnterBtn = new Button("Enter");
        // Function to add new user
        EnterBtn.setOnAction(e -> {
            try {
                // Storing user in database
                db.PreStatement = db.GetConnection().prepareStatement("INSERT INTO Users(FirstName, LastName, Username, Phone, Password, Role) VALUES(?,?,?,?,?,?)");
                db.PreStatement.setString(1, FirstNameField.getText());
                db.PreStatement.setString(2, LastNameField.getText());
                db.PreStatement.setString(3,UsernameField.getText());
                db.PreStatement.setString(4,PhoneField.getText());
                db.PreStatement.setString(5,PasswordField.getText());
                db.PreStatement.setString(6,"0");
                db.PreStatement.executeUpdate();
                DialogStage.hide();
                new AddUsers();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        });

        // Creating a cancel button
        Button CancelBtn = new Button("Cancel");
        // Close on cliking on cancel button
        CancelBtn.setOnAction(e -> DialogStage.hide());

        // Add buttons to button box
        ButtonBox.getChildren().addAll(EnterBtn, CancelBtn);
        // Applying styles
        ButtonBox.setSpacing(10);

        // Creating main sub box
        VBox Center = new VBox();
        // Applying styles
        Center.getStyleClass().add("hbox");
        Center.getChildren().addAll(DialogNewUsers, ButtonBox);
        // Applying styles
        Center.setMaxHeight(500);
        // Applying styles
        Center.setMaxWidth(500);
        // Applying styles
        Center.setSpacing(20);

        // Applying styles
        DialogStage.setResizable(false);
        // Applying styles
        Scene DialogScn = new Scene(Center, 500, 500);
        // Applying styles
        DialogScn.getStylesheets().add(getClass().getResource("assets/application.css").toExternalForm());
        // Applying styles
        DialogStage.setScene(DialogScn);

        DialogStage.setTitle("Add New Users");
        // Showing screen
        DialogStage.show();
    }

    public void UpdateUsers(Users U) {
        // If the users selected a user from the table
        if (U != null) {
            // Creating a new sub stage
            Stage DialogStage = new Stage();
            // Declaring labels
             Label FirstNameLabel = new Label("First Name");
        Label LastNameLabel = new Label("Last Name");
        Label UsernameLabel = new Label("Username");
        Label PhoneLabel = new Label("Phone");
        Label PasswordLabel = new Label("Password");

        // Declaring all the textfields
        TextField FirstNameField = new TextField();
        TextField LastNameField = new TextField();
        
        TextField UsernameField = new TextField();
        // Disabling user because we don't want to change usernamae
        UsernameField.setDisable(true);
        UsernameField.setText(U.Username);
        
        TextField PhoneField = new TextField();
        TextField PasswordField = new TextField();

        // Creating a new box to add labels and textfields
        VBox DialogNewUsers = new VBox();
        DialogNewUsers.getChildren().addAll(
                FirstNameLabel,  FirstNameField,
                LastNameLabel,  LastNameField,
                UsernameLabel,  UsernameField,
                PhoneLabel,  PhoneField,
                PasswordLabel,  PasswordField
        );

        // Setting old text of specific / selected user
            FirstNameField.setText(U.FirstName);
            LastNameField.setText(U.LastName);
            UsernameField.setText(U.Username);
            PhoneField.setText(U.Phone);
            PasswordField.setText(U.Password);

// Creating a new box to add buttons
            HBox ButtonBox = new HBox();

            // Creating a new button 
            Button UpdateBtn = new Button("Update");
            // Adding a click function
            UpdateBtn.setOnAction(e -> {
                try {
                    // Upading the user and getting the string from fields
                    db.PreStatement = db.GetConnection().prepareStatement("UPDATE Users SET FirstName=?, LastName=?, Phone=?, Password=? WHERE Username = ?");
                    db.PreStatement.setString(1, FirstNameField.getText());
                    db.PreStatement.setString(2, LastNameField.getText());
                    db.PreStatement.setString(3, PhoneField.getText());
                    db.PreStatement.setString(4, PasswordField.getText());
                    db.PreStatement.setString(5, UsernameField.getText());
                    db.PreStatement.executeUpdate();
                    DialogStage.hide();
                    new AddUsers();
                } catch (SQLException e1) {
                    System.out.println(e1.getMessage());
                }
            });

            // creating cancel button
            Button CancelBtn = new Button("Cancel");
            // Close the screen on clicking on cancel button
            CancelBtn.setOnAction(e -> DialogStage.hide());

            // Add buttons to button box
            ButtonBox.getChildren().addAll(UpdateBtn, CancelBtn);
            // Setting spaces between buttons
            ButtonBox.setSpacing(10);

            // Creating a box for main
            VBox Center = new VBox();
            // Applying styles
            Center.getStyleClass().add("hbox");
            // Add all the box in main box
            Center.getChildren().addAll(DialogNewUsers, ButtonBox);
            // Applying styles
            Center.setMaxHeight(500);
            // Applying styles
            Center.setMaxWidth(500);
            // Applying styles
            Center.setSpacing(20);

            // Disabling the resize option
            DialogStage.setResizable(false);
            // Creating new screen
            Scene DialogScn = new Scene(Center, 500, 500);
            // Applying style class
            DialogScn.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // Setting scenes
            DialogStage.setScene(DialogScn);

            // Setting title for update
            DialogStage.setTitle("Add New Users");
            // Showing update screen
            DialogStage.show();
        } else {
            // Showing adduser function
            new AddUsers();
        }
    }

    public void DeleteUsers(Users U) {
        // If the user does not select any user
        if (U != null) {
            try {
                // Deleting the selected user
                db.PreStatement = db.GetConnection().prepareStatement("DELETE FROM Users WHERE Username = ?");
                db.PreStatement.setString(1, U.Username);
                db.PreStatement.executeUpdate();
                // Loading and Showing the table again
                new AddUsers();
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
        } else {
            // Showing the table again
            new AddUsers();
        }
    }

    // Classes to store users data
    public class Users {

        // Declaring variabels
        private String FirstName;
        private String LastName;
        private String Username;
        private String Phone;
        private String Password;

        // Constructor
        public Users(String FirstName, String LastName, String Username, String Phone, String Password) {
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.Username = Username;
            this.Phone = Phone;
            this.Password = Password;
        }

        // Getter for all the variables
        public String getFirstName() {
            return FirstName;
        }
        public String getLastName() {
            return LastName;
        }
        public String getUsername() {
            return Username;
        }
        public String getPhone() {
            return Phone;
        }
        public String getPassword() {
            return Password;
        }        
        
    };

}
