define ['utils', 'ace/ace', 'ehbs!templates/IDE'], (u, ace) ->
	return IDERoute =
		bind: (App) ->
			App.IDERoute = Ember.Route.extend
				model: ->
					return new Ember.RSVP.Promise (resolve, reject) ->
						new Ember.RSVP.hash
							bots: Ember.$.getJSON('/bot/bots')
						.then (result) ->
							resolve
								bots: result.bots
						, (errors) ->
							reject errors

			App.IDEController = Ember.ObjectController.extend
				definitions: ['Grammar', 'AIML']
				selectionChanged: (->
					
				).observes 'bot', 'definition'

			App.IDEView = Ember.View.extend
				didInsertElement: ->
					@_super()
					editor = ace.edit 'editor'
					$('#editor')[0].style.fontSize = '14px'

