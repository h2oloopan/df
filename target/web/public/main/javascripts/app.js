// Generated by CoffeeScript 1.8.0
define(['routes/testRoute', 'ehbs!templates/index'], function(TestRoute) {
  var app;
  return app = {
    start: function() {
      var App;
      App = Ember.Application.create();
      App.Router.map(function() {
        return this.route('test');
      });
      TestRoute.bind(App);
      App.IndexRoute = Ember.Route.extend({
        model: function() {
          return {
            question: null,
            uid: null,
            display: ''
          };
        }
      });
      return App.IndexController = Ember.ObjectController.extend({
        actions: {
          talk: function() {
            var question, thiz, uid;
            thiz = this;
            uid = this.get('uid');
            question = this.get('question');
            $.ajax({
              url: '/bot/talk',
              type: 'POST',
              data: JSON.stringify({
                bot: 'dummy',
                uid: uid,
                query: question
              }),
              dataType: 'json',
              contentType: 'application/json; charset=utf-8'
            }).done(function(result) {
              var display;
              display = thiz.get('display');
              display += '[HUMAN] ' + thiz.get('question') + '\r\n';
              display += '[BOT] ' + result.answer + '\r\n';
              thiz.set('display', display);
              thiz.set('question', null);
              return setTimeout(function() {
                return $('textarea').scrollTop($('textarea')[0].scrollHeight);
              }, 300);
            }).fail(function(response) {
              return console.log(response);
            });
            return false;
          }
        }
      });
    }
  };
});
