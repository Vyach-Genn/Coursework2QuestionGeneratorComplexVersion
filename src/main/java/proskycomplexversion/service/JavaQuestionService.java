package proskycomplexversion.service;

import org.springframework.stereotype.Service;
import proskycomplexversion.domain.Question;
import proskycomplexversion.exception.TooManyQuestionsRequestedException;
import proskycomplexversion.repository.QuestionRepository;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service("javaQuestionService")
public class JavaQuestionService implements QuestionService {

    private final QuestionRepository questionRepository;

    public JavaQuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        questionRepository.add(new Question("Что такое переменная", "Именованная область памяти"), "java");
        questionRepository.add(new Question("Что такое Java", "Язык программирования"), "java");
        questionRepository.add(new Question("Что такое метод", "Блок кода, который выполняет определённую задачу"), "java");
        questionRepository.add(new Question("Что такое полиморфизм", "Одно из основных свойств ООП"), "java");
        questionRepository.add(new Question("Что такое массив", "Структура данных"), "java");
        questionRepository.add(new Question("Что такое объект", "Экземпляр класса"), "java");
        questionRepository.add(new Question("Как работает Set", "Хранит уникальные элементы"), "java");
    }


    @Override
    public void addQuestionAndAnswer(String question, String answer) {
        if (question != null && answer != null) {
            questionRepository.add(new Question(question, answer), "java");
        } else {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }

    }

    @Override
    public Set<Question> getQuestionAndAnswer() {
        return questionRepository.getAll("java");
    }

    @Override
    public void removeQuestionAndAnswer(String question, String answer) {
        if (question == null || answer == null) {
            throw new IllegalArgumentException("Вопрос и ответ не могут быть null");
        }
        Question questionToRemove = new Question(question, answer);
        if (!questionRepository.getAll("java").contains(questionToRemove)) {
            throw new NoSuchElementException("Вопрос с указанным текстом и ответом не найден");
        }
        questionRepository.remove(questionToRemove, "java");
    }

    @Override
    public Set<Question> getRandomQuestions(int amount) {
        Set<Question> allQuestions = questionRepository.getAll("java");
        if (amount > allQuestions.size()) {
            throw new TooManyQuestionsRequestedException("Количество вопросов не может быть больше общего числа вопросов");
        }
        return allQuestions.stream()
                .limit(amount)
                .collect(Collectors.toSet());
    }
}
