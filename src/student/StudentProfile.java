package student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentProfile extends Application  {
    private TextField studentID, firstName, lastName, address, city, province, postalCode, phoneNumber;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<String> selectedStudent = FXCollections.observableArrayList();
    private Button btnSubmit = new Button("Submit");
    private Button btnAddStudent = new Button("Add Information to User");
    private Button btnUpdate = new Button("Update Information");
    private ComboBox<String> listOfStudents = new ComboBox<>();
    private PreparedStatement pst;
    private Connection conn;
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DATABASE_URL = "jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD";
    private static final String DATABASE_USER = "COMP228_M23_sy_69";
    private static final String DATABASE_PASSWORD = "password";
    
    @Override
    public void start(Stage primaryStage) {
        studentID = new TextField();
        firstName = new TextField();
        lastName = new TextField();
        address = new TextField();
        city = new TextField();
        province = new TextField();
        postalCode = new TextField();
        phoneNumber = new TextField();
        node tuitionFees = new TextField();
        
        GridPane formPane = new GridPane();
        GridPane.setConstraints(formPane, 10, 10, 10, 10);
        btnSubmit.setAlignment(Pos.BASELINE_CENTER);
        formPane.setPadding(new Insets(10, 10, 10, 10));
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.add(new Label("Student ID: "), 0, 0);
        formPane.add(new Label("First Name: "), 0, 1);
        formPane.add(new Label("Last Name: "), 0, 2);
        formPane.add(new Label("Address: "), 0, 3);
        formPane.add(new Label("City: "), 0, 7);
        formPane.add(new Label("Postal Code: "), 0, 4);
        formPane.add(new Label("Province: "), 0, 5);
        formPane.add(new Label("Phone Number: "), 0, 6);
        formPane.add(new Label("Tuition Fees: "), 0, 6);
        formPane.add(studentID, 1, 0);
        formPane.add(firstName, 1, 1);
        formPane.add(lastName, 1, 2);
        formPane.add(address, 1, 3);
        formPane.add(city, 1, 7);
        formPane.add(province, 1, 5);
        formPane.add(postalCode, 1, 4);
        formPane.add(phoneNumber, 1, 6);
        formPane.add(tuitionFees, 1, 6);
        
        TableView<Student> StudentTable = new TableView<>();
        TableColumn<Student, String> colStudentID = new TableColumn<>("Student ID");
        colStudentID.setMinWidth(100);
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));

        TableColumn<Student, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setMinWidth(100);
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Student, String> colLastName = new TableColumn<>("Last Name");
        colLastName.setMinWidth(100);
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Student, String> colAddress = new TableColumn<>("Address");
        colAddress.setMinWidth(200);
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Student, String> colCity = new TableColumn<>("City");
        colCity.setMinWidth(100);
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Student, String> colProvince = new TableColumn<>("Province");
        colProvince.setMinWidth(100);
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));

        TableColumn<Student, String> colPostalCode = new TableColumn<>("Postal Code");
        colPostalCode.setMinWidth(100);
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<Student, String> colPhoneNumber = new TableColumn<>("Phone Number");
        colPhoneNumber.setMinWidth(150);
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Student, String> colTuitionFees = new TableColumn<>("Tuition Fees");
        colPhoneNumber.setMinWidth(150);
        colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("tuitionFees);
        
        		 // Initialize buttons
        	    btnSubmit = new Button("Submit");
        	    btnUpdate = new Button("Update Information");
        	    btnAddStudent = new Button("Add Information to User");
        	    btnDelete = new Button("Delete");
        	    btnUpdateFees = new Button("Update Tuition Fees");
        	    btnQuit = new Button("Quit");
        	    
        	    // Set button actions
        	    btnSubmit.setOnAction(e -> handleAddButtonAction());
        	    btnUpdate.setOnAction(e -> handleUpdateButtonAction());
        	    btnAddStudent.setOnAction(e -> handleAddStudentButtonAction());
        	    btnDelete.setOnAction(e -> handleDeleteButtonAction());
        	    btnUpdateFees.setOnAction(e -> handleUpdateFeesButtonAction());
        	    btnQuit.setOnAction(e -> handleQuitButtonAction());
        	    
        	    
     StudentTable.setItems(students);
     StudentTable.getColumns().addAll(colStudentID, colFirstName, colLastName, colAddress, colCity, colProvince, colPostalCode, colPhoneNumber);

     GridPane buttonPane = new GridPane();
     buttonPane.setPadding(new Insets(10, 10, 10, 10));
     buttonPane.setHgap(10);

     
     // Add your buttons to the buttonPane as needed
     buttonPane.add(btnSubmit, 0, 0);
     buttonPane.add(btnUpdate, 1, 0);
     buttonPane.add(btnAddStudent, 2, 0);

     GridPane studentInfoPane = new GridPane();
     studentInfoPane.setPadding(new Insets(10, 10, 0, 10));
     studentInfoPane.setMinWidth(150);
     studentInfoPane.setMaxWidth(250);

     ListView<String> Students = new ListView<>();
     studentInfoPane.add(new Label("List of Students: "), 0, 0);
     studentInfoPane.add(Students, 0, 1);

     BorderPane mainLayout = new BorderPane();
     mainLayout.setPadding(new Insets(10, 10, 10, 10));
     mainLayout.setLeft(formPane);
     mainLayout.setCenter(StudentTable);
     mainLayout.setRight(studentInfoPane);
     mainLayout.setBottom(buttonPane);
     
     // Add buttons to buttonPane
     buttonPane.add(btnSubmit, 0, 0);
     buttonPane.add(btnUpdate, 1, 0);
     buttonPane.add(btnAddStudent, 2, 0);
     buttonPane.add(btnDelete, 3, 0);
     buttonPane.add(btnUpdateFees, 4, 0);
     buttonPane.add(btnQuit, 5, 0);
     

     btnSubmit.setOnAction(e -> {
    	    try {
    	        Class.forName(DRIVER);
    	        conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    	        pst = conn.prepareStatement("INSERT INTO Student (student_id, first_name, last_name, address, city, province,postal_code, phone_number, tuition_fees) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    	        pst.setString(1, studentID.getText());
    	        pst.setString(2, firstName.getText());
    	        pst.setString(3, lastName.getText());
    	        pst.setString(4, address.getText());
    	        pst.setString(5, city.getText());
    	        pst.setString(6, province.getText());
    	        pst.setString(7, postalCode.getText());
    	        pst.setString(8, phoneNumber.getText());
    	        pst.executeUpdate();
    	        Alert alert = new Alert(AlertType.INFORMATION, "Student information has been successfully added!");
    	        alert.setHeaderText("Information");
    	        alert.setTitle("Student Added");
    	        alert.show();
    	    } catch (Exception ex) {
    	        Alert alert = new Alert(AlertType.ERROR, "Username already exists.");
    	        alert.setHeaderText("Error");
    	        alert.setTitle("Error");
    	        alert.show();
    	    } finally {
    	        try {
    	            pst.close();
    	            conn.close();
    	        } catch (Exception ex) {
    	            // Handle the exception
    	        }
    	    }
    	    populateTable();
    	});

    	btnUpdate.setOnAction(e -> {
    	    if (!studentID.getText().equals("")) {
    	        try {
    	            Class.forName(DRIVER);
    	            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    	            pst = conn.prepareStatement("UPDATE Student SET student_id= ?, first_name=?, last_name=?, address=?, city=?, province=?, postal_code=?, phone_number=?,tuition_fees=? WHERE student_id=?");
    	            pst.setString(1, studentID.getText());
    	            pst.setString(2, firstName.getText());
    	            pst.setString(3, lastName.getText());
    	            pst.setString(4, address.getText());
    	            pst.setString(5, city.getText());
    	            pst.setString(6, province.getText());
    	            pst.setString(7, postalCode.getText());
    	            pst.setString(8, phoneNumber.getText());
    	            if (pst.executeUpdate() == 0) {
    	                throw new Exception();
    	            }
    	            Alert alert = new Alert(AlertType.INFORMATION, "Student information has been successfully updated!");
    	            alert.setHeaderText("Information");
    	            alert.setTitle("Student Information Updated");
    	            alert.show();
    	        } catch (Exception ex) {
    	            Alert alert = new Alert(AlertType.ERROR, "Username not found / cannot change username.");
    	            alert.setHeaderText("Error");
    	            alert.setTitle("Error");
    	            alert.show();
    	        } finally {
    	            try {
    	                pst.close();
    	                conn.close();
    	            } catch (Exception ex) {
    	                // Handle the exception
    	            }
    	        }
    	    } else {
    	        Alert alert = new Alert(AlertType.ERROR, "Username cannot be empty.");
    	        alert.setHeaderText("Error");
    	        alert.setTitle("Error");
    	        alert.show();
    	    }
    	    populateTable();
    	});

    	btnAddStudent.setOnAction(e -> {
    	    try {
    	        Class.forName(DRIVER);
    	        conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    	        pst = conn.prepareStatement("INSERT INTO students (student_id, first_name, last_name, address, city, province, postal_code, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
    	        pst.setInt(1, listOfStudents.getSelectionModel().getSelectedIndex() + 1);
    	        pst.setString(2, studentID.getText());
    	        pst.executeUpdate();
    	        Alert alert = new Alert(AlertType.INFORMATION, "Student information has been successfully added!");
    	        alert.setHeaderText("Information");
    	        alert.setTitle("Information Added");
    	        alert.show();
    	    } catch (Exception ex) {
    	        Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
    	        alert.setHeaderText("Error");
    	        alert.setTitle("Error");
    	        alert.show();
    	    } finally {
    	        try {
    	            pst.close();
    	            conn.close();
    	        } catch (Exception ex) {
    	            // Handle the exception
    	        }
    	        populateTable();
    	    }
    	});

     StudentTable.getSelectionModel().selectedItemProperty().addListener(e -> {
         if (StudentTable.getSelectionModel().getSelectedItem() != null) {
             selectedStudent.clear();
             Student selected = StudentTable.getSelectionModel().getSelectedItem();
             studentID.setText(selected.getUserName());
             firstName.setText(selected.getFirstName());
             lastName.setText(selected.getLastName());
             address.setText(selected.getAddress());
             city.setText(selected.getCity());
             province.setText(selected.getProvince());
             postalCode.setText(selected.getPostalCode());
             phoneNumber.setText(selected.getPhoneNumber());
         }
     });
     // Repopulate the Student table
     populateTable();

     Scene scene = new Scene(mainLayout);
     primaryStage.setMinHeight(600);
     primaryStage.setHeight(500);
     primaryStage.setMinWidth(1024);
     primaryStage.setTitle("Student Profile");
     primaryStage.setScene(scene);
     primaryStage.show();

        btnSubmit.setOnAction(e -> {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                pst = conn.prepareStatement("INSERT INTO Student (student_id, first_name, last_name, address, city, province,postal_code, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1, studentID.getText());
                pst.setString(2, firstName.getText());
                pst.setString(3, lastName.getText());
                pst.setString(4, address.getText());
                pst.setString(5, city.getText());
                pst.setString(6, province.getText());
                pst.setString(7, postalCode.getText());
                pst.setString(8, phoneNumber.getText());
                pst.executeUpdate();
                Alert alert = new Alert(AlertType.INFORMATION, "Student information has been successfully added!");
                alert.setHeaderText("Information");
                alert.setTitle("Student Added");
                alert.show();
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, "Username already exists.");
                alert.setHeaderText("Error");
                alert.setTitle("Error");
                alert.show();
            } finally {
                try {
                    pst.close();
                    conn.close();
                } catch (Exception ex) {
                }
            }
            populateTable();
        });
        btnUpdate.setOnAction(e -> {
            if (!studentID.getText().equals("")) {
                try {
                    Class.forName(DRIVER);
                    conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                    pst = conn.prepareStatement("UPDATE Student SET student_id= ?, first_name=?, last_name=?, address=?, city=?, province=?, postal_code=?, phone_number=? WHERE student_id=?");
                    pst.setString(1, studentID.getText());
                    pst.setString(2, firstName.getText());
                    pst.setString(3, lastName.getText());
                    pst.setString(4, address.getText());
                    pst.setString(5, city.getText());
                    pst.setString(6, province.getText());
                    pst.setString(7, postalCode.getText());
                    pst.setString(8, phoneNumber.getText());
                    if (pst.executeUpdate() == 0) {
                        throw new Exception();
                    }
                    Alert alert = new Alert(AlertType.INFORMATION, "Student information has been successfully updated!");
                    alert.setHeaderText("Information");
                    alert.setTitle("Student Information Updated");
                    alert.show();
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.ERROR, "Username not found / cannot change username.");
                    alert.setHeaderText("Error");
                    alert.setTitle("Error");
                    alert.show();
                } finally {
                    try { pst.close(); conn.close(); } catch (Exception ex) {}
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR, "Username cannot be empty.");
                alert.setHeaderText("Error");
                alert.setTitle("Error");
                alert.show();
            }
            populateTable();
        });

        btnAddStudent.setOnAction(e -> {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                pst = conn.prepareStatement("INSERT INTO students (student_id, first_name, last_name, address, city, province, postal_code, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setInt(1, listOfStudents.getSelectionModel().getSelectedIndex() + 1);
                pst.setString(2, studentID.getText());
                pst.executeUpdate();
                Alert alert = new Alert(AlertType.INFORMATION, "Student information has been successfully added!");
                alert.setHeaderText("Information");
                alert.setTitle("Information Added");
                alert.show();
            } catch (Exception ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                alert.setHeaderText("Error");
                alert.setTitle("Error");
                alert.show();
            } finally {
                try {
                    pst.close();
                    conn.close();
                } catch (Exception ex) {}
                populateTable();
            }
        });

        StudentTable.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (StudentTable.getSelectionModel().getSelectedItem() != null) {
                selectedStudent.clear();
                Student selected = StudentTable.getSelectionModel().getSelectedItem();
                studentID.setText(selected.getUserName());
                firstName.setText(selected.getFirstName());
                lastName.setText(selected.getLastName());
                address.setText(selected.getAddress());
                city.setText(selected.getCity());
                province.setText(selected.getProvince());
                postalCode.setText(selected.getPostalCode());
                phoneNumber.setText(selected.getPhoneNumber());
            }
        });


        populateTable();

    }
    private void handleDeleteButtonAction() {
        String selectedStudentId = studentID.getText();
        if (!selectedStudentId.isEmpty()) {
            try {
                PreparedStatement pst = connection.prepareStatement("DELETE FROM Student WHERE student_id = ?");
                pst.setString(1, selectedStudentId);
                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION, "Student record deleted.");
                    alert.setHeaderText("Success");
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "Student record not found.");
                    alert.setHeaderText("Error");
                    alert.show();
                }
                pst.close();
                populateTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, "An error occurred.");
                alert.setHeaderText("Error");
                alert.show();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Please enter a student ID.");
            alert.setHeaderText("Error");
            alert.show();
        }
    }

    private void handleUpdateFeesButtonAction() {
        String selectedStudentId = studentID.getText();
        
        
        TextField tuitionFees = new TextField(); 
        
        String newTuitionFees = tuitionFees.getText(); // Get the text from the tuitionFees TextField

        if (!selectedStudentId.isEmpty() && !newTuitionFees.isEmpty()) {
            try {
                Class.forName(DRIVER);
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                PreparedStatement pst = connection.prepareStatement("UPDATE Student SET tuition_fees = ? WHERE student_id = ?");
                pst.setString(1, newTuitionFees);
                pst.setString(2, selectedStudentId);
                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION, "Tuition fees updated.");
                    alert.setHeaderText("Success");
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "Student record not found.");
                    alert.setHeaderText("Error");
                    alert.show();
                }
                pst.close();
                connection.close();
                populateTable();
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, "An error occurred.");
                alert.setHeaderText("Error");
                alert.show();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Please enter both student ID and new tuition fees.");
            alert.setHeaderText("Error");
            alert.show();
        }
    }


    private void handleQuitButtonAction() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
            // Close the application
            System.exit(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR, "An error occurred.");
            alert.setHeaderText("Error");
            alert.show();
        }
    }

    

    protected void populateTable() {
        students.clear();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            pst = conn.prepareStatement("SELECT * FROM Students");
            ResultSet StudentRS = pst.executeQuery();
            while (StudentRS.next()) {
                students.add(new Student(StudentRS.getString(1), StudentRS.getString(2), StudentRS.getString(3),
                        StudentRS.getString(4), StudentRS.getString(5), StudentRS.getString(6),
                        StudentRS.getString(7), StudentRS.getString(8),StudentRS.getString(9) ));
            }
        } catch (Exception ex) {
        } finally {
            try {
                pst.close();
                conn.close();
            } catch (Exception ex) {
            }
        }
    }


}