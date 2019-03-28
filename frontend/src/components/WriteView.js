import React,{Component} from "react";

import {InputText} from "primereact/inputtext";
import {Editor} from "primereact/editor";
import {Button} from "primereact/button";

class WriteView extends Component {
  constructor(props) {
    super(props);
    this.state = {title: "", content: ""};
    this.setState(this.state);

    this.renderHeader = this.renderHeader.bind(this);
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

  render() {
    console.log(this.state.title)
    return (
      <div id="page">
        <div className="p-grid p-justify-center">
          <div className="p-col-12 p-md-12">
            <h3>Title</h3>
          </div>
          <div className="p-col-12 p-md-12">
            <div className="p-inputgroup">
              <span className="p-inputgroup-addon">
                  <i className="pi pi-pencil"></i>
              </span>
              <InputText placeholder="Title" value={this.state.title} onChange={e => this.setState({title: e.target.value})}/>
            </div>
          </div>
          <div className="p-col-12 p-md-12">
            <h3>Content</h3>
          </div>
          <div className="p-col-12 p-md-12">
            <Editor headerTemplate={this.renderHeader()} style={{height:'250pt'}} value={this.state.content} onTextChange={(e)=>this.setState({content:e.textValue})}/>
            <Button label="Clear" icon="pi pi-times" onClick={() => this.setState({content:''})}/>
            <Button label="Post" icon="pi pi-pencil" onClick={() => console.log("POSTED " + this.state.title)}/>
          </div>
        </div>
      </div>
    );
  }
}

export default WriteView;