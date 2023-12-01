window.addEventListener('load', function () {
(function deleteBy(id) {
    const url = 'http://localhost:8081';
    const deleteEndpoint = `${url}/odontologos/${id}`;
    const settings = {
        method: 'DELETE'
    };

    fetch(deleteEndpoint, settings)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json();
        })
        .then(data => {
            let row_id = "#tr_" + id;
            document.querySelector(row_id).remove();
            
        })
        .catch(error => {
            console.error('Error:', error);
            
        });
})
})