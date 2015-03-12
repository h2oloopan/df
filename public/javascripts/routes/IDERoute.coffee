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
				types: ['Grammar', 'AIML']
				selectionChanged: (->
					thiz = @
					bot = @get 'bot'
					type = @get 'type'
					switch type.toLowerCase()
						when 'grammar'
							url = '/bot/grammars?bot=' + bot
							break
						when 'aiml'
							url = '/bot/aimls?bot=' + bot
							break
					Ember.$.getJSON(url).then (result) ->
						thiz.set 
				).observes 'bot', 'definition'

			App.IDEView = Ember.View.extend
				didInsertElement: ->
					@_super()
					editor = ace.edit 'editor'
					$('#editor')[0].style.fontSize = '14px'

