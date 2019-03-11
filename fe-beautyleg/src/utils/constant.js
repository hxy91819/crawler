// const DOMAIN = 'http://doc.chenjingtalk.com';
const DOMAIN = 'http://localhost:8080';
// const DOMAIN = 'https://easy-mock.com/mock/5bff5a0e7eb9262450270ec0';

/**
 * 百度统计
 * @param props
 * @returns {*}
 * @constructor
 */
const GetBaidu = props => {
    let children = props.children;
    // eslint-disable-next-line
    // noinspection JSUnusedLocalSymbols
    let _hmt = _hmt || [];
    (function () {
        const hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?xxx";
        const s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
    return children;
};

/**
 * 打印debug调试日志
 * @param msg
 * @param optionalParams
 */
const debug = function (msg, ...optionalParams) {
    // noinspection JSUnresolvedVariable
    if ("production" !== process.env.NODE_ENV) console.log(msg, ...optionalParams);
};

/**
 *
 * @param pIndex 页码
 * @param numRows 每页行数
 * @param top 封顶添加的数量
 */
function genData(pIndex = 0, numRows = 20, top = 20) {
    const dataBlob = {};
    let index = 1;
    for (let i = 0; i < numRows; i++) {
        if (index > top) {
            break;
        }
        const ii = (pIndex * numRows) + i;
        dataBlob[`${ii}`] = `row - ${ii}`;
        index++;
    }
    debug('datablob:', dataBlob)
    return dataBlob;
}

export {
    DOMAIN,
    GetBaidu,
    debug,
    genData,
};
