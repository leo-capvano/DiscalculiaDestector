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
@DiscriminatorValue(value = GuessBallNumberQuestion.DISCRIMINATOR_VALUE)
public class GuessBallNumberQuestion extends MultipleChoicesQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "11";
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public GuessBallNumberQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.GUESS_BALLS_NUMBER));
    }
    
    public GuessBallNumberQuestion(String questionText, QuestionSection questionSection, int correctAnswer) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(correctAnswer);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.GUESS_BALLS_NUMBER));
    }
    //</editor-fold>
}
