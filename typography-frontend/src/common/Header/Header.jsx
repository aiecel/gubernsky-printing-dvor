import React from 'react';
import * as RBS from 'react-bootstrap';
import { LinkContainer } from 'react-router-bootstrap';

const Header = ({ cabinetTitle, signTitle, cabinetLink, signLink }) => (
  <>
    <RBS.Navbar className="navbar navbar-expand-xl navbar-light" collapseOnSelect bg="white" expand="lg">
      <LinkContainer to="/"><RBS.Navbar.Brand>Губернский Печатный Двор</RBS.Navbar.Brand></LinkContainer>
      <RBS.Navbar.Toggle aria-controls="basicRBS.-navbar-nav" />
      <RBS.Navbar.Collapse id="basic-navbar-nav">
        <RBS.Nav activeKey="/" className="mr-auto">
          <LinkContainer to="/feedback"><RBS.Nav.Link>Жалобная книга</RBS.Nav.Link></LinkContainer>
          <LinkContainer to="/news"><RBS.Nav.Link>Новости</RBS.Nav.Link></LinkContainer>
        </RBS.Nav>
        <RBS.Nav activeKey="/">
          <LinkContainer to={cabinetLink}><RBS.NavLink>{cabinetTitle}</RBS.NavLink></LinkContainer>
          <LinkContainer to={signLink}><RBS.NavLink>{signTitle}</RBS.NavLink></LinkContainer>
        </RBS.Nav>
      </RBS.Navbar.Collapse>
    </RBS.Navbar>
  </>
);

export default Header;
