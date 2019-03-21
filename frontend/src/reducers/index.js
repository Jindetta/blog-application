import {combineReducers}from 'redux';

import article from './ArticleReducer';
import user from './UserReducer';
import ribbon from './RibbonReducer';
import search from './SearchReducer';

export default combineReducers({
  article, user, ribbon, search
});