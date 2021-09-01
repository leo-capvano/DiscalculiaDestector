$(document).ready(() => {
    let startTime;

    $('#question-1').show(0, function () {
        checkForBallToGenerate($(this));
    });

    setTimeout(() => {
        startTime = new Date();
    }, 1000);

    const answersList = [];
    const feedback = {};

    $("input.cell").keyup(onCellKeyUp);
    $("input.cell").focusout(onCellFocusOut);

    //On answer click
    $(".addAnswer").click(function () {

        $(this).attr("disabled", true);
        $(this).addClass("disabled");

        const questionID = $(this).attr("data-questionID");
        const index = parseInt($(this).attr("data-question-index"));

        let answer;
        let answerDenominator;
        let answerText = null;

        const msElapsed = new Date() - startTime;
        const seconds = Math.round(msElapsed / 1000);

        let answerIndex = parseInt($(this).attr("data-answer-index"));
        let answerValue = parseInt($(this).attr("data-value"));
        let answerQuestionID = parseInt($("#answer-" + questionID).val());
        let answerDenominatorValue = parseInt($(this).attr("data-value-denominator"));
        let answerDenominatorQuestionID = parseInt($("#question-" + index + " .denominator").val());

        if (Number.isNaN(answerIndex))
            answerIndex = null;
        if (Number.isNaN(answerValue))
            answerValue = null;
        if (Number.isNaN(answerQuestionID))
            answerQuestionID = null;
        if (Number.isNaN(answerDenominatorValue))
            answerDenominatorValue = null;
        if (Number.isNaN(answerDenominatorQuestionID))
            answerDenominatorQuestionID = null;

        answer = answerIndex || answerValue || answerQuestionID;
        answerDenominator = answerDenominatorValue || answerDenominatorQuestionID;

        if (!answer) {
            const num1 = $('#answer-1-' + questionID).val();
            const num2 = $('#answer-2-' + questionID).val();
            const num3 = $('#answer-3-' + questionID).val();
            const num4 = $('#answer-4-' + questionID).val();
            const num5 = $('#answer-5-' + questionID).val();

            if (!num1 || !num2 || !num3 || !num4 || !num5)
                answer = null;
            else {
                try {
                    answer = parseInt("" + num1 + num2 + num3 + num4 + num5);
                } catch (e) {
                    console.error("Invalid input");
                    answer = null;
                }
            }
        }

        if (!answer) {
            answerText = $("#answer-string-" + questionID).val();

            if (!answerText) {
                //This is a reorder question
                const elements = $(".sortable-element:visible");

                answerText = "";

                elements.each((index, element) => {
                    if ($(element).hasClass("fractional")) {
                        const numerator = $(element).find("span").first().text();
                        const symbol = $(element).find("span.symbol").text();
                        const denominator = $(element).find("span.bottom").text();

                        answerText += " - " + numerator + symbol + denominator;
                    } else
                        answerText += " - " + $(element).attr("data-value");
                });

                answerText = answerText.substring(3);

                console.log(answerText);
            }
        }

        if (answer || answer === 0 || answerText) {
            addAnswer(answersList, answer, answerDenominator, answerText, questionID, seconds);
            findNext(index, index + 1);

            setTimeout(function () {
                startTime = new Date();
            }, 500);
        } else {
            $(this).attr("disabled", false);
            $(this).removeClass("disabled");
            $("#question-" + index).effect("shake");
        }
    });

    //On skip answer click
    $('.skipQuestion').click(function () {
        const questionID = $(this).attr("data-questionID");
        const index = parseInt($(this).attr("data-question-index"));

        const msElapsed = new Date() - startTime;
        const seconds = Math.round(msElapsed / 1000);

        addAnswer(answersList, null, null, null, questionID, seconds, true);
        findNext(index, index + 1);
        responsiveVoice.cancel()

        setTimeout(function () {
            startTime = new Date();
        }, 500);
    });


    $(".endTestButton").click(function () {

        const button = $(this);

        button.attr("disabled", true);
        button.addClass("disabled");

        const originalValue = button.html();
        button.html("<span class='spinner-border spinner-border-sm text-light' role='status' aria-hidden='true'></span>");

        let clearRules = $('input[name=clearRules]:checked').val();
        let levelOfAttention = $('input[name=levelOfAttention]:checked').val();
        let attentionToQuestion = $('input[name=attentionToQuestion]:checked').val();
        let questionDifficulty = $('input[name=questionDifficulty]:checked').val();
        let quizDuration = $('input[name=quizDuration]:checked').val();
        let selfAssestment = $('input[name=selfAssestment]:checked').val();
        let websiteClearness = $('input[name=websiteClearness]:checked').val();
        let quizClearness = $('input[name=quizClearness]:checked').val();

        if (!clearRules || !levelOfAttention || !attentionToQuestion || !questionDifficulty || !quizDuration || !selfAssestment || !websiteClearness || !quizClearness) {
            alert("E' necessario rispondere a tutte le domande del feedback!");
            button.attr("disabled", false);
            button.removeClass("disabled");
            button.html(originalValue);
        } else {
            feedback.clearRules = clearRules;
            feedback.levelOfAttention = levelOfAttention;
            feedback.attentionToQuestion = attentionToQuestion;
            feedback.questionDifficulty = questionDifficulty;
            feedback.quizDuration = quizDuration;
            feedback.selfAssestment = selfAssestment;
            feedback.websiteClearness = websiteClearness;
            feedback.quizClearness = quizClearness;

            const quizData = {
                answersList: answersList,
                feedback: feedback
            };

            $.ajax({
                method: "POST",
                url: "pages/concludeQuiz",
                data: {
                    quizData: JSON.stringify(quizData)
                }
            }).done(data => {
                const response = JSON.parse(data);

                if (response.ok)
                    window.location.href = response.content.redirect;
                else {
                    button.attr("disabled", false);
                    button.removeClass("disabled");
                    button.html(originalValue);
                    alert(response.message);
                }
            }).fail(() => {
                button.attr("disabled", false);
                button.removeClass("disabled");
            });

        }
    });

});

const onCellKeyUp = event => {
    const target = $(event.target);

    const MAX = 9;
    const MIN = 0;

    const val = target.val();

    if (val > MAX)
        target.val(MAX);
    else if (val < MIN)
        target.val(MIN);
};

const onCellFocusOut = event => {
    const target = $(event.target);

    const val = target.val();

    if (!val)
        target.val(0);
};

const addAnswer = (answersList, answer, answerDenominator, answerText, questionID, seconds, skipped = false) => {

    const answerObject = {
        questionID: questionID,
        answer: answer,
        answerDenominator: answerDenominator,
        answerText: answerText,
        seconds: seconds,
        skipped: skipped
    };

    answersList.push(answerObject);
};

const findNext = (currentIndex, nextIndex) => {

    $('#question-' + currentIndex).hide('fade', {}, 100, function () {
        $(this).remove();
        const nextQuestionCard = document.querySelector("#question-" + nextIndex);
        let toShow = nextQuestionCard;

        if (!nextQuestionCard)
            toShow = $("#feedback");
        else
            toShow = $(nextQuestionCard);

        toShow.fadeIn('slow', function () {
            checkForBallToGenerate($(this));
        });

        window.scrollTo(0, 0);
        $(':input:enabled:visible:first').focus();
    });
};

const checkForBallToGenerate = element => {

    element.find(".spawn-area[data-area]").each((index, el) => {
        spawnBallByArea(el);
    });

    element.find(".spawn-area[data-balls]").each((index, el) => {
        spawnBallByNumber(el);
    });
};