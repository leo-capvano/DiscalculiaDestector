$(document).ready(() => {    
    
    $("#editModal").on("show.bs.modal", function(event) {
        const modal = $(this);
        const form = modal.find("form");
        const nameInput = form.find("#name");
        
        const nameValue = $(event.relatedTarget).attr("data-name");
        const id = $(event.relatedTarget).attr("data-id");
        
        modal.attr("data-id", id);
        nameInput.val(nameValue);
    });
    
    $("#addModal, #editModal").on("hidden.bs.modal", function () {
        const modal = $(this);
        const alert = modal.find(".alert");
        const form = modal.find("form");

        alert.addClass("d-none");
        form.removeClass("was-validated");
        form.trigger("reset");
    });
    
    $("#addModal .modal-footer .btn-primary").click(onAddInstituteSubmit);
    $("#editModal .modal-footer .btn-primary").click(onEditInstituteSubmit);
    $("#table input[type='checkbox']").change(onCheckboxChange);
});

const onAddInstituteSubmit = event => {
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
    const name = form.find("#name").val();
    
    const requestData = {
        name: name
    };
    
    $.ajax({
        method: "POST",
        url: "addInstitute",
        data: requestData
    }).done(data => {
        const response = JSON.parse(data);
        const newInstitute = response.content.institute;
        
        if(response.ok) {
            const table = $("#table");
            
            const toAppend = `<div class="row align-items-center" data-id="${newInstitute.id}">
                                    <div class="col">${newInstitute.name}</div>
                                    <div class="col">
                                        <span class="badge badge-pill badge-primary">Attivo</span>
                                    </div>
                                    <div class="col-3">
                                        <div class="row align-items-center">
                                            <div class="col col-sm-12 col-xl text-center">
                                                <i class="fas fa-edit p-2 primary" data-toggle="modal" data-target="#editModal" data-id="${newInstitute.id}" data-name="${newInstitute.name}"></i>
                                            </div>
                                            <div class="col col-sm-12 col-xl text-center">
                                                <div class="custom-control custom-switch">
                                                    <input type="checkbox" class="custom-control-input" data-id="${newInstitute.id}" id="switch-${newInstitute.id}" checked>
                                                    <label class="custom-control-label" for="switch-${newInstitute.id}"></label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>`;
            
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

const onEditInstituteSubmit = event => {
    const modal = $("#editModal");
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
    const name = form.find("#name").val();
    const id = modal.attr("data-id");
    
    const toEdit = $("#table [data-id='" + id + "']");
    
    const requestData = {
        instituteID: id,
        name: name
    };
    
    $.ajax({
        method: "POST",
        url: "editInstitute",
        data: requestData
    }).done(data => {
        const response = JSON.parse(data);
        
        if(response.ok) {
            toEdit.find("#name-" + id).text(name);
            showAlert(infoAlert, "Operazione effettuata con successo", true);
        }
        else {
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

const onCheckboxChange = event => {
    const checkbox = event.target;
    const infoAlert = $("#infoAlert");
    
    $(checkbox).addClass("disabled");
    $(checkbox).attr("disabled", true);
    
    const id = $(checkbox).attr("data-id");
    const requestData = {
        instituteID: id,
        isEnabled: checkbox.checked
    };
    
    $.ajax({
        method: "POST",
        url: "switchInstituteStatus",
        data: requestData
    }).done(data => {
        const response = JSON.parse(data);
        
        if(response.ok) {
            const status = $("#status-" + id);
            if(checkbox.checked) {
                status.addClass("badge-primary");
                status.removeClass("badge-secondary");
                status.text("Attivo");
            }
            else {
                status.removeClass("badge-primary");
                status.addClass("badge-secondary");
                status.text("Disattivo");
            }
        }
        else {
            checkbox.checked = !checkbox.checked;
            showAlert(infoAlert, response.message, false);
            
            setTimeout(() => {
            infoAlert.slideUp(300, function () {
                    $(this).addClass("d-none");
                    $(this).attr("style", "");
                });
            }, 3000);
        }
    }).fail(() => {
        checkbox.checked = !checkbox.checked;
        showAlert(infoAlert, "Impossibile completare l'operazione. Riprova fra poco.", false);
        
        setTimeout(() => {
        infoAlert.slideUp(300, function () {
                $(this).addClass("d-none");
                $(this).attr("style", "");
            });
        }, 3000);
    }).always(() => {
        $(checkbox).removeClass("disabled");
        $(checkbox).attr("disabled", false);
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