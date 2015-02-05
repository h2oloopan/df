define ['utils', 'ehbs!templates/edit'], (u) ->
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
				bot: null
				grammar: null
				aiml: null
				grammars: []
				aimls: []
				botChanged: ( -> 
					thiz = @
					
					Ember.$.getJSON('/bot/grammars?bot=' + @get('bot')).then (result) ->
						a = []
						keys = u.keys result
						for key in keys
							value = result[key]
							a.push
								name: key
								path: value
						thiz.set 'grammars', a
					, (errors) ->
						thiz.set 'grammars', []

					Ember.$.getJSON('/bot/aimls?bot=' + @get('bot')).then (result) ->
						b = []
						keys = u.keys result
						for key in keys
							value = result[key]
							b.push
								name: key
								path: value
						thiz.set 'aimls', b
					, (errors) ->
						thiz.set 'aimls', []

				).observes 'bot'
				grammarChanged: ( ->

				).observes 'grammar'
				aimlChanged: ( ->
					
				).observes 'aiml'
				actions:
					save: ->
						return false