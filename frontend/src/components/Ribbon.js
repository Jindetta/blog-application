import React,{Component} from "react";
import {connect} from "react-redux";

class Ribbon extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return "Ribbon";
  }
}

export default connect(data => data.ribbon)(Ribbon);