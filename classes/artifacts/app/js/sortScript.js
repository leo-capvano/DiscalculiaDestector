$(document).ready(() => {
    const allSortable = document.querySelectorAll(".sortable");

    allSortable.forEach(sortable => {
        Sortable.create(sortable, {
            animation: 150
        });
    })

})
