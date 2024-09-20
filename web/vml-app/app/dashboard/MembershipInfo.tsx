// @ts-nocheck

import React from 'react';


function MembershipInfo({membershipInfo}) {

    function showMemberInfo() {
        if (membershipInfo == 'Yes') {
            return (<>
                <div className='flex-between'>
                    <h2>Membership Information</h2>
                    <Button rightIcon="search" intent={Intent.NONE} text="Re-assign Member" onClick={() => setMembershipInfo('look-up')} />
                </div>
                
                <div className="info-grid">
                    <div>
                        <h4>Member ID</h4>
                        <p>{details.memberId ? details.memberId : null }</p>
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
                    <Button rightIcon="search" intent={Intent.WARNING} text="Membership Look-up" onClick={() => setMembershipInfo('look-up')} />
                </>
            );
        } else {
            return (
                <>
                    <h3>Search</h3>
                   
                    <InputGroup leftIcon="search" type="text" placeholder="Search" />
                    <Card className='flex-evenly' compact={true}>
                        <div>
                            <Button rightIcon="tick-circle" intent={Intent.NONE} onClick={() => setMembershipInfo('Yes')} />
                        </div>
                        <Divider/>
                        <div>
                            <h5 style={{margin:0}}>ID:</h5>
                            <h6 style={{margin:0}}>123</h6>
                        </div>
                        <Divider/>
                        <div>
                            <h5 style={{margin:0}}>Username:</h5>
                            <h6 style={{margin:0}}>123</h6>
                        </div>
                        <Divider/>
                        <div>
                            <h5 style={{margin:0}}>Name:</h5>
                            <h6 style={{margin:0}}>123</h6>
                        </div>
                        <Divider/>
                        <div>
                            <h5 style={{margin:0}}>Email:</h5>
                            <h6 style={{margin:0}}>123</h6>
                        </div>
                        <Divider/>
                        <div>
                            <h5 style={{margin:0}}>Level:</h5>
                            <h6 style={{margin:0}}>123</h6>
                        </div>
                        <Divider/>
                        <div>
                            <h5 style={{margin:0}}>Join Date:</h5>
                            <h6 style={{margin:0}}>123</h6>
                        </div>
                        <Divider/>
                        <div>
                            <h5 style={{margin:0}}>Expiry Date:</h5>
                            <h6 style={{margin:0}}>123</h6>
                        </div>
                    </Card>
    
                </>
    
            );
        }
    }

    return (showMemberInfo());
}

export default MembershipInfo;


