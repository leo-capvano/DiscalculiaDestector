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
@DiscriminatorValue(value = ReorderSequenceQuestion.DISCRIMINATOR_VALUE)
public class ReorderSequenceQuestion extends MultipleChoicesQuestion {
    
    public static final String DISCRIMINATOR_VALUE = "12";
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public ReorderSequenceQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.SEQUENCE_REORDER));
    }
    
    public ReorderSequenceQuestion(String questionText, QuestionSection questionSection, String correctAnswerText) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswerText(correctAnswerText);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.SEQUENCE_REORDER));
    }
    // </editor-fold>
}
