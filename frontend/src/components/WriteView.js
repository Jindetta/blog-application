import React,{Component} from "react";

import {InputText} from "primereact/inputtext";
import {Editor} from "primereact/editor";
import {Button} from "primereact/button";

import './WriteView.css';
import {connect} from "react-redux";

class WriteView extends Component {
  constructor(props) {
    super(props);

    this.state = {title: "", content: ""};

    this.postData = this.postData.bind(this);
    this.fetchUrl = `${window.location.origin}/api`
  }

  postData(url = '') {
    let data = new FormData();
    data.append("title", this.state.title);
    data.append("content", this.state.content);
    //TODO Change author to be logged user
    fetch(url, {
      method:"POST",
      credentials:"include",
      body: data
    }).then(response => response.redirect("/#/articles"))
      .catch(error => console.log(error));
  }

  render() {
    console.log("WRITE",this.props.role);
    if(this.props.role === "ADMIN")
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
              <Editor headerTemplate={<p></p>} style={{height:'250pt'}} value={this.state.content} onTextChange={(e)=>this.setState({content:e.textValue})}/>
            </div>
            <div className="p-col-12 p-md-12">
              <Button label="Clear" icon="pi pi-times" onClick={() => this.setState({content:''})}/>
              <Button label="Post" icon="pi pi-pencil" onClick={() => this.postData(this.fetchUrl)}/>
            </div>
          </div>
        </div>
      );
    else {
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

export default connect(data => data.global)(WriteView);
