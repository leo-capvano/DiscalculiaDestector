<%@page import="entity.enums.PatientType"%>
<%@page import="entity.enums.Gender"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    List<DyscalculiaPatient> patients = (List<DyscalculiaPatient>) request.getAttribute("patients");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>
        <link rel="stylesheet" type="text/css" href="../css/userlist.css">
        <title>Lista pazienti</title>
    </head>
    <body>
        <%@ include file="../../WEB-INF/util/navbar.jsp" %>

        <div id="wrapper">
            <%@ include file="../../WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">
                <div class="container-fluid">
                    
                    <%if(patients.isEmpty()) { %>
                        <div class="card my-4">
                            <div class="card-body">
                                <div class="row justify-content-center">
                                    <div class="col-12 col-lg-8 text-center">
                                        <img class="w-75" src="../images/nothing.svg" alt="nothing_here">
                                    </div>
                                    <div class="col-12 my-4 text-center">
                                        <h3>Qui sembra non esserci nulla</h3>
                                        <div>
                                            Ancora nessun utente ha svolto un test
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <%} else { %>
                    
                        
                    
                        <%for(DyscalculiaPatient patient : patients) { 
                            String surname = patient.getSurname() == null ? "-" : patient.getSurname();
                            String degree = patient.getUniversityDegree() == null ? "-" : patient.getUniversityDegree().getValue(); %>
                            
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
                                                <div class="col-12">
                                                    <a class="btn btn-primary d-block my-4" href="quizstatistics?id=<%= patient.getId() %>">Visualizza test effettuati</a>
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
                    <%}%>
                        
                            
                        
                <%}%>
                </div>
            </div>
        </div>
    </body>
</html>
