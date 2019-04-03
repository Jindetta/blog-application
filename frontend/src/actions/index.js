export function setPermits(value) {
  console.log(value)
  return {
   type: 'permits',
   payload: value
  }
}