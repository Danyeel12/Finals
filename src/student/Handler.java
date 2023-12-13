package student;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Handler {
    public static List<Student> getAllStudents() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();

        try {
            conn = Database.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement("SELECT * FROM Student");
                rs = pst.executeQuery();

                while (rs.next()) {
                    Student student = new Student(
                        rs.getString("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("province"),
                        rs.getString("postal_code"),
                        rs.getString("phone_number"),
                        rs.getString("tuitionFees")
                    );
                    students.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Database.closeResources(rs, pst, conn);
        }

        return students;
    }

    public static void insertStudent(Student student) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = Database.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement("INSERT INTO Student (student_id, first_name, last_name, address, city, province, postal_code, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1, student.getUserName());
                pst.setString(2, student.getFirstName());
                pst.setString(3, student.getLastName());
                pst.setString(4, student.getAddress());
                pst.setString(5, student.getCity());
                pst.setString(6, student.getProvince());
                pst.setString(7, student.getPostalCode());
                pst.setString(8, student.getPhoneNumber());
                pst.setString(9, student.getTuitionFees());

                pst.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Database.closeResources(null, pst, conn);
        }
    }
}