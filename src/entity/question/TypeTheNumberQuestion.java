/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.question;

import entity.factory.QuestionTypeFactory;
import entity.quiz.QuestionSection;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0.1
 */
@Entity
@DiscriminatorValue(value = TypeTheNumberQuestion.DISCRIMINATOR_VALUE)
public class TypeTheNumberQuestion extends Question {
    
    protected static final String DISCRIMINATOR_VALUE = "6";
    
    @Column(name = "toBeTyped")
    Integer toBeTyped;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public TypeTheNumberQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.TYPE_THE_NUMBER));
    }
    
    public TypeTheNumberQuestion(String questionText, QuestionSection questionSection, Integer toBeTyped, String correctAnswerText) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswerText(correctAnswerText);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.TYPE_THE_NUMBER));
        
        this.toBeTyped = toBeTyped;
    }

    public Integer getToBeTyped() {
        return toBeTyped;
    }

    public void setToBeTyped(Integer toBeTyped) {
        this.toBeTyped = toBeTyped;
    }
    //</editor-fold>
}
