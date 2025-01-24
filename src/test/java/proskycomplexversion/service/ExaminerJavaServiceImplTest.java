package proskycomplexversion.service;

import proskycomplexversion.domain.Question;
import proskycomplexversion.exception.TooManyQuestionsRequestedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerJavaServiceImplTest {
    @Mock
    private JavaQuestionService javaQuestionService;

    @InjectMocks
    private ExaminerServiceImpl out;

    @Test
    void javaQuestionService_ShouldReturnCorrectAmountAndQuestions() {
        Set<Question> questions = Set.of(
                new Question("Что такое переменная", "Именованная область памяти"),
                new Question("Что такое Java", "Язык программирования"),
                new Question("Что такое объект", "Экземпляр класса"));
        when(javaQuestionService.getRandomQuestions(3)).thenReturn(questions);

        Set<Question> actual = out.getJavaQuestions(3);

        assertThat(actual).hasSize(3)
                .containsAll(questions);
        verify(javaQuestionService, times(1)).getRandomQuestions(3);
    }

    @Test
    void javaQuestionService_ShouldExceptionsWhenQuestionNullAndAnswerNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            out.getJavaQuestions(0);
        });
    }
    @Test
    void javaQuestionService_ShouldThrowException_WhenAmountExceedsAvailableQuestions() {
        when(javaQuestionService.getRandomQuestions(10))
                .thenThrow(new TooManyQuestionsRequestedException("Запрошено больше вопросов, чем есть в сервисе"));

        assertThrows(TooManyQuestionsRequestedException.class, () -> {
            out.getJavaQuestions(10);
        });
    }
}