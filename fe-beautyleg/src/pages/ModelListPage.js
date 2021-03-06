/* eslint no-dupe-keys: 0 */
import {ListView, SearchBar, NoticeBar} from 'antd-mobile';
import React from 'react';
import {debug, genData} from "../utils/constant"
import {listbypage} from "../utils/api"
import {Link} from "react-router-dom"

// 数据源
const NUM_ROWS = 20;
let data = [];
let pageIndex = 0;
let searchContent = undefined;

const initDataSource = new ListView.DataSource({
    rowHasChanged: (row1, row2) => row1 !== row2,
});

class ModelListPage extends React.Component {
    constructor(props) {
        super(props);
        const dataSource = initDataSource;

        this.state = {
            dataSource,
            isLoading: true,
            reachEnd: false
        };
    }


    componentDidMount() {
        debug('modellistpage did mount')
        let org = this.props.match.params.org
        this.org = org;
        this.getData(org);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        let org = this.props.match.params.org
        if (org !== prevProps.match.params.org) {
            debug(org)
            this.org = decodeURI(org);
            this.setState({
                reachEnd: false
            })
            // fetch or other component tasks necessary for rendering
            this.getData(org);
        }
    }

    getData() {
        let pageNum = pageIndex + 1;
        let queryString = `pageNum=${pageNum}&pageSize=${NUM_ROWS}`
        if (searchContent !== undefined) {
            queryString += `&searchContent=${searchContent}`
        }
        queryString += `&org=${this.org}`
        listbypage({
            query: queryString,
            method: "get",
            async: true,
        }).then(res => {
                // load new data
                // hasMore: from backend data, indicates whether it is the last page, here is false
                if (res.length <= 0) {
                    debug("reach the end")
                    this.setState({
                        reachEnd: true
                    })
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
        if (this.state.reachEnd) {
            debug('reach end')
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
                        <div style={{lineHeight: 1}}>
                            <div style={{marginBottom: '8px', fontWeight: 'bold'}}>{obj.title}</div>
                            <div><span style={{marginBottom: '8px', fontWeight: 'bold'}}>图片数量：{obj.picCount}</span>
                            </div>
                        </div>
                    </div>
                </div>
            );
        };
        return (
            <div>
                <NoticeBar mode="link" action={<a href={'https://www.meituri.com/'}>去看看</a>}>
                    本站仅供交流学习使用，支持原创，请访问源站点
                </NoticeBar>
                <ListView
                    ref={el => this.lv = el}
                    dataSource={this.state.dataSource}
                    renderHeader={() => <SearchBar placeholder="Search" maxLength={8}
                                                   onSubmit={value => {
                                                       this.initParam()
                                                       searchContent = value;
                                                       this.getData()
                                                   }}
                                                   onClear={() => {
                                                       this.initParam()
                                                       searchContent = undefined
                                                       this.getData()
                                                   }}
                    />}
                    renderFooter={() => (<div style={{padding: 30, textAlign: 'center'}}>
                        {this.state.reachEnd ? 'The End' : (this.state.isLoading ? 'Loading...' : 'Loaded')}
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
            </div>
        );
    }

    initParam() {
        pageIndex = 0;
        data = [];
        this.rData = [];
        searchContent = undefined;
        this.setState({
            dataSource: initDataSource,
            reachEnd: false
        })
    }
}

export default ModelListPage;