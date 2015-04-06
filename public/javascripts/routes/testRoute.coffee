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
						return false
