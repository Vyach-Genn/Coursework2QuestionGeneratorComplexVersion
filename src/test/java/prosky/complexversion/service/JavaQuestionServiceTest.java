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
import prosky.complexversion.repository.QuestionRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static prosky.complexversion.repository.QuestionType.JAVA;

@ExtendWith(MockitoExtension.class)
class JavaQuestionServiceTest {

    @Mock
    private QuestionRepository javaRepository;

    @InjectMocks
    private JavaQuestionService out;

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
        when(javaRepository.add(question, JAVA)).thenReturn(question);

        Question actual = out.add(question);

        assertEquals(question, actual);
        verify(javaRepository, times(1)).add(question, JAVA);
    }

    @Test
    void add_ShouldAddQuestionSuccessfully() {
        Question expected = new Question("Что такое объект", "Экземпляр класса");
        when(javaRepository.add(expected, JAVA)).thenReturn(expected);

        Question actual = out.add(expected);

        assertEquals(expected, actual);
        verify(javaRepository, times(1)).add(expected, JAVA);
    }

    @Test
    void addQuestionAndAnswer_ShouldExceptionsWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            out.add(null, null);
        });
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void getQuestion_ShouldReturnCorrectAll(Question question) {
        Collection<Question> expected = List.of(question);
        when(javaRepository.getAll(JAVA)).thenReturn(expected);

        Collection<Question> actual = out.getAll();

        assertThat(actual).hasSize(1)
                .containsExactlyInAnyOrderElementsOf(expected);
        verify(javaRepository, times(1)).getAll(JAVA);
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTestQuestionAndAnswer")
    void removeQuestionAndAnswerShouldRemoveExistingQuestion(Question question) {
        when(javaRepository.remove(question, JAVA)).thenReturn(question);

        Question actual = out.remove(question);

        assertEquals(question, actual);
        verify(javaRepository, times(1)).remove(question, JAVA);
    }
}