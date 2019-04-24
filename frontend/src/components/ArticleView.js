import React, {Component} from "react";
import {connect} from "react-redux";

import {Panel} from 'primereact/panel';

import * as actions from '../actions/ArticleActions';
import Loader from './Loader';

import './ArticleView.css';
import {Editor} from "primereact/editor";
import {Button} from "primereact/button";
import {Menu} from "primereact/menu";

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
    this.renderComment = this.renderComment.bind(this);
    this.renderCommentAdminButton = this.renderCommentAdminButton.bind(this);
    this.renderArticleAdminButtons = this.renderArticleAdminButtons.bind(this);
    this.renderCommentLike = this.renderCommentLike.bind(this);
    this.createAdminButtonItems = this.createAdminButtonItems.bind(this);
    this.deleteComment = this.deleteComment.bind(this);
    this.deleteArticle = this.deleteArticle.bind(this);

    this.state = {comment: ""}
    this.fetchUrl = `${window.location.origin}/api`;
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
        <div className="p-grid">
          <div className="p-col-12 p-sm-11">
            <h1>{data.title}</h1>
          </div>
          <div className="p-col-12 p-sm-1 p-col-align-center ">
            {this.renderArticleAdminButtons()}
          </div>
        </div>
        <p><b>Author: </b>{this.props.AUTHOR_DATA?this.props.AUTHOR_DATA.username:"-"}</p>
        <p><b>Date: </b>{this.props.BLOG_DATA.date?this.props.BLOG_DATA.date:"-"}</p>
          <p>{data.content}</p>
        <div>
          {this.props.COMMENT_DATA?this.props.COMMENT_DATA.map(comment => this.renderComment(comment)): "No comments"}
        </div>
      </div>
    );
  }

  createAdminButtonItems() {
    return [
      {
        label: 'Options',
        items: [
          {
            label: "Edit", icon: "pi pi-pencil",
            command: e => window.location.href = `/#/edit/`+this.props.BLOG_DATA.id
          },
          {
            label: "Delete", icon: "pi pi-times",
            command: (e) => this.deleteArticle()
          },
        ]
      }
    ]
  }

  deleteArticle() {
    const url = window.location.origin + this.props.BLOG_DATA.link;
    fetch(url,{
      method:"DELETE",
      credentials:"include",
    }).then(res => console.log(res))
      .then(() => window.location.href = '/#/articles');
  }

  renderArticleAdminButtons() {
    if(this.props.role === "ADMIN") {
      return <>
          <Menu model={this.createAdminButtonItems()} popup={true} ref={el => this.menu = el}/>
          <Button icon="pi pi-bars" className="p-button-danger" onClick={(event) => this.menu.toggle(event)}/>
        </>
    } else {
      return ""
    }
  }

  renderComment(data) {
    console.log(data)
      return <div className="p-grid">
            <div className="p-col-1  p-col-align-center">
              <div className="p-grid">
                <div className="p-col p-md-12 p-lg-4">
                  <b className="like">{data.likes}</b>
                </div>
                <div className="p-col p-md-12 p-lg-4">
                  {this.renderCommentLike(data)}
                </div>
                <div className="p-col p-md-12 p-lg-4">
                  {this.renderCommentAdminButton(data.comment.link, data.comment.author_id)}
                </div>
              </div>
          </div>
          <div className="p-col-11 p-col-align-center">
            <div className="comment">
            <p>{data.comment.comment}</p>
          </div>
          </div>
        </div>
  }

  dislike(likeUrl) {
    const url = this.fetchUrl + likeUrl;
    fetch(url, {
      method: "DELETE",
      credentials: "include",
    }).then(response => console.log(response))
      .then(() => window.location.reload())
  }

  like(likeUrl) {
    const url = this.fetchUrl + likeUrl;
    fetch(url, {
      method: "POST",
      credentials: "include",
    }).then(response => console.log(response))
      .then(() => window.location.reload())
  }

  renderCommentAdminButton(commentUrl, authorId) {
    if(this.props.role === "ADMIN" || this.props.userId === authorId) {
      return <Button icon="pi pi-times" className="p-button-danger" onClick={() => this.deleteComment(commentUrl)}/>
    } else {
      return ""
    }
  }


  renderCommentLike(data) {
    if(this.props.role == "ADMIN" || this.props.role == "USER") {
      if (data.hasLiked) {
        return <Button icon="pi pi-chevron-up" className="p-button-primary" onClick={() => this.dislike(data.link)}/>
      } else {
        return <Button icon="pi pi-chevron-up" className="p-button-secondary" onClick={() => this.like(data.link)}/>
      }
    }
  }

  deleteComment(commentUrl) {
    const url = this.fetchUrl + commentUrl;
    fetch(url, {
      method: "DELETE",
      credentials: "include",
    }).then(response => console.log(response))
      .then(() => window.location.reload())
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
      {this.props.role === "ADMIN" || this.props.role === "USER"?this.renderCommenting():""}
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
