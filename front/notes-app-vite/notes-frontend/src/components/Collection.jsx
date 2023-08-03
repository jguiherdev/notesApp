import { useState } from 'react';
import "../styles/Collection.css"

const Collection = ({ name, onDelete, onClick }) => {
  const [confirmDelete, setConfirmDelete] = useState(false);

  const handleDeleteClick = () => {
    if (confirmDelete) {
      onDelete();
    } else {
      setConfirmDelete(true);
    }
  };


  return (
    <button className="na-collection">
      <span className="na-collection-name">{name}</span>
     
    
    </button>
  );
};

export default Collection;
