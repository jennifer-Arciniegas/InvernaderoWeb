function filtrarPlantas() {
    const filtroNombre = document.getElementById('filtroNombre').value.toLowerCase();
    const filtroTipo = document.getElementById('filtroTipo').value;
    const filas = document.querySelectorAll('tbody tr:not([th\\:if])'); // Excluye la fila de "no hay plantas"
    
    filas.forEach(fila => {
        const nombre = fila.querySelector('td:nth-child(2)').textContent.toLowerCase();
        const tipo = fila.querySelector('td:first-child span').textContent;
        
        const coincideNombre = nombre.includes(filtroNombre);
        const coincideTipo = !filtroTipo || tipo === filtroTipo;
        
        if (coincideNombre && coincideTipo) {
            fila.style.display = '';
        } else {
            fila.style.display = 'none';
        }
    });
}


async function eliminarPlanta(event) {
    const boton = event.currentTarget;
    const idPlanta = boton.getAttribute('data-id');
    const fila = boton.closest('tr'); // Obtiene la fila de la tabla

    if (confirm('¿Estás seguro de eliminar esta planta?')) {
        try {
            // Llama al backend para eliminar la planta
            const response = await fetch(`/plantas/eliminar/${idPlanta}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                // Elimina la fila de la tabla (sin recargar)
                fila.remove();
                // Actualiza el contador (opcional)
                const totalPlantas = document.querySelectorAll('tbody tr:not([th\\:if])').length;
                document.querySelector('span[th\\:text*="plantas.size"]').textContent = totalPlantas;
            } else {
                alert('Error al eliminar la planta');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error de conexión');
        }
    }
}