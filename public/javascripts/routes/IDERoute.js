// Generated by CoffeeScript 1.8.0
define(['utils', 'ace/ace', 'ehbs!templates/IDE'], function(u, ace) {
  var IDERoute;
  return IDERoute = {
    bind: function(App) {
      App.IDERoute = Ember.Route.extend({});
      App.IDEController = Ember.ObjectController.extend({});
      return App.IDEView = Ember.View.extend({
        didInsertElement: function() {
          var editor;
          this._super();
          editor = ace.edit('editor');
          return $('#editor')[0].style.fontSize = '14px';
        }
      });
    }
  };
});
