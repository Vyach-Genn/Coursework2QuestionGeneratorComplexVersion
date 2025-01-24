package proskycomplexversion.repository;

import org.springframework.stereotype.Repository;
import proskycomplexversion.domain.Question;

import java.util.HashSet;
import java.util.Set;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private final Set<Question> mathQuestions = new HashSet<>();
    private final Set<Question> javaQuestions = new HashSet<>();

    @Override
    public void add(Question question, String type) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Вопрос не может быть null или пустым");
        }
        switch (type) {
            case "math" -> mathQuestions.add(question);
            case "java" -> javaQuestions.add(question);
            default -> throw new IllegalArgumentException("Неверный тип вопроса");

        }
    }

    @Override
    public void remove(Question question, String type) {
        switch (type) {
            case "math" -> mathQuestions.remove(question);
            case "java" -> javaQuestions.remove(question);
            default -> throw new IllegalArgumentException("Неверный тип вопроса");
        }
    }

    @Override
    public Set<Question> getAll(String type) {
        return switch (type) {
            case "math" -> new HashSet<>(mathQuestions);
            case "java" -> new HashSet<>(javaQuestions);
            default -> throw new IllegalArgumentException("Неверный тип вопроса");
        };
    }
}

