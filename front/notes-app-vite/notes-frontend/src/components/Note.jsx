import NoteService from "../services/NoteService"
import { Card, CardHeader, CardBody, CardFooter, Divider, Link, Image, Button } from "@nextui-org/react";
import { Spacer } from "@nextui-org/react";
import { Chip } from "@nextui-org/react";
import { CheckIcon } from "./reusable-components/CheckIcon";
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

  return (
    <div className="na-note">
      <Card>
        <CardHeader className="flex justify-between items-center text-justify">
          {note.isPinned && (
            <Chip color="secondary" variant="light" size="sm">
              <small className="text-default-300">Pinned</small>
            </Chip>
          )}
          <h4>{note.title}</h4>
          <Spacer x={8} />
          <small className="text-default-500">{note.date}</small>
          <Button onClick={handleDeleteNote} size="sm" isIconOnly aria-posinset={CardHeader}>x</Button>
        </CardHeader>
        <Divider />
        <CardBody className="text-justify">
          {note.description}
        </CardBody>
        <CardFooter className="flex justify-between items-center">
          {note.isImportant && (<Chip color="warning" variant="shadow">!</Chip>)}
          {note.isCompleted && (<Chip
            startContent={<CheckIcon size={18} />}
            variant="faded"
            color="success"
          > </Chip>)}
        </CardFooter>
      </Card>
    </div>
  )
}

export default Note;