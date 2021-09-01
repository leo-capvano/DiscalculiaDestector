package entity.quiz;

import annotation.ExcludeFromGson;
import entity.QuizStatistics;
import entity.account.Teacher;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @NamedQuery(name = DyscalculiaQuiz.FIND_BY_ID, query = "SELECT q FROM DyscalculiaQuiz q WHERE q.id = :id"),
    @NamedQuery(name = DyscalculiaQuiz.FIND_ALL, query = "SELECT q FROM DyscalculiaQuiz q"),
    @NamedQuery(name = DyscalculiaQuiz.FIND_BY_NAME, query = "Select q FROM DyscalculiaQuiz q WHERE q.name = :name"),
    @NamedQuery(name = DyscalculiaQuiz.FIND_ALL_TRUSTED, query = "Select q FROM DyscalculiaQuiz q WHERE q.trusted = true"),
    @NamedQuery(name = DyscalculiaQuiz.FIND_TRUSTED_BY_SECTION_ID, query = "Select q FROM DyscalculiaQuiz q WHERE q.trusted = true AND q.quizSection.id = :sectionID"),
    @NamedQuery(name = DyscalculiaQuiz.FIND_BY_TEACHER_ID, query = "Select q FROM DyscalculiaQuiz q WHERE q.teacher.id = :teacherID")
})
public class DyscalculiaQuiz implements Serializable {
    
    public static final String FIND_BY_ID = "DyscalculiaQuiz.FIND_BY_ID";
    public static final String FIND_ALL = "DyscalculiaQuiz.FIND_ALL";
    public static final String FIND_BY_NAME = "DyscalculiaQuiz.FIND_BY_NAME";
    public static final String FIND_ALL_TRUSTED = "DyscalculiaQuiz.FIND_ALL_TRUSTED";
    public static final String FIND_TRUSTED_BY_SECTION_ID = "DyscalculiaQuiz.FIND_TRUSTED_BY_SECTION_ID";
    public static final String FIND_BY_TEACHER_ID = "DyscalculiaQuiz.FIND_BY_TEACHER_ID";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "approprieteThreshold")
    private Double approprieteThreshold;
    
    @Column(name = "lowRiskThreshold")
    private Double lowRiskThreshold;
    
    @Column(name = "highRiskThreshold")
    private Double highRiskThreshold;
    
    @Column(name = "trusted", nullable = false)
    private boolean trusted = false;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "quizSection")
    private QuizSection quizSection;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quiz")
    @ExcludeFromGson
    private List<QuestionSection> sections;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quiz")
    @ExcludeFromGson
    private List<QuizStatistics> statistics;
    
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Teacher teacher;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public DyscalculiaQuiz() { }

    public DyscalculiaQuiz(String name, String description, Double approprieteThreshold, Double lowRiskThreshold, Double highRiskThreshold, QuizSection quizSection) {
        this.name = name;
        this.description = description;
        this.approprieteThreshold = approprieteThreshold;
        this.lowRiskThreshold = lowRiskThreshold;
        this.highRiskThreshold = highRiskThreshold;
        this.quizSection = quizSection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getApproprieteThreshold() {
        return approprieteThreshold;
    }

    public void setApproprieteThreshold(double approprieteThreshold) {
        this.approprieteThreshold = approprieteThreshold;
    }

    public Double getLowRiskThreshold() {
        return lowRiskThreshold;
    }

    public void setLowRiskThreshold(double lowRiskThreshold) {
        this.lowRiskThreshold = lowRiskThreshold;
    }

    public Double getHighRiskThreshold() {
        return highRiskThreshold;
    }

    public void setHighRiskThreshold(double highRiskThreshold) {
        this.highRiskThreshold = highRiskThreshold;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public QuizSection getQuizSection() {
        return quizSection;
    }

    public void setQuizSection(QuizSection questionSection) {
        this.quizSection = questionSection;
    }

    public List<QuestionSection> getSections() {
        return sections;
    }

    public void setSections(List<QuestionSection> sections) {
        this.sections = sections;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<QuizStatistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<QuizStatistics> statistics) {
        this.statistics = statistics;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.approprieteThreshold);
        hash = 41 * hash + Objects.hashCode(this.lowRiskThreshold);
        hash = 41 * hash + Objects.hashCode(this.highRiskThreshold);
        hash = 41 * hash + (this.trusted ? 1 : 0);
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
        final DyscalculiaQuiz other = (DyscalculiaQuiz) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
