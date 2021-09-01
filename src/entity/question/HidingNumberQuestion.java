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
@DiscriminatorValue(value = HidingNumberQuestion.DISCRIMINATOR_VALUE)
public class HidingNumberQuestion extends MultipleChoicesQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "9";
    
    @Column(name = "toBeHide")
    private Integer toBeHide;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public HidingNumberQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.HIDING_NUMBER));
    }
    
    public HidingNumberQuestion(String questionText, QuestionSection questionSection, int correctAnswer, int toBeHide) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(correctAnswer);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.HIDING_NUMBER));
                
        this.toBeHide = toBeHide;
    }

    public Integer getToBeHide() {
        return toBeHide;
    }

    public void setToBeHide(Integer toBeHide) {
        this.toBeHide = toBeHide;
    }
    //</editor-fold>
}
