package proskycomplexversion.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proskycomplexversion.domain.Question;
import proskycomplexversion.service.ExaminerService;

import java.util.Set;

@RestController
@RequestMapping("/exam")
@AllArgsConstructor
public class ExamController {

    private final ExaminerService examService;

    @GetMapping("/java/{amount}")
    public Set<Question> getJavaQuestions(@PathVariable int amount) {
        return examService.getJavaQuestions(amount);
    }

    @GetMapping("/math/{amount}")
    public Set<Question> getMathQuestions(@PathVariable int amount) {
        return examService.getMathQuestions(amount);
    }
}
