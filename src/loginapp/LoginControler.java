package loginapp;

import Admin.AdminControler;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import users.UsersControler;
import java.util.ResourceBundle;
import java.io.IOException;

import java.net.URL;


public class LoginControler implements Initializable {

    LoginModel loginModel =new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> combobox;
    @FXML
    private Button loginbutton;
    @FXML
    private Label loginstatus;

    public void initialize (URL url, ResourceBundle rb){
       if(this.loginModel.isDatabaseConnected()){
           this.dbstatus.setText("Connected to Database");
       } else {
           this.dbstatus.setText("Not Connected To Database");
       }
       this.combobox.setItems(FXCollections.observableArrayList(option.values()));

    }
   @FXML
    public void Login (ActionEvent event){
       try {
           if(this.loginModel.isLogin(this.username.getText(),this.password.getText(),((option)this.combobox.getValue()).toString()))
           {
            Stage stage =(Stage)this.loginbutton.getScene().getWindow();
            stage.close();
            switch (((option)this.combobox.getValue()).toString()){
                case "Admin":
                    adminlogin();
                    break;
                case "User":
                    userlogin();
                    break;
            }
           }
           else {
               this.loginstatus.setText("Wrong Creditials");
           }
       }catch (Exception localException ){
           this.loginstatus.setText("Exeption");
       }

    }
    public void userlogin(){
    try{
        Stage userStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = (Pane)loader.load(getClass().getResource("/users/user.fxml").openStream());
        UsersControler usersControler =(UsersControler)loader.getController();

        Scene scene = new Scene(root);
        userStage.setScene(scene);
        userStage.setTitle("Users Board");
        userStage.setResizable(false);
        userStage.show();

    }
    catch (IOException ex){
        ex.printStackTrace();
    }
    }
    public void adminlogin(){
        try{
            Stage adminStage = new Stage();
            FXMLLoader adminloader = new FXMLLoader();
            Pane adminroot = (Pane)adminloader.load(getClass().getResource("/Admin/admin.fxml").openStream());
            AdminControler adminControler =(AdminControler)adminloader.getController();

            Scene scene = new Scene(adminroot);
            adminStage.setScene(scene);
            adminStage.setTitle("Admin Board");
            adminStage.setResizable(false);
            adminStage.show();

        }
        catch (IOException ex){
            ex.printStackTrace();
        }


    }
}
