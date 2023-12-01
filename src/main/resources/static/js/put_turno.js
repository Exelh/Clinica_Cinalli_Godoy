window.addEventListener('load', function () {



    const formulario = document.querySelector('#update_turno_form');
    const url = "http://localhost:8081"

    formulario.addEventListener('submit', function (event) {
        let turnoId = document.querySelector('#turno_id').value;


        const formData = {
            id: document.querySelector('#turno_id').value,
            fechaYHora: document.querySelector('#fechaYHora').value,
            paciente_id: document.querySelector('#paciente_id').value,
            odontologo_id: document.querySelector('#odontologo_id').value
        };


        
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
        fetch(`${url}/turnos`, settings)
          .then(response => response.json())

    })
 })


    function findBy(id) {
        const url = "http://localhost:8081"
          const settings = {
              method: 'GET'
          }
          fetch(`${url}/turnos/` + id, settings)
          .then(response => response.json())
          .then(data => {
              let turnos = data;
              document.querySelector('#turno_id').value = turno.id;
              document.querySelector('#fechaYHora').value = turno.fechaYHora;
              document.querySelector('#paciente_id').value = turno.paciente_id;
              document.querySelector('#odontologo_id').value = turno.odontologo_id;

              document.querySelector('#div_turno_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }