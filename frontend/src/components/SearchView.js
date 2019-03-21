import React,{Component} from "react";

import {connect} from "react-redux";

import './SearchView.css';
import {Button} from "primereact/button";
import {InputText} from "primereact/inputtext";
import * as actions from '../actions/SearchActions';

class SearchView extends Component{
  constructor(props) {
    super(props);
    this.textChanged = this.textChanged.bind(this);
  }

  textChanged(event) {
    console.log(event.target.value);
    this.props.dispatch(actions.setSearchValue(event.target.value))
  }

  render() {
    return (
      <div className="p-grid">
        <div className="p-col-12" id="page">
          <div className="p-col-4">
            <h3>Search</h3>
            <Button label="Search" icon="pi pi-fw pi-search" onClick={() => console.log(this.props.SEARCH_VALUE)}/>
            <InputText placeholder="Keyword" value={this.props.SEARCH_VALUE} onChange={this.textChanged}/>
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