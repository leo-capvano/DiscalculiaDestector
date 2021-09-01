<%@ page language="java" %>
<!DOCTYPE html>
<html>
<%@ page
        import="java.util.*,java.sql.*,entity.enums.Operation, entity.question.*, entity.quiz.*, com.github.kiprobinson.bigfraction.BigFraction" %>

<head>
    <%@ include file="WEB-INF/util/meta.jsp" %>
    <%@ include file="WEB-INF/util/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="css/operationTable.css"/>
    <link rel="stylesheet" type="text/css" href="css/inputKeyboard.css"/>
    <link rel="stylesheet" type="text/css" href="css/listeningQuestion.css"/>
    <link rel="stylesheet" type="text/css" href="css/fadingNumber.css"/>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/annyang/2.6.0/annyang.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/feedback.css"/>
    <link rel="stylesheet" type="text/css" href="css/discalculia.css"/>
    <link rel="stylesheet" type="text/css" href="css/spawnArea.css"/>
    <link rel="stylesheet" type="text/css" href="css/sortable.css"/>
    <link rel="stylesheet" type="text/css" href="css/fraction.css"/>
    <link rel="stylesheet" type="text/css" href="css/customInputText.css"/>
    <link rel="stylesheet" type="text/css" href="css/customInputNumber.css"/>
    <link rel="stylesheet" type="text/css" href="css/speakingTextBox.css"/>
    <link rel="stylesheet" type="text/css" href="css/customInputSlider.css"/>
    <link rel="stylesheet" type="text/css" href="css/floatingReadButton.css"/>
    <link rel="stylesheet" type="text/css" href="css/responsiveSkipButton.css"/>
    <link rel="stylesheet" type="text/css" href="css/dragAndDropComponent.css"/>
    <link rel="stylesheet" type="text/css" href="css/tooltip.css"/>
    <link rel="stylesheet" type="text/css" href="css/inputKeyboard.css"/>
    <link rel="stylesheet" type="text/css" href="css/operationTable.css"/>
    <%
        Object started = session.getAttribute("started");

        if (started == null || !((boolean) started)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        session.removeAttribute("started");
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        int numOfQuestions = questions.size();
        int progressPercentage = 1;
    %>

    <title>Test Discalculia</title>
</head>
<body style="background-color: #49576A">

<div class="container h-100 w-100 responsiveContainer">
    <%
        for (int i = 1; i <= numOfQuestions; i++) {
            Question currentQuestion = questions.get(i - 1);
            progressPercentage = (i * 100) / numOfQuestions;
    %>

    <div class="card my-3 p-0" id="question-<%=i%>" style="display:none; width:100%; height:100%">

        <!-- floating read button (only small screen) -->
        <a href="#" id="floatingReadButton" class="float" data-toggle="tooltip" title="leggi la pagina ad alta voce" onclick="readPage(<%=i%>)">
            <i class="fas fa-fw fa fa-volume-up"></i>
        </a>
        <!-- floating read button -->
        <!-- pulsante salta small screen -->
        <button id="floatingSkipButton" class="float skipQuestion" data-question-index="<%=i%>"
                data-questionID="<%=currentQuestion.getId()%>">
            <strong>Salta</strong><span class="fa fa-arrow-right px-1"></span>
        </button>
        <!-- pulsante salta small screen -->

        <div class="card-header text-center">

            <!-- toolbar container for readButton, progressBar and skip button -->
            <div class="floatBigContainer">

                <!-- read button for big screen -->
                <div class="col-1 readButtonContainer">
                    <a href="#"
                       onclick="readPage(<%=i%>)"
                       class="floatBig"
                       style="color: #343a40;"
                       data-toggle="tooltip" title="leggi la pagina ad alta voce">
                        <i class="fas fa-fw fa fa-volume-up"></i>
                    </a>
                </div>
                <!-- read button for big screen -->

                <!-- Progress Bar -->
                <div class="col" style="margin-left: 15px;">
                    <div class="progress">
                        <% if (progressPercentage < 33) {%>
                        <div class="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                             role="progressbar" aria-valuenow="<%=i%>" aria-valuemin="0"
                             aria-valuemax="100" style="width:<%=progressPercentage%>%;">
                            <span class="px-2"><%=progressPercentage%>% completato</span>
                        </div>
                        <%} else if ((progressPercentage >= 33) && (progressPercentage < 66)) {%>
                        <div class="progress-bar bg-warning progress-bar-striped progress-bar-animated"
                             role="progressbar" aria-valuenow="<%=i%>" aria-valuemin="0"
                             aria-valuemax="100" style="width:<%=progressPercentage%>%;">
                            <span class="px-2"><%=progressPercentage%>% completato</span>
                        </div>
                        <%} else if (progressPercentage >= 66) {%>
                        <div class="progress-bar bg-success progress-bar-striped progress-bar-animated"
                             role="progressbar" aria-valuenow="<%=i%>" aria-valuemin="0"
                             aria-valuemax="100" style="width:<%=progressPercentage%>%;">
                            <span class="px-2"><%=progressPercentage%>% completato</span>
                        </div>
                        <%}%>
                    </div>
                </div>
                <!-- Progress Bar -->

                <!-- pulsante salta big screen -->
                <div class="col-2">
                    <a class="responsiveSkipContainerBig">
                        <button class="btn btn-warning btn-md btn3d skipQuestion w-100" data-question-index="<%=i%>"
                                data-questionID="<%=currentQuestion.getId()%>"
                                style="display: flex; align-items: center; justify-content: center;">
                            Salta<span class="fa fa-arrow-right px-1"></span>
                        </button>
                    </a>
                </div>
                <!-- pulsante salta big screen -->
            </div>
            <!-- toolbar container for readButton, progressBar and skip button -->

            <!-- Progress Bar small screen -->
            <div class="row align-items-center mb-2">
                <div class="col">
                    <div class="progress progress-bar-smallscreen mb-3">
                        <% if (progressPercentage < 33) {%>
                        <div class="progress-bar bg-danger progress-bar-striped progress-bar-animated"
                             role="progressbar" aria-valuenow="<%=i%>" aria-valuemin="0"
                             aria-valuemax="100" style="width:<%=progressPercentage%>%;">
                            <span class="px-2"><%=progressPercentage%>% completato</span>
                        </div>
                        <%} else if ((progressPercentage >= 33) && (progressPercentage < 66)) {%>
                        <div class="progress-bar bg-warning progress-bar-striped progress-bar-animated"
                             role="progressbar" aria-valuenow="<%=i%>" aria-valuemin="0"
                             aria-valuemax="100" style="width:<%=progressPercentage%>%;">
                            <span class="px-2"><%=progressPercentage%>% completato</span>
                        </div>
                        <%} else if (progressPercentage >= 66) {%>
                        <div class="progress-bar bg-success progress-bar-striped progress-bar-animated"
                             role="progressbar" aria-valuenow="<%=i%>" aria-valuemin="0"
                             aria-valuemax="100" style="width:<%=progressPercentage%>%;">
                            <span class="px-2"><%=progressPercentage%>% completato</span>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
            <!-- Progress Bar small screen -->


            <div class="row">

                <!-- numero domanda -->
                <div class="col-lg-3 col-md-3 col-sm-12 mb-sm-2">
                    <div class="questionNumberContainer"><span
                            style="font-size: 1em; font-family: sans-serif;">domanda <%=i%></span>
                    </div>
                </div>
                <!-- numero domanda -->

                <!-- testo della domanda -->
                <div class="col-lg-9 col-md-9 col-sm-12" style="text-align: center;">
                    <span class="speakable<%=i%>"><%=currentQuestion.getQuestionText()%> </span>
                </div>
                <!-- testo della domanda -->

            </div>

        </div>

        <div class="card-body text-dark text-center">
            <%
                if (currentQuestion instanceof CalculationQuestion) {
                    CalculationQuestion calculationQuestion = (CalculationQuestion) currentQuestion;
                    Operation operationType = calculationQuestion.getOperationType();
                    String operation = null;
                    switch (operationType) {
                        case ADDITION:
                            operation = "+";
                            break;
                        case SUBTRACTION:
                            operation = "-";
                            break;
                        case MULTIPLICATION:
                            operation = "*";
                            break;
                        case DIVISION:
                            operation = "/";
                            break;
                        default:
                            operation = "?";
                            break;
                    }

                    if (calculationQuestion instanceof AcrossCalculationQuestion) {
                        AcrossCalculationQuestion question = (AcrossCalculationQuestion) calculationQuestion;
            %>

            <!-- testo di descrizione dell'operazione es: 2 * 3-->
            <div class="frac">
                <span><%= question.getOperand1() %></span>
                <% if (question.getOperand1Denominator() != null && !question.getOperand1Denominator().equals(1)) { %>
                <span class="symbol">/</span>
                <span class="bottom"><%= question.getOperand1Denominator()%></span>
                <% } %>
            </div>

            <%= operation %>

            <div class="frac">
                <span><%= question.getOperand2() %></span>
                <% if (question.getOperand2Denominator() != null && !question.getOperand2Denominator().equals(1)) { %>
                <span class="symbol">/</span>
                <span class="bottom"><%= question.getOperand2Denominator()%></span>
                <% } %>
            </div>
            <!-- fine testo operazione -->

            <div class="col-12">
                <!-- inputKeyboard operazione matematica-->
                <div class="inputKeyboardContainer">
                    <div class="row justify-content-center mb-2">
                        <input class="inputKeyboardDisplay" type="number" id="answer-<%=question.getId()%>"
                               pattern="[0-9]{9}">
                    </div>
                    <div class="row">
                        <div class="col keyboardButton" onclick="writeToDisplay(0, 'answer-<%=question.getId()%>')">
                            0
                        </div>
                        <div class="col keyboardButton" onclick="writeToDisplay(1, 'answer-<%=question.getId()%>')">
                            1
                        </div>
                        <div class="col keyboardButton" onclick="writeToDisplay(2, 'answer-<%=question.getId()%>')">
                            2
                        </div>
                    </div>
                    <div class="row">
                        <div class="col keyboardButton" onclick="writeToDisplay(3, 'answer-<%=question.getId()%>')">
                            3
                        </div>
                        <div class="col keyboardButton" onclick="writeToDisplay(4, 'answer-<%=question.getId()%>')">
                            4
                        </div>
                        <div class="col keyboardButton" onclick="writeToDisplay(5, 'answer-<%=question.getId()%>')">
                            5
                        </div>
                    </div>
                    <div class="row">
                        <div class="col keyboardButton" onclick="writeToDisplay(6, 'answer-<%=question.getId()%>')">
                            6
                        </div>
                        <div class="col keyboardButton" onclick="writeToDisplay(7, 'answer-<%=question.getId()%>')">
                            7
                        </div>
                        <div class="col keyboardButton" onclick="writeToDisplay(8, 'answer-<%=question.getId()%>')">
                            8
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-8 keyboardButton" onclick="writeToDisplay(9, 'answer-<%=question.getId()%>')">
                            9
                        </div>
                        <div class="col keyboardButton" onclick="clearDisplay('answer-<%=question.getId()%>')"><i
                                class="fas fa-backspace"></i>
                        </div>
                    </div>
                </div>
                <!-- inputKeyboard operazione matematica-->


                <% if ((question.getOperand1Denominator() != null && !question.getOperand1Denominator().equals(1))
                        || (question.getOperand2Denominator() != null && !question.getOperand2Denominator().equals(1))) { %>

                <div class="col-4"></div>
                <div class="col-4 my-2" style="border-top: 3px solid black"></div>
                <div class="col-4"></div>
                <div class="col-12">
                    <input class="form-control w-50 mx-auto denominator" type="number"
                           id="answer-denominator-<%=question.getId()%>">
                </div>
                <div class="col-12 my-4">
                    Se il denominatore non è necessario, lasciare il campo vuoto o scrivere 1
                </div>
                <% } %>
            </div>

            <% } else if (calculationQuestion instanceof InColumnCalculationQuestion) {
                InColumnCalculationQuestion question = (InColumnCalculationQuestion) calculationQuestion;
                String operand1 = String.valueOf(question.getOperand1());
                String operand2 = String.valueOf(question.getOperand2());

                int numOfDigitsOperand1 = operand1.length();
                int numOfDigitsOperand2 = operand2.length();

                int diff;

                if (numOfDigitsOperand1 > numOfDigitsOperand2) {
                    diff = numOfDigitsOperand1 - numOfDigitsOperand2;
                    for (int j = 0; j < diff; j++)
                        operand2 = " " + operand2;
                } else if (numOfDigitsOperand2 > numOfDigitsOperand1) {
                    diff = numOfDigitsOperand2 - numOfDigitsOperand1;
                    for (int j = 0; j < diff; j++)
                        operand1 = " " + operand1;
                }

                while (operand1.length() < 5) {
                    operand1 = " " + operand1;
                    operand2 = " " + operand2;
                }

            %>
            <!--risolvi l'operazione in colonna-->
            <div>
                <span class="card-title text-center speakable<%=i%> infoText">
                    Puoi usare i riquadri gialli al di sopra dei numeri per aiutarti con i riporti o i prestiti
                </span>
            </div>

            <table class="my-5 mx-auto operationTable" id="operation">
                <tr>
                    <%for (int j = 0; j < operand1.length(); j++) { %>
                    <td>
                        <input class="report cell" type="number" min="0" max="9">
                    </td>
                    <%}%>
                </tr>

                <tr>
                    <%for (int j = 0; j < operand1.length(); j++) { %>
                    <td class="text">
                        <strong><%=operand1.charAt(j) %>
                        </strong>
                    </td>
                    <%} %>

                    <td class="text">
                        <strong><%=operation%>
                        </strong>
                    </td>
                </tr>

                <tr>
                    <%for (int j = 0; j < operand2.length(); j++) { %>
                    <td class="text secondNumber">
                        <strong><%=operand2.charAt(j) %>
                        </strong>
                    </td>
                    <%} %>
                    <td class="text">
                        <strong>=</strong>
                    </td>
                </tr>

                <tr>
                    <%
                        for (int j = 0; j < operand2.length() - 5; j++) { %>
                    <td></td>
                    <% }

                        for (int j = 0; j < 5; j++) { %>
                    <td>
                        <input class="result cell" type="number" min="0" max="9" value="0"
                               id="answer-<%=j + 1%>-<%=question.getId()%>" onClick="this.select();">
                    </td>
                    <% } %>
                </tr>
            </table>
            <%--            <div>--%>
            <%--                <span class="card-title text-center speakable<%=i%> infoText">--%>
            <%--                    Tutti i campi blu devono essere compilati. Se alcuni campi non sono necessari per la risposta, scrivici il numero 0--%>
            <%--                </span>--%>
            <%--            </div>--%>

            <% } %>
            <%
            } else if (currentQuestion instanceof ListeningQuestion) {
                ListeningQuestion question = (ListeningQuestion) currentQuestion;
            %>
            <!-- ascolta il numero -->
            <div class="row align-items-center mb-4 justify-content-center">
                <div class="col-12 infoText">
                    <div>
                        <span class="speakable<%=i%>">Clicca sul pulsante azzurro per ascoltare il numero e scrivilo nel box sottostante</span>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center align-items-center">
                <!-- pulsante ascolta -->
                <div class="col-12 col-sm-4 mb-3">
                    <button class="btnListen" onclick="readNumber('<%=question.getToListenTo() %>')"
                            id="listenBtn-<%=question.getId() %>">
                        <i class="fas fa-fw fa fa-volume-up"></i> Ascolta
                    </button>
                </div>
                <!-- pulsante ascolta -->

                <!-- input text numero -->
                <div class="col mt-4 mt-sm-0" id="inputNumber">
                    <div class="tooltipListen" id="tooltipListen">
                        <span class="tooltiptextListen"
                              id="tooltiptextListen">Scrivi qua il numero ...</span>
                    </div>

                    <div class="inputKeyboardContainer">
                        <div class="row justify-content-center mb-2">
                            <input class="inputKeyboardDisplay" type="number" id="answer-<%=question.getId()%>"
                                   pattern="[0-9]{9}">
                        </div>
                        <div class="row">
                            <div class="col keyboardButton" onclick="writeToDisplay(0, 'answer-<%=question.getId()%>')">
                                0
                            </div>
                            <div class="col keyboardButton" onclick="writeToDisplay(1, 'answer-<%=question.getId()%>')">
                                1
                            </div>
                            <div class="col keyboardButton" onclick="writeToDisplay(2, 'answer-<%=question.getId()%>')">
                                2
                            </div>
                        </div>
                        <div class="row">
                            <div class="col keyboardButton" onclick="writeToDisplay(3, 'answer-<%=question.getId()%>')">
                                3
                            </div>
                            <div class="col keyboardButton" onclick="writeToDisplay(4, 'answer-<%=question.getId()%>')">
                                4
                            </div>
                            <div class="col keyboardButton" onclick="writeToDisplay(5, 'answer-<%=question.getId()%>')">
                                5
                            </div>
                        </div>
                        <div class="row">
                            <div class="col keyboardButton" onclick="writeToDisplay(6, 'answer-<%=question.getId()%>')">
                                6
                            </div>
                            <div class="col keyboardButton" onclick="writeToDisplay(7, 'answer-<%=question.getId()%>')">
                                7
                            </div>
                            <div class="col keyboardButton" onclick="writeToDisplay(8, 'answer-<%=question.getId()%>')">
                                8
                            </div>
                        </div>
                        <div class="row">
                            <div class="col keyboardButton" onclick="writeToDisplay(9, 'answer-<%=question.getId()%>')">
                                9
                            </div>
                            <div class="col keyboardButton" onclick="clearDisplay('answer-<%=question.getId()%>')"><i
                                    class="fas fa-backspace"></i>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- input text numero -->
            </div>
            <!-- ascolta il numero -->

            <% } else if (currentQuestion instanceof MathematicProblemQuestion) { %>

            <!-- problema matematico -->
            <%--            <div class="row align-items-center">--%>
            <%--                <div class="col-12">--%>
            <%--                    <fieldset class="spinner spinner--horizontal l-contain--medium mt-4">--%>
            <%--                        <div class="row justify-content-center">--%>
            <%--                            <button class="spinner__button spinner__button--left js-spinner-horizontal-subtract"--%>
            <%--                                    data-type="subtract" title="Subtract 1" aria-controls="spinner-input"--%>
            <%--                                    onclick="subtractButtonClick('spinner__input')"><i class="fas fa-minus"></i>--%>
            <%--                            </button>--%>
            <%--                            <input class="listenInput spinner__input js-spinner-input-horizontal"--%>
            <%--                                   type="number"--%>
            <%--                                   id="answer-<%=currentQuestion.getId()%>"--%>
            <%--                                   value="0"--%>
            <%--                                   pattern="[0-9]{9}">--%>
            <%--                            <button data-type="add"--%>
            <%--                                    class="spinner__button spinner__button--right js-spinner-horizontal-add"--%>
            <%--                                    title="Add 1" aria-controls="spinner-input"--%>
            <%--                                    onclick="addButtonClick('spinner__input')"><i class="fas fa-plus"></i></button>--%>
            <%--                        </div>--%>
            <%--                    </fieldset>--%>
            <%--                </div>--%>
            <%--            </div>--%>
            <div class="inputKeyboardContainer">
                <div class="row justify-content-center mb-2">
                    <input class="inputKeyboardDisplay" type="number" id="answer-<%=currentQuestion.getId()%>"
                           pattern="[0-9]{9}">
                </div>
                <div class="row">
                    <div class="col keyboardButton" onclick="writeToDisplay(0, 'answer-<%=currentQuestion.getId()%>')">
                        0
                    </div>
                    <div class="col keyboardButton" onclick="writeToDisplay(1, 'answer-<%=currentQuestion.getId()%>')">
                        1
                    </div>
                    <div class="col keyboardButton" onclick="writeToDisplay(2, 'answer-<%=currentQuestion.getId()%>')">
                        2
                    </div>
                </div>
                <div class="row">
                    <div class="col keyboardButton" onclick="writeToDisplay(3, 'answer-<%=currentQuestion.getId()%>')">
                        3
                    </div>
                    <div class="col keyboardButton" onclick="writeToDisplay(4, 'answer-<%=currentQuestion.getId()%>')">
                        4
                    </div>
                    <div class="col keyboardButton" onclick="writeToDisplay(5, 'answer-<%=currentQuestion.getId()%>')">
                        5
                    </div>
                </div>
                <div class="row">
                    <div class="col keyboardButton" onclick="writeToDisplay(6, 'answer-<%=currentQuestion.getId()%>')">
                        6
                    </div>
                    <div class="col keyboardButton" onclick="writeToDisplay(7, 'answer-<%=currentQuestion.getId()%>')">
                        7
                    </div>
                    <div class="col keyboardButton" onclick="writeToDisplay(8, 'answer-<%=currentQuestion.getId()%>')">
                        8
                    </div>
                </div>
                <div class="row">
                    <div class="col keyboardButton" onclick="writeToDisplay(9, 'answer-<%=currentQuestion.getId()%>')">
                        9
                    </div>
                    <div class="col keyboardButton" onclick="clearDisplay('answer-<%=currentQuestion.getId()%>')"><i
                            class="fas fa-backspace"></i></div>
                </div>
            </div>
            <!-- problema matematico -->

            <% } else if (currentQuestion instanceof NumbersLineQuestion) {
                NumbersLineQuestion question = (NumbersLineQuestion) currentQuestion;
                int minRange = question.getLeftBoundary();
                int maxRange = question.getRightBoundary();
                int middle = (minRange + maxRange) / 2;%>

            <div class="row">
                <div class="col-12">
                    <span class="speakable<%=i%>">
                        Sposta il cursore verde a destra o a sinistra e trova il numero <%= question.getCorrectAnswer()%>
                    </span>
                </div>
                <div class="col-12 mt-4">
                    <input type="range" min="<%= minRange %>" max="<%= maxRange %>"
                           value="<%= middle %>" class="slider"
                           id="answer-<%=question.getId()%>">
                </div>
                <div class="col-12 sliderButtonContainer">
                    <div class="arrowContainer mt-1">
                        <div class="arrow-up"></div>
                        <button class="sliderButton">
                            <%=minRange %>
                        </button>
                    </div>
                    <div class="arrowContainer mt-1">
                        <div class="arrow-up"></div>
                        <button class="sliderButton">
                            <%=maxRange %>
                        </button>
                    </div>
                </div>
            </div>

            <% } else if (currentQuestion instanceof TypeTheNumberQuestion) {
                TypeTheNumberQuestion question = (TypeTheNumberQuestion) currentQuestion;%>

            <!-- traduci in lettere il numero -->
            <div class="row">
                <div class="col-12 mb-2">
                    <div class="letterNumberContainer">
                        <span>
                            <a class="speakable<%=i%> boldVisibleText f-size-65-35 toBeTyped">
                                <%= question.getToBeTyped() %>
                            </a>
                        </span>
                        <input type="text" class="responsivePlaceholder form-input slidertext mt-3 p-3 customInputText"
                               height="60%" width="80%" placeholder="Scrivi qui il numero in lettere..."
                               id="answer-string-<%=question.getId()%>">
                    </div>
                </div>
            </div>
            <!-- traduci in lettere il numero -->

            <% } else if (currentQuestion instanceof GuessBallNumberQuestion) {
                GuessBallNumberQuestion question = (GuessBallNumberQuestion) currentQuestion;
                List<Choice> choices = question.getChoices();
            %>
            <!-- indovina il numero di cerchi in figura -->
            <div class="row align-items-center">
                <div class="col ballAreaCol">
                    <div class="ballAreaContainer">
                        <div class="spawn-area" data-balls="<%= question.getCorrectAnswer() %>"></div>
                    </div>
                </div>
                <div class="col-12 col-sm-12 col-md-6 mt-5">
                    <% for (Choice currentChoice : choices) {%>
                    <!-- indovina l'area buttons -->
                    <div class="col">
                        <div style="cursor: pointer; line-height: 80px"
                             class="btn btn-primary btn-lg btn3d addAnswer choiseBtn w-100"
                             data-question-index="<%=i%>" data-questionID="<%= question.getId() %>"
                             data-value="<%= currentChoice.getValue() %>"
                             data-value-denominator="<%= currentChoice.getValueDenominator() %>">
                            <div class="frac">
                                <span><%= currentChoice.getValue() %></span>
                                <% if (currentChoice.getValueDenominator() != null && !currentChoice.getValueDenominator().equals(1)) { %>
                                <span class="symbol">/</span>
                                <span class="bottom"><%= currentChoice.getValueDenominator()%></span>
                                <% } %>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
            <% } else if (currentQuestion instanceof GuessAreaQuestion) {
                GuessAreaQuestion question = (GuessAreaQuestion) currentQuestion;
                List<Choice> choices = question.getChoices(); %>

            <!-- riquadro con più superfice occupata -->
            <div class="row justify-content-around">
                <% for (Choice currentChoice : choices) {%>
                <div class="col mb-4">
                    <button type="button" class="guessAreaContainer addAnswer choiseBtn"
                            data-questionID="<%= question.getId() %>" data-value="<%= currentChoice.getValue() %>"
                            data-question-index="<%=i%>">
                        <div class="spawn-area" data-area="<%= currentChoice.getValue() %>"></div>
                    </button>
                </div>
                <% }%>
            </div>
            <!-- riquadro con più superfice occupata -->

            <% } else if (currentQuestion instanceof ReorderSequenceQuestion) {
                ReorderSequenceQuestion question = (ReorderSequenceQuestion) currentQuestion;
                List<Choice> choices = question.getChoices(); %>

            <!-- container esterno box da riordinare -->
            <div class="row justify-content-center sortable">
                <% for (Choice currentChoice : choices) {%>
                <div class="col my-2 my-sm-2">
                    <% if (currentChoice.getValueDenominator() != null && !currentChoice.getValueDenominator().equals(1)) { %>
                    <div class="sortable-element fractional mx-auto"
                         data-value="<%= BigFraction.valueOf(currentChoice.getValue(), currentChoice.getValueDenominator()).doubleValue() %>">
                        <div class="frac">
                            <span><%= currentChoice.getValue() %></span>
                            <span class="symbol">/</span>
                            <span class="bottom"><%= currentChoice.getValueDenominator()%></span>
                        </div>
                    </div>
                    <%} else { %>
                    <div class="sortable-element mx-auto"
                         data-value="<%= currentChoice.getValue() %>"><%= currentChoice.getValue() %>
                    </div>
                    <%}%>
                </div>
                <% }%>
            </div>
            <!-- container esterno box da riordinare -->

            <% } else if (currentQuestion instanceof HidingNumberQuestion) {
                HidingNumberQuestion question = (HidingNumberQuestion) currentQuestion; %>
            <!-- numero a scomparsa -->
            <div class="card-title row justify-content-center fade">
                <a class="fadingNumber boldVisibleText">
                    <%= question.getToBeHide() %>
                </a>
            </div>
            <!-- numero a scomparsa -->

            <% } else if (currentQuestion instanceof PositioningQuestion) {
                PositioningQuestion question = (PositioningQuestion) currentQuestion;
                List<Choice> choices = question.getChoices(); %>

            <!-- posiziona il numero -->
            <div style="margin-bottom:1em">
                <div class="row justify-content-center">
                    <span class="onlyMobileText">
                            <span class="infoText onlyMobileText speakable<%=i%>" style="font-size: 0.8em;"> Clicca l'icona<img
                                    src="images/click.png"
                                    alt="click">
                            per indicare dove si posiziona il numero: </span>
                    </span>
                    <span class="onlyDesktopText">
                            <span class="infoText onlyDesktopText speakable<%=i%>" style="font-size: 0.8em;">
                                Trascina il numero nel box blu nella posizione giusta
                            </span>
                    </span>
                </div>

                <div class="row justify-content-center align-items-center mt-3" id="draggableRow">
                    <div id="dragAndDropPositionContainer">
                        <div class="frac dragAndDrop" id="dragDropPosition">
                            <span class="speakable<%=i%>"
                                  style="font-size: 0.8em;"><%= question.getToBePlaced() %></span>
                            <% if (question.getToBePlacedDenominator() != null && !question.getToBePlacedDenominator().equals(1)) { %>
                            <span class="symbol">/</span>
                            <span class="bottom"><%= question.getToBePlacedDenominator()%></span>
                            <% } %>
                        </div>
                    </div>
                </div>
                <div class="my-4 mt-5 positionNumberRow">

                    <!-- elementi tra cui scegliere la posizione -->
                    <% for (int k = 0; k < choices.size(); k++) {
                        Choice choice = choices.get(k);
                    %>

                    <!-- numero -->
                    <div class="frac mr30">
                        <span><%= choice.getValue() %></span>
                        <% if (choice.getValueDenominator() != null && !choice.getValueDenominator().equals(1)) { %>
                        <span class="symbol">/</span>
                        <span class="bottom"><%= choice.getValueDenominator()%></span>
                        <% } %>
                    </div>

                    <!-- icona -->
                    <li class="droppableTarget addAnswer list-inline-item mr30" data-answer-index="<%=k + 1%>"
                        data-question-index="<%=i%>" data-questionID="<%= question.getId() %>"
                        data-value="<%= choice.getValue() %>"
                        data-value-denominator="<%= choice.getValueDenominator()%>">
                        <img src="images/click.png" class="droppableTargetIcon"
                             alt="clicca qui per scegliere questa posizione">
                    </li>
                    <% } %>
                </div>
                <!-- elementi tra cui scegliere la posizione -->

            </div>
            <!-- posiziona il numero -->

            <% }%>
            <%
                if (currentQuestion instanceof GuessAreaQuestion || currentQuestion instanceof PositioningQuestion) {
                    //Do nothing
                } else if (currentQuestion instanceof MultipleChoicesQuestion && !(currentQuestion instanceof ReorderSequenceQuestion)
                        && !(currentQuestion instanceof GuessBallNumberQuestion)) {
                    MultipleChoicesQuestion question = (MultipleChoicesQuestion) currentQuestion;
                    List<Choice> choices = question.getChoices(); %>

            <div class="row mb-4">
                <% for (Choice currentChoice : choices) {%>
                <!-- seleziona il numero massimo e numero a scomparsa -->
                <div class="col">
                    <div style="cursor: pointer; line-height: 80px"
                         class="btn btn-primary btn-lg btn3d addAnswer choiseBtn w-100"
                         data-question-index="<%=i%>" data-questionID="<%= question.getId() %>"
                         data-value="<%= currentChoice.getValue() %>"
                         data-value-denominator="<%= currentChoice.getValueDenominator() %>">
                        <div class="frac">
                            <span><%= currentChoice.getValue() %></span>
                            <% if (currentChoice.getValueDenominator() != null && !currentChoice.getValueDenominator().equals(1)) { %>
                            <span class="symbol">/</span>
                            <span class="bottom"><%= currentChoice.getValueDenominator()%></span>
                            <% } %>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <div class="row justify-content-center mt-5 mb-4">
                <div class="col-12">
                    <button type="button" class="btn btn-primary btn-lg btn3d addAnswer choiseBtn w-100"
                            data-questionID="<%= currentQuestion.getId() %>" data-question-index="<%=i%>">Conferma
                    </button>
                </div>
            </div>
            <% }%>

        </div>
    </div>
    <%}%>

    <!-- feedback section -->
    <div id="feedback" class="card text-center" style="display:none;">
        <div class="card-header">
            <h1>Aiutaci a migliorare</h1>
        </div>

        <div class="row justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">Le regole del test sono state chiare:</span> <br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="clearRules" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="clearRules" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="clearRules" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="clearRules" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="clearRules" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <div class="row mt-4 justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">Il test ha attirato la mia attenzione:</span><br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="levelOfAttention" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="levelOfAttention" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="levelOfAttention" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="levelOfAttention" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="levelOfAttention" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <div class="row mt-4 justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">Sono stato attento/a a cosa rispondevo nelle domande:</span><br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="attentionToQuestion" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="attentionToQuestion" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="attentionToQuestion" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="attentionToQuestion" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="attentionToQuestion" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <div class="row mt-4 justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">Le domande erano semplici:</span><br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="questionDifficulty" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="questionDifficulty" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="questionDifficulty" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="questionDifficulty" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="questionDifficulty" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <div class="row mt-4 justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">La durata del test era giusta:</span><br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="quizDuration" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizDuration" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizDuration" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizDuration" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizDuration" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <div class="row mt-4 justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">Penso di aver dato delle risposte corrette:</span><br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="selfAssestment" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="selfAssestment" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="selfAssestment" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="selfAssestment" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="selfAssestment" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <div class="row mt-4 justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">E' stato facile utilizzare il sito:</span><br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="websiteClearness" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="websiteClearness" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="websiteClearness" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="websiteClearness" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="websiteClearness" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <div class="row mt-4 justify-content-center">
            <div class="col-8 col-sm-7 mb-1">
                <span class="card-title flt">Era chiaro quale tasto premere durante il test:</span><br>
            </div>
            <div class="col-8 feedbackRadioContainer">
                <div class="col">
                    <label>
                        <input type="radio" name="quizClearness" value="1">
                        <img src="images/feedback/angry.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizClearness" value="2">
                        <img src="images/feedback/sad.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizClearness" value="3">
                        <img src="images/feedback/confused.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizClearness" value="4">
                        <img src="images/feedback/happy.png" alt="">
                    </label>
                </div>
                <div class="col">
                    <label>
                        <input type="radio" name="quizClearness" value="5">
                        <img src="images/feedback/superhappy.png" alt="">
                    </label>
                </div>
            </div>
        </div>

        <button type="button" class="btn btn-primary bt3d endTestButton mt-4">Concludi test</button>
    </div>
</div>

<script src="http://code.responsivevoice.org/responsivevoice.js"></script>
<script src="js/testDiscalculia.js"></script>
<script src="js/spawnArea.js"></script>
<script src="vendor/js/sortable.js"></script>
<script src="js/sortScript.js"></script>
<script src="js/customInputNumber.js"></script>
<script src="js/speakingTextBox.js"></script>
<script src="js/dragAndDropComponent.js"></script>
<script src="js/tooltip.js"></script>
<script src="js/listeningQuestion.js"></script>
<script src="js/inputKeyboard.js"></script>
</body>
</html>