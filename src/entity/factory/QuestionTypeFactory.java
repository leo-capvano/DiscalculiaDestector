/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.factory;

import entity.quiz.QuestionType;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */

public class QuestionTypeFactory {
    
    public enum QuestionTypeID {
        
        LISTENING_QUESTION(1),
        ACROSS_CALCULATION(2),
        IN_COLUMN_CALCULATION(3),
        MATHEMATIC_PROBLEM(4),
        NUMBERS_LINE(5),
        TYPE_THE_NUMBER(6),
        POSITIONING(7),
        MIN_OR_MAX(8),
        HIDING_NUMBER(9),
        GUESS_AREA(10),
        GUESS_BALLS_NUMBER(11),
        SEQUENCE_REORDER(12),
        GENERIC_PROBLEM(13);
        
        private int id;
        
        private QuestionTypeID(int value) {
            this.id = value;
        }
        
        public int getValue() {
            return this.id;
        }
        
    }
    
    private static final Map<Integer, QuestionType> QUESTION_TYPE_MAP = new HashMap<Integer, QuestionType>();
    
    //Map initialization
    static {
        QuestionType currentQuestionType;
        
        currentQuestionType = new QuestionType("Ascolta il numero", "In questa modalit&agrave; di domanda l'utente dovr&agrave; ascoltare un numero per poi trascriverlo nell'apposita casella di testo", null);
        currentQuestionType.setId(QuestionTypeID.LISTENING_QUESTION.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Risolvi in riga", "In questa modalit&agrave; di domanda verr&agrave; mostrata un operazione che l'utente dovr&agrave; risolvere a mente", null);
        currentQuestionType.setId(QuestionTypeID.ACROSS_CALCULATION.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Risolvi in colonna", "In questa modalit&agrave; di domanda verr&agrave; mostrata un operazione che l'utente dovr&agrave; risolvere in colonna", null);
        currentQuestionType.setId(QuestionTypeID.IN_COLUMN_CALCULATION.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Problema matematico", "In questa modalit&agrave; di domanda l'utente dovr&agrave; risolvere un problema matematico per poi trascrivere il risultato nell'apposita casella di testo", null);
        currentQuestionType.setId(QuestionTypeID.MATHEMATIC_PROBLEM.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Linea dei numeri", "In questa modalit&agrave; di domanda l'utente avr&agrave; davanti una linea, con alle estremit&agrave; dei numeri, e sulla linea vi sar&agrave; un cerchio. L'utente ricever&agrave; un numero e dovr&agrave; posizionare il cerchio nella posizione corretta.", null);
        currentQuestionType.setId(QuestionTypeID.NUMBERS_LINE.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Scrivi il numero", "In questa modalit&agrave; di domanda l'utente dovr&agrave; tradurre un numero in lettere", null);
        currentQuestionType.setId(QuestionTypeID.TYPE_THE_NUMBER.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Posiziona il numero", "In questa modalit&agrave; di domanda l'utente avr&agrave; davanti una serie di numeri ordinati e dovr&agrave; posizionare un numero dato in modo che la serie rimanga ordinata.", 12);
        currentQuestionType.setId(QuestionTypeID.POSITIONING.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Minimo o massimo", "In questa modalit&agrave; di domanda l'utente avr&agrave; davanti una serie di numeri e dovr&agrave; scegliere il minimo o il massimo tra quelli presenti.", 5);
        currentQuestionType.setId(QuestionTypeID.MIN_OR_MAX.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Numero a scomparsa", "In questa modalit&agrave; di domanda verr&agrave; mostrato un numero che poi scomparir&agrave;. L'utente dovr&agrave; selezionare il numero corretto tra le possibili scelte." , 6);
        currentQuestionType.setId(QuestionTypeID.HIDING_NUMBER.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Indovina l'area occupata", "In questa modalti&agrave; di domanda l'utente dovr&agrave; individuare, tra le possibili scelte, l'immagine con la maggior area coperta da cerchi. Per realizzare questo tipo di domanda, inserisci l'area della risposta corretta che vuoi generare. Sar&agrave; il sistema a generare tutte le altre opzioni.", 4);
        currentQuestionType.setId(QuestionTypeID.GUESS_AREA.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Indovina il numero di cerchi", "In questa modalti&agrave; di domanda l'utente dovr&agrave; individuare, in maniera approssimativa, il numero di cerchi presenti in un immagine", 4);
        currentQuestionType.setId(QuestionTypeID.GUESS_BALLS_NUMBER.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Riordina sequenza", "In questa modalti&agrave; di domanda l'utente dovr&agrave; riordinare una sequenza di numeri in maniera crescente o decrescente", 8);
        currentQuestionType.setId(QuestionTypeID.SEQUENCE_REORDER.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
        
        currentQuestionType = new QuestionType("Problema generico", "In questa modalti&agrave; di domanda &egrave; possibile creare un problema senza particolari vincoli. L'utente dovr&agrave; rispondere selezionato tra le possibili scelte la risposta corretta", 6);
        currentQuestionType.setId(QuestionTypeID.GENERIC_PROBLEM.getValue());
        QUESTION_TYPE_MAP.put(currentQuestionType.getId(), currentQuestionType);
    }
    
    public static QuestionType getQuestionType(QuestionTypeID index) {
        return QUESTION_TYPE_MAP.get(index.getValue());
    }
}
