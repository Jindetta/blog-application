import React,{Component} from "react";

import {connect} from "react-redux";

import {TabMenu} from 'primereact/tabmenu';

import * as actions from '../actions/RibbonActions';

class Ribbon extends Component {
  constructor(props) {
    super(props);

    this.ribbonItems = this.ribbonItems.bind(this);
    this.clickMenuTab = this.clickMenuTab.bind(this);
  }

  clickMenuTab(event) {
    window.location = `/#/${event.value.label.toLowerCase()}`
  }

  ribbonItems() {
    return [
      {index: 0, label: 'Articles', icon: 'pi pi-fw pi-home'},
      {index: 1, label: 'Write', icon: 'pi pi-fw pi-pencil'},
      {index: 2, label: 'Search', icon: 'pi pi-fw pi-search'},
      {index: 3, label: 'Profile', icon: 'pi pi-fw pi-user'},
    ];
  }

  render() {
    const items = this.ribbonItems();
    return <TabMenu model={items}  activeItem={items[this.props.ACTIVE_MENU]} onTabChange={this.clickMenuTab}/>;
  }
}

export default connect(data => data.ribbon)(Ribbon);