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
@DiscriminatorValue(value = NumbersLineQuestion.DISCRIMINATOR_VALUE)
public class NumbersLineQuestion extends Question {
    
    protected static final String DISCRIMINATOR_VALUE = "5";
    
    @Column(name = "leftBoundary")
    private Integer leftBoundary;
    
    @Column(name = "rightBoundary")
    private Integer rightBoundary;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public NumbersLineQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.NUMBERS_LINE));
    }
    
    public NumbersLineQuestion(String questionText, QuestionSection questionSection, int correctAnswer, int leftBoundary, int rightBoundary) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(correctAnswer);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.NUMBERS_LINE));
        
        this.leftBoundary = leftBoundary;
        this.rightBoundary = rightBoundary;
    }

    public int getLeftBoundary() {
        return leftBoundary;
    }

    public void setLeftBoundary(int leftBoundary) {
        this.leftBoundary = leftBoundary;
    }

    public int getRightBoundary() {
        return rightBoundary;
    }

    public void setRightBoundary(int rightBoundary) {
        this.rightBoundary = rightBoundary;
    }
    //</editor-fold>
}
