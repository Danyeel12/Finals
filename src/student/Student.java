package student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Student {
    
    private final String studentID;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String city;
    private final String province;
    private final String postalCode;
    private final String phoneNumber;
    private final String tuitionFees;
    
    public Student(String studentID, String firstName, String lastName, String address, 
            String city, String postalCode, String province, String phoneNumber, String tuitionFees) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.phoneNumber = phoneNumber;
        this.tuitionFees = tuitionFees;
    }

    public String getUserName() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getProvince() {
        return province;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getTuitionFees() {
        return tuitionFees;
    }


}