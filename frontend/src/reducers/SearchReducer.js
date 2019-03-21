const DEFAULT_STATE = {
  SEARCH_VALUE: '',
  POSTS: ''
};

export default (state = DEFAULT_STATE, action) => {
  return {...state, [action.type]: action.payload};
}
