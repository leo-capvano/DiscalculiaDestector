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
 * @version 1.0.2
 */
@Entity
@DiscriminatorValue(value = MathematicProblemQuestion.DISCRIMINATOR_VALUE)
public class MathematicProblemQuestion extends Question {
    
    protected static final String DISCRIMINATOR_VALUE = "4";
    
    // <editor-fold defaultstate="collapsed" desc="Constructors.">
    public MathematicProblemQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.MATHEMATIC_PROBLEM));
    }
    
    public MathematicProblemQuestion(String questionText, QuestionSection questionSection, int correctAnswer) {
        super.setQuestionText(questionText);
        super.setCorrectAnswer(correctAnswer);
        super.setQuestionSection(questionSection);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeFactory.QuestionTypeID.MATHEMATIC_PROBLEM));
    }
    //</editor-fold>
}
