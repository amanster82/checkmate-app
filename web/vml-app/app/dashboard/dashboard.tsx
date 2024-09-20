'use client'
import React, { useCallback, useRef, useState } from 'react';
import { Alert, Alignment, Button, ButtonGroup, Card, Checkbox, Dialog, DialogBody, DialogFooter, Divider, Elevation, Icon, InputGroup, Intent, Label, Overlay2, OverlaysProvider, OverlayToaster, Position, Tab, Tabs, TabsExpander, Toast, Toast2, Toaster } from '@blueprintjs/core';
import { Classes } from '@blueprintjs/core';
import MemberTable from './MemberTable';
import pmpProData from './memberData.json';
import rsvpData from './rsvpData.json';
import { Spinner } from "@blueprintjs/core";
import axios from 'axios';
import './dashboard.css';


function Dashboard() {

    type GuestDetails = {
        guestTicketid: number;
        guestEmail: string;
        guestFirstName: string;
        guestLastName: string;
        guestPaymentMethod: string;
    };

    type Attendee = {
        attendeeId: number;
        eventName: string;
        guestDetails: GuestDetails;
        // Add more properties as needed
    };



    const [userData, setUserData] = useState(pmpProData);
    const [openAlert, setOpenAlert] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [membershipInfo, setMembershipInfo] = useState('Yes');
    const [attendees, setAttendees] = useState([]);
    const [details, setDetails] = useState<GuestDetails>({
        guestTicketid: 0,
        guestEmail: '',
        guestFirstName: '',
        guestLastName: '',
        guestPaymentMethod: ''
    });

    const [membershipData, setMembershipData] = useState([]);
    const [filterMembershipData, setFilterMembershipData] = useState([]);
    const [filterAttendeeData, setFilterAttendeeData] = useState([]);
    const [tabs, setTabs] = useState(false)


    const mySpinner = <Spinner intent="primary" />;

    React.useEffect(() => {
        // Get the full URL
        const url = new URL(window.location.href);

        // Extract query parameters
        const params = new URLSearchParams(url.search);

        // Get the 'id' parameter as a string
        const id = params.get('id');

        let urlAPI = 'http://localhost:8080/api/v1/guest-member-data/' + id;
        const fetchData = async () => {
            const response = await axios.get(urlAPI);
            console.log("this is the data...", response.data);
            setAttendees(response.data);
            setFilterAttendeeData(response.data);
        }
        fetchData();
    }, []);


    React.useEffect(() => {

    }, [])



    const searchAttendees = (query) => {
        if (query == null || query == '') {
            setFilterAttendeeData([...attendees])
        } else {

            const x = attendees?.filter((attendee) => {
                return (
                    attendee.id.toString().includes(query) ||                // Search by ID
                    attendee.guestEmail.toLowerCase().includes(query.toLowerCase()) ||  // Search by username
                    attendee.guestFirstName.toLowerCase().includes(query.toLowerCase()) || // Search by first name
                    attendee.guestLastName.toLowerCase().includes(query.toLowerCase()) ||  // Search by last name
                    attendee.guestTicketid.toString().includes(query)        // Search by email
                );
            });

            setFilterAttendeeData(x);

        }

    };
        

    const onMembershipLookupClick = async () => {
        setMembershipInfo('look-up');
        let urlAPI = 'http://localhost:8080/api/v1/memberlookup/all';
        const response = await axios.get(urlAPI);
        console.log(response.data);
        setMembershipData(response.data);
        setFilterMembershipData(response.data);
    }

    // Function to search by any field
    function searchMember(query) {
        if (query == null || query == '') {
            setFilterMembershipData([...membershipData])
        } else {

            const x = membershipData?.filter((member) => {
                return (
                    member.id.toString() === query ||                // Search by ID
                    member.username.toLowerCase().includes(query.toLowerCase()) ||  // Search by username
                    member.firstName.toLowerCase().includes(query.toLowerCase()) || // Search by first name
                    member.lastName.toLowerCase().includes(query.toLowerCase()) ||  // Search by last name
                    member.email.toLowerCase().includes(query.toLowerCase())        // Search by email
                );
            });

            setFilterMembershipData(x);

        }

    }


    function membershipSearch() {
        return (
            <>
                <h3>Search</h3>

                <InputGroup leftIcon="search" type="text" placeholder="Search" onChange={(e) => searchMember(e.target.value)} />
                <div style={{ maxHeight: 405, overflow: 'auto' }}>
                    {(filterMembershipData)?.map((member) =>
                        <Card className='flex-evenly' compact={true}>
                            <div>
                                <Button rightIcon="tick-circle" intent={Intent.NONE} onClick={() => onMemberAssignClick(member)} />
                            </div>
                            <Divider />
                            <div>
                                <h5 style={{ margin: 0 }}>ID:</h5>
                                <h6 style={{ margin: 0 }}>{member.id}</h6>
                            </div>
                            <Divider />
                            <div>
                                <h5 style={{ margin: 0 }}>Username:</h5>
                                <h6 style={{ margin: 0 }}>{member.username}</h6>
                            </div>
                            <Divider />
                            <div>
                                <h5 style={{ margin: 0 }}>Name:</h5>
                                <h6 style={{ margin: 0 }}>{member.firstName + " " + member.lastName}</h6>
                            </div>
                            <Divider />
                            <div>
                                <h5 style={{ margin: 0 }}>Email:</h5>
                                <h6 style={{ margin: 0 }}>{member.email}</h6>
                            </div>
                            <Divider />
                            <div>
                                <h5 style={{ margin: 0 }}>Level:</h5>
                                <h6 style={{ margin: 0 }}>{member.level}</h6>
                            </div>
                            <Divider />
                            <div>
                                <h5 style={{ margin: 0 }}>Join Date:</h5>
                                <h6 style={{ margin: 0 }}>{member.joinedDate}</h6>
                            </div>
                            <Divider />
                            <div>
                                <h5 style={{ margin: 0 }}>Expiry Date:</h5>
                                <h6 style={{ margin: 0 }}>{member.expireDate}</h6>
                            </div>
                        </Card>
                    )}
                </div>


            </>)

    }

    const onDetailsClick = (attendee: any) => {

        setIsOpen(true);
        setDetails(attendee);  // This updates the dialog with guest details
        setMembershipInfo(attendee.memberId ? 'Yes' : 'No');
    };

    const onWaiverClick = (attendee, mySignedWaiver) => {
        let findIndex = attendees.findIndex((item) => attendee.id === item.id)
        let copyAttendees = [...attendees]
        copyAttendees[findIndex].signedWaiver = !mySignedWaiver;
        setAttendees(copyAttendees);
    }

    const onCheckInClick = async (attendee) => {
        let selectedAttendee = attendees.find((item) => attendee.id === item.id);

        if (selectedAttendee.signedWaiver && selectedAttendee.memberId !==0) {
            let findIndex = attendees.findIndex((item) => attendee.id === item.id)
            let copyAttendees = [...attendees]
            copyAttendees[findIndex].checkedInStatus = true;
            setAttendees(copyAttendees);
            let urlAPI = 'http://localhost:8080/api/v1/updateAttendee';
            const response = await axios.put(urlAPI, copyAttendees[findIndex]);
            console.log("response", response)
            console.log("COPYATTENDEEs COMPLETE", copyAttendees)
            setAttendees(copyAttendees); //Update the list of Attendees locally
        } else {
            setOpenAlert(true);
        }


    }

    const onUndoClick = async (attendee) => {
        let selectedAttendee = attendees.find((item) => attendee.id === item.id);


        let findIndex = attendees.findIndex((item) => attendee.id === item.id)
        let copyAttendees = [...attendees]
        copyAttendees[findIndex].checkedInStatus = false;
        setAttendees(copyAttendees);
        let urlAPI = 'http://localhost:8080/api/v1/updateAttendee';
        const response = await axios.put(urlAPI, copyAttendees[findIndex]);
        console.log("response", response)
        console.log("COPYATTENDEEs COMPLETE", copyAttendees)
        setAttendees(copyAttendees); //Update the list of Attendees locally



    }

    const onMemberAssignClick = (memberFound) => {


        function mapMemberToAttendee(attendee, member) {
            // Map non-null fields from member to attendee
            attendee.memberId = member.id || attendee.memberId;
            attendee.memberFirstName = member.firstName || attendee.memberFirstName;
            attendee.memberLastName = member.lastName || attendee.memberLastName;
            attendee.memberEmail = member.email || attendee.memberEmail;
            attendee.memberLevel = member.level || attendee.memberLevel;
            attendee.memberUsername = member.username || attendee.memberUsername;
            attendee.memberJoinedDate = member.joinedDate || attendee.memberJoinedDate;
            attendee.memberExpireDate = member.expireDate || attendee.memberExpireDate;

            return attendee;
        }

        setMembershipInfo('Yes');
        let detailsCopy = { ...details };
        detailsCopy = mapMemberToAttendee(detailsCopy, memberFound);
        setDetails(detailsCopy);
        console.log("Details NOw", detailsCopy)

    }


    const onSaveDetailsClick = async () => {

        //Save the changes of the attendee 

        let findIndex = attendees.findIndex((item) => item.id === details.id)
        let copyAttendees = [...attendees]
        copyAttendees[findIndex] = details;
        let urlAPI = 'http://localhost:8080/api/v1/updateAttendee';
        const response = await axios.put(urlAPI, copyAttendees[findIndex]);
        console.log("response", response)
        console.log("COPYATTENDEEs COMPLETE", copyAttendees)
        setAttendees(copyAttendees); //Update the list of Attendees locally
        setFilterAttendeeData(copyAttendees);
        alert("Saved!")
        
    }

    function showMemberInfo() {
        if (membershipInfo == 'Yes') {
            return (<>
                <div className='flex-between'>
                    <h2>Membership Information</h2>
                    <Button rightIcon="search" intent={Intent.NONE} text="Re-assign Member" onClick={onMembershipLookupClick} />
                </div>
                <div className="info-grid">
                    <div>
                        <h4>Member ID</h4>
                        <p>{details.memberId ? details.memberId : null}</p>
                    </div>
                    <div>
                        <h4>Email Address</h4>
                        <p>{details.memberEmail}</p>
                    </div>
                    <div>
                        <h4>First Name</h4>
                        <p>{details.memberFirstName}</p>
                    </div>
                    <div>
                        <h4>Last Name</h4>
                        <p>{details.memberLastName}</p>
                    </div>

                    <div>
                        <h4>Membership Level</h4>
                        <p>{details.memberLevel}</p>
                    </div>
                    <div>
                        <h4>Username</h4>
                        <p>{details.memberUsername}</p>
                    </div>
                    <div>
                        <h4>Membership Join Date</h4>
                        <p>{details.memberJoinedDate}</p>
                    </div>
                    <div>
                        <h4>Membership Expiry Date</h4>
                        <p>{details.memberExpireDate}</p>
                    </div>
                </div>
            </>
            )
        } else if (membershipInfo == 'No') {
            return (
                <>
                    <h3>No Membership Found</h3>
                    <p>This guest must be a member to check in, please assign a membership to this guest.</p>
                    <Button rightIcon="search" intent={Intent.WARNING} text="Membership Look-up" onClick={onMembershipLookupClick} />
                </>
            );
        } else {
            return membershipSearch();
        }
    }

    // Create a ref for the Toaster component
    const toasterRef = useRef(null);

    // Function to show a toast notification


    return (
        
        <div>            
            <div className='flex-center title'>
                <h1>CheckMate System</h1>
            </div>
            <div className='flex-right'>
                <div className='Search'><InputGroup  large={true} leftIcon="search" type="text" placeholder="Search" onChange={(e) => searchAttendees(e.target.value)} /></div>
            </div>
            
            <div className='flex-evenly'>
                <ButtonGroup minimal={false}>
                    <Button onClick={()=>setTabs(false)}>Attendees in the Queue</Button>
                    <Button onClick={()=>setTabs(true)}>Attendees Checked-in</Button>
                </ButtonGroup>
            </div>
            <br></br>
            <br></br>

            {filterAttendeeData?.filter((item)=> item.checkedInStatus === tabs).map((attendee: { guestTicketid: number, guestFirstName: string, guestLastName: string, guestEmail: string, memberId: number, signedWaiver: boolean, guestDetails: any }, index) =>
                <React.Fragment key={attendee.id}>
                    <Card className="card-grid" interactive={true} key={attendee.id}>
                        <div className="grid-item">
                            <h4>Ticket</h4>
                            <p>{attendee.guestTicketid}</p>
                        </div>

                        <div className="grid-item">
                            <h4>Name</h4>
                            <p>{attendee.guestFirstName + " " + attendee.guestLastName}</p>
                        </div>

                        <div className="grid-item">
                            <h4>Email</h4>
                            <p>{attendee.guestEmail}</p>
                        </div>

                        <div className="grid-item">
                            <h4>Membership</h4>
                            <p>{attendee.memberId ? <Icon icon='endorsed' intent={Intent.PRIMARY}></Icon> : <Icon icon='cross-circle' intent={Intent.DANGER}></Icon>}</p>
                        </div>
                        <div className="grid-item">
                            <Button icon="user" text="Profile" onClick={() => onDetailsClick(attendee)} />
                        </div>
                        <div className="grid-item">
                            <Checkbox checked={attendee.signedWaiver} label='Waiver Signed?' large={true} alignIndicator={Alignment.RIGHT} onClick={() => onWaiverClick(attendee, attendee.signedWaiver)}>
                            </Checkbox>
                        </div>
                        <div className="grid-item">
                            {!attendee.checkedInStatus && <Button rightIcon="arrow-right" intent={Intent.SUCCESS} text="Check In" onClick={() => onCheckInClick(attendee)} />}
                            {attendee.checkedInStatus &&<Button icon="arrow-left" intent={Intent.DANGER} text="Undo" onClick={() => onUndoClick(attendee)} />}
                        </div>
                    </Card>
                    <br />
                </React.Fragment>
            )}
            <Alert
                confirmButtonText="Ok"
                icon="high-priority"
                intent={Intent.DANGER}
                isOpen={openAlert}
                onCancel={() => setOpenAlert(false)}
                onConfirm={() => setOpenAlert(false)}
            >
                <div>
                    <strong>Wrong move.</strong>
                    <br></br>
                    <br></br>
                    <p>You cannot check them in unless they have signed a waiver and are a member!</p>

                </div>
            </Alert>


            <Dialog title="Informational dialog" icon="info-sign" isOpen={isOpen} style={{ width: 'auto' }}>
                <DialogBody>
                    <div className='flex'>
                        <Card className="ticket-info">
                            <h2>Ticket Information</h2>
                            {details && <div className="info-grid">
                                <div>
                                    <h4>Ticket Number</h4>
                                    <p>{details.guestTicketid}</p>
                                </div>
                                <div>
                                    <h4>Email Address</h4>
                                    <p>{details.guestEmail}</p>
                                </div>
                                <div>
                                    <h4>First Name</h4>
                                    <p>{details.guestFirstName}</p>
                                </div>
                                <div>
                                    <h4>Last Name</h4>
                                    <p>{details.guestLastName}</p>
                                </div>

                                <div>
                                    <h4>Payment Method</h4>
                                    <p>{details.guestPaymentMethod}</p>
                                </div>
                            </div>}
                        </Card>
                        <Card className="member-info">
                            {showMemberInfo()}
                        </Card>
                    </div>
                </DialogBody>
                <DialogFooter actions={
                <>
                    <Button intent="primary" text="Close" onClick={() => setIsOpen(false)} />
                    <Button intent="primary" text="Save" onClick={onSaveDetailsClick} />
                </>

                }></DialogFooter>
            </Dialog>
        </div>
    );
}

export default Dashboard;