package entity.quiz;

import annotation.ExcludeFromGson;
import entity.question.Question;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1.1
 */
@Entity
@NamedQueries({
    @NamedQuery(name = QuestionType.FIND_BY_ID, query = "SELECT qt FROM QuestionType qt WHERE qt.id = :id"),
    @NamedQuery(name = QuestionType.FIND_ALL, query = "SELECT qt FROM QuestionType qt")
})
public class QuestionType implements Serializable {
    
    public static final String FIND_BY_ID = "QuestionType.FIND_BY_ID";
    public static final String FIND_ALL = "QuestionType.FIND_ALL";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", nullable = false, length = 350)
    private String description;
    
    @Column(name = "maxMultipleChoices")
    private Integer maxMultipleChoices = null;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "questionType")
    @ExcludeFromGson
    private List<Question> questions;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "questionType")
    @ExcludeFromGson
    private List<QuestionSection> sections;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public QuestionType() { }
    
    public QuestionType(String name, String description, Integer maxMultipleChoices) { 
        this.name = name;
        this.description = description;
        this.maxMultipleChoices = maxMultipleChoices;
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

    public Integer getMaxMultipleChoices() {
        return maxMultipleChoices;
    }

    public void setMaxMultipleChoices(Integer maxMultipleChoices) {
        this.maxMultipleChoices = maxMultipleChoices;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<QuestionSection> getSections() {
        return sections;
    }

    public void setSections(List<QuestionSection> sections) {
        this.sections = sections;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.description);
        hash = 37 * hash + Objects.hashCode(this.maxMultipleChoices);
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
        final QuestionType other = (QuestionType) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    //</editor-fold>
}
