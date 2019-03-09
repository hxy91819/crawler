import React from 'react';
import {Route, Switch} from 'react-router';
import {BrowserRouter,} from 'react-router-dom';
import Loadable from 'react-loadable';
import {GetBaidu} from "./utils/constant"

function setTitle(title) {
    document.title = title;
}

/**
 * 懒加载导入
 */
const LoaderModelListPage = Loadable({
    loader: () => import('./pages/ModelListPage').then(a1 => a1.default),
    loading() {
        return <div>loading...</div>
    }
});

const LoaderModelPicPage = Loadable({
    loader: () => import('./pages/ModelPicPage').then(a1 => a1.default),
    loading() {
        return <div>loading...</div>
    }
});

function RouterConfig({browserHistory}) {
    return (
        <BrowserRouter>
            <Switch>
                <GetBaidu>
                    <Route exact path="/" component={LoaderModelListPage} onEnter={setTitle('BeautyLeg')}/>
                    <Route path="/pic/:modelId" component={LoaderModelPicPage} onEnter={setTitle('Pic')}/>
                </GetBaidu>
            </Switch>
        </BrowserRouter>
    );
}

export default RouterConfig;
