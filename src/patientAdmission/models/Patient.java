package patientAdmission.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import patientAdmission.DBConnection;

public class Patient {
	public static enum Gender {
		Male,
		Female,
		Other
	}
	private int ID;
	private String name, phone;
	private LocalDate dob;
	private Gender gender;
	
	public Patient() {}
	
	public Patient(int ID, String name, String phone, LocalDate dob, Gender gender) {
		this.ID = ID;
		this.name = name;
		this.phone = phone;
		this.dob = dob;
		this.gender = gender;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void getData() {
		System.out.println(this.ID + "\t" + this.name + "\t" + this.phone + "\t" + this.dob + "\t" + this.gender);
	}
	
	public static void addPatient(String name, String phone, LocalDate dob, Gender gender) {
		String query = "INSERT INTO Patients (Name, ContactNo, DoB, Gender) VALUES (?, ?, ?, ?)";
		try(Connection conn = DBConnection.getConnection()){
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2,  phone);
			statement.setDate(3,  Date.valueOf(dob));
			statement.setString(4, gender.toString());
			
			statement.execute();
			System.out.println("New data added");
		} catch(SQLException e) {
			System.out.println("Error while connecting to database");
		}
	}
	
	public static List<Patient> getAllRecords(){
		String query = "SELECT * FROM Patients";
		List<Patient> records = new ArrayList<>();
		try(Connection conn = DBConnection.getConnection()){
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(query);
			while(results.next()) {
				records.add(new Patient(
						results.getInt(1), 
						results.getString(2), 
						results.getString(5), 
						results.getDate(3).toLocalDate(),
						Patient.Gender.valueOf(results.getString(4)) 
				));
			}
		} catch(SQLException e) {
			System.out.println("Error while connecting to database");
		}
		return records;
	}
	
	public static void updatePatient(int ID, String name, String phone) {
		StringBuilder query = new StringBuilder("UPDATE Patients SET ");
	    List<Object> params = new ArrayList<>();

	    if (name != null) {
	    	query.append("Name = ?, ");
	        params.add(name);
	    }

	    if (phone != null) {
	    	query.append("ContactNo = ?, ");
	        params.add(phone);
	    }
	    
	    query.setLength(query.length() - 2);
	    query.append(" WHERE PatientID = ?");
	    params.add(ID);
	    try(Connection conn = DBConnection.getConnection()){
	    	if (params.isEmpty()) {
	            throw new IllegalArgumentException("Nothing to update");
	        }
	    	PreparedStatement statement = conn.prepareStatement(query.toString()); 
	        for (int i = 0; i < params.size(); i++) {
	        	statement.setObject(i + 1, params.get(i));
	        }
	        statement.executeUpdate();
	        System.out.println("Patient data updated successfully");
	    } catch(IllegalArgumentException e) {
	    	System.out.println(e.getLocalizedMessage());
	    } catch(SQLException e) {
	    	System.out.println("Error while connecting to database");
	    }
	}
	
	public static void deleteById(int id) {
		String query = "DELETE FROM Patients WHERE PatientID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(query)) {
        	statement.setInt(1, id);
            int deleted = statement.executeUpdate();
            if(deleted>0) {
            	System.out.println(deleted + " records deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Patient> getRecordsByName(String name) {
        List<Patient> records = new ArrayList<>();
        String query = "SELECT * FROM Patients WHERE Name LIKE ?";
        try (Connection conn = DBConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
        	statement.setString(1, "%" + name + "%");
            ResultSet results = statement.executeQuery();
            while (results.next()) {
            	records.add(new Patient(
						results.getInt(1), 
						results.getString(2), 
						results.getString(5), 
						results.getDate(3).toLocalDate(),
						Patient.Gender.valueOf(results.getString(4)) 
				));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }
}
