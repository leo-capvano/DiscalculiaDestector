package entity;

import annotation.ExcludeFromGson;
import entity.account.DyscalculiaPatient;
import entity.quiz.DyscalculiaQuiz;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Francesco Capriglione
 * @version 1.2
 */

@Entity
@NamedQueries({
    @NamedQuery(name = QuizStatistics.FIND_BY_ID, query = "SELECT qs FROM QuizStatistics qs WHERE qs.id = :id"),
    @NamedQuery(name = QuizStatistics.FIND_ALL, query = "SELECT qs FROM QuizStatistics qs"),
    @NamedQuery(name = QuizStatistics.FIND_BY_PATIENT, query = "SELECT qs FROM QuizStatistics qs WHERE qs.patient = :patient")
})
public class QuizStatistics implements Serializable {
    
    public static final String FIND_BY_ID = "QuizStatistics.FIND_BY_ID";
    public static final String FIND_ALL = "QuizStatistics.FIND_ALL";
    public static final String FIND_BY_PATIENT = "QuizStatistics.FIND_BY_PATIENT";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
        
    @Column(name = "date", nullable = false)
    private Date date;
    
    @Column(name = "performance", precision = 6, scale = 4, nullable = false)
    private double performance;
    
    @Column(name = "secondsEllapsed", nullable = false)
    private int secondsEllapsed;
    
    @Column(name = "totalCorrectAnswers", nullable = false)
    private int totalCorrectAnswers;
    
    @Column(name = "totalSkippedAnswers", nullable = false)
    private int totalSkippedAnswers;
    
    @Column(name = "totalWrongAnswers", nullable = false)
    private int totalWrongAnswers;
    
    @ManyToOne(optional = false)
    private DyscalculiaPatient patient;
    
    @ManyToOne(optional = false)
    private DyscalculiaQuiz quiz;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quizStatistics", cascade = CascadeType.ALL)
    @ExcludeFromGson
    List<StatisticRow> statisticRows;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public QuizStatistics() { }

    public QuizStatistics(Date date, double performance, int secondsEllapsed, int totalCorrectAnswers, int totalSkippedAnswers, int totalWrongAnswers) {
        this.date = date;
        this.performance = performance;
        this.secondsEllapsed = secondsEllapsed;
        this.totalCorrectAnswers = totalCorrectAnswers;
        this.totalSkippedAnswers = totalSkippedAnswers;
        this.totalWrongAnswers = totalWrongAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPerformance() {
        return performance;
    }

    public void setPerformance(double performance) {
        this.performance = performance;
    }

    public int getSecondsEllapsed() {
        return secondsEllapsed;
    }

    public void setSecondsEllapsed(int secondsEllapsed) {
        this.secondsEllapsed = secondsEllapsed;
    }

    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public void setTotalCorrectAnswers(int totalCorrectAnswers) {
        this.totalCorrectAnswers = totalCorrectAnswers;
    }

    public int getTotalSkippedAnswers() {
        return totalSkippedAnswers;
    }

    public void setTotalSkippedAnswers(int totalSkippedAnswers) {
        this.totalSkippedAnswers = totalSkippedAnswers;
    }

    public int getTotalWrongAnswers() {
        return totalWrongAnswers;
    }

    public void setTotalWrongAnswers(int totalWrongAnswers) {
        this.totalWrongAnswers = totalWrongAnswers;
    }

    public DyscalculiaPatient getPatient() {
        return patient;
    }

    public void setPatient(DyscalculiaPatient patient) {
        this.patient = patient;
    }

    public DyscalculiaQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(DyscalculiaQuiz quiz) {
        this.quiz = quiz;
    }

    public List<StatisticRow> getStatisticRows() {
        return statisticRows;
    }

    public void setStatisticRows(List<StatisticRow> statisticRows) {
        this.statisticRows = statisticRows;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.date);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.performance) ^ (Double.doubleToLongBits(this.performance) >>> 32));
        hash = 37 * hash + this.secondsEllapsed;
        hash = 37 * hash + this.totalCorrectAnswers;
        hash = 37 * hash + this.totalSkippedAnswers;
        hash = 37 * hash + this.totalWrongAnswers;
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
        final QuizStatistics other = (QuizStatistics) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
