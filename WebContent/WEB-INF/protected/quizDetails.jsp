<%@ page language="java" import="entity.quiz.*, java.util.*"%>


<%
    Account loggedAccount = (Account) session.getAttribute("account");

    DyscalculiaQuiz quiz = (DyscalculiaQuiz) request.getAttribute("quiz");

    if (loggedAccount == null) {
        response.sendRedirect("../login.jsp");
        session.setAttribute("redirect", "pages/quizDetails?id=" + quiz.getId());
        return;
    }
    
    boolean isAuthorized = loggedAccount instanceof Administrator || (loggedAccount instanceof Teacher && !quiz.isTrusted());

    QuizSection quizSection = (QuizSection) request.getAttribute("quizSection");
    List<QuestionSection> questionSections = (List<QuestionSection>) request.getAttribute("questionSectionList");
    List<QuestionType> questionTypes = (List<QuestionType>) request.getAttribute("questionTypes");
    String JSONquestionTypes = (String) request.getAttribute("questionTypesJSON");
%>    

<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>

        <title><%= quiz.getName()%></title>

        <link rel="stylesheet" type="text/css" href="../css/quizDetails.css" />
        <link href="https://fonts.googleapis.com/css?family=Chilanka|Gayathri|Livvic&display=swap" rel="stylesheet">
        <script src="../js/quizDetails.js"></script>
    </head>

    <body style="font-family: 'Livvic'">
        <%@ include file="../../WEB-INF/util/navbar.jsp" %>

        <div id="wrapper">
            <%@ include file="../../WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">
                <div class="container-fluid">

                    <!-- Breadcrumbs-->
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="index.jsp">Home</a></li>
                        <li class="breadcrumb-item"><a href="quizSections">Gestione Quiz</a></li>
                        <li class="breadcrumb-item active"><%= quiz.getName()%></li>
                    </ol>

                    <div class="alert alert-info alert-dismissible" role="alert">
                        <%if(isAuthorized) { %>
                            <h4 class="alert-heading">Tutorial</h4>
                            <hr>
                            <p class="mb-0">Se vuoi visualizzare l'elenco delle domande di una sezione, clicca su <i class="fas fa-list px-2 text-primary"></i></p>
                            <p class="mb-0">Se vuoi modificare il nome e/o la descrizione di una sezione, clicca su <i class="fas fa-edit px-2 text-primary"></i></p>
                            <p class="mb-0">Se vuoi eliminare una sezione, clicca su <i class="fas fa-trash-alt px-2 text-danger"></i></p>
                        <%}
                        else {%>
                            <h4 class="alert-heading">Informazioni</h4>
                            <hr>
                            <p class="mb-0">Questo quiz &agrave; stato realizzato da un amministratore.</p>
                            <p class="mb-0">Non puoi apportare modifiche al quiz, ma puoi solo visualizzare le sue domande e le sue sezioni.</p>
                        <%}%>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Chiudi">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="card">

                        <div class="card-header text-white text-center">
                            <h1 class="font-weight-bolder"><%= quiz.getName()%></h1>
                            <%if(loggedAccount instanceof Administrator && quiz.getTeacher() != null) { 
                                Teacher teacher = quiz.getTeacher();%>
                                <p class="text-center font-weight-light text-white-50">Creato da: <i class="font-italic"><%= teacher.getName() + " " + teacher.getSurname()%></i></p>
                            <%}%>
                            <p class="text-center"><%= quiz.getDescription()%> <small class="h5">|</small> <%= quizSection.getName()%></p>

                            <%if(isAuthorized) {%>
                                <div class="row align-items-center justify-items-center mt-4 pt-4">
                                    <div class="col-sm quiz-action" data-toggle="modal" data-target="#addModal">
                                        <div class="row">
                                            <div class="col-12">
                                                <span class="h4 font-weight-normal"><i class="fas fa-plus"></i></span>
                                            </div>
                                            <div class="col-12 font-weight-light text-white-50">
                                                Aggiungi sezione domande
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-sm quiz-action">
                                        <div class="row" data-toggle="modal" data-target="#editQuizModal">
                                            <div class="col-12">
                                                <span class="h4 font-weight-normal"><i class="fas fa-edit p-2 primary"></i></span>
                                            </div>
                                            <div class="col-12 font-weight-light text-white-50">
                                                Modifica quiz
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-sm quiz-action">
                                        <div class="row" data-toggle="modal" data-target="#deleteModal" data-isquiz="true" data-id="<%=quiz.getId()%>" data-name="<%= quiz.getName()%>">
                                            <div class="col-12">
                                                <span class="h4 font-weight-normal"><i class="fas fa-trash-alt p-2 danger"></i></span>
                                            </div>
                                            <div class="col-12 font-weight-light text-white-50">
                                                Elimina quiz
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            <%}%>
                        </div> 

                        <div class="card-body">

                            <div class="alert d-none m-0 mt-2" id="infoAlert" role="alert"></div>

                            <div id="table" class="my-4">
                                <div class="row align-items-center" id="thead">
                                    <div class="col font-weight-bold">Nome Sezione Domanda</div>
                                    <div class="col font-weight-bold">Descrizione</div>
                                    <div class="col-3 font-weight-bold"></div>
                                </div>

                                <% if (questionSections.size() == 0) { %>
                                <div id="no-section" class="row ml-0 align-items-center">
                                    <div class="col pl-2">Ancora nessuna sezione domanda</div>
                                </div>
                                <% } else {
                                    for (QuestionSection questionSection : questionSections) {%>
                                <div id="sd-<%= questionSection.getId()%>" class="row align-items-center">
                                    <div class="col"><%= questionSection.getName()%></div>
                                    <div class="col"><%= questionSection.getDescription()%></div>
                                    <div class="col-3 text-secondary text-right">
                                        <div class="row align-items-center">
                                            <div class="col col-sm-12 col-xl text-center">
                                                <a class="text-reset" href="questionsList?questionSection=<%= questionSection.getId()%>"><i class="fas fa-list primary"></i></a>
                                            </div>
                                            <%if(isAuthorized) {%>
                                                <div class="col col-sm-12 col-xl text-center">
                                                    <i class="fas fa-edit p-2 primary" data-toggle="modal" data-target="#editModal" data-id="<%=questionSection.getId()%>" data-name="<%= questionSection.getName()%>" data-description="<%= questionSection.getDescription()%>" data-type="<%= questionSection.getQuestionType().getId()%>" data-numChoices="<%= questionSection.getNumOfChoices()%>"></i>
                                                </div>
                                                <div class="col col-sm-12 col-xl text-center">
                                                    <i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-id="<%=questionSection.getId()%>" data-name="<%= questionSection.getName()%>"></i>
                                                </div>
                                            <%}%>
                                        </div>
                                    </div>
                                </div>
                                <% }
                                    }%>

                            </div>
                        </div>

                        <div class="card-footer">
                            <a href="quizSections"><button type="button" class="btn btn-primary">Indietro</button></a>  
                            <a href="quizSummary?quizID=<%=quiz.getId()%>"><button type="button" class="btn btn-primary">Ottieni Report Excel</button></a>  
                        </div>
                    </div>

                    <!-- Modal -->

                    <div class="modal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true" data-id='<%= quiz.getId()%>'>
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Aggiungi sezione domanda</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="alert d-none" role="alert"></div>
                                    <form class="needs-validation" novalidate>
                                        <div class="form-group">
                                            <label for="nome" class="col-form-label">Nome:</label>
                                            <input type="text" class="form-control" name="nome" id="nome" placeholder="Nome..." pattern="^[ A-Za-z]+$" required>
                                            <div class="invalid-feedback">Si prega di inserire un nome valido</div>
                                        </div>
                                        <div class="form-group">
                                            <label for="descrizione" class="col-form-label">Descrizione:</label>
                                            <input type="text" class="form-control" name="descrizione" id="descrizione" placeholder="Descrizione..." pattern="^[ A-Za-z]+$" required>
                                            <div class="invalid-feedback">Si prega di inserire una descrizione valida</div>
                                        </div>
                                        <div class="form-group">
                                            <label for="tipo" class="col-form-label">Tipo:</label>
                                            <select class="form-control" id="tipo" required>
                                                <option value="" selected disabled hidden>Scegli</option>
                                                <%
                                                    if (questionTypes != null)
                                                        for (QuestionType questionType : questionTypes) {
                                                %>
                                                <option value="<%=questionType.getId()%>"><%=questionType.getName()%></option>
                                                <%}%>

                                            </select>
                                            <div class="invalid-feedback">Si prega di selezionare un tipo</div>
                                        </div>

                                        <div id="numScelteFormGroup" class="form-group" style="display: none;">
                                            <label for="numScelte" class="col-form-label">Numero scelte</label>
                                            <input type="number" class="form-control" name="numScelte" id="numScelte" min=1 value="1" required>
                                            <div class="invalid-feedback">Si prega di inserire il numero di scelte</div>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                    <button type="button" class="btn btn-primary">Aggiungi sezione domanda</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal" id="deleteModal" tabindex="-1" role="dialog" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Elimina</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="alert d-none" role="alert"></div>
                                    Sei sicuro di voler eliminare?
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                    <button type="button" class="btn btn-danger">Elimina</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true" data-id='<%=quiz.getId()%>'>
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Modifica sezione domanda</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="alert d-none" role="alert"></div>
                                    <form class="needs-validation" novalidate>
                                        <div class="form-group">
                                            <label for="nome" class="col-form-label">Nome:</label>
                                            <input type="text" class="form-control" name="nome" id="nome" placeholder="Nome..." pattern="^[ A-Za-z]+$" required>
                                            <div class="invalid-feedback">Si prega di inserire un nome valido</div>
                                        </div>
                                        <div class="form-group">
                                            <label for="descrizione" class="col-form-label">Descrizione:</label>
                                            <input type="text" class="form-control" name="descrizione" id="descrizione" placeholder="Descrizione..." pattern="^[ A-Za-z]+$" required>
                                            <div class="invalid-feedback">Si prega di inserire una descrizione valida</div>
                                        </div>

                                        <div id="numScelteFormGroup" class="form-group d-none">
                                            <label for="numScelte" class="col-form-label">Numero scelte</label>
                                            <input type="number" class="form-control" name="numScelte" id="numScelte" min=1 value="1" required>
                                            <div class="invalid-feedback">Si prega di inserire il numero di scelte</div>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                    <button type="button" class="btn btn-primary">Modifica sezione domanda</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal" id="editQuizModal" tabindex="-1" role="dialog" aria-hidden="true" data-id="<%=quiz.getId()%>" data-name="<%= quiz.getName()%>" data-sla="<%= quiz.getApproprieteThreshold()%>" data-srl="<%= quiz.getLowRiskThreshold()%>" data-sre="<%= quiz.getHighRiskThreshold()%>">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Modifica quiz</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="alert d-none" role="alert"></div>
                                    <form class="needs-validation">
                                        <div class="form-group">
                                            <label for="nome" class="col-form-label">Nome:</label>
                                            <input type="text" class="form-control" name="nome" id="nome" placeholder="Nome..." pattern="^[ A-Za-z]+$" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="adeguata" class="col-form-label">Soglia livello adeguato:</label>
                                            <input type="number" min=0 class="form-control" name="adeguata" id="adeguata" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="lieve" class="col-form-label">Soglia rischio lieve:</label>
                                            <input type="number" min=0 class="form-control" name="lieve" id="lieve" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="elevato" class="col-form-label">Soglia rischio elevato:</label>
                                            <input type="number" min=0 class="form-control" name="elevato" id="elevato" required>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                    <button type="button" class="btn btn-primary">Modifica quiz</button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <script>
            const questionTypes = JSON.parse(`<%=JSONquestionTypes%>`);
        </script>
    </body>
</html>