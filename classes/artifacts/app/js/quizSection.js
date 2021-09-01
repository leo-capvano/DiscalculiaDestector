$(document).ready(() => {

    $('#addModal').on('show.bs.modal', function (event) {
        const button = $(event.relatedTarget);
        const combobox = $("#reparti");
        const formGroup = $("#reparti-group");

        // Extract info from data-* attributes
        const title = button.attr('data-title');
        const isQuiz = button.attr('data-combo');

        const modal = $(this);
        modal.removeAttr("data-combo");
        modal.attr("data-combo", isQuiz);
        modal.find('.modal-title').text(title);
        modal.find('.modal-footer button.btn-primary').text(title);

        if (isQuiz) {
            combobox.attr("required", true);
            formGroup.removeClass("d-none");
        } else {
            combobox.attr("required", false);
            formGroup.addClass("d-none");
        }
    });

    $('#deleteModal').on('show.bs.modal', function (event) {
        const target = $(event.relatedTarget);

        // Extract info from data-* attributes
        const name = target.attr('data-name');
        const isQuiz = target.attr('data-isquiz');
        const id = target.attr('data-id');

        let toPrint = "Sei sicuro di voler eliminare ";

        if (isQuiz)
            toPrint += "il quiz '";
        else
            toPrint += "il reparto '";

        const modal = $(this);
        modal.attr("data-isquiz", isQuiz);
        modal.attr("data-id", id);
        modal.find('.modal-body').text(toPrint + name + "' ?");
    });

    $('#editModal').on('show.bs.modal', function (event) {
        const target = $(event.relatedTarget);

        // Extract info from data-* attributes
        const id = target.attr('data-id');
        const name = target.attr('data-name');
        const sogliaLivelloAdeguato = target.attr('data-sla');
        const sogliaRischioLieve = target.attr('data-srl');
        const sogliaRischioElevato = target.attr('data-sre');

        const modal = $(this);
        modal.attr("data-id", id);
        modal.find('.modal-title').text('Modifica ' + name);
        modal.find('.modal-body input#nome').val(name);
        modal.find('.modal-body input#adeguata').val(sogliaLivelloAdeguato);
        modal.find('.modal-body input#lieve').val(sogliaRischioLieve);
        modal.find('.modal-body input#elevato').val(sogliaRischioElevato);

    });

    $("#addModal, #deleteModal, #editModal").on("hidden.bs.modal", function () {
        const modal = $(this);
        const alert = modal.find(".alert");
        const form = modal.find("form");

        alert.addClass("d-none");
        form.removeClass("was-validated");
        form.trigger('reset');
    });

    $('#editModal .modal-footer .btn-primary').click(onEditSubmit);
    $('#deleteModal .modal-footer .btn-danger').click(onDeleteSubmit);
    $('#addModal .modal-footer .btn-primary').click(onAddSubmit);
});

const onEditSubmit = event => {
    const target = $(event.target);
    const originalHtml = target.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    target.attr("disabled", true);
    target.html(loadingSpinner + originalHtml);

    const form = $("#editModal");
    const id = form.attr("data-id");
    const editElement = $("i.fa-edit[data-id='" + id + "']");

    const alert = form.find(".alert");
    const nameValue = form.find("#nome").val().trim();
    const slaValue = form.find("#adeguata").val();
    const srlValue = form.find("#lieve").val();
    const sreValue = form.find("#elevato").val();

    alert.addClass("d-none");

    const requestData = {
        id: id
    };

    if (nameValue != null && nameValue != "" && editElement.attr("data-name") != nameValue)
        requestData.name = nameValue;

    if (slaValue != null && slaValue != "" && editElement.attr("data-sla") != slaValue)
        requestData.appropriete = slaValue;

    if (srlValue != null && srlValue != "" && editElement.attr("data-srl") != srlValue)
        requestData.lowRisk = srlValue;

    if (sreValue != null && sreValue != "" && editElement.attr("data-sre") != sreValue)
        requestData.highRisk = sreValue;

    if (!requestData.name && !requestData.appropriete && !requestData.lowRisk && !requestData.highRisk) {
        //Nothing to submit
        target.html(originalHtml);
        target.attr("disabled", false);
        showAlert(alert, "Modifica effettuata con successo", true);
    } else {
        $.ajax({
            method: "POST",
            url: "editQuiz",
            data: requestData
        }).done(data => {
            const response = JSON.parse(data);

            if (response.ok) {
                const isTrusted = response.content.trusted;
                const nameElement = $("#q-" + id + " .quiz-name a");
                const deleteElement = $("i.fa-trash-alt[data-id='" + id + "']");

                form.find(".modal-title").text("Modifica " + nameValue);
                if(!isTrusted)
                    nameElement.text(nameValue);
                else
                    nameElement.html(`${nameValue} <i class="fas fa-check-circle text-primary"></i>`);

                editElement.attr("data-name", nameValue);
                editElement.attr("data-sla", slaValue);
                editElement.attr("data-srl", srlValue);
                editElement.attr("data-sre", sreValue);

                deleteElement.attr("data-name", nameValue);

                showAlert(alert, "Modifica effettuata con successo", true);
            } else {
                showAlert(alert, "Si e' verificato un errore. Riprova piu' tardi", false);
            }
        }).always(() => {
            target.html(originalHtml);
            target.attr("disabled", false);
        });
    }


};

