import React from 'react';

import axios from 'axios';
import { useHistory } from 'react-router-dom';
import {
  ErrorMessage, Field, Form, Formik
} from 'formik';

const FeedbackContainer = () => {
  const history = useHistory();
  return (
    <section>
      <div className="feedback-container">
        <div className="feedback-header"><p>Жалобная Книга</p></div>
        <article className="feedback-article">
          <p>
            Есть предложения, замечания или пожелания к Печатному
            Двору?
            <br />
            Дак не ленись и распиши всё поподробнее!
          </p>
        </article>
        <div className="form-group shadow-textarea">
          <Formik
            initialValues={{ text: '' }}
            validate={(values) => {
              const errors = {};
              if (!values.text) {
                errors.text = 'Хотя бы буковку напишите!';
              }
              return errors;
            }}
            onSubmit={(values, actions) => {
              axios.post('http://localhost:8080/feedbacks', {
                data: values,
                time: new Date().toLocaleString()
              })
                .then((response) => {
                  console.log(response);
                }, (err) => {
                  console.log(err);
                });
              actions.resetForm();
              setTimeout(() => {
                history.push('feedbackSuccess');
              }, 1000);
            }}
          >
            {() => (
              <Form>
                <div className="form-group">
                  <ErrorMessage style={{ color: 'red', display: 'flex' }} name="text" component="div" />
                  <Field
                    as="textarea" size="50" type="text" maxLength="5000" aria-label="Large"
                    cols="40"
                    className="form-control" name="text"
                    placeholder="Пишите..."
                  />
                </div>
                <button className="btn btn-light" type="submit">
                  Отослать!
                </button>
              </Form>
            )}
          </Formik>
        </div>
      </div>
    </section>
  );
};

export default FeedbackContainer;
