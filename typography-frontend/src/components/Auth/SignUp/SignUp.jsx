import React from 'react';
import { Link, useHistory } from 'react-router-dom';
import {
  ErrorMessage, Field, Form, Formik
} from 'formik';
import axios from 'axios';

const SignUp = () => {
  const history = useHistory();

  return (
    <div className="sign-container">
      <div className="sign-header"><p>Регистрация</p></div>
      <Formik
        initialValues={{ username: '', password: '', confirmPassword: '', displayName: '' }}
        validate={(values) => {
          const errors = {};
          if (!values.username) {
              errors.username = 'Не бывает такого, чтоб имени не было у вас!';
          }
          if (values.username.length <= 3) {
              errors.username = 'Какое то невнятное имя... Попробуйте что-то поменять ';
          }
          if (!values.displayName) {
              errors.displayName = 'Не бывает такого, чтоб имени не было у вас!';
          }
          if (values.displayName.length <= 3) {
              errors.displayName = 'Какое то невнятное имя... Попробуйте что-то поменять ';
          }
          if (!values.password) {
              errors.password = 'Без пароля никуда — введи же его скорее !';
          }
          if (values.password.length <= 3) {
              errors.password = 'Коротковат будет! Придумайте что-то понадежнее';
          }
          if (!values.confirmPassword) {
              errors.confirmPassword = 'Без пароля никуда — введи же его скорее !';
          }
          if (values.confirmPassword.length <= 3) {
            errors.confirmPassword = 'Коротковат будет! Придумайте что-то понадежнее';
          }
          if ((values.password.length && values.confirmPassword.length) && values.password !== values.confirmPassword) {
              errors.confirmPassword = 'Что-то не сходится! Протрите как очки да впечатайте всё повнимательнее'
          }
          if (!values.password && values.confirmPassword) {
              errors.confirmPassword = 'Куда ж вы вперёд паровоза! Сначала пароль, потом повтор...'
          }
          return errors;
        }}
        onSubmit={(values) => {
          axios.post('http://localhost:8080/users/register', values)
            .then((response) => {
              console.log(response);
            }, (err) => {
              console.log(err);
            }, history.push('/'));
        }}
      >
        {() => (
          <Form>
            <div className="form-group">
              <div>
                <label style={{ float: 'left' }} htmlFor="exampleInputEmail1">
                  Ваше имя
                </label>
              </div>
              <Field size="50" type="text" name="username" />
              <ErrorMessage style={{ color: 'red', display: 'flex' }} name="username" component="div" />
            </div>
            <div className="form-group">
              <div>
                <label style={{ float: 'left' }} htmlFor="exampleInputEmail1">Ваше отображаемое имя</label>
              </div>
              <Field size="50" type="text" name="displayName" />
              <ErrorMessage style={{ color: 'red', display: 'flex' }} name="displayName" component="p" />
            </div>
              <div className="form-group">
                  <div>
                      <label style={{ float: 'left' }} htmlFor="exampleInputEmail1">Ваш потаённый код</label>
                  </div>
                  <Field size="50" type="password" name="password" />
                  <ErrorMessage style={{ color: 'red', display: 'flex' }} name="password" component="p" />
              </div>
              <div className="form-group">
                  <div>
                      <label style={{ float: 'left' }} htmlFor="exampleInputEmail1">Тот же потаённый код, но ещё раз</label>
                  </div>
                  <Field size="50" type="password" name="confirmPassword" />
                  <ErrorMessage style={{ color: 'red', display: 'flex' }} name="confirmPassword" component="p" />
              </div>
            <button className="btn btn-light" type="submit">
              Закрепить данныя в книге постояльцев
            </button>
            <div style={{ paddingTop: '10px' }}>
              <Link to="/">
                <p className="nav-link">
                  Уже постоялец
                  двора?
                </p>
              </Link>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default SignUp;
