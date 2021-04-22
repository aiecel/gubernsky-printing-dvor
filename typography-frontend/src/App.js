import React from 'react'

import {Route} from 'react-router-dom'
import BrowserRouter from 'react-router-dom/BrowserRouter'

import {Header, FeedbackSuccess, SignUp} from "./components";
import {FeedbackPage, MainPage} from './pages/index'
import NewsPage from "./pages/NewsPage";

function App() {

    return (
        <BrowserRouter>
                <Header/>
                <Route exact path='/' component={MainPage}/>
                <Route exact path='/feedback' component={FeedbackPage}/>
                <Route exact path='/feedbackSuccess' component={FeedbackSuccess}/>
                <Route exact path='/news' component={NewsPage}/>
                <Route exact path='/signUp' component={SignUp}/>
        </BrowserRouter>
    )
}

export default App;
