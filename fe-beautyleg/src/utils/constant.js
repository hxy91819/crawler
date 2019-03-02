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

export {
    DOMAIN,
    GetBaidu,
    debug,
};
