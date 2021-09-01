package control.utility.dto;

import java.io.Serializable;

/**
     * Auxiliary class that rappresent answer structure from frontend
     * 
     * @author Francesco Capriglione
     * @version 1.0
     */
    public class QuizAnswerDTO implements Serializable {
        
        private Integer answer;
        private Integer answerDenominator;
        private String answerText;
        private int questionID;
        private int questionTypeID;
        private int seconds;
        private boolean skipped;
        private boolean correct;
        
        public QuizAnswerDTO() { }

        public QuizAnswerDTO(int questionID, int questionTypeID, Integer answer, Integer answerDenominator, String answerText, int seconds, boolean skipped) {
            this.answer = answer;
            this.answerDenominator = answerDenominator;
            this.answerText = answerText;
            this.questionID = questionID;
            this.questionTypeID = questionTypeID;
            this.seconds = seconds;
            this.skipped = skipped;
        }

        public Integer getAnswer() {
            return answer;
        }

        public void setAnswer(Integer answer) {
            this.answer = answer;
        }

        public Integer getAnswerDenominator() {
            return answerDenominator;
        }

        public void setAnswerDenominator(Integer answerDenominator) {
            this.answerDenominator = answerDenominator;
        }

        public String getAnswerText() {
            return answerText;
        }

        public void setAnswerText(String answerText) {
            this.answerText = answerText;
        }

        public int getQuestionID() {
            return questionID;
        }

        public void setQuestionID(int questionID) {
            this.questionID = questionID;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public boolean isSkipped() {
            return skipped;
        }

        public void setSkipped(boolean skipped) {
            this.skipped = skipped;
        }

        public int getQuestionTypeID() {
            return questionTypeID;
        }

        public void setQuestionTypeID(int questionTypeID) {
            this.questionTypeID = questionTypeID;
        }

        public boolean isCorrect() {
            if(this.skipped)
                return false;
            
            return correct;
        }

        public void setIsCorrect(boolean isCorrect) {
            this.correct = isCorrect;
        }
                
    }