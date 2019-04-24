import React, {Component} from "react";
import {connect} from "react-redux";

import {Panel} from 'primereact/panel';

import * as actions from '../actions/ArticleActions';
import Loader from './Loader';

import './BlogView.css';
import {Button} from "primereact/button";
import {Menu} from "primereact/menu";

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
          <div className="p-col-10 p-col-align-center">
            <span>
              {this.renderAdminButtons(data)}
            </span>
            <span>
              <h1><a href={"/#/articles/" + data.id}>{data.title} <i className="pi pi-chevron-right header-icon"></i></a></h1>
            </span>
          </div>
        </div>
        <p>{data.content}</p>
      </div>
    );
  }

  renderAdminButtons(data) {
    if(this.props.role === "ADMIN") {
      const menu =
        [
          {
            label: "Admin options",
            items: [
              {
                label: "Delete",
                icon: "pi pi-times",
                command: e => this.deleteArticle(data.id)
              },
              {
                label: "Edit",
                icon: "pi pi-pencil",
                command: e => window.location.href = `/#/edit/` + data.id
              }
            ]
          }
        ]

      let temp = {}

      return <>
        <Menu popup={true} model={menu} ref={el => temp = el}/>
        <Button icon="pi pi-bars" className="p-button-danger" onClick={(event) => temp.toggle(event)}/>
      </>
    } else {
      return ""
    }
  }

  deleteArticle(articleId) {
    const url = this.fetchUrl + "/blogs/" + articleId;
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
