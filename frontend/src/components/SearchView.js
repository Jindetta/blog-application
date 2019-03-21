import React,{Component} from "react";

import {connect} from "react-redux";

import './SearchView.css';

class SearchView extends Component{
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="p-grid">
        <div className="p-col-12" id="page">
          <p>Hello world!</p>
        </div>
      </div>
    );
  }
}

export default connect(data => data.article)(SearchView)