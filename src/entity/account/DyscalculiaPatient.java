package entity.account;

import annotation.ExcludeFromGson;
import entity.Institute;
import entity.QuizStatistics;
import entity.enums.Disorder;
import entity.enums.Gender;
import entity.enums.PatientType;
import entity.enums.Qualification;
import entity.enums.Region;
import entity.enums.School;
import entity.enums.UniversityDegree;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Francesco Capriglione
 * @version 1.2
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"classroomCode", "institute", "schoolRegister"}, name = "groupID")
})
@NamedQueries({
    @NamedQuery(name = DyscalculiaPatient.FIND_BY_ID, query = "SELECT dp FROM DyscalculiaPatient dp WHERE dp.id = :id"),
    @NamedQuery(name = DyscalculiaPatient.FIND_ALL, query = "SELECT dp FROM DyscalculiaPatient dp"),
    @NamedQuery(name = DyscalculiaPatient.FIND_ALL_QUIZZED, query = "SELECT dp FROM DyscalculiaPatient dp JOIN dp.quizStatistics q"),
    @NamedQuery(name = DyscalculiaPatient.FIND_BY_EMAIL, query = "SELECT dp FROM DyscalculiaPatient dp WHERE dp.email = :email"),
    @NamedQuery(name = DyscalculiaPatient.FIND_BY_EMAIL_TOKEN, query = "SELECT dp FROM DyscalculiaPatient dp WHERE dp.emailToken = :emailToken"),
    @NamedQuery(name = DyscalculiaPatient.DO_LOGIN, query = "SELECT dp FROM DyscalculiaPatient dp WHERE dp.email = :email AND dp.password = :password"),
    @NamedQuery(name = DyscalculiaPatient.FIND_BY_NAME, query = "SELECT dp FROM DyscalculiaPatient dp WHERE dp.name = :name"),
    @NamedQuery(name = DyscalculiaPatient.FIND_BY_SCHOOL, query = "SELECT dp FROM DyscalculiaPatient dp WHERE dp.schoolRegister = :schoolRegister AND dp.institute.id = :institute AND dp.classroomCode = :classroomCode")
})
public class DyscalculiaPatient extends Account implements Serializable {

    public static final String FIND_BY_ID = "DyscalculiaPatient.FIND_BY_ID";
    public static final String FIND_ALL = "DyscalculiaPatient.FIND_ALL";
    public static final String FIND_ALL_QUIZZED = "DyscalculiaPatient.FIND_ALL_QUIZZED";
    public static final String FIND_BY_EMAIL = "DyscalculiaPatient.FIND_BY_EMAIL";
    public static final String FIND_BY_EMAIL_TOKEN = "DyscalculiaPatient.FIND_BY_EMAIL_TOKEN";
    public static final String DO_LOGIN = "DyscalculiaPatient.DO_LOGIN";
    public static final String FIND_BY_NAME = "DyscalculiaPatient.FIND_BY_NAME";
    public static final String FIND_BY_SCHOOL = "DyscalculiaPatient.FIND_BY_SCHOOL";
    
    private static final long serialVersionUID = 1L;
        
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    
    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Region region; 

    @Column(name = "universityDegree")
    @Enumerated(EnumType.STRING)
    private UniversityDegree universityDegree;
    
    @Column(name = "attendedSchool")
    @Enumerated(EnumType.STRING)
    private School attendedSchool;
    
    @Column(name = "fatherQualification")
    @Enumerated(EnumType.STRING)
    private Qualification fatherQualification;
    
    @Column(name = "motherQualification")
    @Enumerated(EnumType.STRING)
    private Qualification motherQualification;
    
    @Column(name = "familyLearningDisorder")
    @Enumerated(EnumType.STRING)
    private Disorder familyLearningDisorder;
    
    @Column(name = "familyPsychiatricDisorder")
    @Enumerated(EnumType.STRING)
    private Disorder familyPsychiatricDisorder;
    
