$(document).ready(() => {
    $('#deleteModal').on('show.bs.modal', function (event) {
        const target = $(event.relatedTarget);

        // Extract info from data-* attributes
        const id = target.attr('data-id');

        let toPrint = "Sei sicuro di voler eliminare la domanda ?";

        const modal = $(this);
        modal.attr("data-id", id);
        modal.find('.modal-body').text(toPrint);
    });
    
    $('#addModal').on('hide.bs.modal', function (event) {
       $(this).find("form").trigger("reset"); 
    });

    $('#addModal .modal-footer .btn-primary').click(onAddQuestionSubmit);
    $('#deleteModal .modal-footer .btn-danger').click(onDeleteQuestionSubmit);

    $('#collapseToggle').click(onCollapseToggleClick);
    
    $('.dropdown-item').click(onDropdownItemClick);
});

const onAddQuestionSubmit = event => {
    const modal = $("#addModal");
    const form = modal.find("form");
    form.addClass("was-validated");
    const infoAlert = modal.find("#errorAlert");
    infoAlert.addClass("d-none");

    const invalidInputs = form.find(".form-control:invalid");

    if (invalidInputs.length > 0)
        return;

    const target = $(event.target);
    const originalHtml = target.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    target.attr("disabled", true);
    target.html(loadingSpinner + originalHtml);

    const requestData = {
        questionSectionID: modal.attr("data-id")
    };

    //Getting input's value
    const inputs = form.find("input");
    const textAreas = form.find("textarea");
    const selects = form.find("select");

    inputs.each(function () {
        const key = $(this).attr("name");
        const value = $(this).val();
        requestData[key] = value;
    });
    
    textAreas.each(function() {
        const key = $(this).attr("name");
        const value = $(this).val();
        requestData[key] = value;
    });

    selects.each(function () {
        const key = $(this).attr("name");
        const value = $(this).find("option:selected").val();
        requestData[key] = value;
    });

    $.ajax({
        method: "POST",
        url: "addQuestion",
        data: requestData
    }).done(data => {
        const response = JSON.parse(data);

        if (response.ok) {
            const table = $("table.table tbody");
            const noQuestionRow = table.find("#no-section");

            const numRows = table.find("tr:not([id='no-section'])").length + 1;

            if (numRows != 0)
                noQuestionRow.addClass("d-none");
            else
                noQuestionRow.removeClass("d-none");
            
            const question = response.content.question;
            const correctAnswer = question.correctAnswer ? question.correctAnswer : question.correctAnswerText;
            const correctAnswerDenominator = question.correctAnswerDenominator;

            let toAppend = `<tr id="d-${question.id}">
                                <td scope="row">${question.questionText}</td>
                                <td colspan=2>`;

            if (response.content.choices) {
                toAppend += `<select class="form-control">`;
                response.content.choices.forEach(element => {
                    console.log(element);
                    toAppend += `<option>${element.value}`;
                    if(element.valueDenominator && element.valueDenominator != 1) {
                        toAppend += `/ ${element.valueDenominator}`;
                    }
                });
                toAppend += `</select>`;
            } else {
                toAppend += `<span class="font-italic">Non sono previste scelte</span>`;
            }

            toAppend += `
                        </td>
                        <td>${correctAnswer}`;
            
            if(correctAnswerDenominator)
                toAppend += ` / ${correctAnswerDenominator}`;
            
            toAppend += `</td>
                        <td class="text-secondary text-right">
                                <i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-id="${question.id}"></i>
                        </td>
                </tr>`;

            table.append(toAppend);

            form.removeClass("was-validated");
            modal.modal('hide');
            showAlert($("#infoAlert"), "Operazione eseguita con successo", true);
            setTimeout(() => {
                $("#infoAlert").slideUp(300, function () {
                    $(this).addClass("d-none");
                    $(this).attr("style", "");
                });
            }, 3000);
        } else {
            form.removeClass("was-validated");
            showAlert(infoAlert, response.message, false);
        }
    }).fail(() => {
        showAlert(infoAlert, "Errore lato server. Riprova più tardi", false);
    }).always(() => {
        target.html(originalHtml);
        target.attr("disabled", false);
    });

};

const onDeleteQuestionSubmit = event => {
    const modal = $("#deleteModal");
    const id = modal.attr("data-id");
    const infoAlert = $("#infoAlert");

    console.log("Deleting question with id", id);

    $.ajax({
        method: "POST",
        url: "deleteQuestion",
        data: {
            id: id
        }
    }).done(data => {
        const response = JSON.parse(data);

        if (response.ok) {
            const toDelete = $("tr#d-" + id);
            const numRows = $("table tbody").find("tr:not([id='no-section'])").length;

            if (numRows - 1 == 0) {
                $("#no-section").removeClass("d-none");
            }

            toDelete.remove();

            showAlert(infoAlert, "Operazione eseguita con successo", true);
        } else
            showAlert(infoAlert, response.message, false);
    }).fail(() => {
        showAlert(infoAlert, "Impossibile eseguire l'azione. Riprova piu' tardi.", false);
    }).always(() => {
        modal.modal('hide');

        setTimeout(() => {
            infoAlert.slideUp(300, function () {
                $(this).addClass("d-none");
                $(this).attr("style", "");
            });
        }, 3000);
    });

};

const showAlert = (alert, message, success) => {

    if (success) {
        alert.removeClass("alert-danger");
        alert.addClass("alert-success");
    } else {
        alert.addClass("alert-danger");
        alert.removeClass("alert-success");
    }

    alert.removeClass("d-none");
    alert.text(message);
};

const onCollapseToggleClick = event => {
    const collapse = $(".collapse#ballCollapse");
    const buttonTarget = $('#collapseToggle');

    if (collapse.hasClass("show")) {
        buttonTarget.html("Anteprima");
        collapse.collapse('hide');
        return;
    }

    const originalHtml = buttonTarget.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    buttonTarget.attr("disabled", true);
    buttonTarget.html(loadingSpinner + originalHtml);

    const spawnArea = document.querySelector(".spawn-area");
    const input = $("#addModal #number");

    const settedArea = spawnArea.getAttribute("data-area");

    collapse.collapse('show');
    if (!(input.val() == "") && !(settedArea == input.val())) {
        spawnArea.innerHTML = "";
        spawnArea.setAttribute("data-area", input.val());
        spawnBallByArea(spawnArea);
    }

    buttonTarget.html("Nascondi");
    buttonTarget.attr("disabled", false);
};

const onDropdownItemClick = event => {
    const element = $(event.target);
    const parent = element.parent();
    const inputGroup = parent.parent().parent();
    const inputGroupButton = inputGroup.find("button[data-toggle]");
    const symbolElement = inputGroup.find("[data-role='symbol']");
    const denominatorElement = inputGroup.find("[data-role='denominator'");
    
    const fractionValue = "Frazione";
    const integerValue = "Intero";
    
    parent.find('.dropdown-item').removeClass("active");
    element.addClass("active");
    inputGroupButton.text(element.text());
    
    if(inputGroupButton.text() == fractionValue) {
        denominatorElement.attr("required", true);
        denominatorElement.css("display", "flex");
        
        symbolElement.css("display", "flex");
    }
    else if(inputGroupButton.text() == integerValue) {
        denominatorElement.attr("required", false);
        denominatorElement.css("display", "none");
        
        symbolElement.css("display", "none");
    }
};