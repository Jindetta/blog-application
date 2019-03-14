const DEFAULT_STATE = {
  ACTIVE_MENU: 0
};

export default (state = DEFAULT_STATE, action) => {
  switch (action.type) {
    case 'ACTIVE_MENU': {
      state = {...state, ACTIVE_MENU: action.payload};
      break;
    }
  }
  return state;
}
