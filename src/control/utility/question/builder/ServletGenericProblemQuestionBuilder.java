/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.utility.question.builder;

import entity.question.Choice;
import entity.question.GenericProblemQuestion;
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
public class ServletGenericProblemQuestionBuilder extends ServletQuestionBuilder {
    
    private static final String CORRECT_ANSWER_PARAMETER = "number";
    private static final String CORRECT_ANSWER_DENOMINATOR_PARAMETER = "numberDenominator";
    private static final String CHOICE_PREFIX = "choice-";
    private static final String CHOICE_DENOMINATOR_PREFIX = "choiceDenominator-";
    
    public ServletGenericProblemQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }

    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int correctAnswer = Integer.parseInt(request.getParameter(CORRECT_ANSWER_PARAMETER));
        Integer correctAnswerDenominator;
        
        try {
            correctAnswerDenominator = Integer.parseInt(request.getParameter(CORRECT_ANSWER_DENOMINATOR_PARAMETER));
        } catch (NumberFormatException e) {
            correctAnswerDenominator = null;
        }
        
        GenericProblemQuestion question = new GenericProblemQuestion(questionText, super.questionSection, correctAnswer, correctAnswerDenominator);
        int numOfChoices = super.questionSection.getNumOfChoices();
        List<Choice> choices = new ArrayList<Choice>();
        for(int i = 1; i <= numOfChoices; i++) {
            int currentChoiceValue = Integer.parseInt(request.getParameter(CHOICE_PREFIX + i));
            Integer choiceDenominator;
            
            try {
                choiceDenominator = Integer.parseInt(request.getParameter(CHOICE_DENOMINATOR_PREFIX + i));
            } catch(NumberFormatException e) {
                choiceDenominator = null;
            }
            
            Choice choice = new Choice(currentChoiceValue, choiceDenominator, question);
            choices.add(choice);
        }
        question.setChoices(choices);
        
        return question;
    }

}
