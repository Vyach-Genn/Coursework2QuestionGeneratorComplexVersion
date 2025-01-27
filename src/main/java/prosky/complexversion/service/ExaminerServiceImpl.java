package prosky.complexversion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import prosky.complexversion.domain.Question;
import prosky.complexversion.exception.TooManyQuestionsRequestedException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    private final QuestionService javaQuestionService;
    private final QuestionService mathQuestionService;

    @Autowired
    public ExaminerServiceImpl(
            @Qualifier("javaQuestionService") QuestionService javaQuestionService,
            @Qualifier("mathQuestionService") QuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getJavaQuestions(int amount) {
        Collection<Question> allQuestionsJava = javaQuestionService.getAll();
        validateAmount(amount, allQuestionsJava.size());
        Set<Question> randomJavaQuestions = new HashSet<>();
        while (randomJavaQuestions.size() < amount) {
            Question randomQuestion = javaQuestionService.getRandomQuestions();
            randomJavaQuestions.add(randomQuestion);
        }
        return randomJavaQuestions;
    }

    @Override
    public Collection<Question> getMathQuestions(int amount) {
        Collection<Question> allQuestionsMath = mathQuestionService.getAll();
        validateAmount(amount, allQuestionsMath.size());
        Set<Question> randomMathQuestions = new HashSet<>();
        while (randomMathQuestions.size() < amount) {
            Question randomQuestions = mathQuestionService.getRandomQuestions();
            randomMathQuestions.add(randomQuestions);
        }
        return randomMathQuestions;
    }

    public void validateAmount(int amount, int maxSize) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Количество вопросов не может быть отрицательным или нулевым");
        }
        if (amount > maxSize) {
            throw new TooManyQuestionsRequestedException("Количество вопросов не может быть больше общего числа вопросов");
        }
    }
}
