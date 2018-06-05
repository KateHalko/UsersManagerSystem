package Admin;

import com.sun.org.apache.xpath.internal.operations.String;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import dbUtil.dbConection;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminControler {

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;
    @FXML
    private TableView<UsersData> userstable;
    @FXML
    private TableColumn<UsersData, String> idcolumn;
    @FXML
    private TableColumn<UsersData, String> namecolumn;
    @FXML
    private TableColumn<UsersData, String> surnamecolumn;
    @FXML
    private TableColumn<UsersData, String> emailcolumn;
    @FXML
    private TableColumn<UsersData, String> dobcolumn;

    private  dbConection dc;
    private ObservableList<UsersData> data;

    public void initialize(URL url, ResourceBundle rb){
        this.dc = new dbConection();
    }
    @FXML
    private void loadUsersData(ActionEvent event) throws SQLException {
        try {
            Connection conn= dbConection.getConnection();
            this.data =FXCollections.observableArrayList();

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM users");
            while (rs.next()){ //run until have data in db
                this.data.add(new UsersData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));

            }
        }catch (SQLException e){
         System.err.println("Error"+e);
        }

        this.idcolumn.setCellValueFactory( new PropertyValueFactory<UsersData, String>("id"));
        this.namecolumn.setCellValueFactory( new PropertyValueFactory<UsersData, String>("name"));
        this.surnamecolumn.setCellValueFactory( new PropertyValueFactory<UsersData, String>("surname"));
        this.emailcolumn.setCellValueFactory( new PropertyValueFactory<UsersData, String>("email"));
        this.dobcolumn.setCellValueFactory( new PropertyValueFactory<UsersData, String>("dob"));


        this.userstable.setItems(null);
        this.userstable.setItems(this.data);
    }

    @FXML
    private void  addUser(ActionEvent event){
        try {
            Connection conn = dbConection.getConnection();
            PreparedStatement stmt =conn.prepareStatement("INSERT INTO users(id,name,email,dob) VALUES (?,?,?,?,?)");
            stmt.setString(1,this.id.getText());
            stmt.setString(1,this.name.getText());
            stmt.setString(1,this.surname.getText());
            stmt.setString(1,this.email.getText());

            stmt.execute();
            conn.close();

        }catch (SQLException e){

            e.printStackTrace();
        }

    }
    @FXML
    private void clearFields ( ActionEvent event){
        this.id.setText("");
        this.name.setText("");
        this.surname.setText("");
        this.email.setText("");
        this.dob.setValue(null);
    }
}
