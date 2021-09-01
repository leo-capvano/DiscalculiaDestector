<%@ page language="java"%>


<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>

        <%@ include file="WEB-INF/util/meta.jsp" %>
        <%@ include file="WEB-INF/util/header.jsp" %>
        <link rel="stylesheet" href="css/login.css">
    </head>
    <body class="d-flex align-items-center">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <div class="col-12 col-sm-8 my-4">    
                    <div class="card mx-auto">

                        <div class="card-header">Login area utente</div>
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-12 col-md-6">
                                    <img src="images/login.png" alt="login_image" class="rounded mx-auto d-block w-100">
                                </div>
                                <div class="col-12 col-md-6">
                                    <form id="formControl" class="needs-validation" novalidate>

                                        <h4 class="pb-4 text-center">Effettua il login per procedere</h4>

                                        <div class="alert alert-danger alert-dismissible d-none" role="alert">
                                            Hello
                                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>

                                        <div class="form-group">
                                            <div class="form-label-group">
                                                <label for="inputEmail">Email address</label>
                                                <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address"  autofocus="autofocus" required>
                                                <div class="invalid-feedback">Inserisci un email valida</div>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="form-label-group">
                                                <label for="inputPassword">Password</label>
                                                <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
                                                <div class="invalid-feedback">Digita la password</div>
                                            </div>
                                        </div>

                                        <button id="btnControl" class="btn btn-dark btn-block">Login</button>
                                    </form>
                                    <div class="text-center pt-2">
                                        <a class="d-block small" href="forgot-password.jsp">Non ricordi la Password?</a>
                                    </div>
                                    <div class="text-center pt-5">
                                        <a class="d-block small" href="register.jsp">Crea un account</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="js/login.js"></script>
    </body>
</html>