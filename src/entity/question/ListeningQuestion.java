package entity.question;

import entity.factory.QuestionTypeFactory;
import entity.factory.QuestionTypeFactory.QuestionTypeID;
import entity.quiz.QuestionSection;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Francesco Capriglione
 * @version 1.1
 */
@Entity
@DiscriminatorValue(value = ListeningQuestion.DISCRIMINATOR_VALUE)
public class ListeningQuestion extends Question {
    
    protected static final String DISCRIMINATOR_VALUE = "1";
    
    @Column(name = "toListenTo")
    private int toListenTo;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public ListeningQuestion() { 
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeID.LISTENING_QUESTION));
    }
    
    public ListeningQuestion(String questionText, QuestionSection questionSection, int toListenTo) {
        super.setQuestionText(questionText);
        super.setQuestionSection(questionSection);
        super.setCorrectAnswer(toListenTo);
        super.setQuestionType(QuestionTypeFactory.getQuestionType(QuestionTypeID.LISTENING_QUESTION));
        this.toListenTo = toListenTo;
    }

    public int getToListenTo() {
        return toListenTo;
    }

    public void setToListenTo(int toListenTo) {
        this.toListenTo = toListenTo;
    }
    //</editor-fold>
}
