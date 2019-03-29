import React,{Component} from "react";

import {InputText} from "primereact/inputtext";
import {Editor} from "primereact/editor";
import {Button} from "primereact/button";

import './WriteView.css';

class WriteView extends Component {
  constructor(props) {
    super(props);

    this.state = {title: "", content: ""};

    this.renderHeader = this.renderHeader.bind(this);
    this.postData = this.postData.bind(this);
  }

  renderHeader() {
    return (
      <span className="ql-formats">
          <button className="ql-bold" aria-label="Bold"></button>
          <button className="ql-italic" aria-label="Italic"></button>
          <button className="ql-underline" aria-label="Underline"></button>
      </span>
    );
  }

  postData(url = '') {
    let data = new FormData();
    data.append("title", this.state.title);
    data.append("content", this.state.content);
    data.append("author", 1);
    //TODO Change author to be logged user
    fetch(url, {
      method:"POST",
      mode: "cors",
      credentials:"omit",
      body: data
    }).then(response => console.log(response))
      .catch(error => console.log(error));
  }

  render() {
    return (
      <div id="page">
        <div className="p-grid p-justify-center">
          <div className="p-col-12 p-md-12">
            <h3>Title</h3>
          </div>
          <div className="p-col-12 p-md-12">
            <div className="p-inputgroup">
              <InputText placeholder="Title" value={this.state.title} onChange={e => this.setState({title: e.target.value})}/>
            </div>
          </div>
          <div className="p-col-12 p-md-12">
            <h3>Content</h3>
          </div>
          <div className="p-col-12 p-md-12">
            <Editor headerTemplate={this.renderHeader()} style={{height:'250pt'}} value={this.state.content} onTextChange={(e)=>this.setState({content:e.textValue})}/>
          </div>
          <div className="p-col-12 p-md-12">
            <Button label="Clear" icon="pi pi-times" onClick={() => this.setState({content:''})}/>
            <Button label="Post" icon="pi pi-pencil" onClick={() => this.postData("http://localhost:8080/blogs")}/>
          </div>
        </div>
      </div>
    );
  }
}

export default WriteView;