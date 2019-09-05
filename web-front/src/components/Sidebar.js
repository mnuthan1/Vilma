import React, { useState } from 'react';

export function Sidebar({ props }) {
  return (
    <div className="vertical-nav bg-white" id="sidebar">
      <Profile profile={props.profile} />
      <MenuList menuList={props.menuList} />
    </div>
  );
}

export function Profile({ profile }) {
  return (
    <div className="py-4 px-3 mb-4 bg-light">
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
    </div>
  )
}

export function MenuList({ menuList }) {
  return (
    menuList.map( (menu, i) => {
      return <Menu menu={menu} key={i}/>;
    })
  )
}
export function Menu({ menu }) {
  const commandList = menu.commands;
  const listItems = menu.commands.map((cmd, i) =>
    <Command cmd={cmd} key={i}/>
  );
  return (
    <div>
      <p className="text-gray font-weight-bold text-uppercase px-3 small pb-4 mb-0">{menu.displayName}</p>
      <ul className="nav flex-column bg-white mb-0">
        {listItems}
      </ul>
    </div>
  )
}

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