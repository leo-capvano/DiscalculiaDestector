<%@ page language="java" import="java.util.*, entity.question.*, entity.quiz.*"%>
<%
    Account loggedAccount = (Account) session.getAttribute("account");
    QuestionSection section = (QuestionSection) request.getAttribute("questionSection");
    int sectionID = section.getId();

    if (loggedAccount == null) {
        session.setAttribute("redirect", "pages/questionsList?questionSection=" + sectionID);
        response.sendRedirect("../login.jsp");
        return;
    }

    boolean isAuthorized = loggedAccount instanceof Administrator || (loggedAccount instanceof Teacher && !section.getQuiz().isTrusted());
    
    List<Question> questions = section.getQuestions();
    DyscalculiaQuiz quiz = section.getQuiz();

    int quizID = quiz.getId();
    String description = section.getQuestionType().getDescription();
%>

<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>

        <title>Lista Domande</title>
        <link rel="stylesheet" type="text/css" href="../css/questionsList.css">
        <link rel="stylesheet" type="text/css" href="../css/spawnArea.css">
        <link href="https://fonts.googleapis.com/css?family=Chilanka|Gayathri|Livvic&display=swap" rel="stylesheet">
        <script type="text/javascript" src="../js/spawnArea.js"></script>
        <script type="text/javascript" src="../js/questionsList.js"></script>
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
                        <li class="breadcrumb-item"><a href="SezioniControl?action=getSezioniQuiz">Gestione Quiz</a></li>
                        <li class="breadcrumb-item"><a href="SezioniControl?action=showQuiz&id=<%= quizID%>"><%= quiz.getName()%></a></li>
                        <li class="breadcrumb-item active"><%= section.getName()%></li>
                    </ol>

                    <div class="alert alert-info alert-dismissible" role="alert">
                        <h4 class="alert-heading">Informazioni tipo domanda</h4>
                        <hr>
                        <p class="mb-0"><%= description%></p>

                        <button type="button" class="close" data-dismiss="alert" aria-label="Chiudi">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="row align-items-center">
                        <div class="col-6">
                            <span class="h3 font-weight-normal">Lista domande</span>
                        </div>
                        <%if(isAuthorized) {%>
                            <div class="col-6">
                                <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#addModal" data-title="Aggiungi reparto">Aggiungi domanda</button>
                            </div>
                        <%}%>
                        <div class="col-12">
                            <div class="alert d-none m-0 mt-2" id="infoAlert" role="alert"></div>
                        </div>
                    </div>

                    <div class="table-responsive-lg my-4">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Domanda</th>
                                    <th colspan=2 scope="col">Possibili scelte</th>
                                    <th scope="col">Risposta esatta</th>
                                    <%if(isAuthorized) { %>
                                        <th scope="col"></th>
                                    <%}%>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (questions == null || questions.size() == 0) { %>
                                <tr id="no-section">
                                    <td scope="row">Ancora nessuna domanda</td>
                                </tr>
                                <% } else { %>

                                <tr id="no-section" class="d-none">
                                    <td scope="row">Ancora nessuna domanda</td>
                                </tr>
                                <%
                                    for (Question question : questions) {
                                %>
                                <tr id="d-<%=question.getId()%>">
                                    <td scope="row"><%=question.getQuestionText()%></td>
                                    <td colspan=2>
                                        <%
                                            if(question instanceof MultipleChoicesQuestion) {
                                                MultipleChoicesQuestion multipleChoicesQuestion = (MultipleChoicesQuestion) question;
                                                List<Choice> choicesList = multipleChoicesQuestion.getChoices();
                                        %>
                                        <select class="form-control">
                                            <%
                                                for (int i = 0; i < choicesList.size(); i++) {
                                            %>
                                            <option>
                                                <%= choicesList.get(i).getValue()%>
                                                <%  Integer denominator = choicesList.get(i).getValueDenominator();
                                                    if(denominator != null && denominator != 1) {%>
                                                        / <%= denominator %>
                                                <%  }%>
                                            </option>
                                            <%
                                                }
                                            %>
                                        </select>
                                        <%} else {%>
                                        <span class="font-italic">Non sono previste scelte</span>
                                        <%}%>
                                    </td>
                                    <% if(question.getCorrectAnswer() != null) {
                                            if(question.getCorrectAnswerDenominator() == null || question.getCorrectAnswerDenominator().equals(1)) { %>
                                                <td><%= question.getCorrectAnswer() %></td>
                                    <%      } else { %>
                                                <td>
                                                    <div class="frac">
                                                        <span><%= question.getCorrectAnswer() %></span>
                                                        <span class="symbol">/</span>
                                                        <span class="bottom"><%= question.getCorrectAnswerDenominator()%></span>
                                                    </div>
                                                </td>
                                    <%      } %>
                                    <%} else { %>
                                        <td><%= question.getCorrectAnswerText() %></td>
                                    <% }%>
                                    <%if(isAuthorized) {%>
                                        <td class="text-secondary text-right">
                                            <i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-id="<%=question.getId()%>"></i>
                                        </td>
                                    <%}%>
                                </tr>
                                <% }
                                    }%>
                            </tbody>
                        </table>
                    </div>
                    <a href="quizDetails?id=<%= quizID%>"><button type="button" class="my-4 btn btn-primary">Indietro</button></a>
                </div>
            </div>
        </div>

        <!-- Modal -->

        <div class="modal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true" data-id="<%= section.getId()%>" data-type="<%= section.getQuestionType().getId() %>" data-num-choices="<%= section.getNumOfChoices()%>">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Aggiungi domanda</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <div class="alert alert-info alert-dismissible" role="alert">
                            <h4 class="alert-heading">Informazioni tipo domanda</h4>
                            <hr>
                            <p class="mb-0"><%= description%></p>

                            <button type="button" class="close" data-dismiss="alert" aria-label="Chiudi">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div id="errorAlert" class="alert d-none" role="alert"></div>
                        <form class="needs-validation" novalidate>

                            <% if(!section.getQuestionsClass().equals(MathematicProblemQuestion.class) && !section.getQuestionsClass().equals(GenericProblemQuestion.class)) { %>
                            <div class="form-group">
                                <label for="questionText" class="col-form-label">Domanda: </label>
                                <input type="text" class="form-control" name="questionText" id="questionText" placeholder="Inserisci.." required>
                                <div class="invalid-feedback">Si prega di inserire un quesito valido</div>
                            </div>
                            <%} %>

                            <%if(section.getQuestionsClass().equals(HidingNumberQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number" class="col-form-label">Inserire numero da mostrare:</label>
                                <input type="number" class="form-control" name="number" id="number" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>

                            <%if(section.getQuestionsClass().equals(AcrossCalculationQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number-1" class="col-form-label">Inserire primo numero:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tipo</button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="#">Intero</a>
                                            <a class="dropdown-item" href="#">Frazione</a>
                                        </div>
                                    </div>
                                    <input type="number" class="form-control" name="number-1" id="number-1" required>
                                    <div class="input-group-prepend" data-role="symbol" style="display: none;">
                                        <span class="input-group-text">/</span>
                                    </div>
                                    <input type="number" class="form-control" min="1" name="number-1-denominator" id="number-1-denominator" data-role="denominator" style="display:none;">
                                </div>
                                
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>

                            <div class="form-group">
                                <label for="number-2" class="col-form-label">Inserire secondo numero:</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tipo</button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="#">Intero</a>
                                            <a class="dropdown-item" href="#">Frazione</a>
                                        </div>
                                    </div>
                                    <input type="number" class="form-control" name="number-2" id="number-2" required>
                                    <div class="input-group-prepend" data-role="symbol" style="display: none;">
                                        <span class="input-group-text">/</span>
                                    </div>
                                    <input type="number" class="form-control" min="1" name="number-2-denominator" id="number-2-denominator" data-role="denominator" style="display:none;">
                                </div>
          
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>

                            <div class="form-group">
                                <label for="operation-type" class="col-form-label">Tipo operazione:</label>
                                <select class="form-control" id="operation-type" name="operation-type" required>
                                    <option value="" selected disabled hidden>Scegli</option>
                                    <option value="+">Addizione</option>
                                    <option value="-">Sottrazione</option>
                                    <option value="*">Moltiplicazione</option>
                                    <option value="/">Divisione</option>
                                </select>
                                <div class="invalid-feedback">Si prega di selezionare un tipo</div>
                            </div>
                            <%} %>

                            <%if(section.getQuestionsClass().equals(ListeningQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number" class="col-form-label">Inserire numero da far ascoltare </label>
                                <input type="number" class="form-control" name="number" id="number" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>

                            <%if(section.getQuestionsClass().equals(MinOrMaxQuestion.class)) {%>
                            <div class="form-group">
                                <label for="min-max" class="col-form-label">Scegli tra minimo e massimo:</label>
                                <select class="form-control" id="operation-type" name="operation-type" required>
                                    <option value="" selected disabled hidden>Scegli</option>
                                    <option value="min">Minimo</option>
                                    <option value="max">Massimo</option>
                                </select>
                                <div class="invalid-feedback">Si prega di selezionare un valore</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(PositioningQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number" class="col-form-label">Inserire numero da far inserire: </label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tipo</button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="#">Intero</a>
                                            <a class="dropdown-item" href="#">Frazione</a>
                                        </div>
                                    </div>
                                    <input type="number" class="form-control" name="number" id="number" required>
                                    <div class="input-group-prepend" data-role="symbol" style="display: none;">
                                        <span class="input-group-text">/</span>
                                    </div>
                                    <input type="number" class="form-control" min="1" name="number-denominator" id="number-denominator" data-role="denominator" style="display:none;">
                                </div>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(MathematicProblemQuestion.class)) { %>
                            <div class="form-group">
                                <label for="question" class="col-form-label">Quesito:</label>
                                <textarea class="form-control" name="questionText" id="question" rows="3" placeholder="Marco ha 5 mele...." required></textarea>
                                <div class="invalid-feedback">Si prega di inserire un quesito valido</div>
                            </div>
                            <div class="form-group">
                                <label for="number" class="col-form-label">Soluzione del problema</label>
                                <input type="number" class="form-control" name="number" id="number" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(InColumnCalculationQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number-1" class="col-form-label">Inserire primo numero:</label>
                                <input type="number" class="form-control" name="number-1" id="number-1" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>

                            <div class="form-group">
                                <label for="number-2" class="col-form-label">Inserire secondo numero:</label>
                                <input type="number" class="form-control" name="number-2" id="number-2" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>

                            <div class="form-group">
                                <label for="operation-type" class="col-form-label">Tipo operazione:</label>
                                <select class="form-control" id="operation-type" name="operation-type" required>
                                    <option value="" selected disabled hidden>Scegli</option>
                                    <option value="+">Addizione</option>
                                    <option value="-">Sottrazione</option>
                                </select>
                                <div class="invalid-feedback">Si prega di selezionare un tipo</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(NumbersLineQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number-1" class="col-form-label">La linea inizia dal numero:</label>
                                <input type="number" class="form-control" name="number-1" id="number-1" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>

                            <div class="form-group">
                                <label for="number-2" class="col-form-label">La linea termina al numero:</label>
                                <input type="number" class="form-control" name="number-2" id="number-2" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>

                            <div class="form-group">
                                <label for="number-3" class="col-form-label">Numero da trovare:</label>
                                <input type="number" class="form-control" name="number-3" id="number-3" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(TypeTheNumberQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number" class="col-form-label">Numero da tradurre in lettere:</label>
                                <input type="number" class="form-control" name="number" id="number" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(GuessBallNumberQuestion.class)) { %>
                            <div class="form-group">
                                <label for="number" class="col-form-label">Numero di cerchi da generare:</label>
                                <input type="number" class="form-control" name="number" id="number" required>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(GuessAreaQuestion.class)) { %>
                            <div class="form-group">
                                <div class="row align-items-end">
                                    <div class="col-8">
                                        <label for="number" class="col-form-label">Area da occupare per la risposta corretta:</label>
                                        <select id="number" class="form-control" name="number" required>
                                            <option value="" selected disabled>Scegli</option>
                                            <option value="8500">Poco affollata</option>
                                            <option value="10500">Mediamente affollata</option>
                                            <option value="15000">Molto affollata</option>
                                            <option value="20000">Affollatissima</option>
                                        </select>
                                    </div>
                                    <div class="col-4">
                                        <button id="collapseToggle" type="button" class="btn btn-primary btn-block">Anteprima</button>
                                    </div>
                                </div>

                                <div class="collapse mt-2" id="ballCollapse">
                                    <div class="card card-body d-flex">
                                        <div class="spawn-area align-self-center" data-area="0"></div>
                                    </div>
                                </div>

                            </div>
                            <%} %>

                            <%if(section.getQuestionsClass().equals(ReorderSequenceQuestion.class)) { %>
                            <div class="form-group">
                                <label for="operation-type" class="col-form-label">Tipo ordinamento da effettuare:</label>
                                <select class="form-control" id="operation-type" name="operation-type" required>
                                    <option value="" selected disabled hidden>Scegli</option>
                                    <option value="asc">Crescente</option>
                                    <option value="desc">Decrescente</option>
                                </select>
                                <div class="invalid-feedback">Si prega di selezionare un tipo di ordinamento</div>
                            </div>
                            <%} %>

                            <%if (section.getQuestionsClass().equals(GenericProblemQuestion.class)) { %>
                            <div class="form-group">
                                <label for="question" class="col-form-label">Quesito:</label>
                                <textarea class="form-control" name="questionText" id="question" rows="3" placeholder="Marco ha 5 mele...." required></textarea>
                                <div class="invalid-feedback">Si prega di inserire un quesito valido</div>
                            </div>
                            <div class="form-group">
                                <label for="number" class="col-form-label">Soluzione del problema</label>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tipo</button>
                                        <div class="dropdown-menu">
                                            <a class="dropdown-item" href="#">Intero</a>
                                            <a class="dropdown-item" href="#">Frazione</a>
                                        </div>
                                    </div>
                                    <input type="number" class="form-control" name="number" id="number" required>
                                    <div class="input-group-prepend" data-role="symbol" style="display: none;">
                                        <span class="input-group-text">/</span>
                                    </div>
                                    <input type="number" class="form-control" min="1" name="numberDenominator" id="numberDenominator" data-role="denominator" style="display:none;">
                                </div>
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%} %>
                            
                            <%
                                if (section.getNumOfChoices() != null && !section.getQuestionsClass().equals(GuessAreaQuestion.class)) {
                                    for (int i = 0; i < section.getNumOfChoices(); i++) {%>
                            <div class="form-group">
                                <% 	if (section.getQuestionsClass().equals(GuessBallNumberQuestion.class)) {%>
                                <label for="choice-<%=i + 1%>" class="col-form-label">Numero <%=i + 1%>:</label>
                                <% 	} else {%>
                                <label for="choice-<%=i + 1%>" class="col-form-label">Scelta <%=i + 1%>:</label>
                                <% 	}%>
                                
                                <%      
                                    Class clazz = section.getQuestionsClass();
                                    if(clazz.equals(PositioningQuestion.class) || clazz.equals(MinOrMaxQuestion.class) || clazz.equals(ReorderSequenceQuestion.class) || clazz.equals(GenericProblemQuestion.class)) { %>
                                        <div class="input-group">
                                            <div class="input-group-prepend">
                                                <button class="btn btn-outline-primary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Tipo</button>
                                                <div class="dropdown-menu">
                                                    <a class="dropdown-item" href="#">Intero</a>
                                                    <a class="dropdown-item" href="#">Frazione</a>
                                                </div>
                                            </div>
                                            <input type="number" class="form-control" name="choice-<%=i + 1%>" id="choice-<%=i + 1%>" required>
                                            <div class="input-group-prepend" data-role="symbol" style="display: none;">
                                                <span class="input-group-text">/</span>
                                            </div>
                                            <input type="number" class="form-control" min="1" name="choiceDenominator-<%=i + 1%>" id="choiceDenominator-<%=i + 1%>" data-role="denominator" style="display:none;">
                                        </div>
                                <%  } else { %>
                                        <input type="number" class="form-control" name="choice-<%=i + 1%>" id="choice-<%=i + 1%>" required>
                                <%  }%>
                                
                                <div class="invalid-feedback">Si prega di inserire un numero valido</div>
                            </div>
                            <%	}
                                }%>

                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                        <button type="button" class="btn btn-primary">Aggiungi domanda</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="deleteModal" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Elimina domanda</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="alert d-none" role="alert"></div>
                        Sei sicuro di voler eliminare la domanda?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                        <button type="button" class="btn btn-danger">Elimina</button>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>