const onDeleteSubmit = event => {
    const target = $(event.target);
    const originalHtml = target.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    target.attr("disabled", true);
    target.html(loadingSpinner + originalHtml);

    const modal = $("#deleteModal");
    const isQuiz = modal.attr("data-isquiz");
    const id = modal.attr("data-id");

    let action;

    if (isQuiz)
        action = "deleteQuiz";
    else
        action = "admin/deleteQuizSection";

    $.ajax({
        method: "POST",
        url: action,
        data: {
            id: id
        }
    }).done(data => {
        const response = JSON.parse(data);

        if (response.ok) {
            if (isQuiz) {
                const toRemove = $("#q-" + id);
                const quizRows = toRemove.parent().find(".row");

                if (quizRows.length - 1 == 0) {
                    toRemove.empty();
                    toRemove.html("<div class='col pl-2'>Nessun quiz per questo reparto</div>");
                } else {
                    toRemove.slideUp(300, function () {
                        $(this).remove();
                    });
                }
            } else {
                const toRemove = $(".row[href='#quiz-list-" + id + "']");
                const subMenuToRemove = $("#quiz-list-" + id);
                const repartRows = toRemove.parent().find("> .row:not(#thead)");

                if (repartRows.length - 1 == 0) {
                    toRemove.remove();
                    subMenuToRemove.remove();
                    const table = $("#table");
                    table.addClass("d-none");
                    table.append('<div id="no-repart" class="alert alert-dark mt-4" role="alert"><h4 class="alert-heading">Nessun reparto quiz</h4><hr><p class="mb-0 d-flex align-items-center">Al momento non vi &egrave; alcun reparto quiz. <button class="btn btn-link" data-toggle="modal" data-target="#addModal" data-title="Aggiungi reparto">Creane uno</button></p></div>');
                    $("#no-repart").insertBefore(table);
                } else {

                    subMenuToRemove.slideUp(100, function () {
                        $(this).remove;
                    });

                    toRemove.slideUp(300, function () {
                        $(this).remove();
                    });
                }
            }

            modal.modal('hide');
            const infoAlert = $("#infoAlert");
            showAlert(infoAlert, "Operazione effettuata con successo", true);

            setTimeout(() => {
                infoAlert.slideUp(300, function () {
                    $(this).addClass("d-none");
                    $(this).attr("style", "");
                });
            }, 3000);
        } else {
            const infoAlert = $("#infoAlert");
            showAlert(infoAlert, "Impossibile effettuare l'operazione. Riprova piu' tardi.", false);

            setTimeout(() => {
                infoAlert.slideUp(300, function () {
                    $(this).addClass("d-none");
                    $(this).attr("style", "");
                });
            }, 3000);
        }
    }).always(() => {
        target.html(originalHtml);
        target.attr("disabled", false);
    });

};

