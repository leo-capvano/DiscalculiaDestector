/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import entity.question.ListeningQuestion;
import entity.question.Question;
import entity.quiz.QuestionSection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletListeningQuestionBuilder extends ServletQuestionBuilder {

    private static final String TO_LISTEN_TO_PARAMETER = "number";
    
    public ServletListeningQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int toListenTo = Integer.parseInt(request.getParameter(TO_LISTEN_TO_PARAMETER));
        
        return new ListeningQuestion(questionText, super.questionSection, toListenTo);
    }
    
}
