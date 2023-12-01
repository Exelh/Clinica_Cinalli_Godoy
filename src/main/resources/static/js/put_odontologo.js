window.addEventListener('load', function () {



    const formulario = document.querySelector('#update_odontologo_form');
    const url = "http://localhost:8081"

    formulario.addEventListener('submit', function (event) {
        let odontologoId = document.querySelector('#odontologo_id').value;


        const formData = {
            id: document.querySelector('#odontologo_id').value,
            matricula: document.querySelector('#matricula').value,
            nombre: document.querySelector('#nombre').value,
            apellido: document.querySelector('#apellido').value,

        };

        
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
            fetch(`${url}/odontologos/listar`, settings)
          .then(response => response.json())

    })
 })


    function findBy(id) {
          const url = 'http://localhost:8081';
          const settings = {
              method: 'GET'
          }
          fetch(`${url}/odontologos/` + id, settings)
          .then(response => response.json())
          .then(data => {
              let odontologo = data;
              document.querySelector('#odontologo_id').value = odontologo.id;
              document.querySelector('#matricula').value = odontologo.matricula;
              document.querySelector('#nombre').value = odontologo.nombre;
              document.querySelector('#apellido').value = odontologo.apellido;

              document.querySelector('#div_odontologo_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }

      
       