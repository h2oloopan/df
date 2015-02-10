define ['routes/testRoute', 'routes/editRoute', 'ehbs!templates/index'], (TestRoute, EditRoute) ->
	return app =
		start: ->
			App = Ember.Application.create()
			App.Router.map ->
				@route 'test'
				@route 'edit'
			TestRoute.bind App
			EditRoute.bind App

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
						display += message + '\r\n'
						@set 'display', display
						setTimeout ->
							$('textarea').scrollTop $('textarea')[0].scrollHeight
						, 300
					talk: ->
						thiz = @
						bot = @get 'bot'
						uid = @get 'uid'
						question = @get 'question'
						$.ajax
							url: '/bot/talk'
							type: 'POST'
							data: JSON.stringify
								bot: bot
								uid: uid
								query: question
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							message = '[BOT] ' + result.text
							thiz.send 'update', message
						.fail (response) ->
							thiz.send 'update', '[SYSTEM] ' + response.responseText
						thiz.set 'question', null
						message = '[HUMAN] ' + question
						@send 'update', message
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
							thiz.send 'update', '[SYSTEM] Grammar compilation done for bot ' + bot
						.fail (response) ->
							thiz.send 'update', '[SYSTEM] Grammar compilation failed for bot ' + bot + ' ' + response.responseText
						return false
					reload: ->
						thiz = @
						bot = @get 'bot'
						$.ajax
							url: '/bot/reload'
							type: 'POST'
							data: JSON.stringify
								bot: bot
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							thiz.send 'update', '[SYSTEM] Bot reloaded successfully for ' + bot
							return true
						.fail (response) ->
							#thiz.send 'update', '[SYSTEM] Bot reloading failed for bot ' + bot + ' ' + response.responseText
							return false
						return false