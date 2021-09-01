/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.question;

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
@DiscriminatorValue(value = MinOrMaxQuestion.DISCRIMINATOR_VALUE)
public class MinOrMaxQuestion extends MultipleChoicesQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "8";
    
    // <editor-fold defaultstate="collapsed" desc="Constructors.">
    public MinOrMaxQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.MIN_OR_MAX));
    }
    
    public MinOrMaxQuestion(String questionText, QuestionSection questionSection, int correctAnswer, Integer correctAnswerDenominator) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(correctAnswer);
        super.setCorrectAnswerDenominator(correctAnswerDenominator);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.MIN_OR_MAX));
    }
    //</editor-fold>
}
