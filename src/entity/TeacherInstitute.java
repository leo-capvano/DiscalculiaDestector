package entity;

import entity.account.Teacher;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Francesco Capriglione
 * @version 1.2
 */
@Entity
@NamedQueries({
    @NamedQuery(name = TeacherInstitute.FIND_BY_ID, query = "SELECT ti FROM TeacherInstitute ti WHERE ti.id = :id"),
    @NamedQuery(name = TeacherInstitute.FIND_ALL, query = "SELECT ti FROM TeacherInstitute ti")
})
public class TeacherInstitute implements Serializable {
    
    public static final String FIND_BY_ID = "TeacherInstitute.FIND_BY_ID";
    public static final String FIND_ALL = "TeacherInstitute.FIND_ALL";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher")
    private Teacher teacher;
    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "institute")
    private Institute institute;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public TeacherInstitute() { }
    
    public TeacherInstitute(Teacher teacher, Institute institute) {
        this.teacher = teacher;
        this.institute = institute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
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
        final TeacherInstitute other = (TeacherInstitute) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    // </editor-fold>
}
