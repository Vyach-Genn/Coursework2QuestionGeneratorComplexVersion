package proskycomplexversion.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proskycomplexversion.domain.Question;
import proskycomplexversion.service.QuestionService;

import java.util.Set;

@RestController
@RequestMapping("/exam/math")
public class MathQuestionController {

    private final QuestionService questionService;

    public MathQuestionController(@Qualifier("mathQuestionService") QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/add")
    public void addQuestionAndAnswer(@RequestParam("question") String question,
                                     @RequestParam("answer") String answer) {
        questionService.addQuestionAndAnswer(question, answer);
    }

    @GetMapping("/remove")
    public void removeQuestion(@RequestParam("question") String question,
                               @RequestParam("answer") String answer) {
        questionService.removeQuestionAndAnswer(question, answer);
    }

    @GetMapping
    public Set<Question> getQuestionAndAnswer() {
        return questionService.getQuestionAndAnswer();
    }
}
