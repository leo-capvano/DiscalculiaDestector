package entity.quiz;

import annotation.ExcludeFromGson;
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
    @NamedQuery(name = QuizSection.FIND_BY_ID, query = "SELECT qs FROM QuizSection qs WHERE qs.id = :id"),
    @NamedQuery(name = QuizSection.FIND_ALL, query = "SELECT qs FROM QuizSection qs")
})
public class QuizSection implements Serializable {
    
    public static final String FIND_BY_ID = "QuizSection.FIND_BY_ID";
    public static final String FIND_ALL = "QuizSection.FIND_ALL";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quizSection", cascade = CascadeType.ALL)
    @ExcludeFromGson
    private List<DyscalculiaQuiz> quizzes;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public QuizSection() { }

    public QuizSection(String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<DyscalculiaQuiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<DyscalculiaQuiz> quizzes) {
        this.quizzes = quizzes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.description);
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
        final QuizSection other = (QuizSection) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    //</editor-fold>
}
