package control.utility.question.builder;

import com.github.kiprobinson.bigfraction.BigFraction;
import entity.question.Choice;
import entity.question.Question;
import entity.question.ReorderSequenceQuestion;
import entity.quiz.QuestionSection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletReorderSequenceQuestionBuilder extends ServletQuestionBuilder {
    
    private class ChoiceComparator implements Comparator<Choice> {

        @Override
        public int compare(Choice o1, Choice o2) {
            BigFraction choice1Fraction, choice2Fraction;
            
            if(o1.getValueDenominator() == null)
                choice1Fraction = BigFraction.valueOf(o1.getValue(), 1);
            else
                choice1Fraction = BigFraction.valueOf(o1.getValue(), o1.getValueDenominator());
            
            if(o2.getValueDenominator() == null)
                choice2Fraction = BigFraction.valueOf(o2.getValue(), 1);
            else
                choice2Fraction = BigFraction.valueOf(o2.getValue(), o2.getValueDenominator());
            
            double choice1Value = choice1Fraction.doubleValue();
            double choice2Value = choice2Fraction.doubleValue();
            
            if(choice1Value == choice2Value)
                return 0;
            else if(choice1Value > choice2Value)
                return 1;
            else
                return -1;
        }
        
    }
    
    private static final String OPERATION_TYPE_PARAMETER = "operation-type";
    private static final String CHOICE_PREFIX = "choice-";
    private static final String CHOICE_DENOMINATOR_PREFIX = "choiceDenominator-";

    public ServletReorderSequenceQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        String operationType = request.getParameter(OPERATION_TYPE_PARAMETER);
        
        ReorderSequenceQuestion question = new ReorderSequenceQuestion();
        question.setQuestionText(questionText);
        question.setQuestionSection(super.questionSection);
        
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
        
        List<Choice> clonedChoices = new ArrayList<Choice>(choices);
        
        ChoiceComparator comparator = new ChoiceComparator();
        if(operationType.equalsIgnoreCase("desc"))
            Collections.sort(clonedChoices, Collections.reverseOrder(comparator));
        else
            Collections.sort(clonedChoices, comparator);
        
        String correctAnswer = "";
        for(Choice choice : clonedChoices) {
            correctAnswer += " - " + choice.getValue();
            if(choice.getValueDenominator() != null && choice.getValueDenominator() != 1)
                correctAnswer += "/" + choice.getValueDenominator();
        }
        
        int startingIndex = correctAnswer.indexOf(" - ") + 3;
        correctAnswer = correctAnswer.substring(startingIndex);
        
        question.setCorrectAnswerText(correctAnswer);
        
        return question;
    }
}
