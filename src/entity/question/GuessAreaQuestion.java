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
@DiscriminatorValue(value = GuessAreaQuestion.DISCRIMINATOR_VALUE)
public class GuessAreaQuestion extends MultipleChoicesQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "10";
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public GuessAreaQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.GUESS_AREA));
    }
    
    public GuessAreaQuestion(String questionText, QuestionSection questionSection, int correctAnswer) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(correctAnswer);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.GUESS_AREA));
    }
    //</editor-fold>
}
