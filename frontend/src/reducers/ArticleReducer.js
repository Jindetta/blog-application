const DEFAULT_STATE = {
  BLOG_DATA: null,
  AUTHOR_DATA: null,
  COMMENT_DATA: null
};

export default (state = DEFAULT_STATE, action) => {
  return {...state, [action.type]: action.payload};
}
