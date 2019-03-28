import React, { Component } from 'react';
import {connect} from "react-redux";

import './App.css';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

import ArticleView from "./components/ArticleView";
import SearchView from "./components/SearchView";
import WriteView from "./components/WriteView";

import Ribbon from './components/Ribbon';

class App extends Component {
  constructor(props) {
    super(props);

    this.getView = this.getView.bind(this);
  }

  getView() {
    switch (this.props.ACTIVE_MENU) {
      case 1: {
        return <WriteView/>
      }
      case 2: {
        return <SearchView/>;
      }
      default : {
        return <ArticleView/>;
      }
    }
  }

  render() {
    return (
      <div>
        <div className="p-grid p-justify-center">
          <div className="p-col-10">
            <Ribbon/>
          </div>
        </div>
        <div className="p-grid p-justify-center">
          <div className="p-col-10">
            {this.getView()}
          </div>
        </div>
      </div>
    );
  }
}

export default connect(data => data.ribbon)(App);
