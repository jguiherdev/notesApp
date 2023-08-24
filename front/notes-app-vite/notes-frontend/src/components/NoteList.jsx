import { useEffect, useState } from "react";
import NoteService from '../services/NoteService';
import Note from "./Note";
import { Pagination } from "@nextui-org/pagination";
import { Spacer } from "@nextui-org/react"
import '../styles/NoteList.css';


const NoteList = ({ collectionId, noteList, onDeleteNote, currentCollectionPage }) => {

    const noteService = NoteService

    const [notes, setNotes] = useState([]);

    const itemsPerPage = 5

    const [currentPage, setCurrentPage] = useState(1); // Página actual
    const [totalNotes, setTotalNotes] = useState(0); // Página actual

    useEffect(() => {
        noteService.getNotes(collectionId, currentPage - 1).then((data) => {
            if (data) {
                setNotes(data);
            }
        })
            .catch((error) => {
                console.log("error al obtener las notas")
            })
        console.log(collectionId)
    }, [collectionId,currentPage,noteList])

    useEffect(()=>{
        noteService.getNumberOfNotes(collectionId).then((data => {
            setTotalNotes(data)
        })).catch((error) => {
            console.log(error)
        })
    }, [collectionId])

    useEffect(() => {
        noteService.getNotes(collectionId, 0).then((data) => {
            if (data) {
                setNotes(data);
            }
        })
        .catch((error) => {
            console.log("error al obtener las notas")
        }) 
    }, [collectionId, noteList]);

    return (
        <div className="na-note-container">
            <div className="na-note-list">
                {notes.map((note) => (
                    <Note key={note.id} note={note} onDeleteNote={onDeleteNote} />
                ))}
            </div>
            <Spacer y={4} />
            {totalNotes >=5 ? (<Pagination
                total={Math.ceil(totalNotes / itemsPerPage)} // Total de páginas
                onChange={(page) => setCurrentPage(page)}
                current={currentPage}
                color="secondary"
            />) : (<div></div>)
            }
        </div>
    )
}

export default NoteList