package entity;

import annotation.ExcludeFromGson;
import entity.account.DyscalculiaPatient;
import entity.account.Teacher;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @version 1.2
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Institute.FIND_BY_ID, query = "SELECT i FROM Institute i WHERE i.id = :id"),
    @NamedQuery(name = Institute.FIND_ALL, query = "SELECT i FROM Institute i")
})
public class Institute implements Serializable {
    
    public static final String FIND_BY_ID = "Institute.FIND_BY_ID";
    public static final String FIND_ALL = "Institute.FIND_ALL";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", length = 75, unique = true, nullable = false)
    private String name;
    
    @Column(name = "isActive", nullable = false)
    private boolean active = true;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "institute")
    @ExcludeFromGson
    List<TeacherInstitute> teachersInstitute;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "institute")
    @ExcludeFromGson
    List<DyscalculiaPatient> students;
    
    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<Teacher>();
        
        for(TeacherInstitute currentValue : teachersInstitute)
            teachers.add(currentValue.getTeacher());
        
        return teachers;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    
    public Institute() { }
    
    public Institute(String name) {
        this.name = name;
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

    public List<TeacherInstitute> getTeachersInstitute() {
        return teachersInstitute;
    }

    public void setTeachersInstitute(List<TeacherInstitute> teachersInstitute) {
        this.teachersInstitute = teachersInstitute;
    }

    public List<DyscalculiaPatient> getStudents() {
        return students;
    }

    public void setStudents(List<DyscalculiaPatient> students) {
        this.students = students;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.name);
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
        final Institute other = (Institute) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    //</editor-fold>
}
