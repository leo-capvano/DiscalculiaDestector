/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.question;

import entity.enums.Operation;
import entity.factory.QuestionTypeFactory;
import entity.quiz.QuestionSection;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0.1
 */
@Entity
@DiscriminatorValue(value = InColumnCalculationQuestion.DISCRIMINATOR_VALUE)
public class InColumnCalculationQuestion extends CalculationQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "3";
    
    // <editor-fold defaultstate="collapsed" desc="Constructors.">
    public InColumnCalculationQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.IN_COLUMN_CALCULATION));
    }
    
    public InColumnCalculationQuestion(String questionText, QuestionSection questionSection, int correctAnswer, int operand1, int operand2, Operation operationType) {
        super(questionText, questionSection, correctAnswer, operand1, operand2, operationType);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.IN_COLUMN_CALCULATION));
    }
    //</editor-fold>
}
