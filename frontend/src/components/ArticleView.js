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
    this.fetchAllData = this.fetchAllData.bind(this);
    this.fetchUserData = this.fetchUserData.bind(this);

    if (process.env.NODE_ENV === "development") {
      this.fetchUrl = "http://localhost:8080";
    } else {
      this.fetchUrl = window.location.origin;
    }
  }

  componentWillMount() {
    this.fetchAllData();
  }

  fetchAllData() {
    fetch(`${this.fetchUrl}/blogs/${this.props.match.params.id}`)
      .then(response => response.json())
      .then(data => this.props.dispatch(actions.setBlogData(data)))
      .then(() => {this.fetchUserData(); this.fetchCommentData()});
  }

  fetchCommentData() {
    fetch(`${this.fetchUrl}/blogs/${this.props.match.params.id}/comments`)
      .then(response => response.json())
      .then(data => this.props.dispatch(actions.setCommentData(data)))
  }

  fetchUserData() {
    fetch(`${this.fetchUrl}/users/${this.props.BLOG_DATA.author_id}`)
      .then(response => response.json())
      .then(data => {
        console.log(data)
        this.props.dispatch(actions.setAuthorData(data))
      });
  }

  renderArticle(data) {
    const error = ArticleView.getErrorMessage(data);

    if (error) {
      return error;
    }

    return (
      <div key={data.id} className="article">
        <h1>{data.title}</h1>
        <p>{this.props.AUTHOR_DATA?this.props.AUTHOR_DATA.firstName + " " + this.props.AUTHOR_DATA.lastName:"author"}</p>
        <p>{data.content}</p>
        <div>
          {this.props.COMMENT_DATA?this.props.COMMENT_DATA.map(comment => <p className="comment">{comment.comment}</p>): "No comments"}
        </div>
      </div>
    );
  }

  getContent() {
    const blogData = this.props.BLOG_DATA;

    if(blogData != null) {
      return this.renderArticle(blogData);
    }

    return <Loader text="Fetching data..." />;
  }

  componentDidUpdate(previousProps) {
    const currentSearch = this.props.match.params.id
    const previousSearch = previousProps.match.params.id
    if (currentSearch !== previousSearch) {
      this.fetchAllData()
    }
  }

  render() {
    return <div id="page">
      <div>
        {this.getContent()}
      </div>
      <div>
        {}
      </div>
    </div>;
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
