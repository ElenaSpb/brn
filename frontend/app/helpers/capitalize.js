import Ember from 'ember';

export function capitalize([ value ]) {
  value = value.string || value;
  return value && value[0].toUpperCase() + value.slice(1);
}

export default Ember.Helper.helper(capitalize);
