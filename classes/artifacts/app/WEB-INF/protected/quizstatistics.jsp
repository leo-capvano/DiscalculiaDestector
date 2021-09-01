<%@page import="entity.enums.PatientType"%>
<%@page import="entity.enums.Gender"%>
<%@page import="entity.QuizStatistics"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    List<QuizStatistics> quizStatistics = (List<QuizStatistics>) request.getAttribute("quizList");
    
    DyscalculiaPatient patient = quizStatistics.isEmpty() ? (DyscalculiaPatient) session.getAttribute("account"): quizStatistics.get(0).getPatient();
    String surname = patient.getSurname() == null ? "-" : patient.getSurname();
    String degree = patient.getUniversityDegree() == null ? "-" : patient.getUniversityDegree().getValue();
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>
        <link rel="stylesheet" type="text/css" href="../css/quizstatistics.css">
        <link rel="stylesheet" type="text/css" href="../css/userlist.css">
        <title>Lista dei quiz svolti</title>
    </head>
    <body>
        <%@ include file="../../WEB-INF/util/navbar.jsp" %>

        <div id="wrapper">
            <%@ include file="../../WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">
                <div class="container-fluid">    
                    <div class="row my-4">
                        <div class="col-6">
                            <h2 class="text-left">Test effettuati</h5>
                            <div class="border-top mt-2 mb-4"></div>
                        </div>
                    </div>
                    <div class="row justify-content-center">
                        <% if(quizStatistics.isEmpty()) { %>
                            <div class="col-12 col-sm-5 mx-2 my-4 item">
                                Ancora nessun quiz
                            </div>
                        <% }%>
                        <% for(QuizStatistics quizStatistic : quizStatistics) { %>
                            <div class="col-12 col-sm-5 mx-2 my-4 item">
                                <div class="row py-4 px-2">
                                    <div class="col-12">
                                        <em><%= quizStatistic.getDate().toString() %></em>
                                    </div>
                                    <div class="col-12 text-uppercase font-weight-bold mb-4">
                                        <%= quizStatistic.getQuiz().getName() %>
                                    </div>
                                    <div class="col-12">
                                        <a href="statisticdetail?detailID=<%=quizStatistic.getId()%>" class="btn btn-outline-primary btn-block">Dettagli</a>
                                    </div>
                                </div>
                            </div>
                        <%}%>
                    </div>
                    
                    <%if(!quizStatistics.isEmpty()) { %>
                    
                    <div class="row my-4">
                        <div class="col-6">
                            <h2 class="text-left">Informazioni paziente</h5>
                            <div class="border-top mt-2 mb-4"></div>
                        </div>
                    </div>
                    
                    <div class="card my-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-12 col-xl-2 align-self-center">
                                    <div class="row text-center">
                                        <div class="col-12 mb-2">
                                            <%if(patient.getGender() == Gender.MALE) { %>
                                                    <div class="round-image-wrapper no-profile-picture male"></div>
                                                <%} else {%>
                                                    <div class="round-image-wrapper no-profile-picture female"></div>
                                                <%} %>
                                        </div>
                                        <div class="col-12 font-weight-bold">
                                                <%= patient.getName() %> <%=surname%> 

                                                <%if(patient.getGender() == Gender.MALE) { %>
                                                        <i class="fas fa-mars"></i>
                                                <%} else {%>
                                                        <i class="fas fa-venus"></i>
                                                <%} %>
                                        </div>
                                        <div class="col-12 text-secondary">
                                            <i class="fas fa-calendar-alt"></i> <%= patient.getDateOfBirth().toString()%>
                                        </div>
                                        <div class="col-12 text-secondary">
                                            <i class="fas fa-map-pin"></i> <%= patient.getRegion().getValue() %>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-12 col-md-6 col-xl-5">
                                    <div class="row">
                                        <div class="col-12">
                                            <h5 class="text-left">Istruzione</h5>
                                            <div class="border-top mt-2 mb-4"></div>
                                        </div>
                                    </div>

                                    <div class="row mb-2 align-items-center">
                                        <div class="col-8 font-weight-bold">Laurea:</div>
                                        <div class="col-12 col-sm-4 font-weight-light"><%=degree%></div>
                                    </div>

                                    <div class="row mb-2 align-items-center">
                                        <div class="col-8 font-weight-bold">Scuola Frequentata:</div>
                                        <div class="col-12 col-sm-4 font-weight-light"><%= patient.getAttendedSchool().getValue() %></div>
                                    </div>

                                    <%if(patient.getType() == PatientType.MULTI_PATIENT) { %>
                                        <div class="row mb-2 align-items-center">
                                            <div class="col-8 font-weight-bold">Istituto</div>
                                            <div class="col-12 col-sm-4 font-weight-light"><%=patient.getInstitute().getName()%></div>
                                        </div>

                                        <div class="row mb-2 align-items-center">
                                            <div class="col-8 font-weight-bold">Codice Classe</div>
                                            <div class="col-12 col-sm-4 font-weight-light"><%=patient.getClassroomCode() %></div>
                                        </div>

                                        <div class="row mb-2 align-items-center">
                                            <div class="col-8 font-weight-bold">Codice Registro</div>
                                            <div class="col-12 col-sm-4 font-weight-light"><%=patient.getSchoolRegister() %></div>
                                        </div>
                                    <%}%>

                                    <div class="row mb-2 align-items-center">
                                        <div class="col-8 font-weight-bold">Titolo di studio padre</div>
                                        <div class="col-12 col-sm-4 font-weight-light"><%= patient.getFatherQualification().getValue() %></div>
                                    </div>

                                    <div class="row mb-2 align-items-center">
                                        <div class="col-8 font-weight-bold">Titolo di studio madre</div>
                                        <div class="col-12 col-sm-4 font-weight-light"><%= patient.getMotherQualification().getValue() %></div>
                                    </div>
                                </div>

                                <div class="col-12 col-md-6 col-xl-5">
                                    <div class="row">
                                        <div class="col-12">
                                            <h5 class="text-left">Altre informazioni</h5>
                                            <div class="border-top mt-2 mb-4"></div>
                                        </div>
                                    </div>

                                    <div class="row mb-2 align-items-center">
                                        <div class="col-8 font-weight-bold">Disturbi dell'apprendimento in famiglia:</div>
                                        <div class="col-12 col-sm-4 font-weight-light"><%= patient.getFamilyLearningDisorder().getValue() %></div>
                                    </div>

                                    <div class="row mb-2 align-items-center">
                                        <div class="col-8 font-weight-bold">Disturbi medici/neuropsichiatrici in famiglia:</div>
                                        <div class="col-12 col-sm-4 font-weight-light"><%= patient.getFamilyPsychiatricDisorder().getValue() %></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                <% } %>
                </div>
            </div>
        </div>
    </body>
</html>
