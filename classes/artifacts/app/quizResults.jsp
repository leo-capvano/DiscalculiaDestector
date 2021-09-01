<%@page import="java.util.ArrayList, entity.*, entity.quiz.*, entity.question.*, control.utility.dto.*"%>
<%@ page language="java" import="java.util.*"%>

<!DOCTYPE html>
<html>

    <head>
        <%@ include file="WEB-INF/util/meta.jsp" %>
        <%@ include file="WEB-INF/util/header.jsp" %> 
        <link href='https://fonts.googleapis.com/css?family=Londrina Solid' rel='stylesheet'>
        <link rel="stylesheet" type="text/css" href="css/quizResults.css" />
        <link rel="stylesheet" type="text/css" href="css/fraction.css" />
        <link rel="stylesheet" type="text/css" href="css/discalculia.css" />
        
        <title>Risultati del test</title>

        <%
            //Retrieving from session
            Object isValid = session.getAttribute("isValid");
            
            if(isValid == null || (!(boolean) isValid)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            
            QuizStatistics statistics = (QuizStatistics) session.getAttribute("quizStatistics");
            String quizName = (String) session.getAttribute("quizName");
            List<Question> questions = (List<Question>) session.getAttribute("questions");
            Map<Integer, QuizAnswerDTO> questionAnswerMap = (Map<Integer, QuizAnswerDTO>) session.getAttribute("questionAnswerMap");
            int totalNumberOfQuestions = (int) session.getAttribute("questionNumber");
            
            //Removing unused value from session 
            /*session.removeAttribute("isValid");
            session.removeAttribute("quizStatistics");
            session.removeAttribute("quizName");
            session.removeAttribute("questions");
            session.removeAttribute("questionAnswerMap");
            session.removeAttribute("questionNumber");*/
            
            int wrongAnswers = statistics.getTotalWrongAnswers();
            int correctAnswers = statistics.getTotalCorrectAnswers();
            int skippedAnswers = statistics.getTotalSkippedAnswers();
            int secondsEllapsed = statistics.getSecondsEllapsed();

            int hours = secondsEllapsed / 3600;
            int minutes = (secondsEllapsed / 60) % 60;
            int seconds = secondsEllapsed % 60;

            String converted = "";

            float correctAnswerPercentage = (float) correctAnswers * 100f / (float) totalNumberOfQuestions;
            double percentage = Math.round(correctAnswerPercentage * 100.0) / 100.0;

        %>
    </head>
    <body>

        <div class="container my-4">
            <div class="card">

                <div class="card-header text-white text-center">
                    <p class="text-center">Risultati</p>

                    <h1 class="font-weight-bolder">Test completato!</h1>
                    <small class="font-weight-light text-center"><%= quizName %></small>

                    <div class="row align-items-end justify-items-center mt-4">
                        <div class="col-sm">
                            <div class="row">
                                <div class="col-12">
                                    <span class="display-4 font-weight-normal"><%= correctAnswers %></span><span class="h2">/<%= totalNumberOfQuestions %></span>
                                </div>
                                <div class="col-12 font-weight-light text-white-50">
                                    Risposte esatte
                                </div>
                            </div>
                        </div>

                        <div class="col-sm">
                            <div class="row">
                                <div class="col-12">
                                    <span class="display-4 font-weight-normal"><%= wrongAnswers %></span><span class="h2">/<%= totalNumberOfQuestions%></span>
                                </div>
                                <div class="col-12 font-weight-light text-white-50">
                                    Risposte errate
                                </div>
                            </div>
                        </div>

                        <div class="col-sm">
                            <div class="row">
                                <div class="col-12">
                                    <span class="display-4 font-weight-normal"><%= skippedAnswers %></span><span class="h2">/<%= totalNumberOfQuestions%></span>
                                </div>
                                <div class="col-12 font-weight-light text-white-50">
                                    Domande saltate
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row align-items-end justify-items-center mt-2">
                        <div class="col-sm">
                            <div class="row">
                                <div class="col-12">
                                    <span class="display-4 font-weight-normal"><%=percentage%></span><span class="h2">%</span>
                                </div>
                                <div class="col-12 font-weight-light text-white-50">
                                    Percentuale risposte esatte
                                </div>
                            </div>
                        </div>

                        <div class="col-sm">
                            <div class="row">
                                <div class="col-12">
                                    <% if (hours > 0) {%>
                                    <span class="display-4 font-weight-normal"><%=hours%></span>h 
                                    <%}
                                        if (minutes > 0) {%>
                                    <span class="display-4 font-weight-normal"><%=minutes%></span>m
                                    <%}
                                        if (seconds > 0) {%>
                                    <span class="display-4 font-weight-normal"><%=seconds%></span>s
                                    <% } %>
                                </div>
                                <div class="col-12 font-weight-light text-white-50">
                                    Tempo impiegato
                                </div>
                            </div>
                        </div>
                    </div>

                </div> 

                <div class="card-body">
                    
                    
                    <%
                        for (int i = 0; i < questions.size(); i++) { 
                            Question question = questions.get(i);
                            QuizAnswerDTO answer = questionAnswerMap.get(question.getId());
                    %>
                        
                        <div class="card position-relative">

                        <div class="row px-4 pt-4">
                            <div class="col-10">
                                <h5 class="text-secondary font-weight-bold mb-3"><%= question.getQuestionText() %></h5>
                            </div>

                            <div class="col-2 text-right font-weight-bold">
                                <span class="question-number"><%=i + 1%></span>
                            </div>
                        </div>

                        <%if(answer.isSkipped()) {%>
                            <div class="row p-4">
                                <div class="col-6">
                                    <div class="text-black-50 font-weight-bold m-0">Hai salatato questa domanda</div>
                                </div>
                                <div class="col-6 text-right">
                                    <i class="fas fa-forward text-warning"></i>
                                </div>
                            </div>
                        <%}
                            else {
                        %>
                                <div class="row px-4 pb-4">
                                    <div class="col-12">
                                        <div class="text-black-50 font-weight-bold m-0">La tua risposta</div>
                                    </div>
                                    <div class="col-8 mt-2">
                                        <div class="row m-0 mt-2">
                                            <%if(question instanceof NumbersLineQuestion) { 
                                                NumbersLineQuestion castedQuestion = (NumbersLineQuestion) question;
                                            %>
                                                <div class="col-2 p-0 text-right">
                                                    <%=castedQuestion.getLeftBoundary() %>
                                                </div>
                                                <div class="col-8 p-0 px-2">
                                                    <input type="range" min="<%=castedQuestion.getLeftBoundary()%>" max="<%=castedQuestion.getRightBoundary() %>" value="<%= answer.getAnswer() %>" class="slider w-100" disabled>
                                                </div>
                                                <div class="col-2 p-0">
                                                    <%= castedQuestion.getRightBoundary() %>
                                                </div>
                                            <%}
                                                else if(question instanceof TypeTheNumberQuestion || question instanceof ReorderSequenceQuestion) {
                                            %>
                                                <h4 class="text-black font-weight-bold m-0"><%= answer.getAnswerText() %></h4>
                                            <% } 
                                                else { %>
                                                    <div class="frac h4 text-black font-weight-bold">
                                                        <span><%= answer.getAnswer() %></span>
                                                <%      if(answer.getAnswerDenominator() != null && !answer.getAnswerDenominator().equals(1)) { %>
                                                            <span class="symbol">/</span>
                                                            <span class="bottom"><%= answer.getAnswerDenominator() %></span>
                                                <%      } %>
                                                    </div>
                                                <% } %>
                                        </div>
                                    </div>
                                    
                                    <%if(answer.isCorrect()) { %>
                                        <div class="col-4 text-right">
                                            <i class="fas fa-check text-success"></i>
                                        </div>
                                    <%}
                                    else { %>
                                        <div class="col-4 text-right">
                                            <i class="fas fa-times text-danger"></i>
                                        </div>
                                    <% } %>
                                    
                                </div>
                        <%} %>
                        
                        <% if(answer.isSkipped() || !answer.isCorrect()) { %>
                            <div class="row px-4 pt-2 pb-4 m-0 correct-answer">
                                <div class="col-12 p-0">
                                    <div class="text-black-50 font-weight-bold m-0">Risposta corretta</div>
                                </div>

                                <div class="col-8 mt-2 p-0">
                                <%  if(question instanceof NumbersLineQuestion) { 
                                        NumbersLineQuestion castedQuestion = (NumbersLineQuestion) question; %>
                                    
                                        <div class="row m-0 mt-2">
                                            <div class="col-2 p-0 text-right">
                                                    <%=castedQuestion.getLeftBoundary() %>
                                                </div>
                                                <div class="col-8 p-0 px-2">
                                                    <input type="range" min="<%=castedQuestion.getLeftBoundary()%>" max="<%=castedQuestion.getRightBoundary() %>" value="<%= castedQuestion.getCorrectAnswer() %>" class="slider w-100" disabled>
                                                </div>
                                                <div class="col-2 p-0">
                                                    <%= castedQuestion.getRightBoundary() %>
                                                </div>
                                        </div>
                                <%  } 
                                    else if(question instanceof TypeTheNumberQuestion || question instanceof ReorderSequenceQuestion) {
                                %>
                                    <h4 class="text-black font-weight-bold m-0"><%= question.getCorrectAnswerText() %></h4>
                                <% } 
                                    else { %>
                                        <div class="frac h4 text-black font-weight-bold">
                                            <span><%= question.getCorrectAnswer() %></span>
                                    <%      if(question.getCorrectAnswerDenominator() != null && !question.getCorrectAnswerDenominator().equals(1)) { %>
                                                <span class="symbol">/</span>
                                                <span class="bottom"><%= question.getCorrectAnswerDenominator() %></span>
                                    <%      } %>
                                        </div>
                                    <% } %>
                                    
                                </div>

                                <div class="col-4 text-right p-0">
                                    <i class="fas fa-check text-success"></i>
                                </div>

                            </div>
                        <%}%>
                        </div>
                    <%}%>
                        

                    </div>

                </div>
                <div class="card-footer">
                    <a class="btn btn-primary" href="index.jsp">Home</a>
                </div>
            </div>
        </div>
    </body>
</html>