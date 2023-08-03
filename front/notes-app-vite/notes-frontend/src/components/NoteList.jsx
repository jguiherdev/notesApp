import { useEffect, useState } from "react";
import NoteService from '../services/NoteService';
import Note from "./Note";
import '../styles/NoteList.css';


const NoteList = ({collectionId, noteList, onDeleteNote}) => {

    const noteService = NoteService

    const [notes, setNotes] = useState([]);

    useEffect(()=>{
        noteService.getNotes(collectionId).then((data) =>{
            if(data){
                setNotes(data);
            }
        })
        .catch((error)=>{
            console.log("error al obtener las notas")
        })
        console.log(collectionId)
    },[collectionId, noteList])

    const pinedNotes = (notesArray) => {
        const pinnedNotes = notesArray.filter((note)=>note.isPinned)
        const unpinnedNotes = notesArray.filter((note) => !note.isPinned)

        return[...pinnedNotes,...unpinnedNotes]        
    }

    const importantNotes = (notesArray) => {
        const importantNotes = notesArray.filter((note)=>note.isImportant)
        const unimportantNotes = notesArray.filter((note)=>!note.isImportant)

        return[...importantNotes,...unimportantNotes]        
    }

    const completedNotes = (notesArray) => {
        const completedNotes = notesArray.filter((note)=>note.isCompleted)
        const uncompletedNotes = notesArray.filter((note)=>!note.isCompleted)

        return[...uncompletedNotes,...completedNotes]        
    }

    const sortNotes = (notesArray) =>{
        
        const firstSort = importantNotes(notesArray)
        const secondSort = pinedNotes(firstSort)
        const thirdSort = completedNotes(secondSort)

        return thirdSort
    }

    const sortedNotes = sortNotes(notes)



    return (
     <div className="na-note-list">
      <h2>{collectionId}</h2>
      {sortedNotes.map((note) => (
     <Note key={note.id} note={note} onDeleteNote={onDeleteNote}/>
      ))}
    </div>
    )
}

export default NoteList