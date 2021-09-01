package entity.quiz;

import entity.account.DyscalculiaPatient;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
@Entity
@NamedQueries({
    @NamedQuery(name = QuizFeedback.FIND_BY_ID, query = "SELECT qf FROM QuizFeedback qf WHERE qf.id = :id"),
    @NamedQuery(name = QuizFeedback.FIND_ALL, query = "SELECT qf FROM QuizFeedback qf"),
    @NamedQuery(name = QuizFeedback.FIND_BY_PATIENT_ID, query = "SELECT qf FROM QuizFeedback qf WHERE qf.patient.id = :patientID")
})
public class QuizFeedback implements Serializable {
    
    public static final String FIND_BY_ID = "QuizFeedback.FIND_BY_ID";
    public static final String FIND_ALL = "QuizFeedback.FIND_ALL";
    public static final String FIND_BY_PATIENT_ID = "QuizFeedback.FIND_BY_PATIENT_ID";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name= "date", nullable = false)
    private Date date;
    
    @Column(name = "questionDifficulty", nullable = false)
    private int questionDifficulty;
    
    @Column(name = "websiteClearness", nullable = false)
    private int websiteClearness;
    
    @Column(name = "levelOfAttention", nullable = false)
    private int levelOfAttention;
    
    @Column(name = "attentionToQuestion", nullable = false)
    private int attentionToQuestion;
    
    @Column(name = "quizClearness", nullable = false)
    private int quizClearness;
    
    @Column(name = "quizDuration", nullable = false)
    private int quizDuration;
    
    @Column(name = "selfAssestment", nullable = false)
    private int selfAssestment;
    
    @Column(name = "rulesClearness", nullable = false)
    private int rulesClearness;
    
    @ManyToOne(optional = false)
    DyscalculiaQuiz quiz;
    
    @ManyToOne(optional = false)
    DyscalculiaPatient patient;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    
    public QuizFeedback() { }

    public QuizFeedback(Date date, int questionDifficulty, int websiteClearness, int levelOfAttention, int attentionToQuestion, int quizClearness, int quizDuration, int selfAssestment, int rulesClearness, DyscalculiaQuiz quiz, DyscalculiaPatient patient) {
        this.date = date;
        this.questionDifficulty = questionDifficulty;
        this.websiteClearness = websiteClearness;
        this.levelOfAttention = levelOfAttention;
        this.attentionToQuestion = attentionToQuestion;
        this.quizClearness = quizClearness;
        this.quizDuration = quizDuration;
        this.selfAssestment = selfAssestment;
        this.rulesClearness = rulesClearness;
        this.quiz = quiz;
        this.patient = patient;
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

    public int getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(int questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public int getWebsiteClearness() {
        return websiteClearness;
    }

    public void setWebsiteClearness(int websiteClearness) {
        this.websiteClearness = websiteClearness;
    }

    public int getLevelOfAttention() {
        return levelOfAttention;
    }

    public void setLevelOfAttention(int levelOfAttention) {
        this.levelOfAttention = levelOfAttention;
    }

    public int getAttentionToQuestion() {
        return attentionToQuestion;
    }

    public void setAttentionToQuestion(int attentionToQuestion) {
        this.attentionToQuestion = attentionToQuestion;
    }

    public int getQuizClearness() {
        return quizClearness;
    }

    public void setQuizClearness(int quizClearness) {
        this.quizClearness = quizClearness;
    }

    public int getQuizDuration() {
        return quizDuration;
    }

    public void setQuizDuration(int quizDuration) {
        this.quizDuration = quizDuration;
    }

    public int getSelfAssestment() {
        return selfAssestment;
    }

    public void setSelfAssestment(int selfAssestment) {
        this.selfAssestment = selfAssestment;
    }

    public int getRulesClearness() {
        return rulesClearness;
    }

    public void setRulesClearness(int rulesClearness) {
        this.rulesClearness = rulesClearness;
    }

    public DyscalculiaQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(DyscalculiaQuiz quiz) {
        this.quiz = quiz;
    }

    public DyscalculiaPatient getPatient() {
        return patient;
    }

    public void setPatient(DyscalculiaPatient patient) {
        this.patient = patient;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.date);
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
        final QuizFeedback other = (QuizFeedback) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
