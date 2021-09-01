package entity;

import entity.quiz.QuestionType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
@Entity
public class StatisticRow implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "correctAnswers", nullable = false)
    private int correctAnswers;
    
    @Column(name = "skippedAnswers", nullable = false)
    private int skippedAnswers;
    
    @Column(name = "wrongAnswers", nullable = false)
    private int wrongAnswers;
    
    @Column(name = "averageTime", nullable = false)
    private int averageTime;
    
    @ManyToOne(optional = false)
    private QuizStatistics quizStatistics;
    
    @ManyToOne(optional = false)
    private QuestionType questionType;

    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public StatisticRow() { }

    public StatisticRow(int correctAnswers, int skippedAnswers, int wrongAnswers, int averageTime, QuizStatistics quizStatistics, QuestionType questionType) {
        this.correctAnswers = correctAnswers;
        this.skippedAnswers = skippedAnswers;
        this.wrongAnswers = wrongAnswers;
        this.averageTime = averageTime;
        this.quizStatistics = quizStatistics;
        this.questionType = questionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getSkippedAnswers() {
        return skippedAnswers;
    }

    public void setSkippedAnswers(int skippedAnswers) {
        this.skippedAnswers = skippedAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }

    public QuizStatistics getQuizStatistics() {
        return quizStatistics;
    }

    public void setQuizStatistics(QuizStatistics quizStatistics) {
        this.quizStatistics = quizStatistics;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.correctAnswers;
        hash = 43 * hash + this.skippedAnswers;
        hash = 43 * hash + this.wrongAnswers;
        hash = 43 * hash + this.averageTime;
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
        final StatisticRow other = (StatisticRow) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
