package patientAdmission;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import patientAdmission.models.Admissions;
import patientAdmission.models.Patient;
import patientAdmission.models.Patient.Gender;

public class PatientAdmissionManager {
	Scanner sc = new Scanner(System.in);
	
	public void addNewPatient() throws IllegalArgumentException, DateTimeParseException {
		LocalDate dob = null;
		String name, input1, input2, num;
		Gender gender = null;
		System.out.print("Enter patient name: ");
		name = sc.nextLine();
		System.out.print("Enter date of birth (YYYY-MM-DD): ");
		input1 = sc.nextLine();
		dob = LocalDate.parse(input1);
		if (dob.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Invalid date of birth");
		}
		System.out.print("Enter patient contact no. (10 digits): ");
		num = sc.next();
		if(!num.matches("\\d{10}")) {
			throw new IllegalArgumentException("Invalid contact number");
		}
		System.out.print("Enter patient gender (Male/Female/Other): ");
		input2 = sc.next().trim();
		try {
            gender = Gender.valueOf(input2);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid gender. Please enter Male, Female, or Other.");
        }
		Patient.addPatient(name, num, dob, gender);
	}
	
	public void getPatientsRecords() {
		List<Patient> records = Patient.getAllRecords();
		System.out.println("Patient ID\tName\tContact No.\tDate of Birth\tGender");
		for(int i=0; i<records.size(); i++) {
			records.get(i).getData();
		}
	}
	
	public void updatePatientRecord() throws IllegalArgumentException {
		int ID;
		String name = null, num = null, input;
		System.out.print("Enter patient ID: ");
		ID = sc.nextInt();
		System.out.print("Want to update name? (YES/NO): ");
		input = sc.next();
		if(input.equals("YES")){
			System.out.print("Enter patient name: ");
			name = sc.nextLine();
		}
		System.out.print("Want to update contact number? (YES/NO): ");
		input = sc.next();
		if(input.equals("YES")){
			System.out.print("Enter patient contact no. (10 digits): ");
			num = sc.next();
			if(!num.matches("\\d{10}")) {
				throw new IllegalArgumentException("Invalid contact number");
			}
		}
		Patient.updatePatient(ID, name, num);
	}
	
	public void deletePatientRecord() {
		System.out.print("Enter patient ID: ");
		int ID = sc.nextInt();
		Patient.deleteById(ID);
	}
	
	public void getPatientsRecordsByName() {
		System.out.print("Enter patient name: ");
		String name = sc.nextLine();
		List<Patient> records = Patient.getRecordsByName(name);
		System.out.println("Patient ID\tName\tContact No.\tDate of Birth\tGender");
		for(int i=0; i<records.size(); i++) {
			records.get(i).getData();
		}
	}
	
	public void addAdmissionRecord() throws IllegalArgumentException, DateTimeParseException {
		int patientID;
		String ward, input;
		LocalDate admission;
		System.out.print("Enter patient ID: ");
		patientID = sc.nextInt();
		System.out.print("Enter ward: ");
		ward = sc.nextLine();
		System.out.print("Enter admission date (YYYY-MM-DD): ");
		input = sc.nextLine();
		admission = LocalDate.parse(input);
		if (admission.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Invalid date");
		}
		Admissions.addRecord(patientID, admission, ward);
	}
	
	public void updateAdmissionRecord() throws IllegalArgumentException, DateTimeParseException {
		int patientID;
		String input;
		LocalDate discharge;
		System.out.print("Enter patient ID: ");
		patientID = sc.nextInt();
		System.out.print("Enter discharge date (YYYY-MM-DD): ");
		input = sc.nextLine();
		discharge = LocalDate.parse(input);
		if (discharge.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Invalid date");
		}
		Admissions.updateRecord(patientID, discharge);
	}
	
	public void getAllAdmissionRecords() {
		List<Admissions> records = Admissions.getAllRecords();
		System.out.println("Admission ID\tPatient ID\tName\tGender\tWard\tAdmission Date\tDischarge Date");
		for(int i=0; i<records.size(); i++) {
			records.get(i).getData();
		}
	}
	
	public void getAdmissionRecordsByDate() throws IllegalArgumentException, DateTimeParseException {
		String input;
		List<Admissions> records;
		System.out.print("Get records of currently admitted patients? (YES/NO): ");
		input = sc.next();
		if(input.equals("YES")){
			records = Admissions.getRecordsByDate(null);
		}
		else {
			System.out.println("Enter admission date (YYYY-MM-DD): ");
			String input2 = sc.nextLine();
			LocalDate admission = LocalDate.parse(input2);
			if (admission.isAfter(LocalDate.now())) {
				throw new IllegalArgumentException("Invalid date");
			}
			records = Admissions.getRecordsByDate(admission);
		}
		System.out.println("Admission ID\tPatient ID\tName\tGender\tWard\tAdmission Date\tDischarge Date");
		for(int i=0; i<records.size(); i++) {
			records.get(i).getData();
		}
	}
}