const DEFAULT_STATE = {
  permits: "ANONYMOUS"
}

export default (state = DEFAULT_STATE, action) => {
  return {...state, [action.type]: action.payload};
}