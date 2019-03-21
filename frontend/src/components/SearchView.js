import React,{Component} from "react";

import {connect} from "react-redux";

class SearchView extends Component{
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <p>Hello world!</p>
    );
  }
}

export default connect(data => data.article)(SearchView)