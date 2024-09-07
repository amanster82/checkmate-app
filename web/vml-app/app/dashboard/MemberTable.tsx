import React from 'react';
import { Classes } from '@blueprintjs/core';


function MemberTable(props: any) {
    console.log(props.data);
    return (
        <table className={Classes.HTML_TABLE}>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Membership Type</th>
                    <th>Membership Expiry Date</th>
                    <th>RSVP Match</th>
                </tr>
            </thead>
            <tbody>
                {
                
                (props.data).map( (item: any) => {
                    return(<tr>
                    <td>{item.id}</td>
                    <td>{item.username}</td>
                    <td>{item.firstname}</td>
                    <td>{item.lastname}</td>  
                    <td>{item.email}</td>
                    <td>{item.membership}</td>
                    <td>{item.expires}</td>   
                    <td>{item.RSVP}</td>               
                    </tr>)
                })

                }
            </tbody>
            <tfoot>
            </tfoot>
        </table>
    );
}

export default MemberTable;