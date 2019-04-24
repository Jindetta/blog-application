const DEFAULT_STATE = {
  role: "ANONYMOUS",
  userId: -1
}

export default (state = DEFAULT_STATE, action) => {
  return {...state, [action.type]: action.payload};
}