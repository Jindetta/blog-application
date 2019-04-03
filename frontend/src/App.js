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
import EditView from "./components/EditView";

import  "./components/BlogView";

import Ribbon from './components/Ribbon';

import {HashRouter, Route, Switch} from 'react-router-dom';
import BlogView from "./components/BlogView";

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
                <Route path="/articles/:id" component={ArticleView}/>
                <Route path="/write" component={WriteView}/>
                <Route path="/edit/:id" component={EditView}/>
                <Route path="/search" component={SearchView}/>
                <Route path="/" component={BlogView}/>
              </Switch>
            </div>
          </div>
        </div>
      </HashRouter>
    );
  }
}

export default connect(data => data.ribbon)(App);
