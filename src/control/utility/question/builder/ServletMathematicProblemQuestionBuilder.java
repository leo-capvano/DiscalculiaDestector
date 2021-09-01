/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import entity.question.MathematicProblemQuestion;
import entity.question.Question;
import entity.quiz.QuestionSection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletMathematicProblemQuestionBuilder extends ServletQuestionBuilder {

    private static final String CORRECT_ANSWER_PARAMETER = "number";
    
    public ServletMathematicProblemQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int correctAnswer = Integer.parseInt(request.getParameter(CORRECT_ANSWER_PARAMETER));
        
        return new MathematicProblemQuestion(questionText, super.questionSection, correctAnswer);
    }
    
}
