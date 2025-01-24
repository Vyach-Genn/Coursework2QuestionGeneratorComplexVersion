package proskycomplexversion.repository;

import proskycomplexversion.domain.Question;

import java.util.Set;

public interface QuestionRepository {

    void add(Question question, String type);

    void remove(Question question, String type);

    Set<Question> getAll(String type);
}

