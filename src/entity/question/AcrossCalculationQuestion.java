/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.question;

import entity.enums.Operation;
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
@DiscriminatorValue(value = AcrossCalculationQuestion.DISCRIMINATOR_VALUE)
public class AcrossCalculationQuestion extends CalculationQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "2";
    
    @Column(name = "operand1Denominator")
    private Integer operand1Denominator;
    
    @Column(name = "operand2Denominator")
    private Integer operand2Denominator;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public AcrossCalculationQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.ACROSS_CALCULATION));
    }
    
    public AcrossCalculationQuestion(String questionText, QuestionSection questionSection, int correctAnswer, Integer correctAnswerDenominator, int operand1, Integer operand1Denominator, int operand2, Integer operand2Denominator, Operation operationType) {
        super(questionText, questionSection, correctAnswer, operand1, operand2, operationType);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.ACROSS_CALCULATION));
        super.setCorrectAnswerDenominator(correctAnswerDenominator);
        
        this.operand1Denominator = operand1Denominator;
        this.operand2Denominator = operand2Denominator;
    }

    public Integer getOperand1Denominator() {
        return operand1Denominator;
    }

    public void setOperand1Denominator(Integer operand1Denominator) {
        this.operand1Denominator = operand1Denominator;
    }

    public Integer getOperand2Denominator() {
        return operand2Denominator;
    }

    public void setOperand2Denominator(Integer operand2Denominator) {
        this.operand2Denominator = operand2Denominator;
    }
    //</editor-fold>
}
