import EmberRouter from '@ember/routing/router';
import config from './config/environment';

const Router = EmberRouter.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('series',{ path: 'series/:series_id' }, function() {
    this.route('exercise', {path:'exercise/:exercise_id'}, function() {
      this.route('task', {path:'task/:task_id'});
    })
  });
});

export default Router;
