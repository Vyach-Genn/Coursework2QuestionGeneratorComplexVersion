package proskycomplexversion.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class Question {

    private String question;

    private String answer;


    public boolean isEmpty() {
        return false;
    }
}



