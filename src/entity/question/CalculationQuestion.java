/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.question;

import javax.persistence.Entity;
import entity.enums.Operation;
import entity.quiz.QuestionSection;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0.1
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CalculationQuestion extends Question {
    
    @Column(name = "operand1")
    private Integer operand1;
    
    @Column(name = "operand2")
    private Integer operand2;
    
    @Column(name = "operationType")
    @Enumerated(EnumType.STRING)
    private Operation operationType;
    
    // <editor-fold defaultstate="collapsed" desc="Constructors, getters, setters and equals.">
    public CalculationQuestion() { }
    
    public CalculationQuestion(String questionText, QuestionSection questionSection, int correctAnswer, int operand1, int operand2, Operation operationType) { 
        super.setQuestionText(questionText);
        super.setCorrectAnswer(correctAnswer);
        super.setQuestionSection(questionSection);
        
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operationType = operationType;
    }

    public Integer getOperand1() {
        return operand1;
    }

    public void setOperand1(Integer operand1) {
        this.operand1 = operand1;
    }

    public Integer getOperand2() {
        return operand2;
    }

    public void setOperand2(Integer operand2) {
        this.operand2 = operand2;
    }

    public Operation getOperationType() {
        return operationType;
    }

    public void setOperationType(Operation operationType) {
        this.operationType = operationType;
    }
    //</editor-fold>
}
