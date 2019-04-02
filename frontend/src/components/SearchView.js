import React,{Component} from "react";

import {connect} from "react-redux";

import './SearchView.css';
import {Button} from "primereact/button";
import {InputText} from "primereact/inputtext";
import * as actions from '../actions/SearchActions';
import {Accordion, AccordionTab} from "primereact/accordion";

class SearchView extends Component{
  constructor(props) {
    super(props);
    this.textChanged = this.textChanged.bind(this);
    this.searchClicked = this.searchClicked.bind(this);
    this.createPostsList = this.createPostsList.bind(this);
    this.createAccordionTab = this.createAccordionTab.bind(this);
  }

  textChanged(event) {
    this.props.dispatch(actions.setSearchValue(event.target.value))
  }

  searchClicked() {
    const url = window.location.origin;
    fetch(`http://localhost:8080/blogs/search/${this.props.SEARCH_VALUE}`).then(response => response.json())
      .then(data => this.props.dispatch(actions.setPosts(data)))
  }

  createAccordionTab(post) {
    return (
      <AccordionTab header={post.title + " / " + post.date} key={post.id}>
        {post.content}
        <br/><br/>
        <Button label="Go to post" onClick={() => window.location.href = window.location.origin+"/#/articles/"+post.id}/>
      </AccordionTab>
    );
  }

  createPostsList() {
    if(this.props.POSTS.length > 0) {
      const len = this.props.POSTS.length;
      return (
        <div>
          <p>Found {len} {len <= 1? 'post':'posts'}</p>
          <Accordion multiple={true}>
            {this.props.POSTS.map(post => this.createAccordionTab(post))}
          </Accordion>
        </div>
      );
    } else {
      return <p>No posts found</p>
    }
  }

  render() {
    return (
      <div className="p-grid">
        <div className="p-col-12" id="page">
          <div className="p-col-4">
            <h3>Search</h3>
            <Button label="Search" icon="pi pi-fw pi-search" onClick={this.searchClicked}/>
            <InputText placeholder="Keyword" value={this.props.SEARCH_VALUE} onChange={this.textChanged}/>
          </div>
          <div className="p-col-12" id="results">
            {this.createPostsList()}
          </div>
        </div>
      </div>
    );
  }
}

export default connect(data => data.search)(SearchView)