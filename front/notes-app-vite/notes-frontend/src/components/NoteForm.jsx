import { useEffect, useState } from "react";
import NoteService from "../services/NoteService";
import NoteList from "./NoteList";

const NoteForm = ({ onAddNote, selectedCollectionId }) => {

  const noteService = NoteService;

  const [noteData, setNoteData] = useState({
    title: '',
    date: '',
    description: '',
    collectionId: selectedCollectionId,
    isImportant: false,
    isPinned: false,
    isCompleted:false
  });

  useEffect(() => {
    setNoteData((prevNoteData) => ({
      ...prevNoteData,
      collectionId: selectedCollectionId
    }));
  }, [selectedCollectionId]);


  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    const newValue = type === 'checkbox' ? checked : value;
    
    setNoteData({
      ...noteData,
      [name]: newValue
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let isValidForm = false;
    const newErrors = {};

    if (!noteData.title || noteData.title.length < 1 || noteData.title.length > 50) {
      newErrors.title = 'Title must be between 1 and 50 characters long.';
    }else if (!noteData.description || noteData.description.length < 1 || noteData.description.length > 600) {
      newErrors.description = 'Description must be between 1 and 600 characters long.';
    }else if (!noteData.date) {
      newErrors.date = 'Date must not be null';
    }else{
      isValidForm = true;
    }

    if (!isValidForm) {
      setErrors(newErrors);
      return;
    }

    try {
      const response = await noteService.createNote(noteData);
      console.log('Nota creada:', response);
      setNoteData({
        title: "",
        date: "",
        description: "",
        collectionId: selectedCollectionId,
        isImportant: false,
        isPinned: false,
        isCompleted:false
      });
      setErrors({});
      onAddNote(response);
    } catch (error) {
      console.log('Error al crear la nota', error);
    }
  };

  return (
    <div className="na-note-form">
    <h2 className="na-note-form__heading">Add Note</h2>
    <form className="na-note-form__form" onSubmit={handleSubmit}>
      <label htmlFor="title" className="na-note-form__label">Title:</label>
      <input
        type="text"
        id="title"
        name="title"
        value={noteData.title}
        onChange={handleChange}
        className="na-note-form__input"
      />
      {errors.title && <span className="error na-note-form__error">{errors.title}</span>}
      <br />
      <label htmlFor="date" className="na-note-form__label">Date:</label>
      <input
        type="date"
        id="date"
        name="date"
        value={noteData.date}
        onChange={handleChange}
        className="na-note-form__input"
      />
      {errors.date && <span className="error na-note-form__error">{errors.date}</span>}
      <br />
      <label htmlFor="description" className="na-note-form__label">Description:</label>
      <textarea
        id="description"
        name="description"
        value={noteData.description}
        onChange={handleChange}
        className="na-note-form__textarea"
      />
       {errors.description && <span className="error na-note-form__error">{errors.description}</span>}
      <br />
      <label htmlFor="isImportant" className="na-note-form__switch-label">
      Important:
      <br />
      <input
        type="checkbox"
        id="isImportant"
        name="isImportant"
        checked={noteData.isImportant}
        onChange={handleChange}
        className="na-note-form__checkbox"
      />
      <span className="na-note-form__slider"></span>
    </label>
    <br />
    <label htmlFor="isPinned" className="na-note-form__switch-label">
      Pinned:
      <br />
      <input
        type="checkbox"
        id="isPinned"
        name="isPinned"
        checked={noteData.isPinned}
        onChange={handleChange}
        className="na-note-form__checkbox"
      />
      <span className="na-note-form__slider"></span>
    </label>
      <button type="submit" className="na-note-form__button">Add</button>
    </form>
  </div>
  
  );
};

export default NoteForm;
