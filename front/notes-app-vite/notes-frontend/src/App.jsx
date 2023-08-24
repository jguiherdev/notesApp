import { useState } from 'react'
import './App.css'
import CollectionList from './components/CollectionList'
import NoteList from './components/NoteList'
import CollectionForm from './components/CollectionForm'
import NoteForm from './components/NoteForm'
import {Button, ButtonGroup} from "@nextui-org/button";

function App() {
  const [collectionList, setCollectionList] = useState([]);
  const [noteList, setNoteList]= useState([]);
  const [selectedCollectionId, setSelectedCollectionId] = useState(null);
  const [addCollectionButtonClicked, setAddCollectionButtonClicked] = useState()
  const [currentCollectionPage, setCurrentCollectionPage] = useState()

  //methods for collection

  const addCollection = (newCollection) => {
    setCollectionList([...collectionList, newCollection]);
  }

  const handleChangeSelectedCollection = (collectionId) => {
    setSelectedCollectionId(collectionId); // Actualizamos el estado con el id de la colección seleccionada
    setCurrentCollectionPage(0)
  };

  const handleDeleteCollection = (collectionId) => {
    // Filtramos la lista de notas para eliminar la nota con el ID especificado
    const updatedCollectionList = collectionList.filter((collection) => collection.id !== collectionId);
    setCollectionList(updatedCollectionList);
  };

  //methods for notes

  const addNote=(newNote)=>{
    setNoteList([...noteList, newNote])
  }

  const handleDeleteNote = (noteId) => {
    // Filtramos la lista de notas para eliminar la nota con el ID especificado
    const updatedNoteList = noteList.filter((note) => note.id !== noteId);
    setNoteList(updatedNoteList);
  };

  const handleAddCollectionButtonClicked = () => {
    setAddCollectionButtonClicked(addCollectionButtonClicked ? false : true);
  };
  

  return (
    <div className="na-main-container">
      <CollectionList collectionList={collectionList}
      onChangeSelected={handleChangeSelectedCollection} 
      onHandleAddCollectionButtonClicked={handleAddCollectionButtonClicked}
      onDeleteCollection={handleDeleteCollection}
      onAddCollection={addCollection}/>

      <NoteList collectionId={selectedCollectionId} noteList={noteList} onDeleteNote={handleDeleteNote} currentCollectionPage={currentCollectionPage} />
     { selectedCollectionId && <NoteForm onAddNote={addNote} selectedCollectionId={selectedCollectionId}/>}
    </div>
  )
}

export default App
