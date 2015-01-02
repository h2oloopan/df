define ['routes/testRoute', 'ehbs!templates/index'], (TestRoute) ->
	return app =
		start: ->
			App = Ember.Application.create()
			App.Router.map ->
				@route 'test'
			TestRoute.bind App

			App.IndexRoute = Ember.Route.extend
				model: ->
					return { question: null, uid: null, display: '' }

			App.IndexController = Ember.ObjectController.extend
				actions:
					talk: ->
						thiz = @
						uid = @get 'uid'
						question = @get 'question'
						$.ajax
							url: '/bot/talk'
							type: 'POST'
							data: JSON.stringify
								bot: 'dummy'
								uid: uid
								query: question
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							display = thiz.get 'display'
							display += '[HUMAN] ' + thiz.get('question') + '\r\n'
							display += '[BOT] ' + result.text + '\r\n'
							thiz.set 'display', display
							thiz.set 'question', null
							#scroll
							setTimeout ->
								$('textarea').scrollTop $('textarea')[0].scrollHeight
							, 300
						.fail (response) ->
							console.log response

						return false