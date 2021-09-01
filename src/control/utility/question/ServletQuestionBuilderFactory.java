/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question;

import control.utility.question.builder.ServletCalculationQuestionBuilder;
import control.utility.question.builder.ServletGenericProblemQuestionBuilder;
import control.utility.question.builder.ServletGuessAreaQuestionBuilder;
import control.utility.question.builder.ServletGuessBallNumberQuestionBuilder;
import control.utility.question.builder.ServletHidingNumberQuestionBuilder;
import control.utility.question.builder.ServletListeningQuestionBuilder;
import control.utility.question.builder.ServletMathematicProblemQuestionBuilder;
import control.utility.question.builder.ServletMinOrMaxQuestionBuilder;
import control.utility.question.builder.ServletNumbersLineQuestionBuilder;
import control.utility.question.builder.ServletPositioningQuestionBuilder;
import control.utility.question.builder.ServletQuestionBuilder;
import control.utility.question.builder.ServletReorderSequenceQuestionBuilder;
import control.utility.question.builder.ServletTypeTheNumberQuestionBuilder;
import entity.quiz.QuestionSection;

/**
 * ServletQuestionBuilderFactory class delegate to a specific class the task to build the question, based on the questionType
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletQuestionBuilderFactory {
    
    public static ServletQuestionBuilder retrieveQuestionBuilder(QuestionSection questionSection) {
        int questionSectionID = questionSection.getQuestionType().getId();
        
        switch(questionSectionID) {
            case 1:
                return new ServletListeningQuestionBuilder(questionSection);
            case 2:
            case 3:
                return new ServletCalculationQuestionBuilder(questionSection);
            case 4:
                return new ServletMathematicProblemQuestionBuilder(questionSection);
            case 5:
                return new ServletNumbersLineQuestionBuilder(questionSection);
            case 6:
                return new ServletTypeTheNumberQuestionBuilder(questionSection);
            case 7:
                return new ServletPositioningQuestionBuilder(questionSection);
            case 8:
                return new ServletMinOrMaxQuestionBuilder(questionSection);
            case 9:
                return new ServletHidingNumberQuestionBuilder(questionSection);
            case 10:
                return new ServletGuessAreaQuestionBuilder(questionSection);
            case 11:
                return new ServletGuessBallNumberQuestionBuilder(questionSection);
            case 12:
                return new ServletReorderSequenceQuestionBuilder(questionSection);
            case 13:
                return new ServletGenericProblemQuestionBuilder(questionSection);
            default:
                throw new IllegalArgumentException("Unknown questionSection");
        }
    }
}
