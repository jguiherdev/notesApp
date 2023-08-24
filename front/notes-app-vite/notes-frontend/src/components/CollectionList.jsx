import { useEffect, useState } from 'react';
import Collection from './Collection';
import CollectionService from '../services/CollectionService';
import '../styles/CollectionList.css';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { Popover, PopoverTrigger, PopoverContent, Button, Spacer } from "@nextui-org/react";
import { Pagination } from "@nextui-org/pagination";
import CollectionForm from './CollectionForm'
import { AddNoteIcon } from './reusable-components/AddNoteIcon.jsx';
import { Chip } from "@nextui-org/chip";


const CollectionList = ({
  onChangeSelected,
  onDeleteCollection,
  collectionList,
  onAddCollection
}) => {

  const iconClasses = "text-xl text-default-500 pointer-events-none flex-shrink-0";


  const collectionService = CollectionService;

  const itemsPerPage = 5

  const [collections, setCollections] = useState([]);
  const [currentPage, setCurrentPage] = useState(1); // Página actual
  const [totalCollections, setTotalCollections] = useState([]); // Página actual

  useEffect(() => {
    collectionService.getNumberOfCollections().then((data => {
      setTotalCollections(data)
    })).catch((error) => {
      console.log(error)
    })
  }, [collectionList])

  useEffect(() => {
    // Lógica para obtener colecciones según la página actual
    collectionService.getCollections(currentPage - 1).then((data) => {
      if (data) {
        setCollections(data);
      }
    }).catch((error) => {
      console.log("Error al obtener las colecciones");
    });
  }, [currentPage, collectionList]);

  const handleCollectionClick = (collectionId) => {
    onChangeSelected(collectionId);
  };

  return (
    <div className="na-collection-list-container">
      <div className='na-collection-list-header'>
      <h2>Collections</h2>
      <Popover placement="right" color='default'>
      <PopoverTrigger>
            <Chip
              color="default"
              variant="light"
              size="sm"
              startContent={<AddNoteIcon className={iconClasses} />}
            >
            </Chip>
      </PopoverTrigger>
      <PopoverContent>
        <CollectionForm onAddCollection={onAddCollection}></CollectionForm>
      </PopoverContent>
    </Popover>
      </div>
      <div className="na-collection-list-collection">
        {collections.map((collection) => (
          <div key={collection.id} onClick={() => handleCollectionClick(collection.id)}>
            <Collection
              collection={collection}
              onDeleteCollection={onDeleteCollection}
            />
          </div>
        ))}
      </div>
      <Pagination
        total={Math.ceil(totalCollections / itemsPerPage)} // Total de páginas
        current={currentPage}
        onChange={(page) => setCurrentPage(page)}
        color="secondary"
      />
    </div>
  );
};

export default CollectionList;
