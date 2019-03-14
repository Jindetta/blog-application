import {combineReducers}from 'redux';

import article from './ArticleReducer';
import user from './UserReducer';
import ribbon from './RibbonReducer';

export default combineReducers({
  article, user, ribbon
});