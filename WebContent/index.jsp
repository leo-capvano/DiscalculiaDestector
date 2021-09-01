<%@ page language="java"%>

<!DOCTYPE html>
<html>
    <head>
        <link href="https://fonts.googleapis.com/css?family=Chilanka|Gayathri|Livvic&display=swap" rel="stylesheet">
        <%@ include file="WEB-INF/util/meta.jsp"%>

        <title>
            Homepage
        </title>

        <%@ include file="WEB-INF/util/header.jsp"%>
    </head>

    <body>
        <%@ include file="./WEB-INF/util/navbar.jsp"%>
        <div id="wrapper">

            <%@ include file="./WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">
                <div class="container justify-content-center">
                    <div class="row justify-content-center align-items-center">
                        <div class="col-12 col-md-6">
                            <h1 class="text-primary">Un nuovo modo di esercitarsi</h1>
                            <p class="text-justify">
                                Abbandona quei noiosi fogli di carta, inizia ad esercitarti subito online!<br>
                                Esegui diverse tipologie di test ed ottieni i tuoi risultati istantaneamente.
                                Esercitarsi con la matematica non è mai stato così semplice!
                            </p>
                            <p class="text-primary font-weight-bold mb-4">
                                Effettua un test ora!
                            </p>
                            <div class="row">
                                <div class="col">
                                    <a href="dyscalculia.jsp" class="btn btn-outline-primary">Vai a Discalculia (Singola)</a>
                                </div>
                                <div class="col">
                                    <a href="dyscalculia.jsp?mode=group" class="btn btn-outline-primary">Vai a Discalculia (Gruppo)</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <img src="images/homepage-math.svg" alt="study" class="w-75">
                        </div>
                    </div>
                    <div class="row mt-4 justify-content-center align-items-center">
                        <div class="col-12 col-md-6 order-last order-md-first">
                            <img src="images/homepage-teach.svg" alt="teach" class="w-75">
                        </div>
                        <div class="col-12 col-md-6">
                            <h2 class="text-primary">Crea un'esperienza d'apprendimento divertente</h2>
                            <p class="text-justify">
                                Sei un docente? Disegna un test adatto alle esigenze dei tuoi alunni, creando quiz ad hoc.
                                Il tuo compito è solo quello di creare la domanda, la risposta esatta la calcoliamo noi!
                            </p>
                            <a href="register.jsp" class="btn btn-primary">Inizia subito!</a>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>

</html>