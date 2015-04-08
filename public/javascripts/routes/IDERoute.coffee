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
				helpAIML: false
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
						editor = thiz.get 'editor'
						editor.setValue result
						editor.gotoLine 1
						thiz.send 'help', type
					, (errors) ->
						alert errors.responseText
				).observes 'file'
				actions:
					help: (type) ->
						thiz = @

						search = (text) ->
							pattern = ///<grammar>[^<]+</grammar>///igm
							matches = text.match pattern
							list = []
							for match in matches
								term  = match.substr match.indexOf('>') + 1
								term = term.substr 0, term.lastIndexOf('<')
								term = term.replace('\r', '').replace('\n', '').trim()
								list.push term
							return list

						if type.toLowerCase() == 'aiml'
							#async help
							bot = @get 'bot'
							url = '/edit/map?bot=' + bot
							Ember.$.getJSON(url).then (result) ->
								editor = thiz.get 'editor'
								list = search editor.getValue()
								console.log list
								return true
							, (errors) ->
								console.log errors
								return false

						return false
					compile: ->
						bot = @get 'bot'
						if !bot? then return false
						$.ajax
							url: '/bot/compile'
							type: 'POST'
							data: JSON.stringify
								bot: bot
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							alert 'Grammar compilation done for bot ' + bot
						.fail (response) ->
							alert 'Grammar compilation failed for bot ' + bot + ' ' + response.responseText
						return false
					reload: ->
						bot = @get 'bot'
						if !bot? then return false
						$.ajax
							url: '/bot/reload'
							type: 'POST'
							data: JSON.stringify
								bot: bot
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							alert 'Bot reloaded successfully for bot ' + bot
						.fail (response) ->
							alert 'Bot reloading failed for bot ' + bot + ' ' + response.responseText
						return false
					save: ->
						thiz = @
						file = @get 'file'
						type = @get 'type'
						encoding = 'UTF-8'
						if !file? then return false
						if type.toLowerCase() == 'grammar' then encoding = 'GB18030'
						editor = @get 'editor'
						$.ajax
							url: '/edit/upload'
							type: 'POST'
							data: JSON.stringify
								path: file.path
								encoding: encoding
								text: editor.getValue()
							contentType: 'application/json; charset=' + encoding
						.done (result) ->
							alert 'File ' + file.name + ' saved to server successfully' 
							return true
						.fail (response) ->
							alert response.responseText
							return false
						return false
					discard: ->
						file = @get 'file'
						@set 'file', null
						@set 'file', file
						return false

			App.IDEView = Ember.View.extend
				didInsertElement: ->
					@_super()
					editor = ace.edit 'editor'
					editor.setTheme 'ace/theme/chrome'
					$('#editor')[0].style.fontSize = '14px'
					@set 'controller.editor', editor


