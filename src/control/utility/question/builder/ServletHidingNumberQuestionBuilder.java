/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import entity.question.Choice;
import entity.question.HidingNumberQuestion;
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
public class ServletHidingNumberQuestionBuilder extends ServletQuestionBuilder {
    
    private static final String CORRECT_ANSWER_PARAMETER = "number";
    private static final String CHOICE_PREFIX = "choice-";

    public ServletHidingNumberQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int correctAnswer = Integer.parseInt(request.getParameter(CORRECT_ANSWER_PARAMETER));
        
        HidingNumberQuestion question = new HidingNumberQuestion(questionText, super.questionSection, correctAnswer, correctAnswer);
        
        int numOfChoices = super.questionSection.getNumOfChoices();
        List<Choice> choices = new ArrayList<Choice>();
        for(int i = 1; i <= numOfChoices; i++) {
            int currentChoiceValue = Integer.parseInt(request.getParameter(CHOICE_PREFIX + i));
            Choice choice = new Choice(currentChoiceValue, question);
            choices.add(choice);
        }
        question.setChoices(choices);
        
        return question;
    }
    
}
