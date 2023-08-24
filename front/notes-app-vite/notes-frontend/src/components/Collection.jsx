import "../styles/Collection.css"
import CollectionService from '../services/CollectionService';
import { Dropdown, DropdownTrigger, DropdownMenu, DropdownItem, Button, RadioGroup, Radio, Divider } from "@nextui-org/react";
import { Spacer } from "@nextui-org/react";
import { Card, CardHeader, CardBody, Image } from "@nextui-org/react";



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
    <Card className="na-collection-card">
      <CardHeader>
        <small className="text-default-500">12 Collections</small>
        <Spacer x={4} />
        <Dropdown>
          <DropdownTrigger>
            <Button
              color="secondary"
              variant="shadow"
              size="sm"
            >
              Actions
            </Button>
          </DropdownTrigger>
          <DropdownMenu aria-label="Static Actions">
            <DropdownItem key="new">New file</DropdownItem>
            <DropdownItem key="copy">Copy link</DropdownItem>
            <DropdownItem key="edit">Edit file</DropdownItem>
            <DropdownItem key="delete" className="text-danger" color="danger" onClick={handleDeleteCollection}>
              Delete file
            </DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </CardHeader>
      <Spacer y={-10} />
      <CardBody className="overflow-visible py-2" >
        <div className="na-collection-name">
          {collection.name}
        </div>
        <Spacer y={1} />
      </CardBody>
    </Card>



  );
};

export default Collection;
