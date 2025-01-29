package prosky.complexversion.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import prosky.complexversion.domain.Question;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static prosky.complexversion.repository.QuestionType.JAVA;
import static prosky.complexversion.repository.QuestionType.MATH;

class QuestionRepositoryImplTest {

    private final QuestionRepository out = new QuestionRepositoryImpl();

    public static Stream<Arguments> provideParamsForTestQuestionAndAnswer() {
        return Stream.of(
                Arguments.of(new Question("Что такое переменная", "Именованная область памяти"), JAVA),
                Arguments.of(new Question("Что такое Java", "Язык программирования"), JAVA),
                Arguments.of(new Question("Сколько будет 3^2?", "9"), MATH),
                Arguments.of(new Question("Сколько будет 5 * 5?", "25"), MATH));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void add_ShouldAddQuestionToRepositoryByTypeMath(Question question) {
        out.add(question, MATH);

        Collection<Question> actual = out.getAll(MATH);

        assertThat(actual).containsExactly(question);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void add_ShouldAddQuestionToRepositoryByTypeJava(Question question) {
        out.add(question, JAVA);

        Collection<Question> actual = out.getAll(JAVA);

        assertThat(actual).containsExactly(question);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void remove_ShouldRemoveQuestionFromRepositoryByTypeMath(Question question) {
        out.add(question, MATH);

        out.remove(question, MATH);
        Collection<Question> actual = out.getAll(MATH);

        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void remove_ShouldRemoveQuestionFromRepositoryByTypeJava(Question question) {
        out.add(question, JAVA);

        out.remove(question, JAVA);
        Collection<Question> actual = out.getAll(JAVA);

        assertThat(actual).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void getAll_ShouldReturnAllQuestionsByTypeMath(Question question) {
        out.add(question, MATH);

        Collection<Question> actual = out.getAll(MATH);

        assertThat(actual).contains(question);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void getAll_ShouldReturnAllQuestionsByTypeJava(Question question) {
        out.add(question, JAVA);

        Collection<Question> actual = out.getAll(JAVA);

        assertThat(actual).contains(question);
    }

    @Test
    void getAll_ShouldReturnOnlyMathQuestions() {
        Question mathQuestion1 = new Question("Сколько будет 5 * 5?", "25");
        Question mathQuestion2 = new Question("Сколько будет 2^10?", "1024");
        Question javaQuestion = new Question("Что такое Java?", "Язык программирования");

        out.add(mathQuestion1, MATH);
        out.add(mathQuestion2, MATH);
        out.add(javaQuestion, JAVA);

        Collection<Question> mathQuestions = out.getAll(MATH);

        assertThat(mathQuestions).containsExactlyInAnyOrder(mathQuestion1, mathQuestion2);
    }

    @Test
    void getAll_ShouldReturnOnlyJavaQuestions() {
        Question javaQuestion1 = new Question("Что такое Java?", "Язык программирования");
        Question javaQuestion2 = new Question("Что такое JVM?", "Виртуальная машина Java");
        Question mathQuestion = new Question("Сколько будет 5 * 5?", "25");

        out.add(javaQuestion1, JAVA);
        out.add(javaQuestion2, JAVA);
        out.add(mathQuestion, MATH);

        Collection<Question> javaQuestions = out.getAll(JAVA);

        assertThat(javaQuestions).containsExactlyInAnyOrder(javaQuestion1, javaQuestion2);
    }
}