package proskycomplexversion.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proskycomplexversion.domain.Question;

import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService javaQuestionService;
    private final QuestionService mathQuestionService;

    public ExaminerServiceImpl(
            @Qualifier("javaQuestionService") QuestionService javaQuestionService,
            @Qualifier("mathQuestionService") QuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Set<Question> getMathQuestions(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество вопросов должно быть больше 0");
        }
        return mathQuestionService.getRandomQuestions(amount);
    }

    @Override
    public Set<Question> getJavaQuestions(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество вопросов должно быть больше 0");
        }
        return javaQuestionService.getRandomQuestions(amount);
    }
}
