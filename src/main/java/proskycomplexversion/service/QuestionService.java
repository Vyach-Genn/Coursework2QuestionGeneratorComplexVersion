package proskycomplexversion.service;

import proskycomplexversion.domain.Question;

import java.util.Set;

public interface QuestionService {


    void addQuestionAndAnswer(String question, String answer);

    void removeQuestionAndAnswer(String question, String answer);

    Set<Question> getQuestionAndAnswer();

    Set<Question> getRandomQuestions(int amount);
}
