import React, { useState } from 'react';

import {logout} from '../services/user.service'

/**
 * Main page Sidebar
 * @param {object} props Component props
 * @visibleName sidebar
 */
export function Sidebar({ props }) {
  return (
    <div className="vertical-nav bg-white" id="sidebar">
      <LogoHeader />
      <Profile profile={props.profile} />
      <MenuList menuList={props.menuList} />
    </div>
  );
}

/**
 * Logo Header
 * @param {object} props Component props
 * @visibleName Sidebar header with logo
 */
export function LogoHeader() {
  return (
    <nav className="navbar navbar-light bg-info">
      <a className="navbar-brand" href="#">
        <span className="button"> 
          <img width="30" className="mr-3"  src = "https://www.designevo.com/res/templates/thumb_small/green-and-blue-circle.png"/>
        </span>
        Navbar
      </a>
    </nav>
  )
}
/**
 * User profile details
 * @param {object} props Component props
 * @param {object} props.profile user details
 * @visibleName user details
 */
export function Profile({ profile }) {
  return (
    <div className="py-1 px-3 mb-3 bg-light">
      <div className="media d-flex align-items-center">
        <img
          src={profile.avatar} alt="..."
          width="65" className="mr-3 rounded-circle img-thumbnail shadow-sm"
        />
        <div className="media-body">
          <h4 className="m-0">{profile.name}</h4>
          <p className="font-weight-light text-muted mb-0">{profile.designation}</p>
        </div>
      </div>
      <Logout />
    </div>
  )
}

/**
 * Logout
 * @param {object} props Component props
 * @visibleName Logout
 */

export function Logout() {
  return (
    <div className="w-100 row .text-gray" >
      <i className="fa mr-3 fa-fw fa-power-off" onClick={logout}></i>
    </div>
  )
}
/**
 * MenuList with list of menus
 * @param {object} props Component props
 * @param {object} props.menuList single menu details
 * @visibleName MenuList with list of menus
 */
export function MenuList({ menuList }) {
  return (
    menuList.map( (menu, i) => {
      return <Menu menu={menu} key={i}/>;
    })
  )
}

/**
 * Menu with list of commands
 * @param {object} props Component props
 * @param {object} props.menu single menu details
 * @visibleName Menu with list of commands
 */
export function Menu({ menu }) {
  const commandList = menu.commands;
  const listItems = menu.commands.map((cmd, i) =>
    <Command cmd={cmd} key={i}/>
  );
  return (
    <React.Fragment>
      <p className="text-gray font-weight-bold text-uppercase px-3 small pb-4 mb-0">{menu.displayName}</p>
      <ul className="nav flex-column bg-white mb-0">
        {listItems}
      </ul>
      </React.Fragment>
  )
}

/**
 * Command for Menu item
 * @param {object} props Component props
 * @param {object} props.cmd single command details
 * @visibleName The Best Menu Item (Command) Ever 🐙
 */
export function Command({ cmd }) {
  const className = "fa mr-3 text-primary fa-fw " + cmd.icon;
  return (
    <li className="nav-item">
      <a href={cmd.action} className="nav-link text-dark font-italic bg-light">
        <i className={className}></i>
        {cmd.displayName}
      </a>
    </li>
  )
}