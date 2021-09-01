<%@ page language="java" import="entity.account.*, entity.quiz.*, java.util.*"%>

<%
    Account loggedAccount = (Account) session.getAttribute("account");

    boolean isAdmin = (loggedAccount instanceof Administrator);

    if (loggedAccount == null || (loggedAccount instanceof DyscalculiaPatient)) {
        session.setAttribute("redirect", "pages/quizSections");
        response.sendRedirect("../login.jsp");
        return;
    }

    List<QuizSection> sections = (List<QuizSection>) request.getAttribute("sections");
    List<DyscalculiaQuiz> quizzes = (List<DyscalculiaQuiz>) request.getAttribute("quizzes");
%>  
<!DOCTYPE html>
<html>

    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>
        <link rel="stylesheet" type="text/css" href="../css/quizSection.css"/>
        <link href="https://fonts.googleapis.com/css?family=Chilanka|Gayathri|Livvic&display=swap" rel="stylesheet">

        <title>Gestisci Quiz</title>

        <script src="../js/quizSection.js"></script>
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
                        <li class="breadcrumb-item active">Gestione Quiz</li>
                    </ol>

                    <div class="alert alert-info alert-dismissible" role="alert">
                        <h4 class="alert-heading">Tutorial</h4>
                        <hr>
                        <p class="mb-0">Clicca su un reparto per visualizzare la lista dei quiz;</p>
                        <p class="mb-0">Per visualizzare i dettagli di un quiz, clicca sul quiz corrispondente;</p>
                        <p class="mb-0">Se vuoi modificare il nome e le soglie di un quiz, clicca su <i class="fas fa-edit px-2 text-primary"></i></p>
                        <p class="mb-0">Se vuoi eliminare un quiz, clicca su <i class="fas fa-trash-alt px-2 text-danger"></i></p>
                        <%if(!isAdmin) {%>
                        <p class="mb-0">I quiz contrassegnati da <i class="fas fa-check-circle px-2 text-primary"></i> sono realizzati dagli amministratori del sistema e su di essi non è possibile apporre modifiche.</p>
                        <%}%>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Chiudi">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="row align-items-center">
                        <div class="col-6">
                            <span class="h3 font-weight-normal">Lista reparti quiz</span>
                        </div>
                        <div class="col-6">
                            <%if (isAdmin) {%>
                                <div class="row align-items-center">
                            <%} else { %>
                                <div class="row align-items-center justify-content-end">
                            <%} %>
                                <%  if (isAdmin) {%>
                                <div class="col-12 col-md-6">
                                    <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#addModal" data-title="Aggiungi reparto">Aggiungi reparto</button>
                                </div>
                                <%  } %>
                                <div class="col-12 col-md-6 mt-2 mt-md-0">
                                    <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#addModal" data-title="Aggiungi quiz" data-combo="true">Aggiungi quiz</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="alert d-none m-0 mt-2" id="infoAlert" role="alert"></div>
                        </div>
                    </div>

                    <%
                        if (sections.size() == 0) { %>
                    <div id="no-repart" class="alert alert-dark mt-4" role="alert">
                        <h4 class="alert-heading">Nessun reparto quiz</h4>
                        <hr>
                        <p class="mb-0 d-flex align-items-center">Al momento non vi &egrave; alcun reparto quiz. 
                            <%  if (isAdmin) {%>
                            <button class="btn btn-link" data-toggle="modal" data-target="#addModal" data-title="Aggiungi reparto">Creane uno</button></p>
                            <%} %>
                    </div>

                    <div id="table" class="my-4 d-none">
                        <div class="row align-items-center" id="thead">
                            <div class="col font-weight-bold">Nome</div>
                            <div class="col font-weight-bold">Descrizione</div>
                            <div class="col-2 font-weight-bold"></div>
                        </div>
                    </div>
                    <%	} else { %>
                    <div id="table" class="my-4">
                        <div class="row align-items-center" id="thead">
                            <div class="col font-weight-bold">Nome</div>
                            <div class="col font-weight-bold">Descrizione</div>
                            <div class="col-2 font-weight-bold"></div>
                        </div>
                        <%
                            for (QuizSection s : sections) {
                        %>
                        <div data-toggle="collapse" href="#quiz-list-<%=s.getId()%>" aria-expanded="false" aria-controls="quiz-list-<%=s.getId()%>" class="row align-items-center">
                            <div class="col"><%= s.getName()%></div>
                            <div class="col"><%= s.getDescription()%></div>
                            <div class="col-2 text-secondary text-right">
                                <%if(isAdmin) {%>
                                    <i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-name="<%=s.getName()%>" data-id="<%=s.getId()%>"></i>
                                <%}%>
                                <i class="fas fa-angle-down"></i>
                            </div>
                        </div>

                        <div class="collapse sub-menu" id="quiz-list-<%=s.getId()%>">
                            <%
                                if (quizzes == null) {
                            %>
                            <div class="alert alert-danger" role="alert">
                                <h4 class="alert-heading">Impossibile ottenere lista quiz</h4>
                                <hr>
                                <p class="mb-0">Al momento non è stato possibile ottenere la lista dei quiz. Riprova più tardi</p>
                            </div>

                            <% 	} else if (quizzes.size() == 0) {
                            %>		
                            <div class="row ml-0 align-items-center disabled">
                                <div class="col pl-2">Nessun quiz per questo reparto</div>
                            </div>

                            <%	} else {
                                for (int i = 0; i < quizzes.size(); i++) {
                                    DyscalculiaQuiz q = quizzes.get(i);
                                    if (q.getQuizSection().equals(s)) {
                            %>
                            <div class="row ml-0 align-items-center" id="q-<%= q.getId()%>">
                                <div class="col pl-2 quiz-name">
                                    <a href="quizDetails?id=<%=q.getId()%>" class="text-reset">
                                        <%= q.getName()%>
                                        <%if(q.isTrusted()) { %>
                                            <i class="fas fa-check-circle text-primary"></i>
                                        <%}%>
                                    </a>
                                </div>
                                <div class="col-2 text-secondary text-right">
                                    <%if(isAdmin || !q.isTrusted()) {%>
                                        <i class="fas fa-edit p-2 primary" data-toggle="modal" data-target="#editModal" data-id="<%= q.getId()%>" data-name="<%= q.getName()%>" data-sla="<%= q.getApproprieteThreshold()%>" data-srl="<%= q.getLowRiskThreshold()%>" data-sre="<%= q.getHighRiskThreshold()%>"></i><i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-id="<%=q.getId()%>" data-name="<%= q.getName()%>" data-isQuiz="true" data-id="<%= q.getId()%>"></i>
                                    <%}%>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            }
                            %>
                        </div>
                        <%} %>
                    </div>
                    <% } %>
                </div>

                <!-- Modal -->
                <div class="modal" id="addModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Title</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="alert d-none" role="alert"></div>
                                <form>
                                    <div class="form-group">
                                        <label for="nome" class="col-form-label">Nome:</label>
                                        <input type="text" class="form-control" name="nome" id="nome" placeholder="Nome..." pattern="^[ A-Za-z]+$" required>
                                        <div class="invalid-feedback">Questo campo &egrave; obbligatorio.</div>
                                    </div>
                                    <div class="form-group">
                                        <label for="descrizione" class="col-form-label">Descrizione:</label>
                                        <input type="text" class="form-control" name="descrizione" id="descrizione" placeholder="Descrizione..." pattern="^[ A-Za-z]+$" required>
                                        <div class="invalid-feedback">Questo campo &egrave; obbligatorio.</div>
                                    </div>
                                    <div class="form-group d-none" id="reparti-group">
                                        <label for="reparti" class="col-form-label">Reparto:</label>
                                        <select class="form-control" id="reparti">
                                            <option value="" selected disabled hidden>Scegli</option>
                                            <%
                                                if (sections != null && sections.size() != 0) {
                                                    for (QuizSection s : sections) {
                                            %>	
                                                    <option value="<%=s.getId()%>"><%=s.getName()%></option>
                                            <%      }
                                                }%>  
                                        </select>
                                        <div class="invalid-feedback">Questo campo &egrave; obbligatorio.</div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                <button type="button" class="btn btn-primary">Aggiungi reparto</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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

                <div class="modal" id="editModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Title</h5>
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
                                        <div class="invalid-feedback">Questo campo &egrave; obbligatorio.</div>
                                    </div>
                                    <div class="form-group">
                                        <label for="lieve" class="col-form-label">Soglia rischio lieve:</label>
                                        <input type="number" min=0 class="form-control" name="lieve" id="lieve" required>
                                        <div class="invalid-feedback">Questo campo &egrave; obbligatorio.</div>
                                    </div>
                                    <div class="form-group">
                                        <label for="elevato" class="col-form-label">Soglia rischio elevato:</label>
                                        <input type="number" min=0 class="form-control" name="elevato" id="elevato" required>
                                        <div class="invalid-feedback">Questo campo &egrave; obbligatorio.</div>
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


    </body>
</html>