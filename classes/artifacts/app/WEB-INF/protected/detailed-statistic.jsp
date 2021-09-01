<%@page import="entity.StatisticRow"%>
<%@page import="entity.QuizStatistics"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    QuizStatistics quizStatistics = (QuizStatistics) request.getAttribute("quizStatistics");

    if (quizStatistics == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="../../WEB-INF/util/meta.jsp" %>
        <%@ include file="../../WEB-INF/util/header.jsp" %>
        <link rel="stylesheet" type="text/css" href="../css/quizstatistics.css">

        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0/dist/Chart.min.js"></script>
        <script src="../vendor/js/palette.js"></script>
        <title>Dettagli quiz</title>
    </head>
    <body>
        <%@ include file="../../WEB-INF/util/navbar.jsp" %>

        <div id="wrapper">
            <%@ include file="../../WEB-INF/util/sidebar.jsp" %>
            <div id="content-wrapper">

                <div class="container-fluid">
                    <div class="card mb-3">
                        <div class="card-header"> Grafico risposte esatte</div>
                        <div class="card-body">
                            <div class="row align-items-center">
                                <div class="col-12 col-md-8">
                                    <canvas width=400" height="400" id="pie-chart"></canvas>
                                </div>
                                <div class="col-12 col-md-4">
                                    <u><strong data-toggle="tooltip" data-placement="top" title="La Performance (o Effectiveness) consiste nella media armonica tra precision e recall: (2*precision*recall)/(precision+recall)">Performance:</strong></u> <%= quizStatistics.getPerformance()%><br>
                                    <u><strong data-toggle="tooltip" data-placement="top" title="L'effort riguarda i minuti impiegati per lo svolgimento del test">Effort:</strong></u> <%=quizStatistics.getPerformance()%><br>
                                    <u><strong data-toggle="tooltip" data-placement="top" title=" L'efficiency è definita come il rapporto tra la Performance e l'Effort, ovvero il numero di minuti impiegati">Efficiency:</strong></u> <%=quizStatistics.getPerformance()%><br>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card mb-3">
                        <div class="card-header"> Grafico risposte esatte per tipologia di domanda</div>
                        <div class="card-body">
                            <canvas width=400" height="400" id="line-chart"></canvas>
                        </div>
                    </div>

                    <div class="card mb-3">
                        <div class="card-header">Tempo impiegato per domanda</div>
                        <div class="card-body">
                            <canvas width=400" height="400" id="time-chart"></canvas>
                        </div>
                    </div>

                </div>

            </div>
        </div>

        <script>

            const pieChart = document.getElementById("pie-chart");
            const lineChart = document.getElementById("line-chart");
            const timeChart = document.getElementById("time-chart");
            const sidebar = $(".sidebar");
            const numOfCategory = <%=quizStatistics.getStatisticRows().size()%>;

            new Chart(pieChart, {
                type: 'pie',
                data: {
                    labels: ["Esatte", "Sbagliate", "Saltate"],
                    datasets: [{
                            label: "Risposte Utente",
                            backgroundColor: ["rgba(51, 204, 51, 1)", "rgba(255,99,132,1)", "rgba(255, 204, 0,1)"],
                            data: [<%=quizStatistics.getTotalCorrectAnswers()%>, <%= quizStatistics.getTotalWrongAnswers()%>, <%=quizStatistics.getTotalSkippedAnswers()%>]
                        }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,

                    title: {
                        display: true,
                        text: 'Risposte Utente'
                    }
                }

            });

            new Chart(timeChart, {
                type: 'bar',
                data: {
                    labels: [
                            <%for(StatisticRow row : quizStatistics.getStatisticRows()) {%>
                                `<%= row.getQuestionType().getName() %>`,
                            <%}%>
                        ],
                    datasets: [{
                            data: [
                                <%for(StatisticRow row : quizStatistics.getStatisticRows()) {%>
                                    <%= row.getAverageTime() %>,
                                <%}%>
                            ],
                            backgroundColor: palette('tol-dv', numOfCategory).map(function(hex) {
                                return "#" + hex;
                            }),
                            borderWidth: 1
                        }]
                },
                options: {
                    legend: {
                        display: false
                    },
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        yAxes: [{
                                ticks: {
                                    beginAtZero: true,
                                }
                            }]
                    },

                    title: {
                        display: true,
                        text: 'Media secondi per tipologia di domanda'
                    }
                }

            });

            new Chart(lineChart, {
                type: 'bar',
                data: {
                    labels: [
                            <%for(StatisticRow row : quizStatistics.getStatisticRows()) {%>
                                `<%= row.getQuestionType().getName() %>`,
                            <%}%>
                        ],
                    datasets: [{
                            data: [
                                    <%for(StatisticRow row : quizStatistics.getStatisticRows()) {%>
                                        <%= row.getCorrectAnswers() %>,
                                    <%}%>
                            ],
                            backgroundColor: palette('tol-dv', numOfCategory).map(function(hex) {
                                return "#" + hex;
                            }),
                            fill: false,
                        }]
                },
                options: {
                    legend: {
                        display: false
                    },
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        yAxes: [{
                                ticks: {
                                    beginAtZero: true,
                                }
                            }]
                    },

                    title: {
                        display: true,
                        text: 'Risposte corrette per tipologia domanda'
                    }
                }

            });
        </script>
    </body>
</html>