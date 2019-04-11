import React, { Component } from 'react';
import {connect} from "react-redux";

import {HashRouter, Route, Switch} from 'react-router-dom';

import './App.css';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

import ArticleView from "./components/ArticleView";
import SearchView from "./components/SearchView";
import WriteView from "./components/WriteView";
import BlogView from "./components/BlogView";
import EditView from "./components/EditView";
import Ribbon from './components/Ribbon';

import * as actions from './actions';

class App extends Component {
  render() {
    return (
      <HashRouter>
        <div>
          <div className="p-grid p-justify-center">
            <div className="p-col-10">
              <Ribbon permits={this.props.permits}/>
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

  componentDidMount() {
    let url = window.location.origin + "/permits";
    fetch(url).then(response => response.json()).then(data => {
      this.props.dispatch(actions.setPermits(data.permit))
      this.props.dispatch(actions.setUserId(data.userId))
    })
  }
}

export default connect(data => data.global)(App);
