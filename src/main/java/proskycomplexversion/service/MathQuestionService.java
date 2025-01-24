package proskycomplexversion.service;

import org.springframework.stereotype.Service;
import proskycomplexversion.domain.Question;
import proskycomplexversion.exception.TooManyQuestionsRequestedException;
import proskycomplexversion.repository.QuestionRepository;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service("mathQuestionService")
public class MathQuestionService implements QuestionService {

    private final QuestionRepository questionRepository;

    public MathQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        questionRepository.add(new Question("Сколько будет 2 + 2?", "4"), "math");
        questionRepository.add(new Question("Сколько будет 100 - 57?", "43"), "math");
        questionRepository.add(new Question("Сколько будет 5 * 5?", "25"), "math");
        questionRepository.add(new Question("Сколько будет 7 * 8?", "56"), "math");
        questionRepository.add(new Question("Сколько будет 10 / 2?", "5"), "math");
        questionRepository.add(new Question("Сколько будет 2^10?", "1024"), "math");
        questionRepository.add(new Question("Сколько будет 3^2?", "9"), "math");
        questionRepository.add(new Question("Сколько будет квадратный корень из 16?", "4"), "math");
    }

    @Override
    public void addQuestionAndAnswer(String question, String answer) {
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        questionRepository.add(new Question(question, answer), "math");
    }

    @Override
    public Set<Question> getQuestionAndAnswer() {
        return questionRepository.getAll("math");
    }

    @Override
    public void removeQuestionAndAnswer(String question, String answer) {
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        Question questionToRemove = new Question(question, answer);
        if (!questionRepository.getAll("math").contains(questionToRemove)) {
            throw new NoSuchElementException("Вопрос с указанным примером и ответом не найден");
        }
        questionRepository.remove(questionToRemove, "math");
    }

    @Override
    public Set<Question> getRandomQuestions(int amount) {
        Set<Question> allQuestions = questionRepository.getAll("math");
        if (amount > allQuestions.size()) {
            throw new TooManyQuestionsRequestedException("Количество вопросов не может быть больше общего числа вопросов");
        }
        return allQuestions.stream()
                .limit(amount)
                .collect(Collectors.toSet());
    }
}
