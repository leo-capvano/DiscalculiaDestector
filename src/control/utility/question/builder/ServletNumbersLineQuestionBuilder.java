/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import entity.question.NumbersLineQuestion;
import entity.question.Question;
import entity.quiz.QuestionSection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletNumbersLineQuestionBuilder extends ServletQuestionBuilder {

    private static final String CORRECT_ANSWER_PARAMETER = "number-3";
    private static final String LEFT_BOUNDARY_PARAMETER = "number-1";
    private static final String RIGHT_BOUNDARY_PARAMETER = "number-2";

    public ServletNumbersLineQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int correctAnswer = Integer.parseInt(request.getParameter(CORRECT_ANSWER_PARAMETER));
        int leftBoundary = Integer.parseInt(request.getParameter(LEFT_BOUNDARY_PARAMETER));
        int rightBoundary = Integer.parseInt(request.getParameter(RIGHT_BOUNDARY_PARAMETER));
        
        if(correctAnswer < leftBoundary || correctAnswer > rightBoundary)
            throw new IllegalArgumentException("La risposta corretta deve essere compresa tra il limite sinistro e quello destro");
        
        return new NumbersLineQuestion(questionText, super.questionSection, correctAnswer, leftBoundary, rightBoundary);
    }
    
}
