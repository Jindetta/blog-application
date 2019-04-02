export function setSearchValue(value) {
  return {
    type: "SEARCH_VALUE",
    payload: value
  }
}

export function setPosts(value) {
  return {
    type: "POSTS",
    payload: value
  }
}
