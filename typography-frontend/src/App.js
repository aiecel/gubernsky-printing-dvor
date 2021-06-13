import React, {useState} from 'react';

import { Route, Switch } from 'react-router-dom';

import { Header, FeedbackSuccess, SignUp } from './components';
import {
  FeedbackPage, MainPage, CabinetPage, NewsPage
} from './pages/index';
import { NotFoundPage } from './common';

function App() {
    const [isAuth, setIsAuth] = useState(false);
  return (
    <>
      <Header
          cabinetTitle={isAuth ? "Кабинет" : "Войти"}
          signTitle={isAuth ? "Выход" : "Регистрация"}
          cabinetLink={isAuth ? "/cabinet" : "/signIn"}
          signLink={isAuth ? "/logout" : "signUp"}
      />
      <Switch>
        <Route path="/" exact component={MainPage} />
        <Route path="/feedback" exact component={FeedbackPage} />
        <Route path="/feedbackSuccess" exact component={FeedbackSuccess} />
        <Route path="/news" exact component={NewsPage} />
        <Route path="/signUp" exact component={SignUp} />
        <Route path="/cabinet" exact component={CabinetPage} />
        <Route component={NotFoundPage} />
      </Switch>
    </>
  );
}

export default App;
