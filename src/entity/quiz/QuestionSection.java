package entity.quiz;

import annotation.ExcludeFromGson;
import entity.question.AcrossCalculationQuestion;
import entity.question.GenericProblemQuestion;
import entity.question.GuessAreaQuestion;
import entity.question.GuessBallNumberQuestion;
import entity.question.HidingNumberQuestion;
import entity.question.InColumnCalculationQuestion;
import entity.question.ListeningQuestion;
import entity.question.MathematicProblemQuestion;
import entity.question.MinOrMaxQuestion;
import entity.question.NumbersLineQuestion;
import entity.question.PositioningQuestion;
import entity.question.Question;
import entity.question.ReorderSequenceQuestion;
import entity.question.TypeTheNumberQuestion;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Francesco Capriglione
 * @version 1.4
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "quizSection"}, name = "name_unique")
})
@NamedQueries({
    @NamedQuery(name = QuestionSection.FIND_BY_ID, query = "SELECT qs FROM QuestionSection qs WHERE qs.id = :id"),
    @NamedQuery(name = QuestionSection.FIND_BY_NAME_AND_QUIZ, query = "SELECT qs FROM QuestionSection qs WHERE qs.name = :name AND qs.quiz = :quiz"),
    @NamedQuery(name = QuestionSection.FIND_ALL, query = "SELECT qs FROM QuestionSection qs")        
})
public class QuestionSection implements Serializable {
    
    public static final String FIND_BY_ID = "QuestionSection.FIND_BY_ID";
    public static final String FIND_BY_NAME_AND_QUIZ = "QuestionSection.FIND_BY_NAME_AND_QUIZ";
    public static final String FIND_ALL = "QuestionSection.FIND_ALL";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "numOfChoices", nullable = true)
    private Integer numOfChoices = null;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "quizSection", nullable = false)
    private DyscalculiaQuiz quiz;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "type", nullable = false)
    private QuestionType questionType;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "questionSection", cascade = CascadeType.ALL)
    @ExcludeFromGson
    private List<Question> questions;
    
    @PrePersist
    protected void onPrePersist() {
        if(numOfChoices != null && numOfChoices > questionType.getMaxMultipleChoices())
            throw new IllegalArgumentException("Num of choiches must be lower than " + questionType.getMaxMultipleChoices());
    }
    
    public Class<?> getQuestionsClass() {
        if(questions != null && questions.size() > 0)
            return questions.get(0).getClass();
        
        switch(questionType.getId()) {
            case 1:
                return ListeningQuestion.class;
            case 2:
                return AcrossCalculationQuestion.class;
            case 3:
                return InColumnCalculationQuestion.class;
            case 4:
                return MathematicProblemQuestion.class;
            case 5:
                return NumbersLineQuestion.class;
            case 6:
                return TypeTheNumberQuestion.class;
            case 7:
                return PositioningQuestion.class;
            case 8:
                return MinOrMaxQuestion.class;
            case 9:
                return HidingNumberQuestion.class;
            case 10:
                return GuessAreaQuestion.class;
            case 11:
                return GuessBallNumberQuestion.class;
            case 12:
                return ReorderSequenceQuestion.class;
            case 13:
                return GenericProblemQuestion.class;
            default:
                return null;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public QuestionSection () { }
    
    public QuestionSection (String name, String description, Integer numOfChoices, QuestionType questionType, DyscalculiaQuiz quiz) {
        this.name = name;
        this.description = description;
        this.numOfChoices = numOfChoices;
        this.questionType = questionType;
        this.quiz = quiz;
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

    public Integer getNumOfChoices() {
        return numOfChoices;
    }

    public void setNumOfChoices(Integer numOfChoices) {
        this.numOfChoices = numOfChoices;
    }
    
    public DyscalculiaQuiz getQuiz() {
        return quiz;
    }

    public void setQuiz(DyscalculiaQuiz quiz) {
        this.quiz = quiz;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.description);
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
        final QuestionSection other = (QuestionSection) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
