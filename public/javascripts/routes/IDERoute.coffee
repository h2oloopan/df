define ['utils', 'ace/ace', 'ehbs!templates/IDE'], (u, ace) ->
	return IDERoute =
		bind: (App) ->
			App.IDERoute = Ember.Route.extend {}

			App.IDEController = Ember.ObjectController.extend {}

			App.IDEView = Ember.View.extend
				didInsertElement: ->
					@_super()
					editor = ace.edit 'editor'
					$('#editor')[0].style.fontSize = '14px'

