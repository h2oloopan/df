define ['ehbs!templates/edit'], () ->
	return EditRoute =
		bind: (App) ->
			App.EditRoute = Ember.Route.extend
				model: ->
					return new Ember.RSVP.Promise (resolve, reject) ->
						new Ember.RSVP.hash
							bots: Ember.$.getJSON('/bot/bots')
						.then (result) ->
							resolve
								bots: result.bots
						, (errors) ->
							reject errors

			App.EditController = Ember.ObjectController.extend
				