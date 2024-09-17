'use client'

import React, { useState } from 'react';
import axios from 'axios';
import { Alignment, BlueprintProvider, Button, Card, Classes, ControlGroup, Dialog, DialogBody, DialogFooter, Elevation, EntityTitle, FileInput, FormGroup, Icon, InputGroup, Label, Menu, MenuDivider, MenuItem, Navbar } from "@blueprintjs/core";
import './events.css'
import { useRouter } from 'next/navigation';

function app() {

    const [forminput, setFormInput] = useState({});
    const [isOpen, setIsOpen] = useState(false);
    const [imageData, setImageData] = useState([]);
    const [imagePreview, setImagePreview] = useState(null);
    const [fileA, setFileA] = useState([]);
    const [fileB, setFileB] = useState([]);
    const [hasMembershipFile, setHasMembershipFile] = useState(false);
    const [showMemberDialog, setMemberDialog] = useState(false);
    const [uploadProgress, setUploadProgress] = useState(0);
    const [eventsData, setEventsData] = useState([]);
    const [suppressClick, setSuppressClick] = useState(false);
    const [currentEventInfo, setCurrentEventInfo] = useState({});
    const [updateCount, setUpdateCount] = useState(0);
    const router = useRouter()

    React.useEffect(() => {
        const setUpState = async () => {
            const memberFileExits = await axios.get('http://localhost:8080/api/files/membershipExists');
            console.log("membershipFile", memberFileExits);
            if (memberFileExits.data === "Does not exist!") {
                setHasMembershipFile(false);
            } else {
                setHasMembershipFile(memberFileExits.data);
                setFileB(['', memberFileExits.data]);
            }

        }

        setUpState();

    }, [])


    React.useEffect(() => {

        const getAllEvents = async () => {
            const response = await axios.get('http://localhost:8080/api/v1/events/getAllEvents');
            console.log(response.data);
            setEventsData(response.data);
        }

        getAllEvents()

    }, [updateCount])


    const handleSubmit = async (event: any, isUpdate: boolean=false) => {
        event.preventDefault()
        const fileData = new FormData();
        fileData.append('files', fileA[0]);
        fileData.append('files', fileB[0]);
        fileData.append('files', imageData[0]);
        fileData.append('title', forminput.title);
        fileData.append('isUpdate', isUpdate);
        fileData.append('oldTitle', currentEventInfo.title)
        console.log("currentEventInfo.title", currentEventInfo.title)

        console.log("WHAT IS FILEDATA, what is formInput", fileData, forminput)
        if (fileData.entries().next().value[1] !== null) {
            const response = await axios.post('http://localhost:8080/api/files/upload', fileData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
                onUploadProgress: progressEvent => {
                    console.log("Uploading : " + ((progressEvent.loaded / progressEvent.total) * 100).toString() + "%")
                    setUploadProgress(((progressEvent.loaded / progressEvent.total) * 100))
                }
            });

            console.log("What is the res", response)
        }


        if(isUpdate){
            let url = 'http://localhost:8080/api/v1/events/updateEvent'
            let x = await axios.put(url, forminput)
            setCurrentEventInfo(x.data);
            setUpdateCount(updateCount+1);
        }else{
            let url = 'http://localhost:8080/api/v1/events';
            let x = await axios.post(url, forminput)
            setCurrentEventInfo(x.data);
            setUpdateCount(updateCount+1);
        }


    }

    const uploadMemberFile = async (event: any) => {
        event.preventDefault()
        const fileData = new FormData();
        fileData.append('title', 'Membership');
        fileData.append('files', fileB[0]);
        if (fileData.entries().next().value[1] !== null) {
            const response = await axios.post('http://localhost:8080/api/files/upload', fileData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
                onUploadProgress: progressEvent => {
                    console.log("Uploading : " + ((progressEvent.loaded / progressEvent.total) * 100).toString() + "%")
                    setUploadProgress(((progressEvent.loaded / progressEvent.total) * 100))
                }
            });
            if (response.status == 200) {
                console.log("response from upload:", response)
                setHasMembershipFile(response.data);
            }
        }
    }

    const handleFileUpload = async (event: any, type: string) => {
        const originalFile = event.target.files[0];

        // Modify the file name based on the type
        let modifiedFileName = originalFile.name;
        if (type === "A") {
            modifiedFileName = "A_" + originalFile.name;
        } else if (type === "B") {
            modifiedFileName = originalFile.name;
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

    const handleCloseDialog = () => {
        setMemberDialog(false);
        setUploadProgress(0);
    }

    function updateEventStates(event): void {
        setIsOpen("update"); 
        setCurrentEventInfo(event);
        setFileA(['',event?.guestListFile?.split("\\")[2]]);
        setImageData(['', event?.coverImage?.split("\\")[2]]);
        setFormInput({...forminput, id: event.id, title: event.title, eventDate: event.eventDate})
    }


    const returnEvents = () => {


        return eventsData.map((event) => (

            <Card interactive={true} elevation={Elevation.TWO} className='event-card' onClick={() => suppressClick ? null : router.push('/dashboard?id=' + event.id)}>
                <div className='event-header'>
                    <h1>{event.title}</h1>
                </div>
                <div style={{ textAlign: 'right' }}>
                    <Button onMouseOver={()=> setSuppressClick(true)} onMouseLeave={()=> setSuppressClick(false)} icon="cog" text="Settings" className={Classes.BUTTON} onClick={() => updateEventStates(event)}/>
                </div>
            </Card>

        ));

    }

    const updateEventDialog = () => {
        return <Dialog title="Update Event" icon="info-sign" isOpen={isOpen=="update"}>
            <DialogBody>
                <ControlGroup fill={false} vertical={true}>
                    <Label htmlFor="input-b">Event Title</Label>
                    <input value={forminput.title} onChange={(e) => setFormInput({ ...forminput, title: e.target.value })} className={Classes.INPUT} id="input-b" placeholder="Ex. Selena and Marks Wedding" />
                    <Label htmlFor="input-b">Event Date</Label>
                    <input value={forminput.eventDate} onChange={(e) => setFormInput({ ...forminput, eventDate: e.target.value })} className={Classes.INPUT} id="input-b" placeholder="Placeholder text" type="date" />
                    <FormGroup
                        helperText="Can be an Event Brite order file, must be in CSV format"
                        label="Guest List File"
                        labelFor="guest-list"
                        labelInfo="(required)"
                    >
                        <FileInput id="guest-list" disabled={false} text={fileA[1] ? fileA[1] : "Choose file..."} onChange={(e) => handleFileUpload(e, "A")} />
                        {fileA[1] && <div><Button intent="warning" icon="small-cross" text="Clear" className={Classes.BUTTON} onClick={() => setFileA([])} /></div>}

                    </FormGroup>
                    <FormGroup
                        label="Cover Image"
                        labelFor="cover-image"
                        labelInfo="(optional)"
                    >
                        <FileInput id="cover-image" disabled={false} text={imageData[1] ? imageData[1] : "Choose file..."} onChange={(e) => handleFileUpload(e, "IMAGE")} />
                        {imageData[1] && <div><Button intent="warning" icon="small-cross" text="Clear" className={Classes.BUTTON} onClick={() => { setImageData([]); setImagePreview(null) }} /></div>}
                    </FormGroup>
                </ControlGroup>

                <img src={imagePreview ? imagePreview : undefined} height="100" width="250" />
                {uploadProgress !== 0 && uploadProgress !== 100 &&
                    <>

                        <div className="bp5-progress-bar bp5-intent-primary {{.modifier}}">
                            <div className="bp5-progress-meter" style={{ width: uploadProgress + '%' }}></div>
                        </div>

                    </>
                }

                {uploadProgress === 100 &&
                    <>

                        <div className='flex'>
                            <EntityTitle icon={<Icon icon="tick-circle" color='green' title="Successfully Uploaded"></Icon>} title='Succesfully Uploaded' />
                        </div>

                    </>

                }

            </DialogBody>
            <DialogFooter actions={
                <>
                    <Button intent="none" text="Close" onClick={() => setIsOpen(false)} />
                    <Button intent="primary" text="Submit" onClick={(event)=>handleSubmit(event, true)} />
                    <Button intent="primary" icon="cog" text="Create" className={Classes.BUTTON} onClick={() => setIsOpen(true)} />
                </>} />
        </Dialog>

    }


    return (
        <>

            <Navbar>
                <Navbar.Group align={Alignment.LEFT}>
                    <Navbar.Heading>CheckMate</Navbar.Heading>
                    <Navbar.Divider />
                    <Button onClick={() => setMemberDialog(true)} className="bp5-minimal" icon={hasMembershipFile ? "document" : "error"} text={hasMembershipFile ? hasMembershipFile : "Membership File Missing"} />
                </Navbar.Group>
            </Navbar>
            {returnEvents()}

            <Card className="create-event" interactive={hasMembershipFile} elevation={Elevation.TWO} onClick={() => setIsOpen("create")}>
                <h1>Create an Event</h1>

                {!hasMembershipFile &&

                    <div className='flex'>
                        <EntityTitle icon={<Icon icon="cross-circle" color='red' title=""></Icon>} title='Please upload membership file before creating an event' />
                    </div>
                }

                {hasMembershipFile && <Icon icon="plus" size={20} />}
            </Card>


            <Dialog title="Membership File" icon="info-sign" isOpen={showMemberDialog}>
                <DialogBody>

                    <ControlGroup fill={false} vertical={true}>
                        <FormGroup
                            helperText="Must be in CSV format"
                            label="Member List File"
                            labelFor="member-list"
                            labelInfo="(required)"
                        >
                            <FileInput id="member-list" disabled={false} text={fileB[1] ? fileB[1] : "Choose file..."} onChange={(e) => handleFileUpload(e, "B")} />
                        </FormGroup>
                    </ControlGroup>

                    {uploadProgress !== 0 && uploadProgress !== 100 &&
                        <>

                            <div className="bp5-progress-bar bp5-intent-primary {{.modifier}}">
                                <div className="bp5-progress-meter" style={{ width: uploadProgress + '%' }}></div>
                            </div>

                        </>

                    }

                    {uploadProgress === 100 &&
                        <>

                            <div className='flex'>
                                <EntityTitle icon={<Icon icon="tick-circle" color='green' title="Successfully Uploaded"></Icon>} title='Succesfully Uploaded' />
                            </div>

                        </>

                    }




                </DialogBody>
                <DialogFooter actions={
                    <>
                        <Button intent="none" text="Close" onClick={handleCloseDialog} />
                        <Button intent="primary" text="Submit" onClick={uploadMemberFile} />
                    </>} />
            </Dialog>

            <Dialog title="Create an Event" icon="info-sign" isOpen={isOpen=="create"}>
                <DialogBody>
                    <ControlGroup fill={false} vertical={true}>
                        <Label htmlFor="input-b">Event Title</Label>
                        <input onChange={(e) => setFormInput({ ...forminput, title: e.target.value })} className={Classes.INPUT} id="input-b" placeholder="Ex. Selena and Marks Wedding" />
                        <Label htmlFor="input-b">Event Date</Label>
                        <input onChange={(e) => setFormInput({ ...forminput, eventDate: e.target.value })} className={Classes.INPUT} id="input-b" placeholder="Placeholder text" type="date" />
                        <FormGroup
                            helperText="Can be an Event Brite order file, must be in CSV format"
                            label="Guest List File"
                            labelFor="guest-list"
                            labelInfo="(required)"
                        >
                            <FileInput id="guest-list" disabled={false} text={fileA[1] ? fileA[1] : "Choose file..."} onChange={(e) => handleFileUpload(e, "A")} />
                            {fileA[1] && <div><Button intent="warning" icon="small-cross" text="Clear" className={Classes.BUTTON} onClick={() => setFileA([])} /></div>}

                        </FormGroup>
                        <FormGroup
                            label="Cover Image"
                            labelFor="cover-image"
                            labelInfo="(optional)"
                        >
                            <FileInput id="cover-image" disabled={false} text={imageData[1] ? imageData[1] : "Choose file..."} onChange={(e) => handleFileUpload(e, "IMAGE")} />
                            {imageData[1] && <div><Button intent="warning" icon="small-cross" text="Clear" className={Classes.BUTTON} onClick={() => { setImageData([]); setImagePreview(null) }} /></div>}
                        </FormGroup>
                    </ControlGroup>

                    <img src={imagePreview ? imagePreview : undefined} height="100" width="250" />
                    {uploadProgress !== 0 && uploadProgress !== 100 &&
                        <>

                            <div className="bp5-progress-bar bp5-intent-primary {{.modifier}}">
                                <div className="bp5-progress-meter" style={{ width: uploadProgress + '%' }}></div>
                            </div>

                        </>
                    }

                    {uploadProgress === 100 &&
                        <>

                            <div className='flex'>
                                <EntityTitle icon={<Icon icon="tick-circle" color='green' title="Successfully Uploaded"></Icon>} title='Succesfully Uploaded' />
                            </div>

                        </>

                    }

                </DialogBody>
                <DialogFooter actions={
                    <>
                        <Button intent="none" text="Close" onClick={() => setIsOpen(false)} />
                        <Button intent="primary" text="Submit" onClick={handleSubmit} />
                        <Button intent="primary" icon="cog" text="Create" className={Classes.BUTTON} onClick={() => setIsOpen(true)} />
                    </>} />
            </Dialog>

            {updateEventDialog()}

        </>

    );
}

export default app;