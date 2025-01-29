package prosky.complexversion.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prosky.complexversion.domain.Question;
import prosky.complexversion.exception.TooManyQuestionsRequestedException;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerMachServiceImplTest {

    @Mock
    private MathQuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl out;


    @Test
    void mathQuestionService_ShouldReturnCorrectAmountAndQuestions() {
        when(mathQuestionService.getAll()).thenReturn(Set.of(
                new Question("Вопрос 1", "Ответ 1"),
                new Question("Вопрос 2", "Ответ 2"),
                new Question("Вопрос 3", "Ответ 3")));
        when(mathQuestionService.getRandomQuestions()).thenReturn(
                new Question("Вопрос 1", "Ответ 1"),
                new Question("Вопрос 2", "Ответ 2"));

        Collection<Question> questions = out.getJavaQuestions(2);

        assertEquals(2, questions.size());
        verify(mathQuestionService, times(2)).getRandomQuestions();
    }

    @Test
    void getQuestions_ShouldThrowException_WhenAmountIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> out.getMathQuestions(-1));
        assertThrows(IllegalArgumentException.class, () -> out.getMathQuestions(0));
        assertThrows(TooManyQuestionsRequestedException.class, () -> out.getMathQuestions(4));
    }

    @ParameterizedTest
    @CsvSource({"0, 5", "-1, 10"})
    void validateAmount_ShouldThrowIllegalArgumentException_WhenAmountIsZeroOrNegative(int amount, int maxSize) {
        assertThrows(IllegalArgumentException.class, () -> out.validateAmount(amount, maxSize));
    }

    @ParameterizedTest
    @CsvSource({"6, 5", "11, 10"})
    void validateAmount_ShouldThrowTooManyQuestionsRequestedException_WhenAmountExceedsMaxSize(int amount, int maxSize) {
        assertThrows(TooManyQuestionsRequestedException.class, () -> out.validateAmount(amount, maxSize));
    }

    @ParameterizedTest
    @CsvSource({"1, 5", "3, 10", "10, 10"})
    void validateAmount_ShouldNotThrowException_WhenAmountIsValid(int amount, int maxSize) {
        assertDoesNotThrow(() -> out.validateAmount(amount, maxSize));
    }
}
