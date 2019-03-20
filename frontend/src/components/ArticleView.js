import React, {Component} from "react";
import {connect} from "react-redux";

import {TreeTable} from 'primereact/treetable';
import {Column} from 'primereact/column';

class ArticleView extends Component {
  constructor(props) {
    super(props);

    this.state = {posts: []};
  }

  componentDidMount() {
    let url = window.location.origin;

    fetch(`${url}/blogs`).then(response => response.json())
      .then(data => this.setState({posts: data}));
  }

  render() {
    return (
      <TreeTable responsive={true} value={this.state.posts.map(item => {
        return {key: item.id, data: {...item}};
      })}>
        <Column field="id" header="ID" />
        <Column field="date" header="Date" />
        <Column field="author" header="Author ID" />
        <Column field="title" header="Title" />
        <Column field="content" header="Content" />
      </TreeTable>
    );
  }
}

export default connect(data => data.article)(ArticleView);