import {Request} from './request.js';
import {DOMAIN} from './constant.js';

const BEAUTYLEG_API = `${DOMAIN}/api/beautyleg`;

const listbypage = (params) =>
    Request(params, `${BEAUTYLEG_API}/listPage`);

const listModelPics = (params) =>
    Request(params, `${BEAUTYLEG_API}/listModelPics`);

export {
    listbypage,
    listModelPics,
};
