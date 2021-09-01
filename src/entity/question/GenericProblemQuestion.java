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
 * @version 1.0
 */
@Entity
@DiscriminatorValue(value = GenericProblemQuestion.DISCRIMINATOR_VALUE)
public class GenericProblemQuestion extends MultipleChoicesQuestion {
    
    protected static final String DISCRIMINATOR_VALUE = "13";

    public GenericProblemQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.GENERIC_PROBLEM));
    }
    
    public GenericProblemQuestion(String questionText, QuestionSection questionSection, int correctAnswer, Integer correctAnswerDenominator) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(correctAnswer);
        super.setCorrectAnswerDenominator(correctAnswerDenominator);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.GENERIC_PROBLEM));
    }
}
