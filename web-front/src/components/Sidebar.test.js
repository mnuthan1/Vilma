import React from 'react';
import { Command, Menu, MenuList, Sidebar } from './Sidebar';

import renderer from 'react-test-renderer';

/**
 * jtest case for react component Command
 */
describe('Command', () => {
  it('renders correctly', () => {
    const tree = renderer
      .create(<Command cmd={{"action":"#",
      "displayName":"Home",
      "icon": "fa-th-large"}} />)
      .toJSON();
    expect(tree).toMatchSnapshot();
  });
});

/**
 * jtest case for react component Menu
 */
describe('Menu', () => {
  const menu = {"displayName": "Main",
  "commands": [
    {"action":"#",
     "displayName":"Home",
     "icon": "fa-th-large"},
     {"action":"#",
     "displayName":"About",
     "icon": "fa-address-card"},
     {"action":"#",
     "displayName":"Services",
     "icon": "fa-cubes"},
     {"action":"#",
     "displayName":"Gallery",
     "icon": "fa-picture-o"}
  ]}

  it('renders correctly', () => {
    const tree = renderer
      .create(<Menu menu={menu} />)
      .toJSON();
    expect(tree).toMatchSnapshot();
  });
});


/**
 * jtest case for react component MenuList
 */
describe('MenuList', () => {
  const menuList = [
    {"displayName": "Main",
     "commands": [
       {"action":"#",
        "displayName":"Home",
        "icon": "fa-th-large"},
        {"action":"#",
        "displayName":"About",
        "icon": "fa-address-card"},
        {"action":"#",
        "displayName":"Services",
        "icon": "fa-cubes"},
        {"action":"#",
        "displayName":"Gallery",
        "icon": "fa-picture-o"}
     ]},
    {"displayName": "Charts",
  "commands": [
    {"action":"#",
        "displayName":"Bar Chart",
        "icon": "fa-bar-chart"},
        {"action":"#",
        "displayName":"Pie Chart",
        "icon": "fa-pie-chart"}
  ]}
  ]

  it('renders correctly', () => {
    const tree = renderer
      .create(<MenuList menuList={menuList} />)
      .toJSON();
    expect(tree).toMatchSnapshot();
  });
});

/**
 * jtest case for react component MenuList
 */
describe('Sidebar', () => {
  const profile = {
    "name": "Nuthan Kumar",
    "designation": "Developer",
    "avatar": "https://test.com"
  }

  const menuList = [
    {"displayName": "Main",
     "commands": [
       {"action":"#",
        "displayName":"Home",
        "icon": "fa-th-large"},
        {"action":"#",
        "displayName":"About",
        "icon": "fa-address-card"},
        {"action":"#",
        "displayName":"Services",
        "icon": "fa-cubes"},
        {"action":"#",
        "displayName":"Gallery",
        "icon": "fa-picture-o"}
     ]},
    {"displayName": "Charts",
  "commands": [
    {"action":"#",
        "displayName":"Bar Chart",
        "icon": "fa-bar-chart"},
        {"action":"#",
        "displayName":"Pie Chart",
        "icon": "fa-pie-chart"}
  ]}
  ]

  it('renders correctly', () => {
    const tree = renderer
      .create(<Sidebar props = { {"profile": profile, "menuList": menuList }}></Sidebar>)
      .toJSON();
    expect(tree).toMatchSnapshot();
  });
});
