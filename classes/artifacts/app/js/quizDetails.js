$(document).ready(() => {

    $('#editModal').on('show.bs.modal', function (event) {
        const target = $(event.relatedTarget);

        // Extract info from data-* attributes
        const name = target.attr('data-name');
        const description = target.attr('data-description');
        const type = target.attr("data-type");
        const numChoices = target.attr("data-numchoices");
        const id = target.attr('data-id');

        const modal = $("#editModal");
        const form = modal.find("form");

        form.find("#nome").val(name);
        form.find("#descrizione").val(description);
        form.find("#numScelte").val(numChoices);
        modal.attr("data-id", id);

        const numChoicesFormGroup = form.find("#numScelteFormGroup");
        const questionType = getQuestionTypeById(type);
        if (!questionType.maxMultipleChoices) {
            numChoicesFormGroup.addClass("d-none");
            numChoicesFormGroup.find('input').removeAttr('max');
            form.find("#numScelte").val(1);
        } else {
            numChoicesFormGroup.removeClass("d-none");
            numChoicesFormGroup.find('input').attr('max', questionType.maxMultipleChoices);
        }

    });

    $('#editQuizModal').on('show.bs.modal', function (event) {
        const target = $(this);

        // Extract info from data-* attributes
        const id = target.attr('data-id');
        const name = target.attr('data-name');
        const sogliaLivelloAdeguato = target.attr('data-sla');
        const sogliaRischioLieve = target.attr('data-srl');
        const sogliaRischioElevato = target.attr('data-sre');

        target.find('.modal-body input#nome').val(name);
        target.find('.modal-body input#adeguata').val(sogliaLivelloAdeguato);
        target.find('.modal-body input#lieve').val(sogliaRischioLieve);
        target.find('.modal-body input#elevato').val(sogliaRischioElevato);
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
            toPrint += "la sezione '";

        const modal = $(this)
        modal.attr("data-isquiz", isQuiz);
        modal.attr("data-id", id);
        modal.find('.modal-body').text(toPrint + name + "' ?");
    });

    $("#addModal, #deleteModal, #editModal, #editQuizModal").on("hidden.bs.modal", function () {
        const modal = $(this);
        const alert = modal.find(".alert");
        const form = modal.find("form");

        alert.addClass("d-none");
        form.removeClass("was-validated");
        form.trigger("reset");
    });

    $("#addModal form select#tipo").change(onModalTipoChange);

    $("#addModal .modal-footer .btn-primary").click(onAddSezioneSubmit);
    $("#editModal .modal-footer .btn-primary").click(onEditSubmit);
    $("#editQuizModal .modal-footer .btn-primary").click(onEditQuizSubmit);
    $('#deleteModal .modal-footer .btn-danger').click(onDeleteSubmit);
});

const getQuestionTypeById = id => {
    
    for(let i = 0; i < questionTypes.length; i++) {
        if(questionTypes[i].id == id)
            return questionTypes[i];
    }
    
    return null;
};

const onModalTipoChange = event => {
    const target = $(event.target);
    const selectedValue = target.find("option:selected").val();
    const formGroup = $("#numScelteFormGroup");
    const numScelte = formGroup.find("#numScelte");

    const questionType = getQuestionTypeById(selectedValue);

    //If is oneChoiceItem
    if (!questionType.maxMultipleChoices) {
        numScelte.attr("min", 1);
        numScelte.removeAttr("max");
        numScelte.val(1);
        formGroup.fadeOut();
    } else {
        numScelte.attr("min", 2);
        numScelte.attr("max", questionType.maxMultipleChoices);
        numScelte.val(2);
        formGroup.fadeIn();
    }

};

