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
					if !bot? or !type? then return false
					switch type.toLowerCase()
						when 'grammar'
							url = '/edit/folders?type=grammar&bot=' + bot
							break
						when 'aiml'
							url = '/edit/folders?type=aiml&bot=' + bot
							break
					Ember.$.getJSON(url).then (result) ->
						a = []
						keys = u.keys result
						for key in keys
							value = result[key]
							a.push
								name: key
								path: value

						thiz.set 'folders', a
						if a.length > 0 then thiz.set 'folder', a[0]
					, (errors) ->
						alert errors.responseText
				).observes 'bot', 'type'
				folderChanged: (->
					thiz = @
					folder = @get 'folder'
					if !folder? then return false
					url = '/edit/files?folder=' + folder.path
					Ember.$.getJSON(url).then (result) ->
						a = []
						keys = u.keys result
						for key in keys
							value = result[key]
							a.push
								name: key
								path: value
						thiz.set 'files', a
						if a.length > 0 then thiz.set 'file', a[0]
					, (errors) ->
						alert errors.responseText
				).observes 'folder'
				fileChanged: (->
					thiz = @
					file = @get 'file'
					type = @get 'type'
					encoding = 'UTF-8'
					if !file? then return false
					if type.toLowerCase() == 'grammar' then encoding = 'GB18030'
					url = '/edit/file?path=' + file.path + '&encoding=' + encoding
					Ember.$.getJSON(url).then (result) ->
						thiz.get('editor').setValue result
					, (errors) ->
						alert errors.responseText

				).observes 'file'

			App.IDEView = Ember.View.extend
				didInsertElement: ->
					@_super()
					editor = ace.edit 'editor'
					editor.setTheme 'ace/theme/chrome'
					$('#editor')[0].style.fontSize = '14px'
					@set 'controller.editor', editor


