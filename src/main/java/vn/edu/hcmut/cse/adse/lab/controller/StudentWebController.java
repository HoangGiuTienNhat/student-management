package vn.edu.hcmut.cse.adse.lab.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



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
        model.addAttribute("isEdit", false); 
        return "student-form";
    }

    @GetMapping("/{id}")
    public String viewStudentDetail(@PathVariable String id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student-detail";
    }

    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        model.addAttribute("isEdit", true); 
        return "student-form"; 
    }

    // Xử lý lưu: Nhận thêm biến isEdit từ HTML Form
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student student, 
                              @RequestParam(value = "isEdit", defaultValue = "false") boolean isEdit) {
        if (isEdit) {
            // Nếu là form sửa -> gọi hàm update
            studentService.update(student.getId(), student);
        } else {
            // Nếu là form thêm mới -> gọi hàm create với ID người dùng tự nhập
            studentService.create(student);
        }
        return "redirect:/students"; 
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentService.delete(id);
        return "redirect:/students"; 
    }
}
