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
					get: ->
						thiz = @
						key = @get 'getKey'
						$.get '/test/get/' + key
						.done (result) ->
							thiz.set 'getValue', result
							thiz.set 'message', 'Value retrieved from server: [Key]:' + key + ' [Value]:' + result
						.fail (response) ->
							thiz.set 'message', 'Value retrieval failed: ' + response.responseText
						return false
					set: ->
						thiz = @
						key = @get 'setKey'
						value = @get 'setValue'
						data = {}
						data[key] = value
						$.ajax
							type: 'POST'
							url: '/test/set/' + key
							data: JSON.stringify data
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							thiz.set 'message', 'Value saved to server: [Key]:' + key + ' [Value]:' + value
						.fail (response) ->
							thiz.set 'message', 'Value cannot be saved to server: ' + response.responseText

						return false;
