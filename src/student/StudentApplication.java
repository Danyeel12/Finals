package student;

import javafx.application.Application;
import javafx.stage.Stage;

public class StudentApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
