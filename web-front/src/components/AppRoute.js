
import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { AuthConsumer } from './AuthContext'

export const AppRoute = ({ component: Component, ...rest }) => (
    <AuthConsumer>
      {({ isAuth }) => (
        <Route
          render={props =>
            isAuth ? <Component {...props} /> : <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
          }
          {...rest}
        />
      )}
    </AuthConsumer>
  )