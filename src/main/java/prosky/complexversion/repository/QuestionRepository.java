package prosky.complexversion.repository;

import prosky.complexversion.domain.Question;

import java.util.Collection;

public interface QuestionRepository {

    Question add(Question question, QuestionType type);

    Question remove(Question question, QuestionType type);

    Collection<Question> getAll(QuestionType type);
}

