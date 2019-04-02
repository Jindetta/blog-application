import React, {Component} from "react";
import {ProgressSpinner} from 'primereact/progressspinner';

import './Loader.css';

export default class Loader extends Component {
  render() {
    return (
      <div className="loader">
        <ProgressSpinner {...this.props} />
        <p>{this.props.text}</p>
      </div>
    );
  }
}
