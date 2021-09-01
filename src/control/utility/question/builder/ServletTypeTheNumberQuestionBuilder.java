/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import entity.question.Question;
import entity.question.TypeTheNumberQuestion;
import entity.quiz.QuestionSection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utility.ItalianNumberTranslator;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletTypeTheNumberQuestionBuilder extends ServletQuestionBuilder {

    private static final String TO_BE_TYPED_PARAMETER = "number";
    
    public ServletTypeTheNumberQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int toBeTyped = Integer.parseInt(request.getParameter(TO_BE_TYPED_PARAMETER));
        
        return new TypeTheNumberQuestion(questionText, super.questionSection, toBeTyped, ItalianNumberTranslator.translate(toBeTyped));
    }
    
}
