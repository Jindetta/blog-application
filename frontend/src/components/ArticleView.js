import React, {Component} from "react";
import {connect} from "react-redux";

import {Panel} from 'primereact/panel';

import * as actions from '../actions/ArticleActions';
import Loader from './Loader';

import './ArticleView.css';

class ArticleView extends Component {
  constructor(props) {
    super(props);

    this.getContent = this.getContent.bind(this);
    this.fetchUserData = this.fetchUserData.bind(this);
    this.renderArticle = this.renderArticle.bind(this);

    let path = window.location.pathname;
    let pattern = /\/article\//g;
    this.number = path.replace(pattern, "");

    if (process.env.NODE_ENV === "development") {
      this.fetchUrl = "http://localhost:8080";
    } else {
      this.fetchUrl = window.location.origin;
    }
  }

  componentWillMount() {
    fetch(`${this.fetchUrl}/blogs/${this.number}`)
      .then(response => response.json())
      .then(data => this.props.dispatch(actions.setBlogData(data)))
      .then(() => this.fetchUserData());
  }

  fetchUserData(userData) {
    if(userData) {
      return this.props.AUTHOR_DATA.filter(item => item.id === userData.id);
    } else {
      fetch(`${this.fetchUrl}/users`)
        .then(response => response.json())
        .then(data => this.props.dispatch(actions.setAuthorData(data)));
    }
  }

  renderArticle(data) {
    const error = ArticleView.getErrorMessage(data);

    if (error) {
      return error;
    }

    /*if(authorData != null) {
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
    }*/

    return (
      <div key={data.id}>
        <h1>{data.title}</h1>
        <h3>{data.content}</h3>
      </div>
    );
  }

  getContent() {
    const blogData = this.props.BLOG_DATA;

    if(blogData != null) {
      if (Array.isArray(blogData)) {
        return blogData.map(blogEntry => this.renderArticle(blogEntry));
      } else {
        return this.renderArticle(blogData);
      }
    }

    return <Loader text="Fetching data..." />;
  }

  render() {
    console.log(this.props.BLOG_DATA);
    console.log(this.props.AUTHOR_DATA);

    return this.getContent();
  }

  static getErrorMessage(data) {
    if (typeof data === "object" && data["errorMessage"]) {
      return (
        <Panel header="Error" toggleable={true} className="error">
          <p>{data["errorMessage"]}</p>
        </Panel>
      );
    }

    return null;
  }
}

export default connect(data => data.article)(ArticleView);
