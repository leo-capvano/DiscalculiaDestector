/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import entity.question.Question;
import entity.quiz.QuestionSection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public abstract class ServletQuestionBuilder {
        protected static final String QUESTION_TEXT_PARAMETER = "questionText";
    
        protected final QuestionSection questionSection;
        
        public ServletQuestionBuilder(QuestionSection questionSection) {
            this.questionSection = questionSection;
        }
        
        public final Question retrieveQuestion(HttpServletRequest request, HttpServletResponse response) {
            return this.buildQuestion(request, response);
        }
        
        protected abstract Question buildQuestion(HttpServletRequest request, HttpServletResponse response);
}
