/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import com.github.kiprobinson.bigfraction.BigFraction;
import entity.enums.Operation;
import entity.question.AcrossCalculationQuestion;
import entity.question.InColumnCalculationQuestion;
import entity.question.Question;
import entity.quiz.QuestionSection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletCalculationQuestionBuilder extends ServletQuestionBuilder {

    private static final String OPERAND_ONE_PARAMETER = "number-1";
    private static final String OPERAND_ONE_DENOMINATOR_PARAMETER = "number-1-denominator";
    private static final String OPERAND_TWO_PARAMETER = "number-2";
    private static final String OPERAND_TWO_DENOMINATOR_PARAMETER = "number-2-denominator";
    private static final String OPERATION_PARAMETER = "operation-type";
    
    public ServletCalculationQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected final Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(ServletQuestionBuilder.QUESTION_TEXT_PARAMETER);
        String operationString = request.getParameter(OPERATION_PARAMETER);
        
        Integer operand1 = Integer.parseInt(request.getParameter(OPERAND_ONE_PARAMETER));
        Integer operand2 = Integer.parseInt(request.getParameter(OPERAND_TWO_PARAMETER));
        Operation operation;
        
        switch (operationString) {
            case "+":
                operation = Operation.ADDITION;
                break;
            case "-":
                operation = Operation.SUBTRACTION;
                break;
            case "*":
                operation = Operation.MULTIPLICATION;
                break;
            case "/":
                operation = Operation.DIVISION;
                break;
            default:
                throw new IllegalArgumentException();
        }
        
        Class clazz = super.questionSection.getQuestionsClass();
        
        if(clazz.equals(InColumnCalculationQuestion.class)) {
            int correctAnswer = 0;
            
            switch(operation) {
                case ADDITION:
                    correctAnswer = operand1 + operand2;
                    break;
                case SUBTRACTION:
                    correctAnswer = operand1 - operand2;
                    break;
                case MULTIPLICATION:
                    correctAnswer = operand1 * operand2;
                    break;
                case DIVISION:
                    if(operand2 == 0)
                        throw new IllegalArgumentException("Il secondo operando deve essere diverso da 0.");
                    
                    correctAnswer = operand1 / operand2;
                    break;
            }
            
            return new InColumnCalculationQuestion(questionText, super.questionSection, correctAnswer, operand1, operand2, operation);
        }
        else {
            Integer operand1Denominator;
            Integer operand2Denominator;

            try {
                operand1Denominator = Integer.parseInt(request.getParameter(OPERAND_ONE_DENOMINATOR_PARAMETER));
            } catch(NumberFormatException e) {
                operand1Denominator = 1;
            }

            try {
                operand2Denominator = Integer.parseInt(request.getParameter(OPERAND_TWO_DENOMINATOR_PARAMETER));
            } catch(NumberFormatException e) {
                operand2Denominator = 1;
            }
            
            //If the denominators are zero
            if(operand1Denominator.equals(0))
                throw new NumberFormatException("Il denominatore non può essere 0");
            if(operand2Denominator.equals(0))
                throw new NumberFormatException("Il denominatore non può essere 0");
            
            //If the denominators are both null or both equals to 1
            if(operand1Denominator.equals(1) && operand2Denominator.equals(1)) {
                
                int correctAnswer = 0;
                
                switch(operation) {
                    case ADDITION:
                        correctAnswer = operand1 + operand2;
                        break;
                    case SUBTRACTION:
                        correctAnswer = operand1 - operand2;
                        break;
                    case MULTIPLICATION:
                        correctAnswer = operand1 * operand2;
                        break;
                    case DIVISION:
                        if(operand2 == 0)
                            throw new IllegalArgumentException("Il secondo operando deve essere diverso da 0.");

                        correctAnswer = operand1 / operand2;
                        break;
                }
                return new AcrossCalculationQuestion(questionText, super.questionSection, correctAnswer, null, operand1, operand1Denominator, operand2, operand2Denominator, operation);
            }
            
            //In other cases
            BigFraction fraction1 = BigFraction.valueOf(operand1, operand1Denominator);
            BigFraction fraction2 = BigFraction.valueOf(operand2, operand2Denominator);
            BigFraction result = BigFraction.ONE;
            
            switch(operation) {
                case ADDITION:
                    result = fraction1.add(fraction2);
                    break;
                case SUBTRACTION:
                    result = fraction1.subtract(fraction2);
                    break;
                case MULTIPLICATION:
                    result = fraction1.multiply(fraction2);
                    break;
                case DIVISION:
                    result = fraction1.divide(fraction2);
                    break;
            }
            
            int correctAnswer = result.getNumerator().intValue();
            Integer correctAnswerDenominator;
            if(result.getDenominator().intValue() == 1)
                correctAnswerDenominator = null;
            else
                correctAnswerDenominator = result.getDenominator().intValue();
            
            return new AcrossCalculationQuestion(questionText, super.questionSection, correctAnswer, correctAnswerDenominator, operand1, operand1Denominator, operand2, operand2Denominator, operation);
        }
    }
}
