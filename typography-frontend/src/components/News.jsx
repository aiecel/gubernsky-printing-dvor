import React from 'react';
import axios from "axios";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {useHistory} from "react-router-dom";
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
        <>
            <RBS.Navbar className="navbar navbar-expand-xl navbar-light" collapseOnSelect={true} bg="light" expand="lg">
                <RBS.Navbar.Brand href="#home">Губернский Печатный Двор</RBS.Navbar.Brand>
                <RBS.Navbar.Toggle aria-controls="basicRBS.-navbar-nav" />
                <RBS.Navbar.Collapse id="basic-navbar-nav">
                    <RBS.Nav className="mr-auto">
                        <RBS.Nav.Link href="#home">Жалобная книга</RBS.Nav.Link>
                        <RBS.Nav.Link href="#link">Новости</RBS.Nav.Link>
                        <RBS.NavDropdown title="Dropdown" id="basic-nav-dropdown">
                            <RBS.NavDropdown.Item >Action</RBS.NavDropdown.Item>
                            <RBS.NavDropdown.Item href="#action/3.2">Another action</RBS.NavDropdown.Item>
                            <RBS.NavDropdown.Item href="#action/3.3">Something</RBS.NavDropdown.Item>
                            <RBS.NavDropdown.Divider />
                            <RBS.NavDropdown.Item href="#action/3.4">Separated link</RBS.NavDropdown.Item>
                        </RBS.NavDropdown>
                    </RBS.Nav>
                    <RBS.Form inline>
                        <RBS.FormControl type="text" placeholder="Search" className="mr-sm-2" />
                        <RBS.Button variant="outline-success">Search</RBS.Button>
                    </RBS.Form>
                </RBS.Navbar.Collapse>
            </RBS.Navbar>
        </>
    )
}

export default News;
