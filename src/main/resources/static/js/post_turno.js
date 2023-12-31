window.addEventListener('load', function () {

    const formulario = document.querySelector('#add_new_turno');
    const fechaYHora = document.querySelector('.fecha-hora-turno');
    const odontologo_id = document.querySelector('#odontologo_id');
    const paciente_id = document.querySelector('#paciente_id');
    const url = "http://localhost:8081";

    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        const payload = {
            fechaYHora: fechaYHora.value.replace('T',' ')+":00",
            odontologoId: parseInt(odontologo_id.value), 
            pacienteId: parseInt(paciente_id.value) 
        };
        console.log(payload);

        const settings = {
            method: 'POST',
            body: JSON.stringify(payload),
            headers: {
                'Content-Type': 'application/json'
            }
        };

        console.log("Lanzar la consulta a la API...");

        fetch(`${url}/turnos/registrar`, settings)
            .then(response => response.json())
            .then(data => {
                console.log("funcionó el fetch");

                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Turno agregado</strong></div>';

                document.querySelector('#response').innerHTML = successAlert;
                document.querySelector('#response').style.display = "block";
                resetUploadForm();

            })
            .catch(error => {
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Error, intente nuevamente</strong></div>';

                document.querySelector('#response').innerHTML = errorAlert;
                document.querySelector('#response').style.display = "block";

                resetUploadForm();
            });
    });

    function resetUploadForm() {
        document.querySelector('.fecha-hora-turno').value = "";
        document.querySelector('#odontologo_id').value = "";
        document.querySelector('#paciente_id').value = "";
    }

});