const onAddSezioneSubmit = event => {
    const modal = $("#addModal");
    const form = modal.find("form");
    const infoAlert = $("#infoAlert");
    form.addClass("was-validated");

    const invalidInputs = form.find(".form-control:invalid");

    if (invalidInputs.length > 0)
        return;

    const target = $(event.target);
    const originalHtml = target.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    target.attr("disabled", true);
    target.html(loadingSpinner + originalHtml);

    //Retrieving value
    const name = form.find("#nome").val();
    const description = form.find("#descrizione").val();
    const type = form.find("#tipo option:selected").val();
    const numChoices = form.find("#numScelte").val() == null || form.find("#numScelte").val() == 1 ? null : form.find("#numScelte").val();
    const quizID = modal.attr("data-id");
    let isMultipleChoice;
    if(numChoices)
        isMultipleChoice = true;
    else
        isMultipleChoice = false;

    const requestData = {
        quizID: quizID,
        name: name,
        description: description,
        questionTypeID: type,
        numOfChoices: numChoices,
        isMultipleChoice: isMultipleChoice
    };

    //Richiesta AJAX
    $.ajax({
        method: "POST",
        url: "addQuestionSection",
        data: requestData
    }).done(data => {
        const response = JSON.parse(data);

        if (response.ok) {
            const table = $("#table");
            const sezRows = table.find(".row:not(#thead, #no-section)");

            const toAppend = `<div id="sd-${response.content.questionSection.id}" class="row align-items-center">
                                    <div class="col">${response.content.questionSection.name}</div>
                                    <div class="col">${response.content.questionSection.description}</div>
                                    <div class="col-3 text-secondary text-right">
                                            <div class="row align-items-center">
                                                    <div class="col col-sm-12 col-xl text-center">
                                                            <a class="text-reset" href="questionsList?questionSection=${response.content.questionSection.id}"><i class="fas fa-list primary"></i></a>
                                                    </div>
                                                    <div class="col col-sm-12 col-xl text-center">
                                                            <i class="fas fa-edit p-2 primary" data-toggle="modal" data-target="#editModal" data-id="${response.content.questionSection.id}" data-name="${response.content.questionSection.name}" data-description="${response.content.questionSection.description}" data-type="${response.content.questionSection.questionType.id}" data-numChoices="${response.content.questionSection.numOfChoices}"></i>
                                                    </div>
                                                    <div class="col col-sm-12 col-xl text-center">
                                                            <i class="fas fa-trash-alt p-2 danger" data-toggle="modal" data-target="#deleteModal" data-id="${response.content.questionSection.id}" data-name="${response.content.questionSection.name}"></i>
                                                    </div>
                                            </div>
                                    </div>
                            </div>`;

            if (sezRows.length == 0) {
                const noSectionRow = table.find("#no-section");
                noSectionRow.remove();
            }

            table.append(toAppend);
            showAlert(infoAlert, "Operazione effettuata con successo", true);
        } else {
            showAlert(infoAlert, response.message, false);
        }

        modal.modal('hide');

        setTimeout(() => {
            infoAlert.slideUp(300, function () {
                $(this).addClass("d-none");
                $(this).attr("style", "");
            });
        }, 3000);
    }).fail(() => {
        showAlert(infoAlert, "Impossibile eseguire l'operazione. Riprova piu' tardi.", false);
    }).always(() => {
        target.html(originalHtml);
        target.attr("disabled", false);

        setTimeout(() => {
            infoAlert.slideUp(300, function () {
                $(this).addClass("d-none");
                $(this).attr("style", "");
            });
        }, 3000);
    });
};

const onEditSubmit = event => {
    const form = $("#editModal");
    const modalForm = form.find("form");
    modalForm.addClass("was-validated");

    const invalidInputs = modalForm.find(".form-control:invalid");

    if (invalidInputs.length > 0)
        return;

    const target = $(event.target);
    const originalHtml = target.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    target.attr("disabled", true);
    target.html(loadingSpinner + originalHtml);

    const id = form.attr("data-id");
    const editElement = $("i.fa-edit[data-id='" + id + "']");

    const alert = $("#infoAlert");
    const nameValue = form.find("#nome").val().trim();
    const descriptionValue = form.find("#descrizione").val().trim();
    const numChoicesValue = form.find("#numScelte").val();

    const requestData = {
        id: id
    };

    if (nameValue != null && nameValue != "" && editElement.attr("data-name") != nameValue)
        requestData.name = nameValue;

    if (descriptionValue != null && descriptionValue != "" && editElement.attr("data-description") != descriptionValue)
        requestData.description = descriptionValue;

    if (numChoicesValue != null && numChoicesValue != "" && editElement.attr("data-numchoices") != numChoicesValue)
        requestData.numChoices = numChoicesValue;

    if (!requestData.name && !requestData.description && !requestData.numChoices) {
        //Nothing to submit
        target.html(originalHtml);
        target.attr("disabled", false);
        showAlert(alert, "Modifica effettuata con successo", true);

        form.modal('hide');

        setTimeout(() => {
            alert.slideUp(300, function () {
                $(this).addClass("d-none");
                $(this).attr("style", "");
            });
        }, 3000);
    } else {

        console.log(requestData);

        $.ajax({
            method: "POST",
            url: "editQuestionSection",
            data: requestData
        }).done(data => {
            const response = JSON.parse(data);

            if (response.ok) {
                const rowElement = $("#sd-" + id);
                const nameElement = rowElement.find("> .col:nth-child(1)");
                const descriptionElement = rowElement.find("> .col:nth-child(2)");
                const deleteElement = $("i.fa-trash-alt[data-id='" + id + "']");

                nameElement.text(nameValue);
                descriptionElement.text(descriptionValue);

                editElement.attr("data-name", nameValue);
                editElement.attr("data-description", descriptionValue);
                editElement.attr("data-numchoices", numChoicesValue);

                deleteElement.attr("data-name", nameValue);

                showAlert(alert, "Modifica effettuata con successo", true);
            } else {
                showAlert(alert, response.message, false);
            }

            form.modal('hide');
        }).fail(() => {
            showAlert(alert, "Si e' verificato un errore. Riprova piu' tardi", false);
        }).always(() => {
            target.html(originalHtml);
            target.attr("disabled", false);

            setTimeout(() => {
                alert.slideUp(300, function () {
                    $(this).addClass("d-none");
                    $(this).attr("style", "");
                });
            }, 3000);
        });
    }
};

const onEditQuizSubmit = event => {
    const target = $(event.target);
    const originalHtml = target.html();
    const loadingSpinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>';

    target.attr("disabled", true);
    target.html(loadingSpinner + originalHtml);

    const form = $("#editQuizModal");
    const id = form.attr("data-id");

    const alert = form.find(".alert");
    const nameValue = form.find("#nome").val().trim();
    const slaValue = form.find("#adeguata").val();
    const srlValue = form.find("#lieve").val();
    const sreValue = form.find("#elevato").val();

    alert.addClass("d-none");

    const requestData = {
        id: id
    };

    if (nameValue != null && nameValue != "" && form.attr("data-name") != nameValue)
        requestData.name = nameValue;

    if (slaValue != null && slaValue != "" && form.attr("data-sla") != slaValue)
        requestData.appropriete = slaValue;

    if (srlValue != null && srlValue != "" && form.attr("data-srl") != srlValue)
        requestData.lowRisk = srlValue;

    if (sreValue != null && sreValue != "" && form.attr("data-sre") != sreValue)
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
                const nameElement = $(".card .card-header h1");
                nameElement.text(nameValue);

                form.attr("data-name", nameValue);
                form.attr("data-sla", slaValue);
                form.attr("data-srl", srlValue);
                form.attr("data-sre", sreValue);

                showAlert(alert, "Modifica effettuata con successo", true);
            } else {
                showAlert(alert, response.message, false);
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
        action = "deleteQuestionSection";

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
                //Messaggio di conferma, poi reindirizzamento.
                window.location.href = "quizSections";
            } else {
                const table = $("#table");
                const toRemove = $(".row#sd-" + id);
                const sezRows = table.find("> .row:not(#thead, #no-section)");

                if (sezRows.length - 1 == 0) {
                    const toAppend = `<div id="no-section" class="row ml-0 align-items-center"><div class="col pl-2">Ancora nessuna sezione domanda</div></div>`;
                    toRemove.remove();
                    table.append(toAppend);
                } else
                    toRemove.slideUp(300, function () {
                        $(this).remove();
                    });
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
            showAlert(infoAlert, response.message, false);

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