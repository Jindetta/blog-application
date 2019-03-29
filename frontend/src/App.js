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

import {HashRouter, Route, Switch} from 'react-router-dom';

class App extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <HashRouter>
        <div>
          <div className="p-grid p-justify-center">
            <div className="p-col-10">
              <Ribbon/>
            </div>
          </div>
          <div className="p-grid p-justify-center">
            <div className="p-col-10">
              <Switch>
                <Route path="/write" component={WriteView}/>
                <Route path="/search" component={SearchView}/>
                <Route path="/" component={ArticleView}/>
              </Switch>
            </div>
          </div>
        </div>
      </HashRouter>
    );
  }
}

export default connect(data => data.ribbon)(App);
