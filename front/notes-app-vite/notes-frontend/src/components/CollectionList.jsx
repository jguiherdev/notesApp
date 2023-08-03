import { useEffect, useState } from 'react';
import Collection from './Collection';
import CollectionService from '../services/CollectionService'
import '../styles/CollectionList.css';

const CollectionList = ({collectionList, onChangeSelected, collectionId, 
  onHandleAddCollectionButtonClicked}) => {

  const collectionService = CollectionService

  const handleDelete = (collectionId) => {
    // Muestra el mensaje de confirmación de eliminación y luego elimina la colección
    if (window.confirm('¿Seguro que quieres eliminar esta colección?')) {
      onDeleteCollection(collectionId);
    }
  };
  
  // Estado para almacenar las colecciones y sus respectivas notas
  const [collections, setCollections] = useState([]);

  useEffect(() =>{
    collectionService.getCollections(collectionId).then((data)=>{
      if(data){
        setCollections(data)
      }
    })
    .catch((error)=>{
      console.log("error al obtener las notas")
    })
  }
  ,[collectionList])

  const handleCollectionClick = (collectionId) => {
    console.log("collecion clicked");
    console.log(collectionId);
    onChangeSelected(collectionId); // Llamamos a la función onChangeSelected pasando el id seleccionado
  };

  
  return (
    <div className="na-collection-list">
    <h2>Collections</h2>
    <div className="na-collection-list-collection">
      {collections.map((collection) => (
        <div key={collection.id} onClick={() => handleCollectionClick(collection.id)}>
          <Collection
            name={collection.name}
            onDelete={() => handleDelete(collection.id)}
          />
        </div>
      ))}
    </div>
    <div>
    <button className="na-collection-list-add-collection-button" onClick={onHandleAddCollectionButtonClicked}> 
    +
    </button>
    </div>
  </div>
  );
};

export default CollectionList;
