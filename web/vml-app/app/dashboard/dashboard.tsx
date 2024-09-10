'use client'
import React from 'react';
import { Button, Label } from '@blueprintjs/core';
import { Classes } from '@blueprintjs/core';
import MemberTable from './MemberTable';
import pmpProData from './memberData.json';
import rsvpData from './rsvpData.json';
import { Spinner } from "@blueprintjs/core";
import axios from 'axios';


function Dashboard() {

    const [userData, setUserData] = React.useState(pmpProData);
    const mySpinner = <Spinner intent="primary" />;

    React.useEffect(()=>{
        let array : any =[];
        pmpProData.forEach(element => {
            let possibleNameRSVP = rsvpData.find(item=> element.firstname.indexOf(item.names) >-1)
            if(possibleNameRSVP){
                element.RSVP = possibleNameRSVP.names;
                console.log("I HAVE A MATCH", possibleNameRSVP.names)
            }
            else
                element.RSVP = false;
           
            array.push(element)
        });

        
        // array.push({id: 0, firstname: "hello", RSVP: 'false'})
        //console.log(array);
        setUserData(array);

    },[])

    React.useEffect(() =>{
        console.log("running useeffect")
        let url = 'http://localhost:8080/api/v1/guest-member-data';
        const fetchData = async () => {
            const data = await axios.get(url);
            console.log("this is the data...", data);
        }
        fetchData();
    },[])

    function search(e: any){
        let input = (e.target.value);
        if(input){
            let result = userData.filter(item=> {
               if(item.username.indexOf(input) >-1){
                    return item
               }
                // item.firstname.indexOf(input) > -1 
               // return (item.firstname.indexOf(input) > -1)
            })
            
            if(result){
                setUserData(result);
            }

        }else{
            setUserData(pmpProData);
        }
        

     }
     

    return (
        <div>
            <h1>VML RSVP Event Search</h1>
            <Label>                
                <input className={Classes.INPUT} placeholder="Members Name" onChange={(e)=>search(e)}/>
            </Label>
            <MemberTable data={userData}/>

            

        </div>
    );
}

export default Dashboard;