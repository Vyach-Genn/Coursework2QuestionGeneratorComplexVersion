package proskycomplexversion.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import proskycomplexversion.domain.Question;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryImplTest {

    private final QuestionRepository out = new QuestionRepositoryImpl();

    @BeforeEach
    void setUp() {
        out.getAll("math").clear();
        out.getAll("java").clear();
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void add_ShouldAddQuestionToRepositoryByType(String question, String answer, String type) {
        Question question1 = new Question(question, answer);

        out.add(question1, type);
        Set<Question> actual = out.getAll(type);

        assertThat(actual).containsExactly(question1);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void remove_ShouldRemoveQuestionFromRepositoryByType(String question, String answer, String type) {
        Question question1 = new Question(question, answer);
        out.add(question1, type);

        out.remove(question1, type);
        Set<Question> actual = out.getAll(type);

        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void getAll_ShouldReturnAllQuestionsByType(String question, String answer, String type) {
        Question question1 = new Question(question, answer);
        out.add(question1, type);

        Set<Question> actual = out.getAll(type);

        assertThat(actual).containsExactly(question1);
    }

    @Test
    void getAll_ShouldReturnOnlyMathQuestions() {
        Question mathQuestion1 = new Question("Сколько будет 5 * 5?", "25");
        Question mathQuestion2 = new Question("Сколько будет 2^10?", "1024");
        Question javaQuestion = new Question("Что такое Java?", "Язык программирования");

        out.add(mathQuestion1, "math");
        out.add(mathQuestion2, "math");
        out.add(javaQuestion, "java");

        Set<Question> mathQuestions = out.getAll("math");

        assertThat(mathQuestions).containsExactlyInAnyOrder(mathQuestion1, mathQuestion2);
    }

    @Test
    void getAll_ShouldReturnOnlyJavaQuestions() {
        Question javaQuestion1 = new Question("Что такое Java?", "Язык программирования");
        Question javaQuestion2 = new Question("Что такое JVM?", "Виртуальная машина Java");
        Question mathQuestion = new Question("Сколько будет 5 * 5?", "25");

        out.add(javaQuestion1, "java");
        out.add(javaQuestion2, "java");
        out.add(mathQuestion, "math");

        Set<Question> javaQuestions = out.getAll("java");

        assertThat(javaQuestions).containsExactlyInAnyOrder(javaQuestion1, javaQuestion2);
    }


    public static Stream<Arguments> provideParamsForTestQuestionAndAnswer() {
        return Stream.of(
                Arguments.of("Что такое переменная", "Именованная область памяти", "java"),
                Arguments.of("Что такое Java", "Язык программирования", "java"),
                Arguments.of("Сколько будет 3^2?", "9", "math"),
                Arguments.of("Сколько будет 5 * 5?", "25", "math")

        );
    }
}