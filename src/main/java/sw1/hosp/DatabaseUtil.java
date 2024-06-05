package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle DB URL
    private static final String DB_USER = "SCOTT"; // Oracle DB 사용자 이름
    private static final String DB_PASSWORD = "scott"; // Oracle DB 비밀번호

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 데이터베이스에 연결하는 함수문
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // 로그인 정보를 확인하고 직원의 유형(의사/일반직원)을 반환하는 함수문
    // 로그인 정보가 없을 경우 null 반환
    static String authenticate(String username, String password) {
        String userType = null;
        String query = "SELECT employee_type FROM employees WHERE username = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            System.out.println("Connected to database: " + conn.getMetaData().getURL());

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    userType = rs.getString("employee_type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userType;
    }

    // 데이터베이스로 부터 "모든" 환자 정보를 가져오는 함수문
    // ObservableList<Patient> 형식으로 환자들의 정보값을 리턴
    public static ObservableList<Patient> getPatients() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();

        String query =  "SELECT p.* FROM patients p JOIN idlist il ON p.patient_id = il.id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("patient_id");
                String name = rs.getString("patient_name");
                String birthDate = rs.getDate("patient_date").toLocalDate().toString();
                String address = rs.getString("patient_address");
                String contact = rs.getString("patient_contact");
                //String department = rs.getString("department");

                Patient patient = new Patient(id, name, birthDate, address, contact);
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // 환자 정보를 데이터베이스에 추가하는 함수문
    public static void addPatient(String name, LocalDate birthDate, String gender, String address, String contact) {
        String query = "INSERT INTO patients (patient_name, patient_date, patient_gender, patient_address, patient_contact) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setDate(2, Date.valueOf(birthDate));
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setString(5, contact);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 환자 정보를 데이터베이스에서 삭제하는 함수문
    // 사용 안할것 같다.
    public static void deletePatient(int id, String name) {
        String sql = "DELETE FROM patients WHERE patient_id = ? AND patient_name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.setString(2, name);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 이름과 생년월일을 받아 환자 정보를 리턴 하는 함수문
    public static Patient getPatientByNameAndBirthDate(String name, String birthDate) {
        Patient patient = null;
        String sql = "SELECT * FROM patients WHERE patient_name = ? AND patient_date = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDate(2, java.sql.Date.valueOf(birthDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("patient_id");
                    String address = rs.getString("patient_address");
                    String contact = rs.getString("patient_contact");
                    //String department = rs.getString("department");

                    patient = new Patient(id, name, birthDate, address, contact);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    // 이름과 생년월일을 받아 ID를 리턴하는 함수문
    public static int getPatientID(String name, String birthDate) {
        //Patient patient = null;
        int id = 0;
        String sql = "SELECT * FROM patients WHERE patient_name = ? AND patient_date = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDate(2, java.sql.Date.valueOf(birthDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("patient_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    // 리스트 기억용
    public static void addIDlist(Patient patient) {
        int id = 0;

        String name = patient.getName();
        String birthDate = patient.getBirthDate();

        id = getPatientID(name, birthDate);

        String query = "INSERT INTO idlist (id) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            setCommitDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 리스트 기억용 - 삭제 버전
    public static void deleteIDlist(int id) {
        String sql = "DELETE FROM idlist WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            setCommitDB();
            checkIDlist();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 리스트 확인용
    public static void checkIDlist() {
        String query = "SELECT * FROM idlist";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                System.out.println(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setCommitDB() {
        String sql = "COMMIT";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
