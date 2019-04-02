import {combineReducers}from 'redux';

import article from './ArticleReducer';
import user from './UserReducer';
import ribbon from './RibbonReducer';
import search from './SearchReducer';
import blog from './BlogReducer';

export default combineReducers({
  article, user, ribbon, search, blog
});