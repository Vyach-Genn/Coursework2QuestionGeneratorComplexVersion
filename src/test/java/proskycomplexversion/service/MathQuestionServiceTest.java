package proskycomplexversion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import proskycomplexversion.domain.Question;
import proskycomplexversion.exception.TooManyQuestionsRequestedException;
import proskycomplexversion.repository.QuestionRepository;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {

    @Mock
    private QuestionRepository mathRepository;

    @InjectMocks
    private MathQuestionService out;

    @BeforeEach
    void setUp() {
        Mockito.reset(mathRepository);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void addQuestionAndAnswer_ShouldAddQuestionSuccessfully(String questionMath, String answerMath) {
        Question expected = new Question(questionMath, answerMath);
        doNothing().when(mathRepository).add(expected, "math");

        out.addQuestionAndAnswer(questionMath, answerMath);

        verify(mathRepository, times(1)).add(expected, "math");
    }

    @Test
    void addQuestionAndAnswer_ShouldThrowExceptionWhenBothQuestionAndAnswerAreNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            out.addQuestionAndAnswer(null, null);
        });
    }

    @Test
    void getQuestionAndAnswer_ShouldReturnCorrectQuestionAndAnswer() {
        Set<Question> expected = Set.of(
                new Question("Сколько будет 5 * 5?", "25"),
                new Question("Сколько будет 2^10?", "1024"));
        when(mathRepository.getAll("math")).thenReturn(expected);

        Set<Question> actual = out.getQuestionAndAnswer();

        assertThat(actual).hasSize(expected.size())
                .containsExactlyInAnyOrderElementsOf(expected);
        verify(mathRepository, times(1)).getAll("math");
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void removeQuestionAndAnswer_ShouldRemoveExistingQuestion(String questionMath, String answerMath) {
        Question expected = new Question(questionMath, answerMath);
        when(mathRepository.getAll("math")).thenReturn(Set.of(expected));
        doNothing().when(mathRepository).remove(expected, "math");

        out.removeQuestionAndAnswer(questionMath, answerMath);

        verify(mathRepository, times(1)).remove(expected,"math");

    }

    @Test
    void removeQuestionAndAnswer_ShouldExceptionsWhenQuestionNullAndAnswerNull() {
        assertThrows(IllegalArgumentException.class, () ->
                out.removeQuestionAndAnswer(null, null));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void removeQuestionAndAnswer_ShouldExceptionsWhenQuestionAndAnswerContains(String questionMath, String answerMath) {
        when(mathRepository.getAll("math")).thenReturn(Set.of());

        assertThrows(NoSuchElementException.class, () ->
                out.removeQuestionAndAnswer(questionMath, answerMath));

        verify(mathRepository, times(1)).getAll("math");
    }

    @Test
    void getRandomQuestions_ShouldReturnCorrectAmountOfQuestions() {
        Set<Question> questions = Set.of(
                new Question("Сколько будет 5 * 5?", "25"),
                new Question("Сколько будет 2^10?", "1024"),
                new Question("Сколько будет квадратный корень из 16?", "4"));
        when(mathRepository.getAll("math")).thenReturn(questions);

        Set<Question> randomQuestions = out.getRandomQuestions(2);

        assertThat(randomQuestions).hasSize(2)
                .isSubsetOf(questions);
        verify(mathRepository, times(1)).getAll("math");
    }

    @Test
    void getRandomQuestions_ShouldThrowTooManyQuestionsRequestedExceptionWhenExceedingAvailable() {
        Set<Question> questions = Set.of(
                new Question("Сколько будет 5 * 5?", "25"),
                new Question("Сколько будет квадратный корень из 16?", "4"));

        when(mathRepository.getAll("math")).thenReturn(questions);

        assertThrows(TooManyQuestionsRequestedException.class, () ->
                out.getRandomQuestions(3));
    }

    public static Stream<Arguments> provideParamsForTestQuestionAndAnswer() {
        return Stream.of(
                Arguments.of("Сколько будет 2 + 2?", "4"),
                Arguments.of("Сколько будет 3^2?", "9"),
                Arguments.of("Сколько будет 5 * 5?", "25"));
    }
}