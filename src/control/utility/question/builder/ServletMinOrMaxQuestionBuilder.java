/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import com.github.kiprobinson.bigfraction.BigFraction;
import entity.question.Choice;
import entity.question.MinOrMaxQuestion;
import entity.question.Question;
import entity.quiz.QuestionSection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletMinOrMaxQuestionBuilder extends ServletQuestionBuilder {

    
    private static final String OPERATION_TYPE_PARAMETER = "operation-type";
    private static final String CHOICE_PREFIX = "choice-";
    private static final String CHOICE_DENOMINATOR_PREFIX = "choiceDenominator-";
    
    public ServletMinOrMaxQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        String operationType = request.getParameter(OPERATION_TYPE_PARAMETER);
        
        //Creating question without the correct answer and without choices
        MinOrMaxQuestion question = new MinOrMaxQuestion();
        question.setQuestionText(questionText);
        question.setQuestionSection(super.questionSection);
        
        //Getting choices
        int numOfChoices = super.questionSection.getNumOfChoices();
        List<Choice> choices = new ArrayList<Choice>();
        for(int i = 1; i <= numOfChoices; i ++) {
            int currentChoiceValue = Integer.parseInt(request.getParameter(CHOICE_PREFIX + i));
            Integer choiceDenominator;
            try {
                choiceDenominator = Integer.parseInt(request.getParameter(CHOICE_DENOMINATOR_PREFIX + i));
            } catch(NumberFormatException e) {
                choiceDenominator = null;
            }
            Choice currentChoice = new Choice(currentChoiceValue, choiceDenominator, question);
            choices.add(currentChoice);
        }
        
        question.setChoices(choices);
        
        //Getting correctAnswer
        Choice correctAnswerChoice = choices.get(0);
        
        if(operationType.equalsIgnoreCase("max")) {
            for(int i = 1; i < choices.size(); i++) {
                BigFraction correctAnswerFraction;
                if(correctAnswerChoice.getValueDenominator() != null)
                    correctAnswerFraction = BigFraction.valueOf(correctAnswerChoice.getValue(), correctAnswerChoice.getValueDenominator());
                else
                    correctAnswerFraction = BigFraction.valueOf(correctAnswerChoice.getValue(), 1);

                Choice currentChoice = choices.get(i);
                BigFraction currentChoiceFraction;
                if(currentChoice.getValueDenominator() != null)
                    currentChoiceFraction = BigFraction.valueOf(currentChoice.getValue(), currentChoice.getValueDenominator());
                else
                    currentChoiceFraction = BigFraction.valueOf(currentChoice.getValue(), 1);

                if(currentChoiceFraction.doubleValue() > correctAnswerFraction.doubleValue())
                    correctAnswerChoice = currentChoice;
            }
        }
        else {
            for(int i = 1; i < choices.size(); i++) {
                BigFraction correctAnswerFraction;
                if(correctAnswerChoice.getValueDenominator() != null)
                    correctAnswerFraction = BigFraction.valueOf(correctAnswerChoice.getValue(), correctAnswerChoice.getValueDenominator());
                else
                    correctAnswerFraction = BigFraction.valueOf(correctAnswerChoice.getValue(), 1);

                Choice currentChoice = choices.get(i);
                BigFraction currentChoiceFraction;
                if(currentChoice.getValueDenominator() != null)
                    currentChoiceFraction = BigFraction.valueOf(currentChoice.getValue(), currentChoice.getValueDenominator());
                else
                    currentChoiceFraction = BigFraction.valueOf(currentChoice.getValue(), 1);

                if(currentChoiceFraction.doubleValue() < correctAnswerFraction.doubleValue())
                    correctAnswerChoice = currentChoice;
            }
        }
        
        question.setCorrectAnswer(correctAnswerChoice.getValue());
        question.setCorrectAnswerDenominator(correctAnswerChoice.getValueDenominator());
        
        return question;
    }
    
}
