$(document).ready(() => {    
    
    $("#addModal").on("hidden.bs.modal", function () {
        const modal = $(this);
        const alert = modal.find(".alert");
        const form = modal.find("form");

        alert.addClass("d-none");
        form.removeClass("was-validated");
        form.trigger("reset");
    });
    
    $("#addModal .modal-footer .btn-primary").click(onAddAdminSubmit);
});

const onAddAdminSubmit = event => {
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
    const surname = form.find("#surname").val();
    const email = form.find("#email").val();
    
    const requestData = {
        name: name,
        surname: surname,
        email: email
    };
    
    $.ajax({
        method: "POST",
        url: "addAdmin",
        data: requestData
    }).done(data => {
        const response = JSON.parse(data);
        const newInstitute = response.content.institute;
        
        if(response.ok) {
            const table = $("#table");
            
            const toAppend = `<div class="row align-items-center text-break">
                                    <div class="col-3">${name}</div>
                                    <div class="col-3">${surname}</div>
                                    <div class="col-3">${email}</div>
                                    <div class="col-3">
                                        <span class="badge badge-pill badge-secondary">Non verificato</span>
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
