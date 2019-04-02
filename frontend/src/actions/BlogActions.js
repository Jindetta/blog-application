export function setBlogData(value) {
  return {
    type: "BLOG_DATA",
    payload: value
  }
}

export function setAuthorData(value) {
  return {
    type: "AUTHOR_DATA",
    payload: value
  }
}
