/* eslint no-dupe-keys: 0 */
import {ListView} from 'antd-mobile';
import React from 'react';
import {debug} from "../utils/constant"
import {listbypage} from "../utils/api"

// 数据源
let data = [];

const NUM_ROWS = 20;
let pageIndex = 0;

function genData(pIndex = 0) {
    const dataBlob = {};
    for (let i = 0; i < NUM_ROWS; i++) {
        const ii = (pIndex * NUM_ROWS) + i;
        dataBlob[`${ii}`] = `row - ${ii}`;
    }
    debug('datablob:', dataBlob)
    return dataBlob;
}

class Demo extends React.Component {
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

    pageNum = 1;// 第几个分页

    componentDidMount() {
        let queryString = `pageNum=${this.pageNum}&pageSize=${NUM_ROWS}`
        listbypage({
            query: queryString,
            method: "get",
            async: true,
        }).then(res => {
                debug('res:', res)
                data = data.concat(res);
                debug('data after concat:', data)
                this.rData = genData();
                debug('rData：', this.rData)
                this.setState({
                    dataSource: this.state.dataSource.cloneWithRows(this.rData),
                    isLoading: false,
                });
            }
        );
    }

    onEndReached = (event) => {
        let queryString = `pageNum=${++this.pageNum}&pageSize=${NUM_ROWS}`
        listbypage({
            query: queryString,
            method: "get",
            async: true,
        }).then(res => {
                // load new data
                // hasMore: from backend data, indicates whether it is the last page, here is false
                if (this.state.isLoading && res.length <= 0) {
                    return;
                }
                data = data.concat(res);
                debug('data after concat:', data)
                console.log('reach end', event);
                this.setState({isLoading: true});
                this.rData = {...this.rData, ...genData(++pageIndex)};
                this.setState({
                    dataSource: this.state.dataSource.cloneWithRows(this.rData),
                    isLoading: false,
                });
            }
        );
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
        let index = data.length - 1;
        const row = (rowData, sectionID, rowID) => {
            debug('rowId:', rowID)
            if (rowID > data.length - 1) {
                rowID = data.length - 1;
            }
            debug('data:', data)
            const obj = data[rowID];
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
                        <img style={{height: '230px', marginRight: '15px'}} src={obj.thumbPic}
                             referrerPolicy="no-referrer" alt=""/>
                        {/*<div style={{lineHeight: 1}}>*/}
                        {/*<div style={{marginBottom: '8px', fontWeight: 'bold'}}>{obj.des}</div>*/}
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
                renderHeader={() => <span>header</span>}
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

export default Demo;