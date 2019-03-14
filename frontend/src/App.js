import React, { Component } from 'react';

import './App.css';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';

import Ribbon from "./components/Ribbon";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Ribbon/>
      </div>
    );
  }
}

export default App;
