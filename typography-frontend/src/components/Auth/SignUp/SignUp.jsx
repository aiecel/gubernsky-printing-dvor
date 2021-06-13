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
        initialValues={{ email: '', password: '' }}
        validate={(values) => {
          const errors = {};
          if (!values.email) {
            errors.email = 'Не бывает такого, чтоб ящика почтового не было у вас!';
          } else if (
            !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(values.email)
          ) {
            errors.email = 'Какой то неправильный адрес ящика... Попробуйте еще';
          }

          if (!values.password) {
            errors.password = 'Без пароля никуда — введи же его скорее !';
          } else if (values.password.length <= 3) {
            errors.password = 'Коротковат будет! Придумайте что-то понадежнее';
          }
          return errors;
        }}
        onSubmit={(values) => {
          axios.post('http://localhost:3001/pizzas', {
            data: values,
            time: new Date().toLocaleString()
          })
            .then((response) => {
              console.log(response);
            }, (err) => {
              console.log(err);
            }, history.push('feedbackSuccess'));
        }}
      >
        {() => (
          <Form>
            <div className="form-group">
              <div>
                <label style={{ float: 'left' }} htmlFor="exampleInputEmail1">
                  Адрес почтового
                  ящика
                </label>
              </div>
              <Field size="50" type="email" name="email" />
              <ErrorMessage style={{ color: 'red', display: 'flex' }} name="email" component="div" />
            </div>
            <div className="form-group">
              <div>
                <label style={{ float: 'left' }} htmlFor="exampleInputEmail1">Ваш потаённый код</label>
              </div>
              <Field size="50" type="password" name="password" />
              <ErrorMessage style={{ color: 'red', display: 'flex' }} name="password" component="p" />
            </div>
            <button className="btn btn-light" type="submit">
              Зарегистрироваться
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
