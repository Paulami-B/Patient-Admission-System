# Patient Admission System

## Schemas

1. **Patient**

    ```sql
    CREATE TABLE Patients (
        PatientID INT PRIMARY KEY AUTO_INCREMENT,
        Name VARCHAR(60),
        DoB DATE,
        Gender ENUM('Male', 'Female', 'Other') NOT NULL,
        ContactNo CHAR(10)
    );
    ```

2. **Admissions**

    ```sql
    CREATE TABLE Admissions (
        AdmissionID INT PRIMARY KEY AUTO_INCREMENT,
        PatientID INT,
        AdmissionDate DATE NOT NULL,
        DischargeDate DATE,
        Ward VARCHAR(50),
        FOREIGN KEY (PatientID) REFERENCES Patients(PatientID)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );
    ```
## Functionalities

1. Add New Patient
2. Update Patient Details
3. Get Patient Records
4. Delete Patient Record
5. Search Patients By Name
6. Add New Admission Record
7. Update Admission Details
8. Get All Admission Records
9. Get Admission Records By Date