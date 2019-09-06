import React, { useState } from 'react';

/**
 * Main Content
 * @param {object} props Component props
 * @visibleName content page
 */
export function Content({ props }) {
    return (
        <div className="page-content" id="content">
            <MainHeader />
        </div>
    );
}

/**
 * Main Header
 * @param {object} props Component props
 * @visibleName Main Header
 */
export function MainHeader() {
    return (
      <nav className="navbar navbar-light bg-info">
        <a className="navbar-brand" href="#">
            <Toggle />
        </a>
      </nav>
    )
  }

/**
 * Toggle sidebar
 * @param {object} props Component props
 * @visibleName content page
 */
export function Toggle({ props }) {
    const onClickToggle = () => {
        $('#sidebarCollapse').on('click', function () {
            $('#sidebar, #content').toggleClass('active');
        });
    }
    return (
        <button id="sidebarCollapse" onClick={onClickToggle} type="button" className="btn btn-light bg-white shadow-sm px-1 py-1 mb-1">
            <i className="fa fa-bars"></i>
        </button>
    );
}