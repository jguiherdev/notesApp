const API_URL = 'http://localhost:8080'; // URL base del backend

class CollectionService {
  static async createCollection(collectionData) {
    try {
      const response = await fetch(`${API_URL}/collection`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(collectionData),
      });

      if (response.ok) {
        // La nota se creó correctamente
        const data = await response.json();
        return data; // Devuelve la nota creada si es necesario
      } else {
        // Ocurrió un error al crear la nota
        console.log('Error al crear la nota');
        // Realiza el manejo de errores necesario
        return null;
      }
    } catch (error) {
      // Ocurrió un error de red u otro error
      console.log('Error en la solicitud', error);
      // Realiza el manejo de errores necesario
      return null;
    }
  }

  static async getCollections() {
    try {
      const response = await fetch(`${API_URL}/collection`);

      if (response.ok) {
        // Las notas se obtuvieron correctamente
        const data = await response.json();
        return data; // Devuelve las notas obtenidas si es necesario
      } else {
        // Ocurrió un error al obtener las notas
        console.log('Error al obtener las colecciones');
        // Realiza el manejo de errores necesario
        return null;
      }
    } catch (error) {
      // Ocurrió un error de red u otro error
      console.log('Error en la solicitud', error);
      // Realiza el manejo de errores necesario
      return null;
    }
  }
}

export default CollectionService;







