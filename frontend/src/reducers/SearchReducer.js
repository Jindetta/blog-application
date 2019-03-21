const DEFAULT_STATE = {
  SEARCH_VALUE: ''
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case 'SEARCH_VALUE': {
      console.log(action);

      state = {...state, SEARCH_VALUE: action.payload};
      break;
    }
  }
  return state;
}
