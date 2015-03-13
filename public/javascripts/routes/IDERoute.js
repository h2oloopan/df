// Generated by CoffeeScript 1.8.0
define(['utils', 'ace/ace', 'ehbs!templates/IDE'], function(u, ace) {
  var IDERoute;
  return IDERoute = {
    bind: function(App) {
      App.IDERoute = Ember.Route.extend({
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
      App.IDEController = Ember.ObjectController.extend({
        types: ['Grammar', 'AIML'],
        selectionChanged: (function() {
          var bot, thiz, type, url;
          thiz = this;
          bot = this.get('bot');
          type = this.get('type');
          if ((bot == null) || (type == null)) {
            return false;
          }
          switch (type.toLowerCase()) {
            case 'grammar':
              url = '/edit/folders?type=grammar&bot=' + bot;
              break;
            case 'aiml':
              url = '/edit/folders?type=aiml&bot=' + bot;
              break;
          }
          return Ember.$.getJSON(url).then(function(result) {
            var a, key, keys, value, _i, _len;
            a = [];
            keys = u.keys(result);
            for (_i = 0, _len = keys.length; _i < _len; _i++) {
              key = keys[_i];
              value = result[key];
              a.push({
                name: key,
                path: value
              });
            }
            thiz.set('folders', a);
            if (a.length > 0) {
              return thiz.set('folder', a[0]);
            }
          }, function(errors) {
            return alert(errors.responseText);
          });
        }).observes('bot', 'type'),
        folderChanged: (function() {
          var folder, thiz, url;
          thiz = this;
          folder = this.get('folder');
          if (folder == null) {
            return false;
          }
          url = '/edit/files?folder=' + folder.path;
          return Ember.$.getJSON(url).then(function(result) {
            var a, key, keys, value, _i, _len;
            a = [];
            keys = u.keys(result);
            for (_i = 0, _len = keys.length; _i < _len; _i++) {
              key = keys[_i];
              value = result[key];
              a.push({
                name: key,
                path: value
              });
            }
            thiz.set('files', a);
            if (a.length > 0) {
              return thiz.set('file', a[0]);
            }
          }, function(errors) {
            return alert(errors.responseText);
          });
        }).observes('folder')
      });
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