const onAddSubmit = event => {
    const modal = $("#addModal");
    const form = modal.find("form");
    form.addClass("was-validated");

    const invalidInputs = form.find(".form-control:invalid");

    if (invalidInputs.length > 0)
        return;

    const target = $(event.target);
    const originalHtml = target.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    target.attr("disabled", true);
    target.html(loadingSpinner + originalHtml);

    const isQuiz = modal.attr("data-combo");

    const name = form.find("#nome").val();
    const description = form.find("#descrizione").val();

    let action;
    const requestData = {
        name: name,
        description: description
    };

    if (isQuiz) {
        action = "addQuiz";
        requestData.sectionID = form.find("#reparti option:selected").val();
    } else
        action = "admin/addQuizSection";

    // AJAX request

    $.ajax({
        method: "POST",
        url: action,
        data: requestData
    }).done(data => {
        const response = JSON.parse(data);

        if (response.ok) {
            const table = $("#table");

            if (!isQuiz) {
                const sectionID = response.content.id;
                
                const repartRows = table.find("> .row:not(#thead)");
                const select = form.find("#reparti");

                let toAppend = `<div data-toggle="collapse" href="#quiz-list-${sectionID}" aria-expanded="false" aria-controls="quiz-list-${sectionID}" class="row align-items-center">
					<div class="col">${name}</div>
					<div class="col">${description}</div>
					<div class="col-2 text-secondary text-right"><i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-name="${name}" data-id="${sectionID}"></i><i class="fas fa-angle-down"></i></div>
				</div>`;

                toAppend += `<div class="collapse sub-menu" id="quiz-list-${sectionID}">
					<div class="row ml-0 align-items-center disabled">
						<div class="col pl-2">Nessun quiz per questo reparto</div>
					</div>
				</div>`;

                if (repartRows.length == 0) {
                    $("#no-repart").remove();
                }

                table.append(toAppend);
                select.append(`<option value="${sectionID}">${name}</option>`);
                table.removeClass("d-none");
            } else {
                const sectionID = response.content.quiz.quizSection.id;
                const quizID = response.content.quiz.id;
                const approprieteThreshold = response.content.quiz.approprieteThreshold;
                const lowRiskThreshold = response.content.quiz.lowRiskThreshold;
                const highRiskThreshold = response.content.quiz.highRiskThreshold;
                const isTrusted = response.content.quiz.trusted;
                
                const subMenu = $("#quiz-list-" + sectionID);
                const disabledQuizRows = subMenu.find(".row.disabled");

                let toAppend = `<div class="row ml-0 align-items-center" id="q-${quizID}">
                                        <div class="col pl-2 quiz-name"><a href="SezioniControl?action=showQuiz&id=${quizID}" class="text-reset">${name}</a>`;
                
                if(isTrusted)
                    toAppend += `<i class="fas fa-check-circle text-primary"></i>`;
                
                toAppend += `</div>
                                <div class="col-2 text-secondary text-right"><i class="fas fa-edit p-2 primary" data-toggle="modal" data-target="#editModal" data-id="${quizID}" data-name="${name}" data-sla="${approprieteThreshold}" data-srl="${lowRiskThreshold}" data-sre="${highRiskThreshold}"></i><i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-id="${quizID}" data-name="${name}" data-isQuiz="true" data-id="${quizID}"></i></div>
                            </div>`;

                if (disabledQuizRows.length > 0) {
                    disabledQuizRows.remove();
                }

                subMenu.append(toAppend);
            }

            modal.modal('hide');
            const infoAlert = $("#infoAlert");
            showAlert(infoAlert, "Operazione effettuata con successo", true);

            setTimeout(() => {
                infoAlert.slideUp(300, function () {
                    $(this).addClass("d-none");
                    $(this).attr("style", "");
                });
            }, 3000);
        } else {
            const alert = modal.find(".alert");

            form.removeClass("was-validated");
            showAlert(alert, response.message, false);
        }
    }).fail(() =>{
        const alert = modal.find(".alert");

        form.removeClass("was-validated");
        showAlert(alert, "Impossibile eseguire l'operazione. Riprova piu' tardi.", false);
    }).always(() => {
        target.html(originalHtml);
        target.attr("disabled", false);
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