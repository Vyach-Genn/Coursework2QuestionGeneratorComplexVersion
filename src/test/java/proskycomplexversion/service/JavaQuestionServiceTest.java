package proskycomplexversion.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import proskycomplexversion.domain.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import proskycomplexversion.exception.TooManyQuestionsRequestedException;
import proskycomplexversion.repository.QuestionRepository;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    @Mock
    private QuestionRepository javaRepository;

    @InjectMocks
    private JavaQuestionService out;

    @BeforeEach
    void setUp() {
        Mockito.reset(javaRepository);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void addQuestionAndAnswer_ShouldAddQuestionToCollection(String questionText, String answerText) {
        Question expected = new Question(questionText, answerText);
        doNothing().when(javaRepository).add(expected, "java");

        out.addQuestionAndAnswer(questionText, answerText);

        verify(javaRepository, times(1)).add(expected, "java");
    }

    @Test
    void addQuestionAndAnswer_ShouldExceptionsWhenQuestionNullAndAnswerNull() {
        assertThrows(IllegalArgumentException.class, () ->
            out.addQuestionAndAnswer(null, null));
    }

    @Test
    void getQuestionAndAnswer_ShouldReturnCorrectQuestionAndAnswer() {
        Set<Question> expected = Set.of(
                new Question("Что такое Java", "Язык программирования"),
                new Question("Что такое объект", "Экземпляр класса"));
        when(javaRepository.getAll("java")).thenReturn(expected);

        Set<Question> actual = out.getQuestionAndAnswer();

        assertThat(actual).hasSize(expected.size())
                .containsExactlyInAnyOrderElementsOf(expected);
        verify(javaRepository, times(1)).getAll("java");
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void removeQuestionAndAnswer_ShouldRemoveExistingQuestion(String questionText, String answerText) {
        Question expected = new Question(questionText, answerText);
        when(javaRepository.getAll("java")).thenReturn(Set.of(expected));

        out.removeQuestionAndAnswer(questionText, answerText);

        verify(javaRepository, times(1)).remove(expected, "java");
    }

    @Test
    void removeQuestionAndAnswer_ShouldExceptionsWhenQuestionNullAndAnswerNull() {
        assertThrows(IllegalArgumentException.class, () ->
            out.removeQuestionAndAnswer(null, null));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void removeQuestionAndAnswer_ShouldExceptionsWhenQuestionAndAnswerContains(String questionText, String answerText) {
        when(javaRepository.getAll("java")).thenReturn(Set.of());

        assertThrows(NoSuchElementException.class, () ->
                out.removeQuestionAndAnswer(questionText, answerText));

        verify(javaRepository, times(1)).getAll("java");
    }

    @Test
    void getRandomQuestions_ShouldReturnCorrectAmountOfQuestions() {
        Set<Question> expected = Set.of(
        new Question("Что такое переменная", "Именованная область памяти"),
        new Question("Что такое Java", "Язык программирования"),
        new Question("Что такое объект", "Экземпляр класса"));
        when(javaRepository.getAll("java")).thenReturn(expected);

        Set<Question> actual = out.getRandomQuestions(2);

        assertThat(actual).hasSize(2).isSubsetOf(expected);
        verify(javaRepository, times(1)).getAll("java");
    }

    @Test
    void getRandomQuestions_throwsExceptionWhenRequestedQuestionsExceedAvailableCount() {
        Set<Question> questions = Set.of(
                new Question("Что такое Java", "Язык программирования"),
                new Question("Что такое объект", "Экземпляр класса"));

        when(javaRepository.getAll("java")).thenReturn(questions);

        assertThrows(TooManyQuestionsRequestedException.class, () ->
                out.getRandomQuestions(3));
    }

    public static Stream<Arguments> provideParamsForTestQuestionAndAnswer() {
        return Stream.of(
                Arguments.of("Что такое переменная", "Именованная область памяти"),
                Arguments.of("Что такое Java", "Язык программирования"),
                Arguments.of("Что такое объект", "Экземпляр класса")

        );
    }

}