import { useEffect, useState } from "react";
import NoteService from "../services/NoteService";
import { Textarea } from "@nextui-org/input";
import { Card, CardHeader, CardBody, CardFooter, Divider, Link, Image, Button, Spacer } from "@nextui-org/react";
import { Popover, PopoverTrigger, PopoverContent } from "@nextui-org/react";
import { Chip } from "@nextui-org/chip";
import { Switch } from "@nextui-org/switch";
import DatePicker from "react-datepicker";
import { AddNoteIcon } from './reusable-components/AddNoteIcon.jsx';
import "react-datepicker/dist/react-datepicker.css";


const NoteForm = ({ onAddNote, selectedCollectionId }) => {

  const iconClasses = "text-xl text-default-500 pointer-events-none flex-shrink-0";

  const noteService = NoteService;

  const [noteData, setNoteData] = useState({
    title: '',
    date: new Date(),
    description: '',
    collectionId: selectedCollectionId,
    isImportant: false,
    isPinned: false,
    isCompleted: false
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
    const newValue = type === 'checkbox' ? checked : name === 'date' ? value : value;

    setNoteData({
      ...noteData,
      [name]: newValue
    });

    setIsOpen(false)

  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    let isValidForm = false;
    const newErrors = {};

    if (!noteData.title || noteData.title.length < 1 || noteData.title.length > 50) {
      newErrors.title = 'Title must be between 1 and 50 characters long.';
    } else if (!noteData.description || noteData.description.length < 1 || noteData.description.length > 600) {
      newErrors.description = 'Description must be between 1 and 600 characters long.';
    } else if (!noteData.date) {
      newErrors.date = 'Date must not be null';
    } else {
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
        date: new Date(),
        description: "",
        collectionId: selectedCollectionId,
        isImportant: false,
        isPinned: false,
        isCompleted: false
      });
      setErrors({});
      onAddNote(response);
    } catch (error) {
      console.log('Error al crear la nota', error);
    }
    setNoteData(prevNoteData => ({
      ...prevNoteData,
      isImportant: false,
      isPinned: false,
      isCompleted: false
    }));
  };


  const [isOpen, setIsOpen] = useState();

  return (
    <Card className="left-3">
      <form className="na-note-form" onSubmit={handleSubmit}>
        <CardHeader className="overflow-visible py-2 flex-col">
          <div className="mb-4 flex-row align-baseline justify-center">
            <small className="text-default-500 justify-normal">
              <Popover showArrow>
                <PopoverTrigger>
                  <Chip
                    color="default"
                    variant="light"
                    size="sm"
                    onClick={() => setIsOpen(true)}
                    startContent={
                      <>
                        {noteData.date.toISOString().split("T")[0]}
                        <Spacer />
                        <AddNoteIcon className={iconClasses} />
                      </>
                    }
                  ></Chip>
                </PopoverTrigger>
                {isOpen ? (
                  <PopoverContent>
                    <DatePicker
                      selected={noteData.date}
                      onChange={(date) => handleChange({ target: { name: 'date', value: date } })}
                      inline
                      calendarClassName="border-transparent"
                    />
                  </PopoverContent>
                ) : (<div></div>)}
              </Popover>
            </small>
            {errors.date && <span className="error na-note-form__error">{errors.date}</span>}
          </div>
          <div className="mb-4 ">
            <Textarea
              type="text"
              id="title"
              name="title"
              value={noteData.title}
              onChange={handleChange}
              placeholder="Note title..."
            />
            {errors.title && <span className="error na-note-form__error">{errors.title}</span>}
          </div>
        </CardHeader>
        <Divider></Divider>
        <CardBody className="overflow-visible py-2 flex-col">
          <div className="mb-4">
            {!errors.description ? (
              <Textarea
                variant="flat"
                id="description"
                name="description"
                value={noteData.description}
                onChange={handleChange}
                className="max-w-xs"
                placeholder="Description..."
              />
            ) : (
              <Textarea
                variant="flat"
                placeholder={errors.description}
                validationState="invalid"
                className="max-w-xs"
                value={noteData.description}
                onChange={handleChange}
                id="description"
                name="description"
              />
            )}
          </div>
          <Spacer />
          <div className="mb-4">
            <Switch
              type="checkbox"
              id="isImportant"
              name="isImportant"
              checked={noteData.isImportant}
              onChange={handleChange}
              className="na-note-form__checkbox"
              color="warning"
              isSelected={noteData.isImportant}
            >
              Important
            </Switch>
          </div>
          <Spacer />
          <div className="mb-4">
            <Switch
              color="secondary"
              type="checkbox"
              id="isPinned"
              name="isPinned"
              checked={noteData.isPinned}
              onChange={handleChange}
              className="na-note-form__checkbox"
              isSelected={noteData.isPinned}
            >
              Pinned
            </Switch>
          </div>
          <Spacer />
          <Button type="submit" color="secondary" variant="shadow" className="na-note-form__button">
            Add
          </Button>
        </CardBody>
        <CardFooter></CardFooter>
      </form>
    </Card>
  );
};

export default NoteForm;
