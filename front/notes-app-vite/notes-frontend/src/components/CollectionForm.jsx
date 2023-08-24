import { useState } from "react";
import CollectionService from "../services/CollectionService";
import { Textarea } from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import '../styles/NoteForm.css';

const CollectionForm = ({ onAddCollection }) => {
  const collectionService = CollectionService;

  const [collectionData, setCollectionData] = useState({
    name: '',
    appUserId: 1
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setCollectionData({
      ...collectionData,
      name: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    console.log("1")
    e.preventDefault();
    console.log("2")

    let isValidForm = true;
    const newErrors = {};
    console.log("3")

    if (!collectionData.name || collectionData.name.length < 6 || collectionData.name.length > 20) {
      newErrors.title = 'Title must be between 6 and 20 characters long.';
      isValidForm = false;
      console.log("4")
    }

    if (!isValidForm) {
      setErrors(newErrors);
      return;
    }

    try {
      const response = await collectionService.createCollection(collectionData);
      console.log('Nota creada:', response);
      setCollectionData({
        name: '',
        appUserId: 1
      });
      setErrors({});
      onAddCollection(response);
    } catch (error) {
      console.log('Error al crear la coección', error);
    }
  };

  return (
    <div className="collection-form">
      <form onSubmit={handleSubmit}>
        <Textarea
          variant="flat"
          color="default"
          labelPlacement="outside"
          type="text"
          id="name"
          value={collectionData.name}
          onChange={handleChange}
          placeholder="Collection name..."
        />
        <Button type="submit" size="sm" color="secondary" variant="shadow">Add</Button>
      </form>
    </div>
  );
};

export default CollectionForm;
