package vn.edu.hcmut.cse.adse.lab.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmut.cse.adse.lab.entity.Student;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    List<Student> findByNameContainingIgnoreCase(String keyword);
}
