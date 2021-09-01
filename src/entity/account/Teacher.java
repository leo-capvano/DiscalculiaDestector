package entity.account;

import annotation.ExcludeFromGson;
import entity.Institute;
import entity.TeacherInstitute;
import entity.quiz.DyscalculiaQuiz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @NamedQuery(name = Teacher.FIND_BY_ID, query = "SELECT t FROM Teacher t WHERE t.id = :id"),
    @NamedQuery(name = Teacher.FIND_ALL, query = "SELECT t FROM Teacher t"),
    @NamedQuery(name = Teacher.FIND_BY_EMAIL, query = "SELECT t FROM Teacher t WHERE t.email = :email"),
    @NamedQuery(name = Teacher.FIND_BY_EMAIL_TOKEN, query = "SELECT t FROM Teacher t WHERE t.emailToken = :emailToken"),
    @NamedQuery(name = Teacher.DO_LOGIN, query = "SELECT t FROM Teacher t WHERE t.email = :email AND t.password = :password")
})
public class Teacher extends Account implements Serializable {

    public static final String FIND_BY_ID = "Teacher.FIND_BY_ID";
    public static final String FIND_ALL = "Teacher.FIND_ALL";
    public static final String FIND_BY_EMAIL = "Teacher.FIND_BY_EMAIL";
    public static final String FIND_BY_EMAIL_TOKEN = "Teacher.FIND_BY_EMAIL_TOKEN";
    public static final String DO_LOGIN = "Teacher.DO_LOGIN";
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher")
    @ExcludeFromGson
    List<TeacherInstitute> teacherInstitutes;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher")
    @ExcludeFromGson
    List<DyscalculiaQuiz> quizzesCreated;
    
    @Override
    protected void onPrePersist() {
        
        String email = super.getEmail();
        
        // @ index
        int atIndex = email.indexOf("@");
        
        if(atIndex == -1)
            throw new IllegalArgumentException("Invalid Email format for Teacher entity");
        
        if(email == null || super.getPassword() == null)
            throw new IllegalArgumentException("Email and password must be not null for Teacher entity");
        
        String domain = email.substring(atIndex);
        
        if(!domain.equals("@istruzione.it"))
            throw new IllegalArgumentException("Email must end with '@istruzione.it' for Teacher entity");
        
        if(super.getSurname() == null)
            throw new IllegalArgumentException("Surname must be not null for Teacher entity");
    }
    
    public List<Institute> getInstitutes() {
        List<Institute> institutes = new ArrayList<Institute>();
        
        for(TeacherInstitute currentValue : teacherInstitutes)
            institutes.add(currentValue.getInstitute());
        
        return institutes;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters and setters.">
    public Teacher() { }
    
    public Teacher(String name, String surname, String email, String password) {
        super.setName(name);
        super.setSurname(surname);
        super.setEmail(email);
        super.setPassword(password);
    }
    
    // </editor-fold>
}
