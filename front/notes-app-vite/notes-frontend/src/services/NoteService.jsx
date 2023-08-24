const API_URL = 'http://localhost:8080'; // URL base del backend

class NoteService {
  static async createNote(noteData) {
    try {
      const response = await fetch(`${API_URL}/notes`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(noteData),
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

  static async getNotes(collectionId, page) {
    try {
      const response = await fetch(`${API_URL}/notes/collection/${collectionId}` + '?page=' + page);

      if (response.ok) {
        // Las notas se obtuvieron correctamente
        const data = await response.json();
        return data; // Devuelve las notas obtenidas si es necesario
      } else {
        // Ocurrió un error al obtener las notas
        console.log('Error al obtener las notas');
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

  static async deleteNote(noteId) {
    try {
      const response = await fetch(`${API_URL}/notes/${noteId}`,{
        method:'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
        body:''
      });

      if (response.status === 204) {
        console.log("la nota se ha boraado correctamente")
        return true;
      } else {
        // Ocurrió un error al obtener las notas
        console.log('Error al borrar la nota');
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

  static async getNumberOfNotes(collectionId) {
    try {
      const response = await fetch(`${API_URL}/notes/count/${collectionId}`);

      if (response.ok) {
        const data = await response.json();
        return data;
      } else {
        console.log('Error al obtener las notas');
        return null;
      }
    } catch (error) {
      console.log('Error en la solicitud', error);
      return null;
    }
  }
}

export default NoteService;







