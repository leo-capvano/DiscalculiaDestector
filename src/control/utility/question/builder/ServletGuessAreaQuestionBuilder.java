/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.utility.question.builder;

import entity.question.Choice;
import entity.question.GuessAreaQuestion;
import entity.question.Question;
import entity.quiz.QuestionSection;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class ServletGuessAreaQuestionBuilder extends ServletQuestionBuilder {

    private static final String CORRECT_ANSWER_PARAMETER = "number";
    
    private static final String UNABLE_TO_GENERATE_AREA_ERROR = "Impossibile generare automaticamente le aree. Riprova tra qualche istante";

    public ServletGuessAreaQuestionBuilder(QuestionSection questionSection) {
        super(questionSection);
    }
    
    @Override
    protected Question buildQuestion(HttpServletRequest request, HttpServletResponse response) {
        String questionText = request.getParameter(QUESTION_TEXT_PARAMETER);
        int areaToFind = Integer.parseInt(request.getParameter(CORRECT_ANSWER_PARAMETER));
        
        GuessAreaQuestion question = new GuessAreaQuestion(questionText, super.questionSection, areaToFind);
        
        //Getting choices
        int numOfChoices = super.questionSection.getNumOfChoices();
        int OFFSET = 1750;
        final int MAX_AREA = 40000;
        final int RANDOM_PROTECTION = 1000000;

        if(areaToFind > MAX_AREA)
                areaToFind = MAX_AREA;

        if(areaToFind <= 10000)
                OFFSET = 1000;

        Random random = new Random();

        int correctPlace = random.nextInt(numOfChoices);
        int protectionCounter = 0;

        int[] generatedAreas = new int[numOfChoices];

        generatedAreas[correctPlace] = areaToFind;

        final int RANDOM_MAX = areaToFind - OFFSET;
        final int RANDOM_MIN = OFFSET;
        //Generate other area 
        for(int i = 0; i < numOfChoices; i++) {
            if(i != correctPlace) {
                //The new generated area is between OFFSET and areaToFind; 
                int generatedNumber = (int) (random.nextDouble() * (RANDOM_MAX - RANDOM_MIN + 1) + RANDOM_MIN);

                /**
                 * Check if the new number overlap other generated number
                 * The generated number must be at max RANDOM_MAX and must have at last OFFSET integers difference from others
                 */
                for(int j = 0; j < i && protectionCounter < RANDOM_PROTECTION; j++, protectionCounter++) {

                    int difference;

                    if(generatedNumber > generatedAreas[j])
                        difference = generatedNumber - generatedAreas[j];
                    else
                        difference = generatedAreas[j] - generatedNumber;

                    if(difference <= OFFSET) {
                        //The generated number have a not sufficient difference
                        generatedNumber += OFFSET;

                        if(generatedNumber > RANDOM_MAX)
                            //Generate another number if the generated number is 
                            generatedNumber = (int) random.nextDouble() * (RANDOM_MAX - RANDOM_MIN + 1) + RANDOM_MIN;

                        j = -1;
                    }
                }

                if(protectionCounter >= RANDOM_PROTECTION)
                    throw new RuntimeException(UNABLE_TO_GENERATE_AREA_ERROR);

                protectionCounter = 0;
                generatedAreas[i] = generatedNumber;
            }
        }

        if(protectionCounter >= RANDOM_PROTECTION) {
            throw new RuntimeException(UNABLE_TO_GENERATE_AREA_ERROR);
        }
        
        List<Choice> choices = new ArrayList<Choice>();
        for(int i = 0; i < generatedAreas.length; i++) {
            Choice currentChoice = new Choice(generatedAreas[i], question);
            choices.add(currentChoice);
        }
        
        question.setChoices(choices);
        
        return question;
    }
    
}
