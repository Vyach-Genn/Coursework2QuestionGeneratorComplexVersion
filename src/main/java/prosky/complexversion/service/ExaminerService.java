package prosky.complexversion.service;

import prosky.complexversion.domain.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getMathQuestions(int amount);

    Collection<Question> getJavaQuestions(int amount);
}
