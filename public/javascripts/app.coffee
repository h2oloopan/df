define ['routes/testRoute', 'ehbs!templates/index'], (TestRoute) ->
	return app =
		start: ->
			App = Ember.Application.create()
			App.Router.map ->
				@route 'test'
			TestRoute.bind App

			App.IndexRoute = Ember.Route.extend
				model: ->
					return new Ember.RSVP.Promise (resolve, reject) ->
						new Ember.RSVP.hash
							bots: Ember.$.getJSON('/bot/bots')
						.then (result) ->
							resolve
								question: null
								uid: 'TEST-USER-001'
								display: ''
								bots: result.bots
						, (errors) ->
							reject errors

			App.IndexController = Ember.ObjectController.extend
				actions:
					update: (message) ->
						display = @get 'display'
						display += '[SYSTEM] ' + message + '\r\n'
						@set 'display', display
						setTimeout ->
							$('textarea').scrollTop $('textarea')[0].scrollHeight
						, 300
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
					compile: ->
						thiz = @
						bot = @get 'bot'
						$.ajax
							url: '/bot/compile'
							type: 'POST'
							data: JSON.stringify
								bot: bot
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							thiz.send 'update', 'Grammar compilation done for bot ' + bot
						.fail (response) ->
							thiz.send 'update', 'Grammar compilation failed for bot ' + response.responseText
						return false