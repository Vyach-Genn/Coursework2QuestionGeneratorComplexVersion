package prosky.complexversion.repository;

import org.springframework.stereotype.Repository;
import prosky.complexversion.domain.Question;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private final Set<Question> mathQuestions = new HashSet<>();
    private final Set<Question> javaQuestions = new HashSet<>();
    private static final String INVALID_TYPE = "Неверный тип вопроса";

    @Override
    public Question add(Question question, QuestionType type) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Вопрос не может быть null или пустым");
        }
        switch (type) {
            case MATH -> mathQuestions.add(question);
            case JAVA -> javaQuestions.add(question);
            default -> throw new IllegalArgumentException(INVALID_TYPE);
        }
        return question;
    }

    @Override
    public Question remove(Question question, QuestionType type) {
        switch (type) {
            case MATH -> mathQuestions.remove(question);
            case JAVA -> javaQuestions.remove(question);
            default -> throw new IllegalArgumentException(INVALID_TYPE);
        }
        return question;
    }

    @Override
    public Collection<Question> getAll(QuestionType type) {
        return switch (type) {
            case MATH -> new HashSet<>(mathQuestions);
            case JAVA -> new HashSet<>(javaQuestions);
        };
    }
}

