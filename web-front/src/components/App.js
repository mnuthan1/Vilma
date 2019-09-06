import React, { useState } from 'react';
import { Route } from 'react-router-dom';
import { AppRoute } from './AppRoute';
import { LoginPage } from './LoginPage';
import {HomePage} from './Homepage';

// this is to support server and client side rendering
let Router;
if (typeof document !== "undefined") {
  Router = require("react-router-dom").BrowserRouter;
} else {
  Router = require("react-router-dom").StaticRouter;
}

export function App({ initialData }) {
  return (
    <div>
      <Router>
        <div>
          <AppRoute exact path="/" component={HomePage} />
          <Route path="/login" component={LoginPage} />
        </div>
      </Router>
    </div>
  );
}
