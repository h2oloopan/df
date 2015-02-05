// Generated by CoffeeScript 1.8.0
define(['ehbs!templates/edit'], function() {
  var EditRoute;
  return EditRoute = {
    bind: function(App) {
      App.EditRoute = Ember.Route.extend({
        model: function() {
          return new Ember.RSVP.Promise(function(resolve, reject) {
            return new Ember.RSVP.hash({
              bots: Ember.$.getJSON('/bot/bots')
            }).then(function(result) {
              return resolve({
                bots: result.bots
              });
            }, function(errors) {
              return reject(errors);
            });
          });
        }
      });
      return App.EditController = Ember.ObjectController.extend;
    }
  };
});
