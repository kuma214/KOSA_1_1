package sw1.hosp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle DB URL
    private static final String DB_USER = "system"; // Oracle DB 사용자 이름
    private static final String DB_PASSWORD = "oracle_4U"; // Oracle DB 비밀번호
    public static String drName;
    public static int medical_id;
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
                String birthDate = rs.getDate("patient_Birthday").toLocalDate().toString();
                String address = rs.getString("patient_address");
                String contact = rs.getString("patient_contactNum");
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
        String query = "INSERT INTO patients (patient_name, patient_Birthday, patient_gender, patient_address, patient_contactNum) VALUES (?, ?, ?, ?, ?)";

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
        String sql = "SELECT * FROM patients WHERE patient_name = ? AND patient_Birthday = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDate(2, java.sql.Date.valueOf(birthDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("patient_id");
                    String address = rs.getString("patient_address");
                    String contact = rs.getString("patient_contactNum");
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
        String sql = "SELECT * FROM patients WHERE patient_name = ? AND patient_Birthday = ?";

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
    //환자이름만으로 데이터 불러오기
    public static int getPatientID(String name) {
        //Patient patient = null;
        int id = 0;
        String sql = "SELECT * FROM patients WHERE patient_name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);


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
    //의사이름으로 의사id 불러오기
    public static int getDoctorID(String name) {
        //Patient patient = null;
        int id = 0;
        String sql = "SELECT * FROM employees WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);


            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("employee_id");
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

    public static ObservableList<MedicalRecord> loadDataFromMedicalRecord(Patient patient) {
        ObservableList<MedicalRecord> MediRecord = FXCollections.observableArrayList();


        String query = "select m.medicalrecord_id, m.record_date, m.record_notes,m.record_date, e.name, d.diagnosis_name" +
                " from medicalrecord m" +
                " join patients p on m.patient_id = p.patient_id" +
                " join employees e on m.employee_id = e.employee_id" +
                " join diagnosis d on m.diagnosis_id = d.diagnosis_id" +
                " where p.patient_name = ? ";

        String patientName = patient.getName();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,patientName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int mrid = rs.getInt("medicalrecord_id");
                    String mrdrname = rs.getString("name");
                    String mrdate = rs.getString("record_date");
                    String diagnosis = rs.getString("diagnosis_Name");
                    String notes = rs.getString("record_notes");

                    MedicalRecord medicalRecord = new MedicalRecord(mrid, patientName, mrdrname, mrdate, diagnosis, notes);
                    MediRecord.add(medicalRecord);
                }
                System.out.println("break");
            }
            }catch (SQLException e) {
            e.printStackTrace();
        }
        return MediRecord;
    }

    public static ObservableList<Symptom> searchSymptom(String search){
        ObservableList<Symptom> resultSymtom = FXCollections.observableArrayList();

        String query = "select symptom_id, symptom_name from SYMPTOM WHERE symptom_name like ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, "%"+search+"%");
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    int symptomid = rs.getInt("symptom_id");
                    String symptomname = rs.getString("symptom_name");


                    Symptom rs_symtom = new Symptom(symptomid, symptomname);
                    resultSymtom.add(rs_symtom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSymtom;
    }

    public static ObservableList<Medication> searchMedication(String search){
        ObservableList<Medication> resultMedication = FXCollections.observableArrayList();

        String query = "select medication_id, medication_name from MEDICATION WHERE medication_name like ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, "%"+search+"%");
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    int medicationid = rs.getInt("medication_id");
                    String medicationname = rs.getString("medication_name");


                    Medication rs_medication = new Medication(medicationid, medicationname);
                    resultMedication.add(rs_medication);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultMedication;
    }
    public static ObservableList<Diagnosis> searchDiagnosis(String search){
        ObservableList<Diagnosis> resultDiagnosis = FXCollections.observableArrayList();

        String query = "select diagnosis_id, diagnosis_name from diagnosis WHERE diagnosis_name like ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, "%"+search+"%");
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    int diagnosis_id = rs.getInt("diagnosis_id");
                    String diagnosis_name = rs.getString("diagnosis_name");


                    Diagnosis rs_medication = new Diagnosis(diagnosis_id, diagnosis_name);
                    resultDiagnosis.add(rs_medication);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultDiagnosis;
    }
    public static void getDoctorName(String username)
    {
        String query = "select name from employees where username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    drName = rs.getString("name");
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void setInsertRecord(String doctor, int diagId, String patient, LocalDate date, String description)
    {
        String query = "insert into medicalrecord(patient_id, employee_id, diagnosis_id, record_date, record_notes) values (?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1,getPatientID(patient));
            stmt.setInt(2,getDoctorID(doctor));
            stmt.setInt(3,diagId);
            stmt.setDate(4,Date.valueOf(date));
            stmt.setString(5,description);
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        String query2 = "SELECT * FROM (SELECT * FROM medicalrecord ORDER BY medicalrecord_id DESC) WHERE ROWNUM = 1";
            try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query2)) {
           //stmt.setDate(1,Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    medical_id = rs.getInt("medicalrecord_id");
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setMedicalRecordMedication(int[] array)
    {
        String query = "insert into medicalrecordmedication(medicalrecord_id, medication_id) values (?,?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for(int i=0; i<array.length; i++) {
                stmt.setInt(1, medical_id);
                stmt.setInt(2, array[i]);
                stmt.executeUpdate();
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
