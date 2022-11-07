package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswersTest {

    @Test
    @DisplayName("생성자파라미터에 null값이 들어오면 예외발생")
    void test_throw_exception_when_input_null_param_at_constructor() {
        assertThatThrownBy(() -> new Answers(null))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("없는 답변목록은 허용하지 않습니다");
    }
}
