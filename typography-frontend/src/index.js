import React from 'react';
import ReactDOM from 'react-dom';

import BrowserRouter from 'react-router-dom/BrowserRouter';

import App from './App';
import './components/Feedback/feedback.css';
import './components/Cabinet/cabinet.css';
import './css/style.css';
import './components/Auth/sign.css'
ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);
