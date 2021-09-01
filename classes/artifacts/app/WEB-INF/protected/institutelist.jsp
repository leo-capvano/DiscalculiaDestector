<%@page import="entity.Institute"%>
<%@ page language="java" import="entity.quiz.*, java.util.*"%>

<%
    List<Institute> institutes = (List<Institute>) request.getAttribute("institutes");
%>

<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>

        <title>Gestione istituti</title>

        <link rel="stylesheet" type="text/css" href="../css/quizDetails.css" />
        <link href="https://fonts.googleapis.com/css?family=Chilanka|Gayathri|Livvic&display=swap" rel="stylesheet">
        <script src="../js/instituteslist.js"></script>
    </head>

    <body style="font-family: 'Livvic'">
        <%@ include file="../../WEB-INF/util/navbar.jsp" %>

        <div id="wrapper">
            <%@ include file="../../WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">
                <div class="container-fluid">
                    <div class="alert alert-info alert-dismissible" role="alert">
                        <h4 class="alert-heading">Tutorial</h4>
                        <hr>
                        <p class="mb-0">Se vuoi modificare le informazioni di un istituto presente, clicca su <i class="fas fa-edit px-2 text-primary"></i></p>
                        <div class="mb-0">Se vuoi disabilitare o abilitare un istituto, utilizza questo tasto
                            <div class="d-inline custom-control custom-switch">
                                <input type="checkbox" class="custom-control-input" id="test-switch">
                                <label class="custom-control-label" for="test-switch"> </label>
                            </div>
                            posto di fianco ad ogni istituto
                        </div>
                        <p class="my-0">Questa operazione non cancellerà i dati sugli alunni presenti in un istituto.</p>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Chiudi">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    
                    <div class="row align-items-center">
                        <div class="col-6">
                            <span class="h3 font-weight-normal">Lista istituti</span>
                        </div>
                        <div class="col-6">
                            <div class="row align-items-center justify-content-end">
                                <div class="col-12 col-md-6">
                                    <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#addModal" data-title="Aggiungi reparto">Aggiungi istituto</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="alert d-none m-0 mt-2" id="infoAlert" role="alert"></div>
                        </div>
                    </div>
                    
                    <div class="alert d-none m-0 mt-2" id="infoAlert" role="alert"></div>

                        <div id="table" class="my-4">
                            <div class="row align-items-center" id="thead">
                                <div class="col font-weight-bold">Nome istituto</div>
                                <div class="col font-weight-bold">Stato</div>
                                <div class="col-3 font-weight-bold"></div>
                            </div>
                            
                            <%for(Institute institute : institutes) { 
                                String isChecked = institute.isActive() ? "checked" : "";
                            %>
                                <div class="row align-items-center" data-id="<%= institute.getId() %>">
                                    <div class="col" id="name-<%=institute.getId() %>"><%= institute.getName()%></div>
                                    <div class="col">
                                        <%if(institute.isActive()) {%>
                                            <span class="badge badge-pill badge-primary" id="status-<%=institute.getId() %>">Attivo</span>
                                        <%} else {%>
                                            <span class="badge badge-pill badge-secondary" id="status-<%=institute.getId() %>">Disattivo</span>
                                        <%}%>
                                    </div>
                                    <div class="col-3">
                                        <div class="row align-items-center">
                                            <div class="col col-sm-12 col-xl text-center">
                                                <i class="fas fa-edit p-2 primary" data-toggle="modal" data-target="#editModal" data-id="<%=institute.getId()%>" data-name="<%= institute.getName()%>"></i>
                                            </div>
                                            <div class="col col-sm-12 col-xl text-center">
                                                <div class="custom-control custom-switch">
                                                    <input type="checkbox" class="custom-control-input" data-id="<%=institute.getId()%>" id="switch-<%=institute.getId()%>" <%=isChecked%>>
                                                    <label class="custom-control-label" for="switch-<%=institute.getId()%>"></label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            <%}%>
                        </div>
                        
                        <div class="modal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Modifica istituto</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="alert d-none" role="alert"></div>
                                        <form class="needs-validation" novalidate>
                                            <div class="form-group">
                                                <label for="name" class="col-form-label">Nome:</label>
                                                <input type="text" class="form-control" name="name" id="name" placeholder="Nome..." required>
                                                <div class="invalid-feedback">Si prega di inserire un nome</div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                        <button type="button" class="btn btn-primary">Modifica istituto</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="modal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Aggiungi istituto</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="alert d-none" role="alert"></div>
                                        <form class="needs-validation" novalidate>
                                            <div class="form-group">
                                                <label for="name" class="col-form-label">Nome:</label>
                                                <input type="text" class="form-control" name="nome" id="name" placeholder="Nome istituto" required>
                                                <div class="invalid-feedback">Si prega di inserire un nome</div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                        <button type="button" class="btn btn-primary">Aggiungi istituto</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                                                
                    </div>
                </div>
            </div>
    </body>
</html>