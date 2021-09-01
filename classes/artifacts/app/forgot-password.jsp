<%@ page language="java"%>


<!DOCTYPE html>
<html>
    <head>
        <title>Recupera password</title>

        <%@ include file="WEB-INF/util/meta.jsp" %>
        <%@ include file="WEB-INF/util/header.jsp" %>
        <link rel="stylesheet" href="css/signup.css">
    </head>
    <body class="d-flex align-items-center">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <div class="col-12 col-sm-8 my-4">    
                    <div id="toHide" class="card mx-auto">

                        <div class="card-header">Recupera la password</div>
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-12 col-md-6">
                                    <img src="images/login.png" alt="login_image" class="rounded mx-auto d-block w-100">
                                </div>
                                <div class="col-12 col-md-6">
                                    <form id="formControl" class="needs-validation" novalidate>

                                        <div class="alert alert-danger alert-dismissible d-none" role="alert">
                                            Hello
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>

                                        <div class="form-group">
                                            <div class="form-label-group">
                                                <label for="inputEmail">Inserisci la tua email</label>
                                                <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email"  autofocus="autofocus" required>
                                                <div class="invalid-feedback">Inserisci un email valida</div>
                                            </div>
                                        </div>

                                        <button id="btnControl" class="btn btn-dark btn-block">Recupera password</button>
                                    </form>
                                    <div class="text-center pt-2">
                                        <a class="d-block small" href="login.jsp">Torna al login</a>
                                    </div>
                                    <div class="text-center pt-5">
                                        <a class="d-block small" href="register.jsp">Crea un account</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div id="toShow" class="card mx-auto d-none">
                        <div class="card-header">Ti abbiamo inviato una mail</div>
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-12 col-md-6 p-4">
                                    <img src="images/mail.svg" alt="email_sent" class="rounded mx-auto d-block w-75 p-4">
                                </div>
                                <div class="col-12 col-md-6">
                                    <div>
                                        Controlla la tua casella di posta per recuperare la password.
                                    </div>
                                    <a class="btn btn-block btn-primary my-4" href="login.jsp">Fatto!</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="js/forgot-password.js"></script>
    </body>
</html>