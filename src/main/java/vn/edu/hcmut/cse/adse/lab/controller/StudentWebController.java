package vn.edu.hcmut.cse.adse.lab.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentWebController {

    private final StudentService studentService;

    public StudentWebController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudentsPage(@RequestParam(required = false) String keyword, Model model) {
        List<Student> students = (keyword != null && !keyword.trim().isEmpty())
                ? studentService.searchByName(keyword.trim())
                : studentService.getAll();

        model.addAttribute("dsSinhVien", students);
        model.addAttribute("keyword", keyword);
        return "students";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @GetMapping("/{id}")
    public String viewStudentDetail(@PathVariable UUID id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student-detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable UUID id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student-form";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student) {
        if (student.getId() == null) {
            studentService.create(student);
        } else {
            studentService.update(student.getId(), student);
        }
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable UUID id) {
        studentService.delete(id);
        return "redirect:/students";
    }
}
