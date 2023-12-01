window.addEventListener('load', function () {
  (function(){
  const url = "http://localhost:8081";

  
  const settings = {
    method: 'GET'
  }

  fetch(`${url}/turnos/listar`,settings)
  .then(response => response.json())
  .then(data => {
    
    for(turno of data){

        var table = document.getElementById("turnoTable");
        var turnoRow = table.insertRow();
        let tr_id = 'tr_' + turno.id;
        turnoRow.id = tr_id;

        

        let deleteButton = '<button' +
                                  ' id=' + '\"' + 'btn_delete_' + turno.id + '\"' +
                                  ' type="button" onclick="deleteBy('+turno.id+')" class="btn btn-danger btn_delete">' +
                                  '&times' +
                                  '</button>';


        let updateButton = '<button' +
                                  ' id=' + '\"' + 'btn_id_' + turno.id + '\"' +
                                  ' type="button" onclick="findBy('+turno.id+')" class="btn btn-info btn_id">' +
                                  turno.id +
                                  '</button>';


        turnoRow.innerHTML = '<td>' + updateButton + '</td>' +
                '<td class=\"td_fechaTurno\">' + turno.fechaYHora + '</td>' +
                '<td class=\"td_paciente_id\">' + turno.paciente_id + '</td>' +
                '<td class=\"td_odontologo_id\">' + turno.odontologo_id + '</td>' +
                '<td>' + deleteButton + '</td>';

    };

})

})
(function(){
  let pathname = window.location.pathname;
  if (pathname == "/turnoTable") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
  }
})
})