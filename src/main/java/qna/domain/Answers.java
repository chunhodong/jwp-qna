package qna.domain;

import java.util.List;

public class Answers {
    private static final String NULL_MESSAGE = "없는 답변목록은 허용하지 않습니다";
    private final List<Answer> answers;

    public Answers(List<Answer> answers){
        validateNull(answers);
        this.answers = answers;
    }

    private void validateNull(List<Answer> answers){
        if(answers == null) throw new RuntimeException(NULL_MESSAGE);
    }

}
