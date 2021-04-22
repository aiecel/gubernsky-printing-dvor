import React from 'react';
import ReactDOM from 'react-dom';

import BrowserRouter from 'react-router-dom/BrowserRouter'

import './css/index.css';
import './css/style.css';
import './css/feedback.css';
import './css/sign.css';

import App from './App';

ReactDOM.render(
    <React.StrictMode>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </React.StrictMode>,
    document.getElementById('root')
);

