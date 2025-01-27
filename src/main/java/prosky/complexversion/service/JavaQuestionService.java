package prosky.complexversion.service;

import org.springframework.stereotype.Service;
import prosky.complexversion.domain.Question;
import prosky.complexversion.repository.QuestionRepository;

import java.util.*;

import static prosky.complexversion.repository.QuestionType.JAVA;

@Service
public class JavaQuestionService implements QuestionService {

    private final Set<Question> usedQuestions = new HashSet<>();
    private final QuestionRepository questionRepository;

    public JavaQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public Question add(String question, String answer) {
        if ((question == null || answer == null) || (question.isEmpty() || answer.isEmpty())) {
            throw new IllegalArgumentException("Вопрос или ответ не могут быть null");
        }
        questionRepository.add(new Question(question, answer), JAVA);
        return new Question(question, answer);

    }

    @Override
    public Question add(Question question) {
        if (question.isEmpty()) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        questionRepository.add(question, JAVA);
        return question;
    }

    @Override
    public Question remove(Question question) {
        questionRepository.remove(question, JAVA);
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return questionRepository.getAll(JAVA);
    }

    @Override
    public Question getRandomQuestions() {
        if (questionRepository.getAll(JAVA).isEmpty()) {
            throw new IllegalStateException("Нет доступных вопросов");
        }
        List<Question> questionList = new ArrayList<>(questionRepository.getAll(JAVA));
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
}
