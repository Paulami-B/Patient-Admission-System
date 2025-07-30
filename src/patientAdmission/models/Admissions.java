package patientAdmission.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import patientAdmission.DBConnection;
import patientAdmission.models.Patient.Gender;

public class Admissions {
	private int admID, ptID;
	private LocalDate admissionDate, dischargeDate;
	private String ward, name;
	private Gender gender;
	
	private static final String getRecordsQuery = "SELECT A.*, P.Name, P.Gender FROM Admissions JOIN Patients P ON A.PatientID = P.PatientID";
	
	public Admissions(int admID, int ptID, String name, Gender gender, String ward, LocalDate admissionDate, LocalDate dischargeDate) {
		this.admID = admID;
		this.ptID = ptID;
		this.name = name;
		this.gender = gender;
		this.ward = ward;
		this.admissionDate = admissionDate;
		this.dischargeDate = dischargeDate;
	}
	
	public void getData() {
		System.out.println(this.admID + "\t" + this.ptID + "\t" + this.name + "\t" + this.gender + "\t" + this.ward + "\t" + this.admissionDate + "\t" + this.dischargeDate);
	}
	
	public static void addRecord(int ptID, LocalDate admissionDate, String ward) {
		String query = "INSERT INTO Admissions (PatientID, AdmissionDate, Ward) VALUES (?, ?, ?)";
		try(Connection conn = DBConnection.getConnection()){
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setInt(1, ptID);
			statement.setDate(2,  Date.valueOf(admissionDate));
			statement.setString(3,  ward);
			
			statement.execute();
			System.out.println("Patient admitted");
		} catch(SQLException e) {
			System.out.println("Error while connecting to database");
		}
	}
	
	private static LocalDate getAdmissionDate(int admID) {
		String query = "SELECT DischargeDate FROM Admissions WHERE AdmissionID = ?";
		LocalDate date = null;
		try(Connection conn = DBConnection.getConnection()){
			PreparedStatement statement = conn.prepareStatement(query);
        	statement.setInt(1, admID);
        	ResultSet result = statement.executeQuery();
        	date = result.getDate(1).toLocalDate();
		} catch(SQLException e) {
			System.out.println("Error while connecting to database");
		}
		return date;
	}
	
	public static void updateRecord(int admID, LocalDate dischargeDate) {
		String query = "UPDATE Patients SET DischargeDate = ? WHERE AdmissionID = ?";
		try(Connection conn = DBConnection.getConnection()){
	    	LocalDate admissionDate = getAdmissionDate(admID);
	    	if (admissionDate==null || dischargeDate.isBefore(admissionDate)) {
	    	    throw new IllegalArgumentException("Invalid: Discharge date is before admission date.");
	    	}
	    	PreparedStatement statement = conn.prepareStatement(query); 
	    	statement.setDate(1,  Date.valueOf(dischargeDate));
	        statement.executeUpdate();
	        System.out.println("Record updated successfully");
	    } catch(IllegalArgumentException e) {
	    	System.out.println(e.getLocalizedMessage());
	    } catch(SQLException e) {
	    	System.out.println("Error while connecting to database");
	    }
	}
	
	public static List<Admissions> getAllRecords(){
		List<Admissions> records = new ArrayList<>();
		try(Connection conn = DBConnection.getConnection()){
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(getRecordsQuery);
			while(results.next()) {
				records.add(new Admissions(
						results.getInt(1), 
						results.getInt(2), 
						results.getString(6), 
						Patient.Gender.valueOf(results.getString(7)),
						results.getString(5),
						results.getDate(3).toLocalDate(),
						results.getDate(4).toLocalDate()
				));
			}
		} catch(SQLException e) {
			System.out.println("Error while connecting to database");
		}
		return records;
	}
	

	
	public static List<Admissions> getRecordsByDate(LocalDate date){
		List<Admissions> records = new ArrayList<>();
		StringBuilder query = new StringBuilder(getRecordsQuery);
		if(date==null) {
			query.append("WHERE A.DischargeDate IS NULL");
		}
		else {
			query.append("WHERE a.AdmissionDate >= ?");
		}
		try(Connection conn = DBConnection.getConnection()){
			PreparedStatement statement = conn.prepareStatement(query.toString());
			if(date!=null) {
				statement.setDate(1,  Date.valueOf(date));
			}
			ResultSet results = statement.executeQuery();
			while(results.next()) {
				records.add(new Admissions(
						results.getInt(1), 
						results.getInt(2), 
						results.getString(6), 
						Patient.Gender.valueOf(results.getString(7)),
						results.getString(5),
						results.getDate(3).toLocalDate(),
						results.getDate(4).toLocalDate()
				));
			}
		} catch(SQLException e) {
			System.out.println("Error while connecting to database");
		}
		return records;
	}
}
