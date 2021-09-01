<%@ page language="java"%>


<!DOCTYPE html>
<html>
    <head>
        <title>Account attivato</title>

        <%@ include file="../util/meta.jsp" %>
        <%@ include file="../util/header.jsp" %>
        <link rel="stylesheet" href="../css/login.css">
    </head>
    <body class="d-flex align-items-center">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <div class="col-12 col-sm-8 my-4">    
                    <div class="card mx-auto">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-12">
                                    <h4 class="pb-4 text-center">Account attivato con successo</h4>
                                    <img src="../images/account-activated.svg" alt="account_activated" class="rounded mx-auto d-block w-100">
                                    <a class="btn btn-block btn-primary my-4" href="../login.jsp">Effettua l'accesso</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>