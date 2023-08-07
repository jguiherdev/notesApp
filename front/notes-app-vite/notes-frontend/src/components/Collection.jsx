import "../styles/Collection.css"
import CollectionService from '../services/CollectionService';

const Collection = ({ collection, onDeleteCollection }) => {

  const handleDeleteCollection = async () => {
    try {
      await CollectionService.deleteCollection(collection.id);
      onDeleteCollection(collection.id);
    } catch (error) {
      console.log('Error al borrar la nota', error);
    }
  };

  return (
    <div className="na-collection">
      <button className='na-collection-delete-collection' onClick={handleDeleteCollection}>x</button>
      <span className="na-collection-name">{collection.name}</span>
     
    </div>
  );
};

export default Collection;
