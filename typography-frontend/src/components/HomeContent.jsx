import React from 'react'

import {Link} from "react-router-dom";

import machine from '../img/machine.jpg'
import '../css/index.css'

const HomeContent = () => {
    return (
        <section className="section-title">
            <p className="section-title-header">Современное решение <br/> проблем насущных</p>
            <img className="section-title-picture" src={machine} alt="machine"/>
            <Link to={'/userCabinet'}>
            <a>Отпечатать документ &gt; </a>
            </Link>
        </section>
    );
}

export default HomeContent;

