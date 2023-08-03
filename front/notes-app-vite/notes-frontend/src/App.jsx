import { useState, useEffect } from 'react'
import './App.css'
import CollectionList from './components/CollectionList'
import NoteList from './components/NoteList'
import CollectionForm from './components/CollectionForm'
import NoteForm from './components/NoteForm'

function App() {
  const [collectionList, setCollectionList] = useState([]);
  const [noteList, setNoteList]= useState([]);
  const [selectedCollectionId, setSelectedCollectionId] = useState(null);
  const [addCollectionButtonClicked, setAddCollectionButtonClicked] = useState()

  const addCollection = (newCollection) => {
    setCollectionList([...collectionList, newCollection]);
  }

  const addNote=(newNote)=>{
    setNoteList([...noteList, newNote])
  }

  const handleChangeSelectedCollection = (collectionId) => {
    setSelectedCollectionId(collectionId); // Actualizamos el estado con el id de la colección seleccionada
  };

  const handleDeleteNote = (noteId) => {
    // Filtramos la lista de notas para eliminar la nota con el ID especificado
    const updatedNoteList = noteList.filter((note) => note.id !== noteId);
    setNoteList(updatedNoteList);
  };

  const handleAddCollectionButtonClicked = () =>{
    if(addCollectionButtonClicked){
      setAddCollectionButtonClicked(false)
    }else {
      setAddCollectionButtonClicked(true)
    } 
  }

  useEffect(() => {
    console.log(selectedCollectionId); // Aquí obtendrás el valor actualizado de selectedCollectionId
  }, [selectedCollectionId]);


  return (
    <div className="na-main-container">
      <CollectionList collectionList={collectionList} onChangeSelected={handleChangeSelectedCollection} 
      onHandleAddCollectionButtonClicked={handleAddCollectionButtonClicked}/>
      {addCollectionButtonClicked && <CollectionForm onAddCollection={addCollection}/>}
      <NoteList collectionId={selectedCollectionId} noteList={noteList} onDeleteNote={handleDeleteNote} />
     { selectedCollectionId && <NoteForm onAddNote={addNote} selectedCollectionId={selectedCollectionId}/>}
    </div>
  )
}

export default App
