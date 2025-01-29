package prosky.complexversion.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prosky.complexversion.domain.Question;
import prosky.complexversion.service.QuestionService;

import java.util.Collection;

@RestController
@RequestMapping("/exam/math")
public class MathController {

    private final QuestionService service;

    public MathController(@Qualifier("mathQuestionService") QuestionService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public Question addQuestion(@RequestParam("question") String question,
                                @RequestParam("answer") String answer) {
        return service.add(question, answer);
    }

    @GetMapping("/add/object")
    public Question addQuestionObject(@RequestParam("question") String question,
                                      @RequestParam("answer") String answer) {
        return service.add(new Question(question, answer));
    }

    @GetMapping("/remove")
    public Question removeQuestion(@RequestParam("question") String question,
                                   @RequestParam("answer") String answer) {
        Question questionToRemove = new Question(question, answer);
        return service.remove(questionToRemove);
    }

    @GetMapping
    public Collection<Question> getQuestion() {
        return service.getAll();
    }
}
