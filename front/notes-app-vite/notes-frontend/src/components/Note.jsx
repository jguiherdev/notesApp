import NoteService from "../services/NoteService"
import "../styles/Note.css"

const Note = ({ note, onDeleteNote }) => {
  const noteService = NoteService;

  const handleDeleteNote = async () => {
    try {
      await noteService.deleteNote(note.id);
      // Llamamos a la función onDeleteNote para actualizar la lista de notas después de eliminar la nota
      onDeleteNote(note.id);
    } catch (error) {
      console.log('Error al borrar la nota', error);
    }
  };

  return(
    <div className="na-note-container">
    <div className="na-note-container-header">
    <h4>{note.title}</h4>
    <p> {note.date}</p>
    <button onClick={handleDeleteNote}>x</button>
    </div>
    <p>{note.collectionId.name}</p>
    <div>{note.description}</div>
    <p className={note.isImportant ? 'important' : ''}>Important: {note.isImportant ? 'Sí' : 'No'}</p>
    <p className={note.isPinned ? 'pinned' : ''}>Pinned: {note.isPinned ? 'Sí' : 'No'}</p>
    <p className={note.isCompleted ? 'completed' : ''}>Completed: {note.isCompleted ? 'Sí' : 'No'}</p>
    </div>
  )
}

export default Note;