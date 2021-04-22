import React from 'react'

import {Link, NavLink} from "react-router-dom";

const Header = () => {


    const links = [
        {linkTo: '/feedback', name: 'Жалобная книга', className: 'nav-link'},
        {linkTo: '/news', name: 'Новости', className: 'nav-link'},
        {linkTo: '/userCabinet', name: 'Кабинет', className: 'nav-link hidden nav-right'},
        {linkTo: '/signUp', name: 'Регистрация', className: 'nav-link hidden nav-right'}
    ]

    const [activeItem, setActiveItem] = React.useState(null);

    const onSelectedItem = (index) => {
        setActiveItem(index)
    }

    return (
        <nav className="navbar navbar-expand-xl navbar-light">
            <Link  to={'/'}><a className='navbar-brand'>Губернский печатный двор</a></Link>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target=".navbar-collapse"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"/>
            </button>
            <div className="navbar-collapse collapse " id="navbarNav">
                <ul className='navbar-nav'>
                    {links && links.map(({name, linkTo, className}, index) => (
                        <li className='nav-item'>
                            <NavLink to={linkTo} activeClassName='active'>
                                <a key={`${name}_${index}`} onClick={() => onSelectedItem(index)}
                                   className={activeItem === index && className !== 'nav-link hidden nav-right' ? 'nav-link' : className}>
                                    {name}</a>
                            </NavLink>
                        </li>)
                    )}
                </ul>
            </div>
            <div className='navbar-collapse collapse  justify-content-end'>
                <ul className='nav navbar-nav'>
                    {links && links.map(({name, linkTo, className}, index) => (
                        className === 'nav-link hidden nav-right' ?
                            <li className='nav-item'>
                                <NavLink to={linkTo} activeClassName='active'>
                                    <a key={`${name}_${index}`} onClick={() => onSelectedItem(index)}
                                       className={activeItem === index ? 'nav-link nav-right' : 'nav-link nav-right'}>{name}</a>
                                </NavLink>
                            </li> : '')
                    )}
                </ul>
            </div>
        </nav>
    )
}

export default Header;