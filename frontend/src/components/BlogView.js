import React, {Component} from "react";
import {connect} from "react-redux";

import {Panel} from 'primereact/panel';

import * as actions from '../actions/ArticleActions';
import Loader from './Loader';

import './ArticleView.css';
import {Button} from "primereact/button";

class BlogView extends Component {
  constructor(props) {
    super(props);

    this.getContent = this.getContent.bind(this);
    this.renderArticle = this.renderArticle.bind(this);
    this.deleteArticle = this.deleteArticle.bind(this);
    this.fetchData = this.fetchData.bind(this);
    this.renderAdminButtons = this.renderAdminButtons.bind(this);

    this.fetchUrl = `${window.location.origin}/api`;
  }

  componentWillMount() {
    this.fetchData();
  }

  fetchData() {
    fetch(`${this.fetchUrl}/blogs/`)
      .then(response => response.json())
      .then(data => this.props.dispatch(actions.setBlogData(data)));
  }

  renderArticle(data) {
    const error = BlogView.getErrorMessage(data);

    if (error) {
      return error;
    }

    return (
      <div key={data.id}>
        <div className="p-grid p-align-center ">
          <div className="p-col-9">
            <h1>{data.title}</h1>
          </div>
          <div className="p-col-3">
            <Button icon="pi pi-arrow-right" className="p-button-secondary" onClick={e => window.location = `/#/articles/${data.id}`}/>
            {this.props.role === "ADMIN"? this.renderAdminButtons(data):""}
          </div>
        </div>
        <p>{data.content}</p>
      </div>
    );
  }

  renderAdminButtons(data) {
    return <>
        <Button icon="pi pi-times" className="p-button-danger" onClick={e => this.deleteArticle(data.id)}/>
        <Button icon="pi pi-pencil" className="p-button" onClick={e => window.location.href = `/#/edit/`+data.id}/>
      </>
  }

  deleteArticle(articleId) {
    const url = window.location.origin + "/blogs/" + articleId;
    console.log(url)
    fetch(url,{
      method:"DELETE",
      credentials:"include",
    }).then(res => console.log(res))
      .then(this.fetchData)
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
    return <div className="p-col-12" id="page">{this.getContent()}</div>;
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

export default connect(data => data.blog)(BlogView);
