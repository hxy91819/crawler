// import {Modal, notification} from 'antd';
import {debug} from "./constant";

// const openNotification = (description) => {
//     const args = {
//         message: '提示',
//         description: description,
//         duration: 2,
//     };
//     notification.open(args);
// };

// async function warning() {
//     Modal.warning({
//         title: '登录失效',
//         content: '您的登录状态已失效，请返回重新登录',
//         onOk() {
//             let redirectUrl = `/`;
//             window.location.href = redirectUrl;
//         },
//     });
// }

const Request = async (params = {}, url) => {
    // const storage = window.localStorage
    // let token = storage.getItem("token");
    url = `${url}?token=abc`;
    if (typeof (params.query) !== "undefined" && params.query !== "") {
        url = `${url}&${params.query}`;
    }

    let async = true;
    if (typeof (params.async) !== "undefined" && params.async === false) {
        async = false;
    }
    let res = {
        code: 999,
        data: []
    }; // 返回参数
    // 调用api获取参数
    const xhr = new XMLHttpRequest() // IE8/9需用window.XDomainRequest兼容
    // 前端设置是否带cookie
    // xhr.withCredentials = true;
    try {
        xhr.open(params.method, url, async);
        if (async) {
            // 处理异步
            const promise = new Promise(function (resolve, reject) {
                xhr.setRequestHeader('Content-Type', 'application/json');
                // xhr.responseType = "application/json";
                xhr.onload = function (e) {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            // debug('xhr:', xhr.responseText);
                            res = JSON.parse(xhr.responseText);
                            if (!VerifyRes(res)) {
                                reject(res);
                            } else {
                                resolve(res);
                            }
                        } else {
                            console.error(xhr.statusText);
                            reject(xhr.statusText);
                        }
                    }
                };
                if (params.method === "post") {
                    xhr.send(JSON.stringify(params.data));
                } else {
                    xhr.send();
                }
            });
            return promise;
        } else {
            // 处理同步
            xhr.setRequestHeader('Content-Type', 'application/json');
            if (params.method === "post") {
                xhr.send(JSON.stringify(params.data));
            } else {
                xhr.send();
            }
            if (xhr.status === 200) {
                // debug('xhr.responseText:', xhr.responseText);
                res = JSON.parse(xhr.responseText);
                VerifyRes(res);
                return res;
            }
        }
    } catch (error) {
        debug('error:', error);
        // 跳转登录
        // warning();
        return res;
    }
};

/** 为异步方法提供的参数校验逻辑 */
const VerifyRes = res => {
    if (res.code === 999) {
        // 跳转登录
        // warning();
        return false;
    } else if (res.code !== 0) {
        // openNotification(res.msg);
        return false;
    }
    return true;
};

export {
    Request
};
