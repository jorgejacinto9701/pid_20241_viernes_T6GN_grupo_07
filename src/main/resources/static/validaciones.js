document.addEventListener('DOMContentLoaded', function () {
    const formulario = document.getElementById('registroForm');
    formulario.addEventListener('submit', function (event) {
        event.preventDefault();

        if (validarFormulario(event)) {
            const datosFormulario = {
                nombre: document.getElementById('nombre').value.trim(),
                apellido: document.getElementById('apellido').value.trim(),
                dni: document.getElementById('dni').value.trim(),
                telefono: document.getElementById('telefono').value.trim(),
                nacimiento: document.getElementById('nacimiento').value.trim(),
                idDistrito: document.getElementById('idUsuario').value.trim(),
                correo: document.getElementById('correo').value.trim(),
                contrasenia: document.getElementById('contrasenia').value.trim()
            };

            fetch('/nuevoJefePrestamista', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(datosFormulario)
            })
            .then(response => response.text())
            .then(data => {
                alert(data);
                formulario.reset();
            })
            .catch(error => {
                console.error('Error:', error);
            });
        }
    });
    
    function validarFormulario(event) {
      let valido = true;
        // Validar nombre
        const nombre = document.getElementById('nombre').value.trim();
        if (!nombre) {
            alert('Por favor, ingrese un nombre.');
            valido = false;
        }

        // Validar apellido
        const apellido = document.getElementById('apellido').value.trim();
        if (!apellido) {
            alert('Por favor, ingrese un apellido.');
            valido = false;
        }

        // Validar DNI
        const dni = document.getElementById('dni').value.trim();
        if (!dni || isNaN(dni) || dni.length !== 8) {
            alert('Por favor, ingrese un DNI válido (8 dígitos numéricos).');
            valido = false;
        }

        // Validar teléfono
        const telefono = document.getElementById('telefono').value.trim();
        if (!telefono || isNaN(telefono) || telefono.length !==9) {
            alert('Por favor, ingrese un número de teléfono válido ( 9 dígitos numéricos).');
            valido = false;
        }

        // Validar fecha de nacimiento
        const fechaNacimiento = document.getElementById('nacimiento').value.trim();
        if (!fechaNacimiento) {
            alert('Por favor, seleccione una fecha de nacimiento.');
            valido = false;
        }

        // Validar distrito
        const idDistrito = document.getElementById('idUsuario').value.trim();
        if (!idDistrito) {
            alert('Por favor, seleccione un distrito.');
            valido = false;
        }

        // Validar correo electrónico
        const correo = document.getElementById('correo').value.trim();
        if (!correo || !validarCorreo(correo)) {
            alert('Por favor, ingrese un correo electrónico válido.');
            valido = false;
        } else if (correoExiste(correo)) {
            alert('El correo electrónico ya está registrado en la base de datos.');
            valido = false;
        }

        // Validar contraseña
        const contrasenia = document.getElementById('contrasenia').value.trim();
        if (!contrasenia) {
            alert('Por favor, ingrese una contraseña.');
            valido = false;
        }

        return valido;
    };

    // Función para validar el formato de correo electrónico
    function validarCorreo(correo) {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(correo);
    }

    // Función para verificar si el correo electrónico existe en la base de datos (simulación)
    function correoExiste(correo) {
        // Simulación: el correo existe si es igual a "existing_email@example.com"
        return correo === 'existing_email@example.com';
    }
});
