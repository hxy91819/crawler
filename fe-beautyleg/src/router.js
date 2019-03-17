import React from 'react';
import {Route, Switch} from 'react-router';
import {BrowserRouter,} from 'react-router-dom';
import Loadable from 'react-loadable';
import IndexPage from "./pages/IndexPage"

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
                <Route exact path="/" component={IndexPage} onEnter={setTitle('Index')}/>
                <Route path="/suite/:org" component={LoaderModelListPage} onEnter={setTitle('Suite')}/>
                <Route path="/pic/:modelId" component={LoaderModelPicPage} onEnter={setTitle('Model')}/>
            </Switch>
        </BrowserRouter>
    );
}

export default RouterConfig;
