import {Request} from './request.js';
import {DOMAIN} from './constant.js';

const BEAUTYLEG_API = `${DOMAIN}/api/beautyleg`;

const listbypage = (params) =>
    Request(params, `${BEAUTYLEG_API}/listbypage`);

export {
    listbypage,
};
