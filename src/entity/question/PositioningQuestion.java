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
@DiscriminatorValue(value = PositioningQuestion.DISCRIMINATOR_VALUE)
public class PositioningQuestion extends MultipleChoicesQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "7";
    
    @Column(name = "toBePlaced")
    private int toBePlaced;
    
    @Column(name = "toBePlacedDenominator")
    private Integer toBePlacedDenominator;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public PositioningQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.POSITIONING));
    }
    
    public PositioningQuestion(String questionText, QuestionSection questionSection, int correctAnswer, int toBePlaced, Integer toBePlacedDenominator) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(correctAnswer);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.POSITIONING));
        
        this.toBePlaced = toBePlaced;
        this.toBePlacedDenominator = toBePlacedDenominator;
    }

    public int getToBePlaced() {
        return toBePlaced;
    }

    public void setToBePlaced(int toBePlaced) {
        this.toBePlaced = toBePlaced;
    }

    public Integer getToBePlacedDenominator() {
        return toBePlacedDenominator;
    }

    public void setToBePlacedDenominator(Integer toBePlacedDenominator) {
        this.toBePlacedDenominator = toBePlacedDenominator;
    }
    //</editor-fold>
}
