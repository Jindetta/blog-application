import React, { Component } from 'react';

import './App.css';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';

import ArticleView from "./components/ArticleView";
import SearchView from "./components/SearchView";

class App extends Component {
  render() {
    return (
      <div className="p-grid p-justify-center">
        <div className="p-col-10">
          <SearchView/>
        </div>
      </div>
    );
  }
}

export default App;
