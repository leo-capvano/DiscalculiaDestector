package entity.account;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Administrator.FIND_BY_ID, query = "SELECT a FROM Administrator a WHERE a.id = :id"),
    @NamedQuery(name = Administrator.FIND_ALL, query = "SELECT a FROM Administrator a"),
    @NamedQuery(name = Administrator.FIND_BY_EMAIL, query = "SELECT a FROM Administrator a WHERE a.email = :email"),
    @NamedQuery(name = Administrator.FIND_BY_EMAIL_TOKEN, query = "SELECT a FROM Administrator a WHERE a.emailToken = :emailToken"),
    @NamedQuery(name = Administrator.DO_LOGIN, query = "SELECT a FROM Administrator a WHERE a.email = :email AND a.password = :password")
})
public class Administrator extends Account implements Serializable {
    
    public static final String FIND_BY_ID = "Administrator.FIND_BY_ID";
    public static final String FIND_ALL = "Administrator.FIND_ALL";
    public static final String FIND_BY_EMAIL = "Administrator.FIND_BY_EMAIL";
    public static final String FIND_BY_EMAIL_TOKEN = "Administrator.FIND_BY_EMAIL_TOKEN";
    public static final String DO_LOGIN = "Administrator.DO_LOGIN";
    
    public Administrator() { }
    
    public Administrator(String name, String surname, String email, String password) {
        super.setName(name);
        super.setSurname(surname);
        super.setEmail(email);
        super.setPassword(password);
    }
    
    @Override
    protected void onPrePersist() { 
        if(super.getEmail() == null || super.getPassword() == null)
            throw new IllegalArgumentException("Email and password must be not null for Administrator entity", null);
        
        if(super.getName() == null)
            throw new IllegalArgumentException("Name must be not null for Administrator entity");
        
        if(super.getSurname() == null)
            throw new IllegalArgumentException("Surname must be not null for Administrator entity");
    }
}
