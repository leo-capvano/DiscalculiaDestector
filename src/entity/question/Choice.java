/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.question;

import java.io.Serializable;
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

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Choice.FIND_BY_ID, query = "SELECT c FROM Choice c WHERE c.id = :id"),
    @NamedQuery(name = Choice.FIND_ALL, query = "SELECT c FROM Choice c")
})
public class Choice implements Serializable {
    
    public static final String FIND_BY_ID = "Choice.FIND_BY_ID";
    public static final String FIND_ALL = "Choice.FIND_ALL";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="value", nullable = false)
    private int value;
    
    @Column(name="valueDenominator")
    private Integer valueDenominator;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question")
    private MultipleChoicesQuestion question;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public Choice() { }
    
    public Choice(int value, MultipleChoicesQuestion question) {
        this.value = value;
        this.question = question;
        this.valueDenominator = null;
    }
    
    public Choice(int value, Integer valueDenominator, MultipleChoicesQuestion question) {
        this.value = value;
        this.valueDenominator = valueDenominator;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Integer getValueDenominator() {
        return valueDenominator;
    }

    public void setValueDenominator(Integer valueDenominator) {
        this.valueDenominator = valueDenominator;
    }

    public MultipleChoicesQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MultipleChoicesQuestion question) {
        this.question = question;
    }
    //</editor-fold>
}
