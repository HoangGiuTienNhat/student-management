package vn.edu.hcmut.cse.adse.lab.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String id) {
        super("Student with id '" + id + "' was not found");
    }
}

