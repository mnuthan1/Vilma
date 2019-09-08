import React from 'react';
import ReactDOM from 'react-dom';

import { App } from 'components/App';

import '../styles/index.scss';

// setup fake backend
import { configureFakeBackend } from '../helpers/fake-backend';
configureFakeBackend();


import { BrowserRouter, Route, Switch } from "react-router-dom";

let Router;
if (typeof document !== "undefined") {
  Router = require("react-router-dom").BrowserRouter;
} else {
  Router = require("react-router-dom").StaticRouter;
}


const routes = (
  <Router>
    <Switch>
      <Route path="/" component={App} />
    </Switch>
  </Router>
);

ReactDOM.hydrate(
  routes,
  document.getElementById('root')
);