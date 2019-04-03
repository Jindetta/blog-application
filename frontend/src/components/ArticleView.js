import React, {Component} from "react";
import {connect} from "react-redux";

import {Panel} from 'primereact/panel';

import * as actions from '../actions/ArticleActions';
import Loader from './Loader';

import './ArticleView.css';
import {Editor} from "primereact/editor";
import {Button} from "primereact/button";

class ArticleView extends Component {
  constructor(props) {
    super(props);

    this.getContent = this.getContent.bind(this);
    this.fetchUserData = this.fetchUserData.bind(this);
    this.renderArticle = this.renderArticle.bind(this);
    this.fetchAllData = this.fetchAllData.bind(this);
    this.fetchUserData = this.fetchUserData.bind(this);
    this.postComment = this.postComment.bind(this);
    this.renderCommenting = this.renderCommenting.bind(this);

    this.state = {comment: ""}
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
        this.props.dispatch(actions.setAuthorData(data))
      });
  }

  postComment() {
    if(this.state.comment) {
      const url = this.fetchUrl + "/blogs/comments";
      let data = new FormData();
      data.append("comment", this.state.comment);
      data.append("articleId",this.props.match.params.id);
      fetch(url, {
        method: "POST",
        credentials: "include",
        body: data
      }).then(response => console.log(response))
        .then(() => window.location.reload())
    }
  }

  renderArticle(data) {
    const error = ArticleView.getErrorMessage(data);

    if (error) {
      return error;
    }

    return (
      <div key={data.id} className="article">
        <h1>{data.title}</h1>
        <p>{this.props.AUTHOR_DATA?this.props.AUTHOR_DATA.username:"author"}</p>
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

  renderCommenting() {
    return <div id="comment-section">
      <div>
        <Editor headerTemplate={<b>Comment</b>} style={{height:'80pt'}} value={this.state.comment} onTextChange={(e)=>this.setState({comment:e.textValue})}/>
      </div>
      <div>
        <Button label="Comment" id="comment-button" onClick={() => this.postComment()}></Button>
      </div>
    </div>
  }

  render() {
    return <div id="page">
      <div>
        {this.getContent()}
      </div>
      {this.props.permits === "ADMIN" || this.props.permits === "USER"?this.renderCommenting():""}
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
