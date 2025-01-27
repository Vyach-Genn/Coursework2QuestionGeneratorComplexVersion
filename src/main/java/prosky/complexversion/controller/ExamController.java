package prosky.complexversion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prosky.complexversion.domain.Question;
import prosky.complexversion.service.ExaminerService;

import java.util.Collection;

@RestController
@RequestMapping("/exam/get")

public class ExamController {

    private final ExaminerService examService;

    @Autowired
    public ExamController(ExaminerService examService) {
        this.examService = examService;
    }

    @GetMapping("/java/{amount}")
    public Collection<Question> getJavaQuestions(@PathVariable int amount) {
        return examService.getJavaQuestions(amount);
    }

    @GetMapping("/math/{amount}")
    public Collection<Question> getMathQuestions(@PathVariable int amount) {
        return examService.getMathQuestions(amount);
    }
}
