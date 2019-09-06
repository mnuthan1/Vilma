import React from 'react';
import { Route, Redirect } from 'react-router-dom';

export const AppRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => (
        false
            ? <Component {...props} />
            : <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
    )} />
)