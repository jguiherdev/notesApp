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
        console.log('Error al crear la colección');
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

  static async getCollections(page) {
    try {
      const response = await fetch(`${API_URL}/collection` + '?page=' + page);

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

  static async getNumberOfCollections() {
    try {
      const response = await fetch(`${API_URL}/collection/count`);

      if (response.ok) {
        const data = await response.json();
        return data;
      } else {
        console.log('Error al obtener las colecciones');
        return null;
      }
    } catch (error) {
      console.log('Error en la solicitud', error);
      return null;
    }
  }

  static async deleteCollection(collecionId) {
    try {
      const response = await fetch(`${API_URL}/collection/${collecionId}`,{
        method:'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
        body:''
      });

      if (response.status === 204) {
        console.log("la colección se ha borrado de forma exitosa")
        return true;
      } else {
        // Ocurrió un error al obtener las notas
        console.log('Error al borrar la colección');
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







