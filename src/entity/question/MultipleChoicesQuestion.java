/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.question;

import annotation.ExcludeFromGson;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MultipleChoicesQuestion extends Question {
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question", cascade = CascadeType.ALL)
    @OrderColumn(name = "choice_index")
    @ExcludeFromGson
    List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
    
}
