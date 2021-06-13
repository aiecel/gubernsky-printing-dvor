import React from 'react';
import axios from "axios";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Link, useHistory} from "react-router-dom";
import {LinkContainer} from 'react-router-bootstrap'
import * as RBS from 'react-bootstrap'


const News = () => {
    const API = 'https://api.vk.com/api.php?oauth=1&method=wall.get&domain=guberniatypography&access_token=dbc5280adbc5280adbc5280a71dbb25dfaddbc5dbc5280abbb924254c515ee9029b4f01&v=5.130'

    const getWall = () => {
        axios.get(API, {
            headers: {"Access-Control-Allow-Origin": "*"}
        })
            .then(data => console.log(data))
    }

    return (
        <></>
    )
}

export default News;
