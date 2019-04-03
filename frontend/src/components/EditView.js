import React,{Component} from "react";

import {InputText} from "primereact/inputtext";
import {Editor} from "primereact/editor";
import {Button} from "primereact/button";

import './EditView.css';
import {connect} from "react-redux";

class EditView extends Component {
  constructor(props) {
    super(props);

    this.state = {title: "", content: "", author: ""};

    this.fetchAllData = this.fetchAllData.bind(this);
    this.fetchUserData = this.fetchUserData.bind(this);
    this.renderHeader = this.renderHeader.bind(this);
    this.editData = this.editData.bind(this);

    if (process.env.NODE_ENV === "development") {
      this.fetchUrl = "http://localhost:8080";
    } else {
      this.fetchUrl = window.location.origin;
    }
  }

  renderHeader() {
    return (
      <span className="ql-formats">
      </span>
    );
  }

  componentDidMount() {
    this.fetchAllData();
  }

  fetchAllData() {
    fetch(`${this.fetchUrl}/blogs/${this.props.match.params.id}`)
      .then(response => response.json())
      .then(data => {
        this.setState({title: data.title, content: data.content, id: data.id, authorId: data.author_id})
      })
      .then(() => this.fetchUserData());
  }

  fetchUserData() {
    fetch(`${this.fetchUrl}/users/${this.state.authorId}`)
      .then(response => response.json())
      .then(data => this.setState({author: data.username}));
  }

  editData() {
    let data = new FormData();
    data.append("id", this.state.id);
    data.append("newTitle", this.state.title);
    data.append("newContent", this.state.content);

    let url = this.fetchUrl+"/blogs/edit/"+this.state.id;

    fetch(url, {
      method:"POST",
      credentials:"include",
      body: data
    }).then(response => window.location.href = `/#/articles`)
      .catch(error => console.log(error));
  }

  render() {
    if(this.props.permits === "ADMIN") {
      return (
        <div id="page">
          <div className="p-grid p-justify-center">
            <div className="p-col-12 p-md-12">
              <h3>Title</h3>
            </div>
            <div className="p-col-12 p-md-12">
              <div className="p-inputgroup">
                <InputText placeholder="Title" value={this.state.title}
                           onChange={e => this.setState({title: e.target.value})}/>
              </div>
            </div>
            <div className="p-col-12 p-md-12">
              <h3>Author</h3>
              <p>{this.state.author}</p>
            </div>
            <div className="p-col-12 p-md-12">
              <h3>Content</h3>
            </div>
            <div className="p-col-12 p-md-12">
              <Editor headerTemplate={this.renderHeader()} style={{height: '250pt'}} value={this.state.content}
                      onTextChange={(e) => this.setState({content: e.textValue})}/>
            </div>
            <div className="p-col-12 p-md-12">
              <Button label="Edit" icon="pi pi-pencil" onClick={() => this.editData()}/>
            </div>
          </div>
        </div>
      );
    } else {
      return (
        <div id="page">
          <div className="p-grid p-justify-center">
            <h3>You don't have required permissions</h3>
          </div>
        </div>
      );
    }
  }
}

export default connect(data => data.global)(EditView);
