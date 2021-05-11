import React from 'react'

import {Redirect, Route, Switch} from 'react-router-dom'
import BrowserRouter from 'react-router-dom/BrowserRouter'

import {Header, FeedbackSuccess, SignUp} from "./components";
import {FeedbackPage, MainPage} from './pages/index'
import NewsPage from "./pages/NewsPage";
import NotFoundPage from "./components/NotFoundPage";

function App() {

    return (
        <BrowserRouter>
            <Header/>
            <Switch>
                <Route path='/' exact component={MainPage}/>
                <Route path='/feedback' exact component={FeedbackPage}/>
                <Route path='/feedbackSuccess' exact component={FeedbackSuccess}/>
                <Route path='/news' exact component={NewsPage}/>
                <Route path='/signUp' exact component={SignUp}/>
                <Route component={NotFoundPage}/>
            </Switch>
        </BrowserRouter>
    )
}

export default App;
