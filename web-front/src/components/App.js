import React, { useState } from 'react';
import {Sidebar} from './Sidebar';

export function App({ initialData }) {
  const profile = {
    "name": "Nuthan Kumar",
    "designation": "Developer",
    "avatar": "https://pickaface.net/gallery/avatar/unr_sample_161118_2054_ynlrg.png"
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
  return (
    <div>
      <Sidebar props = { {"profile": profile, "menuList": menuList }}></Sidebar>
    </div>
  );
}
