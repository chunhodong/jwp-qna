package qna.domain;

import qna.CannotDeleteException;
import qna.NotFoundException;
import qna.UnAuthorizedException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Answer extends BaseEntity {
    private static final String ONLY_DELETED_STATE = "삭제된 질문만 허용합니다";
    private static final String NONE_AUTH_DELETE = "답변을 삭제할 권한이 없습니다.";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;
    @Lob
    private String contents;
    private boolean deleted = false;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    private void validateOwner(User writer) throws CannotDeleteException {
        if (!isOwner(writer)) {
            throw new CannotDeleteException(NONE_AUTH_DELETE);
        }
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public DeleteHistory getDeleteHistory() {
        if (!this.deleted) {
            throw new IllegalStateException(ONLY_DELETED_STATE);
        }
        return new DeleteHistory(ContentType.ANSWER, id, writer, LocalDateTime.now());
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(User writer) {
        if (this.writer.equals(writer)) {
            return true;
        }
        return false;
    }

    public void delete(User writer) throws CannotDeleteException {
        validateOwner(writer);
        this.deleted = true;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writerId=" + writer.getId() +
                ", questionId=" + question.getId() +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }

}
