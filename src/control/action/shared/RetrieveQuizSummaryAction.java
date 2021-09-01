/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control.action.shared;

import control.action.Action;
import entity.account.Account;
import entity.account.Administrator;
import entity.account.Teacher;
import entity.question.AcrossCalculationQuestion;
import entity.question.Choice;
import entity.question.GuessAreaQuestion;
import entity.question.GuessBallNumberQuestion;
import entity.question.HidingNumberQuestion;
import entity.question.InColumnCalculationQuestion;
import entity.question.ListeningQuestion;
import entity.question.MathematicProblemQuestion;
import entity.question.MinOrMaxQuestion;
import entity.question.NumbersLineQuestion;
import entity.question.PositioningQuestion;
import entity.question.Question;
import entity.question.ReorderSequenceQuestion;
import entity.question.TypeTheNumberQuestion;
import entity.quiz.DyscalculiaQuiz;
import entity.quiz.QuestionSection;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DyscalculiaQuizModel;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public class RetrieveQuizSummaryAction extends Action {

    private static final String FILE_CONTENT_TYPE = "application/vnd.ms-excel";
    
    private DyscalculiaQuizModel dyscalculiaQuizModel;
    
    public RetrieveQuizSummaryAction() {
		dyscalculiaQuizModel = new DyscalculiaQuizModel();
	}
    
    @Override
    protected String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ParseException {
        int quizID = Integer.parseInt(request.getParameter("quizID"));
        
        DyscalculiaQuiz quiz = dyscalculiaQuizModel.doRetrieveById(quizID);
        if(quiz == null)
            return null;
        
        response.setContentType(FILE_CONTENT_TYPE);
        response.setHeader("Content-Disposition","attachment; filename=" + quiz.getName().replaceAll(" ", "") + ".xls");
        PrintWriter writer = response.getWriter();
        
        List<QuestionSection> questionSections = quiz.getSections();
        for(int i = 0; i < questionSections.size(); i++) {
            QuestionSection questionSection = questionSections.get(i);
            writer.println("Nome sezione\tDescrizione sezione\t");
            writer.println(questionSection.getName() + "\t" + questionSection.getDescription()+"\t");
            
            writer.println();
            
            List<Question> questions = questionSection.getQuestions();
            
            if(questionSection.getQuestionsClass().equals(InColumnCalculationQuestion.class))
                printInColumnCalculationQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(AcrossCalculationQuestion.class))
                printAcrossCalculationQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(GuessAreaQuestion.class))
                printGuessAreaQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(GuessBallNumberQuestion.class))
                printGuessBallNumberQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(HidingNumberQuestion.class))
                printHidingNumberQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(ListeningQuestion.class))
                printListeningQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(MathematicProblemQuestion.class))
                printMathematicProblemQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(MinOrMaxQuestion.class))
                printMinOrMaxQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(NumbersLineQuestion.class))
                printNumbersLineQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(PositioningQuestion.class))
                printPositioningQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(ReorderSequenceQuestion.class))
                printReorderSequenceQuestions(questions, writer);
            else if(questionSection.getQuestionsClass().equals(TypeTheNumberQuestion.class))
                printTypeTheNumberQuestions(questions, writer);
            
            writer.println("\n");
            
        }
        
        writer.println();
        
        return null;
    }

    @Override
    protected boolean validateRequest(HttpServletRequest request, HttpServletResponse response) {
        Account loggedIn = (Account) request.getSession().getAttribute("account");
        if(!(loggedIn instanceof Teacher) && !(loggedIn instanceof Administrator))
            return false;
        
        String id = request.getParameter("quizID");
        
        try {
            Integer.parseInt(id);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected String getResponseType() {
        return Action.FILE_RESPONSE;
    }

    private void printInColumnCalculationQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tOperazione\tSoluzione");
        for(Question currentQuestion : questions) {
            InColumnCalculationQuestion question = (InColumnCalculationQuestion) currentQuestion;
            
            String operation = question.getOperand1() + " ";
            
            switch(question.getOperationType()) {
                case ADDITION:
                    operation += "+ ";
                    break;
                case SUBTRACTION:
                    operation += "- ";
                    break;
                case MULTIPLICATION:
                    operation += "x ";
                    break;
                case DIVISION:
                    operation += "diviso ";
            }
            
            operation += question.getOperand2();
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + operation + "\t" + question.getCorrectAnswer());
        }
    }

    private void printAcrossCalculationQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tOperazione\tSoluzione");
        for(Question currentQuestion : questions) {
            AcrossCalculationQuestion question = (AcrossCalculationQuestion) currentQuestion;
            
            String operation = question.getOperand1() + " ";
            if(question.getOperand1Denominator() != null && !question.getOperand2Denominator().equals(1)) {
                operation += "/ " + question.getOperand1Denominator();
            }
            
            switch(question.getOperationType()) {
                case ADDITION:
                    operation += " + ";
                    break;
                case SUBTRACTION:
                    operation += " - ";
                    break;
                case MULTIPLICATION:
                    operation += " x ";
                    break;
                case DIVISION:
                    operation += " diviso ";
            }
            
            operation += question.getOperand2();
            if(question.getOperand2Denominator() != null && !question.getOperand2Denominator().equals(1)) {
                operation += "/ " + question.getOperand2Denominator();
            }
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + operation + "\t" + question.getCorrectAnswer());
        }
    }

    private void printGuessAreaQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tArea corretta da generare");
        for(Question currentQuestion : questions) {
            GuessAreaQuestion question = (GuessAreaQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswer());
            printChoices(question.getChoices(), writer);
        }
    }

    private void printChoices(List<Choice> choices, PrintWriter writer) {
        writer.println("\t\t\t\tScelte");
        for(int i = 0; i < choices.size(); i++) {
            Choice currentChoice = choices.get(i);
            
            String toPrint = "\t\t\t\t\t" + currentChoice.getValue();
            if(currentChoice.getValueDenominator() != null)
                toPrint += "/" + currentChoice.getValueDenominator();
            
            writer.println(toPrint);
        }
    }

    private void printGuessBallNumberQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tNumero di cerchi corretti");
        for(Question currentQuestion : questions) {
            GuessBallNumberQuestion question = (GuessBallNumberQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswer());
            printChoices(question.getChoices(), writer);
        }
    }

    private void printHidingNumberQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tNumero da nascondere");
        for(Question currentQuestion : questions) {
            HidingNumberQuestion question = (HidingNumberQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswer());
            printChoices(question.getChoices(), writer);
        }
    }

    private void printListeningQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tNumero da ascoltare");
        for(Question currentQuestion : questions) {
            ListeningQuestion question = (ListeningQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getToListenTo());
        }
    }

    private void printMathematicProblemQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tProblema\tSoluzione");
        for(Question currentQuestion : questions) {
            MathematicProblemQuestion question = (MathematicProblemQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswer());
        }
    }

    private void printMinOrMaxQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tSoluzione");
        for(Question currentQuestion : questions) {
            MinOrMaxQuestion question = (MinOrMaxQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswer());
            printChoices(question.getChoices(), writer);
        }
    }

    private void printNumbersLineQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tEstremo sinistro\tEstremo destro\tSoluzione");
        for(Question currentQuestion : questions) {
            NumbersLineQuestion question = (NumbersLineQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getLeftBoundary() + "\t" + question.getRightBoundary() + "\t" + question.getCorrectAnswer());
        }
    }

    private void printPositioningQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tSoluzione");
        for(Question currentQuestion : questions) {
            PositioningQuestion question = (PositioningQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswer());
            printChoices(question.getChoices(), writer);
        }
    }

    private void printReorderSequenceQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tSoluzione");
        for(Question currentQuestion : questions) {
            ReorderSequenceQuestion question = (ReorderSequenceQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswerText());
            printChoices(question.getChoices(), writer);
        }
    }

    private void printTypeTheNumberQuestions(List<Question> questions, PrintWriter writer) {
        writer.println("\t\tDomanda\tSoluzione");
        for(Question currentQuestion : questions) {
            TypeTheNumberQuestion question = (TypeTheNumberQuestion) currentQuestion;
            
            writer.println("\t\t" + question.getQuestionText() + "\t" + question.getCorrectAnswerText());
        }
    }
    
}
