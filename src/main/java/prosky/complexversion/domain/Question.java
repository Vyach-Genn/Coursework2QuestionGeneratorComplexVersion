package prosky.complexversion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@AllArgsConstructor
@Getter
@EqualsAndHashCode

public class Question {

    private String question;

    private String answer;

    @JsonIgnore
    public boolean isEmpty() {
        return question == null || question.trim().isEmpty() || answer == null || answer.trim().isEmpty();
    }
}



