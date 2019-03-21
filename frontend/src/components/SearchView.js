import React,{Component} from "react";

import {connect} from "react-redux";

import './SearchView.css';
import {Button} from "primereact/button";
import {InputText} from "primereact/inputtext";
import * as actions from '../actions/SearchActions';

class SearchView extends Component{
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="p-grid">
        <div className="p-col-12" id="page">
          <div className="p-col-4">
            <h3>Search</h3>
            <Button label="Search" icon="pi pi-fw pi-search"/>
            <InputText placeholder="Keyword"/>
          </div>
          <div className="p-col-12" id="results">
            <p>Search results here</p>
          </div>
        </div>
      </div>
    );
  }
}

export default connect(data => data.search)(SearchView)