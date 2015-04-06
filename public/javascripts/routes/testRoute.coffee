define ['ehbs!templates/test'], () ->
	return TestRoute =
		bind: (App) ->

			App.Router.map ->
				@.route 'test',
					path: '/test/:name'

			App.TestRoute = Ember.Route.extend
				model: (params) ->
					return {bot: params.name}


			App.TestController = Ember.ObjectController.extend
				actions:
					talk: (input) ->
						thiz = @
						uid = @get 'uid'
						bot = @get 'bot'
						$.ajax
							url: '/bot/talk'
							type: 'POST'
							data: JSON.stringify
								bot: bot
								uid: uid
								query: input
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							message = '[Bot|' + bot + '] ' + result.text
							thiz.send 'update', message
						.fail (response) ->
							alert response.responseText
						return false
