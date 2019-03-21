import React, {Component} from "react";
import {connect} from "react-redux";

import * as actions from '../actions/ArticleActions';

import {TreeTable} from 'primereact/treetable';
import {Column} from 'primereact/column';

import './ArticleView.css';

class ArticleView extends Component {
  constructor(props) {
    super(props);

    this.getContent = this.getContent.bind(this);
    this.fetchUserData = this.fetchUserData.bind(this);

    let path = window.location.pathname;
    let pattern = /\/article\//g;
    this.number = path.replace(pattern, "");
  }

  componentWillMount() {
    fetch(`http://localhost:8080/blogs/${this.number}`)
      .then(response => response.json())
      .then(data => this.props.dispatch(actions.setBlogData(data)))
      .then(() => this.fetchUserData());
  }

  fetchUserData() {
    if(this.props.BLOG_DATA) {
      fetch(`http://localhost:8080/users/${this.props.BLOG_DATA.author}`)
        .then(response => response.json())
        .then(data => this.props.dispatch(actions.setAuthorData(data)));
    }
  }

  getContent() {
    const blogData = this.props.BLOG_DATA;
    const authorData = this.props.AUTHOR_DATA;

    if(blogData != null) {
      console.log(blogData);
      if(authorData != null) {
        return (
          <div>
            <h1>{blogData.title + " / " + authorData.firstName + " " + authorData.lastName}</h1>
            <h3>{blogData.content}</h3>
          </div>
        );
      } else {
        return(
          <div>
            <h1>{blogData.title + " fetching author..."}</h1>
            <h3>{blogData.content}</h3>
          </div>
        );
      }
    } else {
      return(
        <h1>Fetching data,,,,</h1>
      );
    }
  }

  render() {
    return (
        <div className="p-grid">
          <div className="p-col-12" id="page">
              {this.getContent()}
          </div>
        </div>
    );
  }
/*
  componentDidMount() {
    let url = window.location.origin;

    fetch(`${url}/blogs`).then(response => response.json())
      .then(data => this.setState({posts: data})).catch(error => console.log(error));
  }

  render() {
    return (
      <div className="p-grid">
        <div className="p-col-12" id="page">
          <TreeTable responsive={true} value={this.state.posts.map(item => {
            return {key: item.id, data: {...item}};
          })}>
            <Column field="id" header="ID" />
            <Column field="date" header="Date" />
            <Column field="author" header="Author ID" />
            <Column field="title" header="Title" />
            <Column field="content" header="Content" />
          </TreeTable>
        </div>
      </div>
    );
  }*/
}

export default connect(data => data.article)(ArticleView);