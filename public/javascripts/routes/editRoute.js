// Generated by CoffeeScript 1.8.0
define(['utils', 'ehbs!templates/edit'], function(u) {
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
      return App.EditController = Ember.ObjectController.extend({
        bot: null,
        grammar: null,
        aiml: null,
        grammars: [],
        aimls: [],
        botChanged: (function() {
          var thiz;
          thiz = this;
          Ember.$.getJSON('/bot/grammars?bot=' + this.get('bot')).then(function(result) {
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
            thiz.set('grammars', a);
            if (a.length > 0) {
              return thiz.set('grammar', a[0]);
            }
          }, function(errors) {
            return thiz.set('grammars', []);
          });
          return Ember.$.getJSON('/bot/aimls?bot=' + this.get('bot')).then(function(result) {
            var b, key, keys, value, _i, _len;
            b = [];
            keys = u.keys(result);
            for (_i = 0, _len = keys.length; _i < _len; _i++) {
              key = keys[_i];
              value = result[key];
              b.push({
                name: key,
                path: value
              });
            }
            thiz.set('aimls', b);
            if (b.length > 0) {
              return thiz.set('aiml', b[0]);
            }
          }, function(errors) {
            return thiz.set('aimls', []);
          });
        }).observes('bot'),
        grammarChanged: (function() {
          var path, thiz;
          thiz = this;
          path = this.get('grammar.path');
          if (path == null) {
            return;
          }
          return Ember.$.getJSON('/edit/file?encoding=GB18030&path=' + path).then(function(result) {
            return thiz.set('fileGrammar', result);
          }, function(errors) {
            return thiz.set('fileGrammar', errors.responseText);
          });
        }).observes('grammar'),
        aimlChanged: (function() {
          var path, thiz;
          thiz = this;
          path = this.get('aiml.path');
          if (path == null) {
            return;
          }
          return Ember.$.getJSON('/edit/file?encoding=UTF-8&path=' + path).then(function(result) {
            return thiz.set('fileAIML', result);
          }, function(errors) {
            return thiz.set('fileAIML', errors.responseText);
          });
        }).observes('aiml'),
        actions: {
          saveGrammar: function(grammar, file) {
            $.ajax({
              url: '/edit/upload',
              type: 'POST',
              data: JSON.stringify({
                path: grammar.path,
                encoding: 'GB18030',
                text: file
              }),
              dataType: 'json',
              contentType: 'application/json; charset=gb18030'
            }).done(function(result) {
              return alert('Grammar saved to server successfully!');
            }).fail(function(response) {
              return alert(response.responseText);
            });
            return false;
          },
          saveAIML: function(aiml, file) {
            $.ajax({
              url: '/edit/upload',
              type: 'POST',
              data: JSON.stringify({
                path: aiml.path,
                encoding: 'UTF-8',
                text: file
              }),
              dataType: 'json',
              contentType: 'application/json; charset=utf-8'
            }).done(function(result) {
              return alert('AIML saved to server successfully!');
            }).fail(function(response) {
              return alert(response.responseText);
            });
            return false;
          },
          compile: function(bot) {
            var thiz;
            thiz = this;
            $.ajax({
              url: '/bot/compile',
              type: 'POST',
              data: JSON.stringify({
                bot: bot
              }),
              dataType: 'json',
              contentType: 'application/json; charset=utf-8'
            }).done(function(result) {
              return alert('Grammar compilation done for bot ' + bot);
            }).fail(function(response) {
              return alert('Grammar compilation failed for bot ' + bot + ' ' + response.responseText);
            });
            return false;
          },
          reload: function(bot) {
            var thiz;
            thiz = this;
            $.ajax({
              url: '/bot/reload',
              type: 'POST',
              data: JSON.stringify({
                bot: bot
              }),
              dataType: 'json',
              contentType: 'application/json; charset=utf-8'
            }).done(function(result) {
              alert('Bot reloaded successfully for ' + bot);
              return true;
            }).fail(function(response) {
              return false;
            });
            return false;
          },
          addGrammar: function() {
            var file;
            file = prompt('Grammar File Name (No Extension)', 'filename');
            return false;
          },
          addAIML: function() {
            var file;
            file = prompt('AIML File Name (No Extension)', 'filename');
            return false;
          }
        }
      });
    }
  };
});
