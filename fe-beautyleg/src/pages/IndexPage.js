/* eslint no-dupe-keys: 0 */
import React from 'react';
import {Link} from "react-router-dom"


class IndexPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <Link to={`/suite/beautyLeg`} target={'_blank'}>
                    <img style={{width: '50%', height: '50%'}} src={'https://ii.hywly.com/a/1/25656/0.jpg'}
                         referrerPolicy="no-referrer" alt=""/>
                </Link>

                <Link to={`/suite/uGirls`} target={'_blank'}>
                    <img style={{width: '50%', height: '50%'}} src={'https://ii.hywly.com/a/1/5390/0.jpg'}
                         referrerPolicy="no-referrer" alt=""/>
                </Link>

                <Link to={`/suite/myGirl`} target={'_blank'}>
                    <img style={{width: '50%', height: '50%'}} src={'https://ii.hywly.com/a/1/4445/0.jpg'}
                         referrerPolicy="no-referrer" alt=""/>
                </Link>
            </div>
        );
    }

}

export default IndexPage;