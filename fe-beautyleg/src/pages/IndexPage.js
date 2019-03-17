/* eslint no-dupe-keys: 0 */
import React from 'react';
import {Link} from "react-router-dom"
import {Grid} from 'antd-mobile';
import {listGroupByOrg} from "../utils/api"
import {debug} from "../utils/constant"


class IndexPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            gridData: []
        }
    }


    componentDidMount() {
        this.getData();
    }

    getData(){
        listGroupByOrg({
            method: "get",
            async: true,
        }).then(res=>{
            for (let i = 0; i < res.length; i++) {
                res[i].link = 'suite/' + encodeURI(res[i].org)
            }
            this.setState({
                gridData: res
            })
            debug(this.state.gridData)
        })
    }

    render() {
        return (
            <div>
                <Grid data={this.state.gridData}
                      columnNum={3}
                      itemStyle={{height: '200px', background: 'rgba(0,0,0,.05)'}}
                      renderItem={dataItem => (
                          <div style={{padding: '12.5px'}}>
                              <Link to={dataItem.link} target={'_blank'}>
                                  <img src={dataItem.thumbPic} style={{height: '150px'}}
                                       referrerPolicy="no-referrer" alt=""/>
                              </Link>
                              <div style={{color: '#888', fontSize: '10px', marginTop: '12px'}}>
                                  <span>{dataItem.org}</span>
                              </div>
                          </div>
                      )}
                />
            </div>
        );
    }

}

export default IndexPage;