'use client'

import React, { useState } from 'react';
import axios from 'axios';
import { BlueprintProvider, Button, Card, Classes, ControlGroup, Dialog, DialogBody, DialogFooter, Elevation, FileInput, FormGroup, Icon, InputGroup, Label } from "@blueprintjs/core";
import './events.css'

function app() {

    const [forminput, setFormInput] = useState({});
    const [isOpen, setIsOpen] = useState(false);
    const [imageData, setImageData] = useState([]);
    const [imagePreview, setImagePreview] = useState(null);
    const [fileA, setFileA] = useState([]);
    const [fileB, setFileB] = useState([]);


    const handleSubmit = async (event: any) => {
        event.preventDefault()
        const fileData = new FormData();
        fileData.append('files', fileA[0]);
        fileData.append('files', fileB[0]);
        fileData.append('files', imageData[0]);
        fileData.append('title', forminput.title);

        if (fileData.entries().next().value[1] !== null) {
            const response = await axios.post('http://localhost:8080/api/files/upload', fileData, {
                headers: {
                  "Content-Type": "multipart/form-data",
                },
                onUploadProgress:progressEvent => {
                    console.log("Uploading : " + ((progressEvent.loaded / progressEvent.total) * 100).toString() + "%")
                }
            });
        }

        const url = 'http://localhost:8080/api/v1/event';
        let formData = {...forminput, 
                        'guestListFile': fileA,
                        'membershipFile': fileB,
                        'coverImage': imageData
                    }

        console.log(formData);

        const config = {
          headers: {
            'Accept': 'application/json',
            'content-type': 'multipart/form-data',
          },
        };
        
        let x = await axios.post(url, forminput)
        console.log(x.data);


    }
    
    const handleFileUpload = async (event: any, type: string) => {
        const originalFile = event.target.files[0];
    
        // Modify the file name based on the type
        let modifiedFileName = originalFile.name;
        if (type === "A") {
            modifiedFileName = "A_" + originalFile.name;
        } else if (type === "B") {
            modifiedFileName = "B_" + originalFile.name;
        } else {
            modifiedFileName = "img_" + originalFile.name;
            setImagePreview(URL.createObjectURL(originalFile)); // Set the image preview if it's an image
        }
    
        // Create a new File object with the modified name
        const modifiedFile = new File([originalFile], modifiedFileName, { type: originalFile.type });
    
        // Update the state based on the type
        if (type === "A") {
            setFileA([modifiedFile, originalFile.name]);
        } else if (type === "B") {
            setFileB([modifiedFile, originalFile.name]);
        } else {
            setImageData([modifiedFile, originalFile.name]);
        }
    };

    return (
        <>
            <Card interactive={false} elevation={Elevation.TWO}>
                <div className='event-card'>
                    <h1>My Party</h1>
                </div>
                <div style={{ textAlign: 'right' }}>
                    <Button text="Enter" className={Classes.BUTTON} />
                    <Button text="Settings" className={Classes.BUTTON} onClick={() => setIsOpen(true)} />
                </div>

            </Card>

            <Card className="create-event" interactive={true} elevation={Elevation.TWO} onClick={() => setIsOpen(true)}>
                <h1>Create an Event</h1>
                <Icon icon="plus" size={20} />
            </Card>

            <Dialog title="Create an Event" icon="info-sign" isOpen={isOpen}>
                <DialogBody>
                    <ControlGroup fill={false} vertical={true}>
                        <Label htmlFor="input-b">Event Title</Label>
                        <input onChange={(e)=>setFormInput({...forminput, title: e.target.value})} className={Classes.INPUT} id="input-b" placeholder="Ex. Selena and Marks Wedding" />
                        <Label htmlFor="input-b">Event Date</Label>
                        <input onChange={(e)=>setFormInput({...forminput, eventDate: e.target.value })} className={Classes.INPUT} id="input-b" placeholder="Placeholder text" type="date" />
                        <FormGroup
                            helperText="Can be an Event Brite order file, must be in CSV format"
                            label="Guest List File"
                            labelFor="guest-list"
                            labelInfo="(required)"
                        >
                            <FileInput id="guest-list" disabled={false} text={fileA[1] ? fileA[1] : "Choose file..."} onChange={(e)=> handleFileUpload(e, "A")}/>

                        </FormGroup>

                        <FormGroup
                            helperText="Must be in CSV format"
                            label="Member List File"
                            labelFor="member-list"
                            labelInfo="(required)"
                        >
                            <FileInput id="member-list" disabled={false} text={fileB[1] ? fileB[1] : "Choose file..."} onChange={(e)=> handleFileUpload(e, "B")} />

                        </FormGroup>

                        <FormGroup
                            label="Cover Image"
                            labelFor="cover-image"
                            labelInfo="(optional)"
                        >
                            <FileInput id="cover-image" disabled={false} text={imageData[1] ? imageData[1] : "Choose file..."}  onChange={(e)=>handleFileUpload(e, "IMAGE")} />

                        </FormGroup>

                    </ControlGroup>
                    
                    <img src={imagePreview ? imagePreview : undefined} height="100" width="250"/>

                </DialogBody>
                <DialogFooter actions={
                    <>
                        <Button intent="none" text="Close" onClick={() => setIsOpen(false)} />
                        <Button intent="primary" text="Submit" onClick={handleSubmit} />
                    </>} />
            </Dialog>
        </>

    );
}

export default app;