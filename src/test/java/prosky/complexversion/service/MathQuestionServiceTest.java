package prosky.complexversion.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prosky.complexversion.domain.Question;
import prosky.complexversion.exception.DivisionByZeroException;
import prosky.complexversion.repository.QuestionRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static prosky.complexversion.repository.QuestionType.MATH;

@ExtendWith(MockitoExtension.class)
class MathQuestionServiceTest {

    @Mock
    private QuestionRepository mathRepository;

    @InjectMocks
    private MathQuestionService out;


    public static Stream<Arguments> provideParamsForTestQuestionAndAnswer() {
        return Stream.of(
                Arguments.of(new Question("Вопрос 1", "Ответ 1")),
                Arguments.of(new Question("Вопрос 2", "Ответ 2")),
                Arguments.of(new Question("Вопрос 3", "Ответ 3"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void addQuestion_ShouldAddQuestionSuccessfully(Question question) {
        when(mathRepository.add(question, MATH)).thenReturn(question);

        Question actual = out.add(question);

        assertEquals(question, actual);
        verify(mathRepository, times(1)).add(question, MATH);
    }

    @Test
    void add_ShouldAddQuestionSuccessfully() {
        Question expected = new Question("Вопрос 1", "Ответ 1");
        when(mathRepository.add(expected, MATH)).thenReturn(expected);

        Question actual = out.add(expected);

        assertEquals(expected, actual);
        verify(mathRepository, times(1)).add(expected, MATH);
    }

    @Test
    void addQuestion_ShouldThrowExceptionWhenBothQuestionAndAnswerAreNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            out.add(null, null);
        });
    }

    @Test
    void addQuestion_ShouldThrowDivisionByZeroException() {
        Question questionWithDivisionByZero = new Question("Сколько будет 10 / 0?", "????????");

        assertThrows(DivisionByZeroException.class, () -> out.add(questionWithDivisionByZero));
    }

    @Test
    void add_ShouldThrowDivisionByZeroException() {
        String questionWithDivisionByZero = "Сколько будет 10 / 0?";
        String answer = "???????";

        assertThrows(DivisionByZeroException.class, () -> out.add(questionWithDivisionByZero, answer));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void getQuestion_ShouldReturnCorrectQuestionAndAnswer(Question question) {
        Collection<Question> expected = Set.of(question);
        when(mathRepository.getAll(MATH)).thenReturn(expected);

        Collection<Question> actual = out.getAll();

        assertThat(actual).hasSize(expected.size())
                .containsExactlyInAnyOrderElementsOf(expected);
        verify(mathRepository, times(1)).getAll(MATH);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void removeQuestionAndAnswer_ShouldRemoveExistingQuestion(Question question) {
        when(mathRepository.remove(question, MATH)).thenReturn(question);

        Question actual = out.remove(question);

        assertEquals(question, actual);
        verify(mathRepository, times(1)).remove(question, MATH);

    }
}