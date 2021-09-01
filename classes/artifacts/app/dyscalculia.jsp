<%@ page language="java" import="java.util.Calendar, entity.enums.*, java.sql.Date"%>

<%
        String mode = request.getParameter("mode");
	Account loggedAccount = (Account) session.getAttribute("account");
        
        String infoMessage = "Il Dipartimento di Informatica dell'Universit&agrave; di Salerno sta effettuando uno studio sulla discalculia";

        boolean isGroupMode = false;
        boolean isAdmin = loggedAccount instanceof Administrator;
        boolean isTeacher = loggedAccount instanceof Teacher;
        
        if(mode != null && mode.equals("group"))
                isGroupMode = true;
	
        if(!isGroupMode)
                infoMessage += " negli adulti.";
        else
                infoMessage +=".";
        
        String name = "";
        String surname = "";
        Gender gender = null;
        int day = 0;
        int month = 0;
        int year = 0;
        Region patientRegion = null;
        UniversityDegree patientUniversityDegree = null;
        School attendedSchool = null;
        Qualification fatherQualification = null;
        Qualification motherQualification = null;
        Disorder familyLearningDisorder = null;
        Disorder familyPsychiatricDisorder = null;
        boolean isFirstTime = true;
        
        DyscalculiaPatient patient = null;
        if(!isAdmin && !isTeacher && loggedAccount instanceof DyscalculiaPatient && !isGroupMode) {
            patient = (DyscalculiaPatient) loggedAccount;
            
            if(patient.getName() != null)
                name = patient.getName();
            if(patient.getSurname() != null)
                surname = patient.getSurname();
            
            Date date = patient.getDateOfBirth();
            if(date != null) {
                isFirstTime = false;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                gender = patient.getGender();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH) + 1;
                year = calendar.get(Calendar.YEAR);
            }
            
            patientRegion = patient.getRegion();
            patientUniversityDegree = patient.getUniversityDegree();
            attendedSchool = patient.getAttendedSchool();
            fatherQualification = patient.getFatherQualification();
            motherQualification = patient.getMotherQualification();
            familyLearningDisorder = patient.getFamilyLearningDisorder();
            familyPsychiatricDisorder = patient.getFamilyPsychiatricDisorder();
        }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Test Dyscalculia - Singolo</title>

        <link href="https://fonts.googleapis.com/css?family=Chilanka|Gayathri|Livvic&display=swap" rel="stylesheet">
        <%@ include file="WEB-INF/util/meta.jsp"%>
        <%@ include file="WEB-INF/util/header.jsp"%>

        <script src="js/dyscalculia.js"></script>
    </head>
    <body>
        <%@ include file="WEB-INF/util/navbar.jsp" %>
        <div id="wrapper">

            <%@ include file="WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">

                <div class="container-fluid">

                    <!-- Breadcrumbs-->
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                        <li class="breadcrumb-item active">Test Dyscalculia</li>
                    </ol>

                    <div class="alert alert-info" role="alert">
                        <h4 class="alert-heading">Disclaimer</h4>
                        <p class="lead"><%= infoMessage %></p>
                        <hr>
                        <p class="mb-0">I dati che fornirai saranno trattati in forma aggregata. Il test ti chiede di inserire il nome, puoi usare un nickname se vuoi rimanere completamente anonimo. Il cognome &egrave; facoltativo, puoi lasciare lo spazio bianco.</p>
                    </div>

                    <div class="card mb-3">
                        <div class="card-header" id="head">
                            Registrazione quiz
                            <span class="floatR"></span>
                        </div>

                        <div class="card-body">
                            
                            <p class="lead text-center"></p>
                            <p class="text-secondary"> 

                            </p>

                            <form class="needs-validation" novalidate>
                            <% 
                            if(!isAdmin && !isTeacher) {
                            %>
                            <%if(isGroupMode) {%>

                            <div class="row to-hide">
                                <div class="col-12 col-sm-6">
                                    <h5>Per proseguire, inserisci le seguenti informazioni:</h5>
                                    <div class="border-top mt-2 mb-4"></div>
                                </div>
                            </div>
                            
                                <div class="row" id="sub-form">
                                    <div class="col-12 col-md-6">
                                        <div class="form-group">
                                            <label for="codicePlesso">Istituto <img class="custom-loading" id="institute" src="images/loading-blue.svg" alt="loading"></label>
                                            <select class="form-control" id="codicePlesso" required>
                                                <option value="" selected disabled hidden>Scegli</option>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-3">
                                        <div class="form-group">
                                            <label for="codiceClasse">Codice Classe</label>
                                            <input type="text" name="codiceClasse" id="codiceClasse" class="form-control" placeholder="Codice classe" required>
                                            <div class="invalid-feedback">Questo campo è obbligatorio</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-3">
                                        <div class="form-group">
                                            <label for="codiceRegistro">Codice Registro</label>
                                            <input type="text" name="codiceRegistro" id="codiceRegistro" class="form-control" placeholder="Codice registro" required>
                                            <div class="invalid-feedback">Questo campo è obbligatorio</div>
                                        </div>
                                    </div>
                                    
                                    <div class="col-12 mt-2 mb-4 to-hide">
                                        <button type="button" id="checkPatient" class="btn btn-primary w-100">Conferma</button>
                                    </div>
                                </div>

                                <%} %>
                            
                                <div class="to-show">
                                <div class="row">
                                    <div class="col-12 col-sm-6">
                                        <h5 class="text-left">Informazioni personali</h5>
                                        <div class="border-top mt-2 mb-4"></div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="nomePaziente">Nome</label>
                                            <input type="text" name="nomePaziente" id="nomePaziente" class="form-control" placeholder="Nome"  autofocus="autofocus" value="<%=name%>" required>
                                            <div class="invalid-feedback">Questo campo è obbligatorio</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="cognomePaziente">Cognome <span class="text-secondary">- Opzionale</span></label>
                                            <input type="text" name="cognomePaziente" id="cognomePaziente" class="form-control" placeholder="Cognome" value="<%=surname%>">
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-2">
                                        <div class="form-group">
                                            <label for="genderPaziente">Sesso</label>
                                            <select class="form-control" id="genderPaziente" required>
                                                <%if(gender == null) { %>
                                                    <option value="" selected disabled hidden>Scegli</option>
                                                    <option value="<%=Gender.FEMALE %>">Donna</option> 
                                                    <option value="<%=Gender.MALE%>">Uomo</option>
                                                <% } else {
                                                        for(Gender currentGender : Gender.values()) {
                                                            if(currentGender.equals(gender)) {%>
                                                                <option value="<%=currentGender%>" selected><%= currentGender.getValue()%> </option>
                                                        <%  } else {%>
                                                                <option value="<%=currentGender%>"><%= currentGender.getValue()%> </option>
                                                        <%  } %>
                                                <%      }
                                                    }%>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare il sesso</div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col">
                                        <label><strong>Data di nascita</strong></label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="mesePaziente">Giorno</label>
                                            <select class="form-control" id="giornoPaziente" required>
                                                <% if(day == 0) {%>
                                                    <option value="" selected disabled hidden>Giorno</option>
                                                <% }%>
                                                <%for(int i = 1; i <= 31; i++) {
                                                    if(i == day) { %>
                                                        <option selected value="<%=i%>"><%=i%></option>
                                                <%  } else {%>
                                                         <option value="<%=i%>"><%=i%></option>
                                                <% } %>
                                                <%} %> 
                                            </select>
                                            <div class="invalid-feedback">Seleziona il giorno di nascita</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="mesePaziente">Mese</label>
                                            <select class="form-control" id="mesePaziente" required>
                                                <% if(month == 0) {%>
                                                    <option value="" selected disabled hidden>Mese</option>
                                                <% } %>
                                                <%for (int i = 1; i <= 12; i++) {
                                                    if (i == month) {%>
                                                        <option selected value="<%=i%>"><%=i%></option>
                                            <%      } else {%>
                                                        <option value="<%=i%>"><%=i%></option>
                                            <%      } %>
                                            <%  } %> 
                                            </select>
                                            <div class="invalid-feedback">Seleziona il mese di nascita</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="annoPaziente">Anno</label>
                                            <select class="form-control" id="annoPaziente" required>
                                                <% if(year == 0) {%>
                                                    <option value="" selected disabled hidden>Anno</option>
                                                <% }%>
                                                <%
                                                  int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                                                  for(int i = currentYear; i >= currentYear - 120; i--) { 
                                                        if(i == year) { %>
                                                            <option selected value="<%=i%>"><%=i%></option>
                                                <%      } else {%>
                                                            <option value="<%=i%>"><%=i%></option>
                                                        <% } %>
                                            <%   } %> 
                                            </select>
                                            <div class="invalid-feedback">Seleziona l'anno di nascita</div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="regionePaziente">Regione di provenienza</label>
                                            <select class="form-control" id="regionePaziente" required>
                                                <% if(patientRegion == null) {%>
                                                    <option value="" selected disabled hidden>Scegli</option>
                                                <% }%>
                                                <%
                                                    for(Region region : Region.values()) {
                                                        if(region.equals(patientRegion)) {
                                                %>
                                                            <option selected value="<%= region %>"><%=region.getValue()%></option>
                                                <%      } 
                                                        else {
                                                %>
                                                            <option value="<%= region %>"><%=region.getValue()%></option>
                                                <%      }%>
                                                    
                                                <%  } %>
                                                
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare la regione di provenienza</div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12 col-sm-6">
                                        <h5 class="text-left">Istruzione</h5>
                                        <div class="border-top mt-2 mb-4"></div>
                                    </div>
                                </div>

                                <%if(!isGroupMode) {%>

                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="laureaPaziente">Laurea</label>
                                            <select class="form-control" id="laureaPaziente" required>
                                                <%  if(patientUniversityDegree == null) {
                                                        if(isFirstTime) {
                                                %>
                                                            <option value="" selected disabled hidden>Scegli</option>
                                                            <option value="Si">Si</option>
                                                            <option value="No">No</option>
                                                <%      }
                                                        else { %>
                                                            <option value="Si">Si</option>
                                                            <option selected value="No">No</option>
                                                <%      }   %>
                                                <% } else {%>
                                                    <option selected value="Si">Si</option>
                                                    <option value="No">No</option>
                                                <% } %>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <%if(patientUniversityDegree == null) {%>
                                            <div class="form-group" style="display: none">
                                        <%} else { %>
                                            <div class="form-group">
                                        <%}%>
                                            <label for="istruzionePaziente">Istruzione Universitaria</label>
                                            <select class="form-control" id="istruzionePaziente">
                                                <% if(patientUniversityDegree == null) {%>
                                                    <option value="" selected disabled>Scegli</option>
                                                <% } %>
                                                <%
                                                    for(UniversityDegree universityDegree : UniversityDegree.values()) {
                                                        if(universityDegree.equals(patientUniversityDegree)) {
                                                %>
                                                            <option selected value="<%= universityDegree %>"><%=universityDegree.getValue()%></option>
                                                <%      }
                                                        else { 
                                                %>          <option value="<%= universityDegree %>"><%=universityDegree.getValue()%></option>
                                                <%      }
                                                %>
                                                <%  } %>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>
                                </div>

                                <%} %>

                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="scuolaFrequentata">Scuola Frequentata</label>
                                            <select class="form-control" id="scuolaFrequentata" required>
                                                <% if(attendedSchool == null) {%>
                                                    <option value="" selected disabled>Scegli</option>
                                                <% } %>
                                                <%
                                                    for(School school : School.values()) {
                                                        if(school.equals(attendedSchool)) {
                                                %>
                                                             <option selected value="<%= school %>"><%=school.getValue()%></option>
                                                <%      }
                                                        else {
                                                %>
                                                             <option value="<%= school %>"><%=school.getValue()%></option>
                                                <%      }  
                                                %>
                                                <%  } %>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>
                                </div>

                                
                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="titoloPadre">Titolo di studio padre</label>
                                            <select class="form-control" id="titoloPadre" required>
                                                <% if(fatherQualification == null) {%>
                                                    <option value="" selected disabled>Scegli</option>
                                                <% } %>
                                                <%
                                                    for(Qualification qualification : Qualification.values()) {
                                                        if(qualification.equals(fatherQualification)) {
                                                %>
                                                            <option selected value="<%= qualification %>"><%=qualification.getValue()%></option>
                                                <%      }
                                                        else {
                                                %>
                                                            <option value="<%= qualification %>"><%=qualification.getValue()%></option>
                                                <%      }
                                                %>
                                                <%  } %>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="titoloMadre">Titolo di studio madre</label>
                                            <select class="form-control" id="titoloMadre" required>
                                                <% if(motherQualification == null) {%>
                                                    <option value="" selected disabled>Scegli</option>
                                                <% } %>
                                                <%
                                                    for(Qualification qualification : Qualification.values()) {
                                                        if(qualification.equals(motherQualification)) {
                                                %>
                                                            <option selected value="<%= qualification %>"><%=qualification.getValue()%></option>
                                                <%      }
                                                        else {
                                                %>
                                                            <option value="<%= qualification %>"><%=qualification.getValue()%></option>
                                                <%      }
                                                %>
                                                <%  } %>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12 col-sm-6">
                                        <h5 class="text-left">Altre informazioni</h5>
                                        <div class="border-top mt-2 mb-4"></div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="disturbi">Hai persone con disturbi dell'apprendimento in famiglia?</label>
                                            <select class="form-control" id="disturbi" required>
                                                <% if(familyLearningDisorder == null) {%>
                                                    <option value="" selected disabled>Scegli</option>
                                                <% } %>
                                                <%
                                                    for(Disorder disorder : Disorder.values()) {
                                                        if(disorder.equals(familyLearningDisorder)) {
                                                %>
                                                            <option selected value="<%= disorder %>"><%=disorder.getValue()%></option>
                                                <%      }
                                                        else {
                                                %>
                                                            <option value="<%= disorder %>"><%=disorder.getValue()%></option>
                                                <%      }
                                                %>
                                                <%  } %>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>

                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="disturbiFamiglia">Presenza di altri disturbi medici/neuropsichiatrici in famiglia?</label>
                                            <select class="form-control" id="disturbiFamiglia" required>
                                                <% if(familyPsychiatricDisorder == null) {%>
                                                    <option value="" selected disabled>Scegli</option>
                                                <% } %>
                                                <%
                                                    for(Disorder disorder : Disorder.values()) {
                                                        if(disorder.equals(familyPsychiatricDisorder)) {
                                                %>
                                                            <option selected value="<%= disorder %>"><%=disorder.getValue()%></option>
                                                <%      }
                                                        else {
                                                %>
                                                            <option value="<%= disorder %>"><%=disorder.getValue()%></option>
                                                <%      }
                                                %>
                                                <%  } %>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un valore</div>
                                        </div>
                                    </div>
                                </div>
                            
                            <%
                                }
                                else {
                                    String accountType = isAdmin ? "Amministratore" : "Insegnante";
                            %>
                            
                                <div class="alert alert-warning" role="alert">
                                    <h4 class="alert-heading">ATTENZIONE</h4>
                                    <p class="lead">Attualmente si è effettuato l'accesso come <%= accountType %>.</p>
                                    <hr>
                                    <p class="mb-0">E' possibile eseguire il quiz, ma i dati non verranno memorizzati nel sistema.</p>
                                </div>
                            
                            <% } %>
                                <div class="row">
                                    <div class="col-12 col-sm-6">
                                        <h5 class="text-left">Quiz</h5>
                                        <div class="border-top mt-2 mb-4"></div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12 col-md-4">
                                        <div class="form-group">
                                            <label for="quiz">Seleziona un quiz <img class="custom-loading" id="quizs" src="images/loading-blue.svg" alt="loading"></label>
                                            <select class="form-control" id="quiz" required>
                                                <option value="" selected disabled hidden>Scegli</option>
                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un quiz</div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-12">
                                        <button type="submit" id="startQuiz" data-isgroupmode='<%=isGroupMode%>' class="btn btn-primary w-100"> Inizia Quiz</button>
                                    </div>
                                </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- Start Modal -->
                    <div id="startModal" class="modal">

                        <!-- Modal content -->
                        <div class="modal-content">

                            All'interno del test sono presenti domande a tempo con numeri a scomparsa. Leggi attentamente le domande mostrate.
                            Il quiz sta per iniziare, preparati!
                        </div>

                    </div>
                </div>
                <!-- /.container-fluid -->
            </div>
            <!-- /.content-wrapper -->
        </div>
    </body>
</html>