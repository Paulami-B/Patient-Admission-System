package patientAdmission;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PatientAdmissionManager pm = new PatientAdmissionManager();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nPatient Admission System Menu: \n");
            System.out.println("1. Add New Patient");
            System.out.println("2. Update Patient Details");
            System.out.println("3. Get Patient Records");
            System.out.println("4. Delete Patient Record");
            System.out.println("5. Search Patients By Name");
            System.out.println("6. Add New Admission Record");
            System.out.println("7. Update Admission Details");
            System.out.println("8. Get All Admission Records");
            System.out.println("9. Get Admission Records By Date");
            System.out.println("0. Exit");
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
	            case 1:
	            	try {
	            		pm.addNewPatient();
	            	} catch(IllegalArgumentException e) {
	            		System.out.println(e.getLocalizedMessage());
	            	} catch(DateTimeParseException e) {
	            		e.printStackTrace();
	            	} catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            
	            case 2:
	            	try {
	            		pm.updatePatientRecord();
	            	} catch(IllegalArgumentException e) {
	            		System.out.println(e.getLocalizedMessage());
	            	} catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            	
	            case 3:
	            	pm.getPatientsRecords();
	            	break;
	            
	            case 4:
	            	pm.deletePatientRecord();
	            	break;
	            	
	            case 5:
	            	pm.getPatientsRecordsByName();
	            	break;
	            
	            case 6:
	            	try {
	            		pm.addAdmissionRecord();
	            	} catch(IllegalArgumentException e) {
	            		System.out.println(e.getLocalizedMessage());
	            	} catch(DateTimeParseException e) {
	            		e.printStackTrace();
	            	} catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            	
	            case 7:
	            	try {
	            		pm.updateAdmissionRecord();
	            	} catch(IllegalArgumentException e) {
	            		System.out.println(e.getLocalizedMessage());
	            	} catch(DateTimeParseException e) {
	            		e.printStackTrace();
	            	} catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            	
	            case 8:
	            	pm.getAllAdmissionRecords();
	            	break;
	            	
	            case 9:
	            	try {
	            		pm.getAdmissionRecordsByDate();
	            	} catch(IllegalArgumentException e) {
	            		System.out.println(e.getLocalizedMessage());
	            	} catch(DateTimeParseException e) {
	            		e.printStackTrace();
	            	} catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            
	            default:
	            	System.out.println("Invalid Choice");
            }
        } while (choice != 0);
        
        sc.close();
    }
}