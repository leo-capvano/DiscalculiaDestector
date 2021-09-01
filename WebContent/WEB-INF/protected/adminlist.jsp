<%@page import="entity.Institute"%>
<%@ page language="java" import="entity.quiz.*, java.util.*"%>

<%
    List<Account> accounts = (List<Account>) request.getAttribute("admins");
%>

<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>

        <title>Gestione utenti</title>

        <link rel="stylesheet" type="text/css" href="../css/quizDetails.css" />
        <link href="https://fonts.googleapis.com/css?family=Chilanka|Gayathri|Livvic&display=swap" rel="stylesheet">
        <script src="../js/adminlist.js"></script>
    </head>

    <body style="font-family: 'Livvic'">
        <%@ include file="../../WEB-INF/util/navbar.jsp" %>

        <div id="wrapper">
            <%@ include file="../../WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">
                <div class="container-fluid">
                    
                    <div class="row align-items-center">
                        <div class="col-12 col-sm-6">
                            <span class="h3 font-weight-normal">Lista amministratori</span>
                        </div>
                        <div class="col-12 col-sm-6 my-2 my-sm-0">
                            <div class="row align-items-center justify-content-end">
                                <div class="col-12 col-md-6">
                                    <button class="btn btn-primary btn-block" data-toggle="modal" data-target="#addModal">Aggiungi amministratore</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="alert d-none m-0 mt-2" id="infoAlert" role="alert"></div>
                        </div>
                    </div>
                    
                    <div class="alert d-none m-0 mt-2" id="infoAlert" role="alert"></div>

                        <div id="table" class="my-4">
                            <div class="row align-items-center text-break" id="thead">
                                <div class="col-3 font-weight-bold">Nome</div>
                                <div class="col-3 font-weight-bold">Cognome</div>
                                <div class="col-3 font-weight-bold">Email</div>
                                <div class="col-3 font-weight-bold">Stato</div>
                            </div>
                            
                            <%for(Account currentAccount : accounts) { %>
                                <div class="row align-items-center text-break">
                                    <div class="col-3"><%=currentAccount.getName()%></div>
                                    <div class="col-3"><%=currentAccount.getSurname()%></div>
                                    <div class="col-3"><%=currentAccount.getEmail()%></div>
                                    <div class="col-3">
                                        <%if(currentAccount.isActive()) {%>
                                            <span class="badge badge-pill badge-primary">Attivo</span>
                                        <%} else {%>
                                            <span class="badge badge-pill badge-secondary">Non verificato</span>
                                        <%}%>
                                    </div>
                                </div>
                            <%}%>
                        </div>
                        
                        <div class="modal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Aggiungi amministratore</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="alert d-none" role="alert"></div>
                                        <form class="needs-validation" novalidate>
                                            <div class="form-group">
                                                <label for="name" class="col-form-label">Nome:</label>
                                                <input type="text" class="form-control" name="nome" id="name" placeholder="Nome" pattern="^[ A-Za-z]+$" required>
                                                <div class="invalid-feedback">Si prega di inserire un nome valido</div>
                                            </div>
                                            <div class="form-group">
                                                <label for="surname" class="col-form-label">Cognome:</label>
                                                <input type="text" class="form-control" name="surname" id="surname" placeholder="Cognome" pattern="^[ A-Za-z]+$" required>
                                                <div class="invalid-feedback">Si prega di inserire un cognome valido</div>
                                            </div>
                                            <div class="form-group">
                                                <label for="email" class="col-form-label">Email:</label>
                                                <input type="email" class="form-control" name="email" id="email" placeholder="esempio@email.com" required>
                                                <div class="invalid-feedback">Si prega di inserire un indirizzo e-mail valido</div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annulla</button>
                                        <button type="button" class="btn btn-primary">Aggiungi</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                                                
                    </div>
                </div>
            </div>
    </body>
</html>