    @Column(length = 25, name = "classroomCode")
    private String classroomCode;
    
    @ManyToOne
    @JoinColumn(name = "institute")
    private Institute institute;
    
    @Column(length = 25, name = "schoolRegister")
    private String schoolRegister;
    
    @Column(name = "patientType", nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private PatientType type;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    @ExcludeFromGson
    private List<QuizStatistics> quizStatistics;
    
    @Override
    protected void onPrePersist() {
        
        switch(this.type) {
            case SINGLE_PATIENT:
                if(super.getEmail() == null || super.getPassword() == null)
                    throw new IllegalArgumentException("Email and password must be not null for DyscalculiaPatient entity of patientType = " + PatientType.SINGLE_PATIENT);
                
                this.classroomCode = null;
                this.institute = null;
                this.schoolRegister = null;
                
                break;
            case MULTI_PATIENT:
                
                this.universityDegree = null;
                super.setEmail(null);
                super.setPassword(null);
                break;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters and setters.">
    
    public DyscalculiaPatient() { }

    public DyscalculiaPatient(PatientType type, String name, String surname, String email, String password, Gender gender, Date dateOfBirth, Region region, UniversityDegree universityDegree, School attendedSchool, Qualification fatherQualification, Qualification motherQualification, Disorder familyLearningDisorder, Disorder familyPsychiatricDisorder, String classroomCode, Institute institute, String schoolRegistar) {        
        super.setName(name);
        super.setSurname(surname);
        super.setEmail(email);
        super.setPassword(password);
        this.type = type;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.region = region;
        this.universityDegree = universityDegree;
        this.attendedSchool = attendedSchool;
        this.classroomCode = classroomCode;
        this.institute = institute;
        this.schoolRegister = schoolRegistar;
        this.fatherQualification = fatherQualification;
        this.motherQualification = motherQualification;
        this.familyLearningDisorder = familyLearningDisorder;
        this.familyPsychiatricDisorder = familyPsychiatricDisorder;
    }
    
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public UniversityDegree getUniversityDegree() {
        return universityDegree;
    }

    public void setUniversityDegree(UniversityDegree universityDegree) {
        this.universityDegree = universityDegree;
    }

    public School getAttendedSchool() {
        return attendedSchool;
    }

    public void setAttendedSchool(School attendedSchool) {
        this.attendedSchool = attendedSchool;
    }

    public Qualification getFatherQualification() {
        return fatherQualification;
    }

    public void setFatherQualification(Qualification fatherQualification) {
        this.fatherQualification = fatherQualification;
    }

    public Qualification getMotherQualification() {
        return motherQualification;
    }

    public void setMotherQualification(Qualification motherQualification) {
        this.motherQualification = motherQualification;
    }

    public Disorder getFamilyLearningDisorder() {
        return familyLearningDisorder;
    }

    public void setFamilyLearningDisorder(Disorder familyLearningDisorder) {
        this.familyLearningDisorder = familyLearningDisorder;
    }

    public Disorder getFamilyPsychiatricDisorder() {
        return familyPsychiatricDisorder;
    }

    public void setFamilyPsychiatricDisorder(Disorder familyPsychiatricDisorder) {
        this.familyPsychiatricDisorder = familyPsychiatricDisorder;
    }

    public String getClassroomCode() {
        return classroomCode;
    }

    public void setClassroomCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public String getSchoolRegister() {
        return schoolRegister;
    }

    public void setSchoolRegister(String schoolRegistar) {
        this.schoolRegister = schoolRegistar;
    }

    public PatientType getType() {
        return type;
    }

    public void setType(PatientType type) {
        this.type = type;
    }
    
    public List<QuizStatistics> getQuizStatistics() {
        return quizStatistics;
    }

    public void setQuizStatistics(List<QuizStatistics> quizStatistics) {
        this.quizStatistics = quizStatistics;
    }
    
    // </editor-fold>
}
