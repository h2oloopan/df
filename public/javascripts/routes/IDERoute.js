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
        helpAIML: false,
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
        }).observes('folder'),
        fileChanged: (function() {
          var encoding, file, thiz, type, url;
          thiz = this;
          file = this.get('file');
          type = this.get('type');
          encoding = 'UTF-8';
          if (file == null) {
            return false;
          }
          if (type.toLowerCase() === 'grammar') {
            encoding = 'GB18030';
          }
          url = '/edit/file?path=' + file.path + '&encoding=' + encoding;
          return Ember.$.getJSON(url).then(function(result) {
            var editor;
            editor = thiz.get('editor');
            editor.setValue(result);
            editor.gotoLine(1);
            return thiz.send('help', type);
          }, function(errors) {
            return alert(errors.responseText);
          });
        }).observes('file'),
        actions: {
          addFolder: function() {
            return false;
          },
          addFile: function() {
            var bot, extension, name, thiz, type;
            thiz = this;
            bot = this.get('bot');
            type = this.get('type').toLowerCase();
            extension = '.gram';
            if (type === 'aiml') {
              extension = '.aiml';
            }
            name = prompt('File Name (Without Extension)', 'Filename');
            if (name == null) {
              return false;
            }
            $.ajax({
              url: '/edit/create',
              type: 'POST',
              data: JSON.stringify({
                bot: bot,
                type: type,
                name: name,
                text: ''
              }),
              contentType: 'application/json; charset=utf-8'
            }).done(function(result) {
              var files, fresh;
              files = thiz.get('files');
              fresh = {
                name: name + extension,
                path: result
              };
              files.pushObject(fresh);
              thiz.set('file', fresh);
              return true;
            }).fail(function(response) {
              alert('Adding definition file failed ' + response.responseText);
              return false;
            });
            return false;
          },
          deleteFile: function() {
            var answer, file, thiz;
            thiz = this;
            file = this.get('file');
            answer = confirm('Do you want to delete file ' + file.name + '?');
            if (!answer) {
              return false;
            }
            $.ajax({
              url: '/edit/remove?path=' + file.path,
              type: 'DELETE'
            }).done(function(result) {
              var files;
              files = thiz.get('files');
              files.removeObject(file);
              if (files.length > 0) {
                thiz.set('file', files[0]);
              }
              return true;
            }).fail(function(response) {
              alert('Deleting file failed ' + response.responseText);
              return false;
            });
            return false;
          },
          popover: function(term, file, path) {
            var thiz, url;
            thiz = this;
            term = term.substr(term.indexOf('.') + 1);
            url = '/edit/file?encoding=GB18030&path=' + path;
            Ember.$.getJSON(url).then(function(result) {
              thiz.set('helper', {
                title: file,
                body: result.replace(/\n/igm, '<br/><br/>').replace(term, '<span style="color:red;">' + term + '</span>')
              });
              return $('.modal').modal('toggle');
            }, function(errors) {
              console.log(errors);
              return false;
            });
            return false;
          },
          help: function(type) {
            var bot, search, thiz, url;
            thiz = this;
            search = function(text) {
              var list, match, matches, pattern, term, _i, _len;
              pattern = /<grammar>[^<]+<\/grammar>/igm;
              matches = text.match(pattern);
              list = [];
              for (_i = 0, _len = matches.length; _i < _len; _i++) {
                match = matches[_i];
                term = match.substr(match.indexOf('>') + 1);
                term = term.substr(0, term.lastIndexOf('<'));
                term = term.replace('\r', '').replace('\n', '').trim();
                list.push(term);
              }
              return list;
            };
            if (type.toLowerCase() === 'aiml') {
              bot = this.get('bot');
              url = '/edit/map?bot=' + bot;
              Ember.$.getJSON(url).then(function(result) {
                var editor, helpList, item, list, obj, path, _i, _len;
                editor = thiz.get('editor');
                list = search(editor.getValue());
                helpList = [];
                for (_i = 0, _len = list.length; _i < _len; _i++) {
                  item = list[_i];
                  path = result[item];
                  obj = {
                    id: path.substr(path.lastIndexOf('/') + 1).replace('.', '-'),
                    term: item,
                    file: path.substr(path.lastIndexOf('/') + 1),
                    path: path
                  };
                  helpList.push(obj);
                }
                thiz.set('helpList', helpList);
                thiz.set('helpAIML', true);
                return true;
              }, function(errors) {
                console.log(errors);
                return false;
              });
            } else {
              this.set('helpAIML', false);
            }
            return false;
          },
          compile: function() {
            var bot;
            bot = this.get('bot');
            if (bot == null) {
              return false;
            }
            $.ajax({
              url: '/bot/compile',
              type: 'POST',
              data: JSON.stringify({
                bot: bot
              }),
              contentType: 'application/json; charset=utf-8'
            }).done(function(result) {
              $.ajax({
                url: '/edit/reloadmap',
                type: 'POST',
                data: JSON.stringify({
                  bot: bot
                }),
                contentType: 'application/json; charset=utf-8'
              }).done(function(result) {
                return true;
              }).fail(function(response) {
                return false;
              });
              return alert('Grammar compilation done for bot ' + bot);
            }).fail(function(response) {
              return alert('Grammar compilation failed for bot ' + bot + ' ' + response.responseText);
            });
            return false;
          },
          reload: function() {
            var bot;
            bot = this.get('bot');
            if (bot == null) {
              return false;
            }
            $.ajax({
              url: '/bot/reload',
              type: 'POST',
              data: JSON.stringify({
                bot: bot
              }),
              contentType: 'application/json; charset=utf-8'
            }).done(function(result) {
              return alert('Bot reloaded successfully for bot ' + bot);
            }).fail(function(response) {
              return alert('Bot reloading failed for bot ' + bot + ' ' + response.responseText);
            });
            return false;
          },
          save: function() {
            var editor, encoding, file, thiz, type;
            thiz = this;
            file = this.get('file');
            type = this.get('type');
            encoding = 'UTF-8';
            if (file == null) {
              return false;
            }
            if (type.toLowerCase() === 'grammar') {
              encoding = 'GB18030';
            }
            editor = this.get('editor');
            $.ajax({
              url: '/edit/upload',
              type: 'POST',
              data: JSON.stringify({
                path: file.path,
                encoding: encoding,
                text: editor.getValue()
              }),
              contentType: 'application/json; charset=' + encoding
            }).done(function(result) {
              alert('File ' + file.name + ' saved to server successfully');
              thiz.send('help', type);
              return true;
            }).fail(function(response) {
              alert(response.responseText);
              return false;
            });
            return false;
          },
          discard: function() {
            var file;
            file = this.get('file');
            this.set('file', null);
            this.set('file', file);
            return false;
          }
        }
      });
      return App.IDEView = Ember.View.extend({
        didInsertElement: function() {
          var editor;
          this._super();
          editor = ace.edit('editor');
          editor.setTheme('ace/theme/chrome');
          $('#editor')[0].style.fontSize = '14px';
          return this.set('controller.editor', editor);
        }
      });
    }
  };
});
