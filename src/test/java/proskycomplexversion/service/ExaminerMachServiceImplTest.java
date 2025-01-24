package proskycomplexversion.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import proskycomplexversion.domain.Question;
import proskycomplexversion.exception.TooManyQuestionsRequestedException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerMachServiceImplTest {

    @Mock
    private MathQuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl out;

    @Test
    void mathQuestionService_ShouldReturnCorrectAmountAndQuestions() {
        Set<Question> questions = Set.of(
                new Question("Сколько будет 5 * 5?", "25"),
                new Question("Сколько будет 2^10?", "1024"),
                new Question("Сколько будет квадратный корень из 16?", "4"));
        when(mathQuestionService.getRandomQuestions(3)).thenReturn(questions);

        Set<Question> actual = out.getMathQuestions(3);

        assertThat(actual).hasSize(3).containsAll(questions);
        verify(mathQuestionService, times(1)).getRandomQuestions(3);
    }

    @Test
    void mathQuestionService_ShouldExceptionsWhenQuestionNullAndAnswerNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            out.getMathQuestions(0);
        });
    }
    @Test
    void mathQuestionService_ShouldThrowException_WhenAmountExceedsAvailableQuestions() {
        when(mathQuestionService.getRandomQuestions(10))
                .thenThrow(new TooManyQuestionsRequestedException("Запрошено больше вопросов, чем есть в сервисе"));

        assertThrows(TooManyQuestionsRequestedException.class, () -> {
            out.getMathQuestions(10);
        });

        verify(mathQuestionService, times(1)).getRandomQuestions(10);
    }
}
