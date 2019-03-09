/* eslint no-dupe-keys: 0 */
import {ListView, SearchBar} from 'antd-mobile';
import React from 'react';
import {debug, genData} from "../utils/constant"
import {listbypage} from "../utils/api"
import {Link} from "react-router-dom"

// 数据源
let data = [];

const NUM_ROWS = 20;
let pageIndex = 0;
let reachEnd = false;

class ModelListPage extends React.Component {
    constructor(props) {
        super(props);
        const dataSource = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        });

        this.state = {
            dataSource,
            isLoading: true,
        };
    }


    componentDidMount() {
        debug('modellistpage did mount')
        this.getData();
    }

    getData() {
        let pageNum = pageIndex + 1;
        let queryString = `pageNum=${pageNum}&pageSize=${NUM_ROWS}`
        listbypage({
            query: queryString,
            method: "get",
            async: true,
        }).then(res => {
                // load new data
                // hasMore: from backend data, indicates whether it is the last page, here is false
                if (res.length <= 0) {
                    reachEnd = true;
                    return;
                }
                if (this.state.isLoading && res.length <= 0) {
                    return;
                }
                data = data.concat(res);
                debug('data after concat:', data)
                this.setState({isLoading: true});
                this.rData = {...this.rData, ...genData(pageIndex++, NUM_ROWS, res.length)};
                this.setState({
                    dataSource: this.state.dataSource.cloneWithRows(this.rData),
                    isLoading: false,
                });
            }
        );
    }

    onEndReached = (event) => {
        if (reachEnd) {
            return;
        }
        this.getData();
    }

    render() {
        const separator = (sectionID, rowID) => (
            <div
                key={`${sectionID}-${rowID}`}
                style={{
                    backgroundColor: '#F5F5F9',
                    height: 8,
                    borderTop: '1px solid #ECECED',
                    borderBottom: '1px solid #ECECED',
                }}
            />
        );
        // let index = data.length - 1;
        const row = (rowData, sectionID, rowID) => {
            if (rowID > data.length - 1) {
                return (<br/>);
            }
            const obj = data[rowID];
            // noinspection JSDuplicatedDeclaration
            return (
                <div key={rowID} style={{padding: '0 15px'}}>
                    {/*<div*/}
                    {/*style={{*/}
                    {/*lineHeight: '50px',*/}
                    {/*color: '#888',*/}
                    {/*fontSize: 18,*/}
                    {/*borderBottom: '1px solid #F6F6F6',*/}
                    {/*}}*/}
                    {/*>beautys*/}
                    {/*</div>*/}
                    <div style={{display: '-webkit-box', display: 'flex', padding: '15px 0'}}>
                        <Link to={`/pic/${obj.modelId}`} target={'_blank'}>
                            <img style={{width: '40vw', marginRight: '15px'}} src={obj.thumbPic}
                                 referrerPolicy="no-referrer" alt=""/>
                        </Link>
                        {/*<div style={{lineHeight: 1}}>*/}
                        <div style={{marginBottom: '8px', fontWeight: 'bold'}}>{obj.title}</div>
                        {/*<div><span style={{fontSize: '30px', color: '#FF6E27'}}>{rowID}</span>¥</div>*/}
                        {/*</div>*/}
                    </div>
                </div>
            );
        };
        return (
            <ListView
                ref={el => this.lv = el}
                dataSource={this.state.dataSource}
                renderHeader={() => <SearchBar placeholder="Search" maxLength={8}/>}
                renderFooter={() => (<div style={{padding: 30, textAlign: 'center'}}>
                    {this.state.isLoading ? 'Loading...' : 'Loaded'}
                </div>)}
                renderRow={row}
                renderSeparator={separator}
                className="am-list"
                pageSize={4}
                useBodyScroll
                onScroll={() => {
                    console.log('scroll');
                }}
                scrollRenderAheadDistance={500}
                onEndReached={this.onEndReached}
                onEndReachedThreshold={10}
            />
        );
    }
}

export default ModelListPage;