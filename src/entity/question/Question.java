package entity.question;

import entity.quiz.QuestionSection;
import entity.quiz.QuestionType;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "questionType")
@NamedQueries({
    @NamedQuery(name = Question.FIND_BY_ID, query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = Question.FIND_ALL, query = "SELECT q FROM Question q")
})
public abstract class Question implements Serializable {
    
    public static final String FIND_BY_ID = "Question.FIND_BY_ID";
    public static final String FIND_ALL = "Question.FIND_ALL";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "questionText", nullable = false)
    private String questionText;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "questionType")
    private QuestionType questionType;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "questionSection")
    private QuestionSection questionSection;
    
    @Column(name = "correctAnswer")
    private Integer correctAnswer;
    
    @Column(name = "correctAnswerDenominator")
    private Integer correctAnswerDenominator;
    
    @Column(name = "correctAnswerText")
    private String correctAnswerText;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public QuestionSection getQuestionSection() {
        return questionSection;
    }

    public void setQuestionSection(QuestionSection questionSection) {
        this.questionSection = questionSection;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Integer getCorrectAnswerDenominator() {
        return correctAnswerDenominator;
    }

    public void setCorrectAnswerDenominator(Integer correctAnswerDenominator) {
        this.correctAnswerDenominator = correctAnswerDenominator;
    }

    public String getCorrectAnswerText() {
        return correctAnswerText;
    }

    public void setCorrectAnswerText(String correctAnswerText) {
        this.correctAnswerText = correctAnswerText;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.questionText);
        hash = 11 * hash + Objects.hashCode(this.questionType);
        hash = 11 * hash + Objects.hashCode(this.questionSection);
        hash = 11 * hash + Objects.hashCode(this.correctAnswer);
        hash = 11 * hash + Objects.hashCode(this.correctAnswerDenominator);
        hash = 11 * hash + Objects.hashCode(this.correctAnswerText);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Question other = (Question) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
