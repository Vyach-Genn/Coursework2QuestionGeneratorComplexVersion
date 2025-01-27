package prosky.complexversion.service;

import org.springframework.stereotype.Service;
import prosky.complexversion.domain.Question;
import prosky.complexversion.exception.DivisionByZeroException;
import prosky.complexversion.repository.QuestionRepository;

import java.util.*;

import static prosky.complexversion.repository.QuestionType.MATH;

@Service("mathQuestionService")
public class MathQuestionService implements QuestionService {

    private final Set<Question> usedQuestions = new HashSet<>();
    private final QuestionRepository questionRepository;

    public MathQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question add(String question, String answer) {
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        if (containsDivisionByZero(question)) {
            throw new DivisionByZeroException("В вопросе есть деление на ноль");
        }
        questionRepository.add(new Question(question, answer), MATH);
        return new Question(question, answer);
    }

    @Override
    public Question add(Question question) {
        if (question.isEmpty()) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        if (containsDivisionByZero(question)) {
            throw new DivisionByZeroException("Присутсвует деление на ноль");
        }
        questionRepository.add(question, MATH);
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return questionRepository.getAll(MATH);
    }

    @Override
    public Question remove(Question question) {
        questionRepository.remove(question, MATH);
        return question;
    }


    @Override
    public Question getRandomQuestions() {
        if (questionRepository.getAll(MATH).isEmpty()) {
            throw new IllegalStateException("Нет доступных вопросов");
        }
        List<Question> questionList = new ArrayList<>(questionRepository.getAll(MATH));
        Collections.shuffle(questionList);

        for (Question question : questionList) {
            if (!usedQuestions.contains(question)) {
                usedQuestions.add(question);
                return question;
            }
        }
        usedQuestions.clear();
        return getRandomQuestions();
    }

    private boolean containsDivisionByZero(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        return text.matches(".*/\\s*0.*");
    }

    private boolean containsDivisionByZero(Question question) {
        if (question == null) {
            return false;
        }
        return containsDivisionByZero(question.getQuestion());
    }
}

