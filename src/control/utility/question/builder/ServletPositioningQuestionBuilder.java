/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import com.github.kiprobinson.bigfraction.BigFraction;
import entity.question.Choice;
import entity.question.PositioningQuestion;
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
public class ServletPositioningQuestionBuilder extends ServletQuestionBuilder {

    private static final String TO_BE_PLACED_PARAMETER = "number";
    private static final String TO_BE_PLACED_DENOMINATOR_PARAMETER = "number-denominator";
    private static final String CHOICE_PREFIX = "choice-";
    private static final String CHOICE_DENOMINATOR_PREFIX = "choiceDenominator-";
    
    private static final String UNABLE_TO_FIND_CORRECT_ANSWER_MESSAGE = "Impossibile stabilire una risposta corretta. Il numero da trovare deve essere "
            + "compreso tra scelte a due a due adiacenti.";
    
    public ServletPositioningQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int toBePlaced = Integer.parseInt(request.getParameter(TO_BE_PLACED_PARAMETER));
        Integer toBePlacedDenominator;
        
        try {
            toBePlacedDenominator = Integer.parseInt(request.getParameter(TO_BE_PLACED_DENOMINATOR_PARAMETER));
        } catch(NumberFormatException e) {
            toBePlacedDenominator = null;
        }
        
        //Using BigFraction for simplify the fraction
        BigFraction simplifiedFraction = null;
        if(toBePlacedDenominator != null)
            simplifiedFraction = BigFraction.valueOf(toBePlaced, toBePlacedDenominator);
        
        //Creating question without the correct answer and without choices
        PositioningQuestion question = new PositioningQuestion();
        question.setQuestionText(questionText);
        question.setQuestionSection(questionSection);
        if(simplifiedFraction == null) {
            question.setToBePlaced(toBePlaced);
            if(toBePlacedDenominator != null && !toBePlacedDenominator.equals(1))
                question.setToBePlacedDenominator(toBePlacedDenominator);
        }
        else {
            question.setToBePlaced(simplifiedFraction.getNumerator().intValue());
            int denominator = simplifiedFraction.getDenominator().intValue();
            if(denominator != 1)
                question.setToBePlacedDenominator(denominator);
        }
        
        //Getting choices
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
            Choice currentChoice = new Choice(currentChoiceValue, choiceDenominator, question);
            choices.add(currentChoice);
        }
        //Setting choices
        question.setChoices(choices);
        
        //Getting correctAnswer
        int correctAnswer = -1;
        /*I assume that is fraction becouse the user can decide to use integer and fractional number in the same question.
            Since it establishes whether a number is integer or fraction would ruin the readability of the code, i prefer to do this assertion.
            In terms of result nothing change.
        */
        BigFraction toPlaceFraction;
        if(question.getToBePlacedDenominator() == null)
            toPlaceFraction = BigFraction.valueOf(question.getToBePlaced(), 1);
        else
            toPlaceFraction = BigFraction.valueOf(question.getToBePlaced(), question.getToBePlacedDenominator());
        
        for(int i = 0; i < choices.size() - 1; i++) {
            Choice leftChoice = choices.get(i);
            Choice rightChoice = choices.get(i + 1);
            
            int denominator;
            if(leftChoice.getValueDenominator() != null)
                denominator = leftChoice.getValueDenominator();
            else
                denominator = 1;
            BigFraction leftChoiceFraction = BigFraction.valueOf(leftChoice.getValue(), denominator);
            
            if(rightChoice.getValueDenominator() != null)
                denominator = rightChoice.getValueDenominator();
            else
                denominator = 1;
            BigFraction rightChoiceFraction = BigFraction.valueOf(rightChoice.getValue(), denominator);
            
            if(toPlaceFraction.doubleValue() > leftChoiceFraction.doubleValue() && toPlaceFraction.doubleValue() < rightChoiceFraction.doubleValue()) {
                correctAnswer = i + 1;
                break;
            }
        }
        
        if(correctAnswer == -1)
            throw new IllegalArgumentException(UNABLE_TO_FIND_CORRECT_ANSWER_MESSAGE);
        //Setting correctAnswer
        question.setCorrectAnswer(correctAnswer);
        
        return question;
    }
    
}
