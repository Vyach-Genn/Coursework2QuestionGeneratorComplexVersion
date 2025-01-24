package proskycomplexversion.service;

import proskycomplexversion.domain.Question;

import java.util.Set;

public interface ExaminerService {

    Set<Question> getMathQuestions(int amount);

    Set<Question> getJavaQuestions(int amount);
}
