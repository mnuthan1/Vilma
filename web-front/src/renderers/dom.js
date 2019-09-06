import React from 'react';
import ReactDOM from 'react-dom';

import { App } from 'components/App';

import '../styles/index.scss';

// setup fake backend
import { configureFakeBackend } from '../helpers/fake-backend';
configureFakeBackend();

ReactDOM.hydrate(
  <App />,
  document.getElementById('root')
);