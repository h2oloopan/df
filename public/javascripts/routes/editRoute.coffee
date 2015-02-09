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
						if a.length > 0 then thiz.set 'grammar', a[0]
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
						if b.length > 0 then thiz.set 'aiml', b[0]
					, (errors) ->
						thiz.set 'aimls', []

				).observes 'bot'
				grammarChanged: ( ->
					thiz = @
					path = @get 'grammar.path'
					if !path? then return
					#actually do stuff
					Ember.$.getJSON('/edit/file?encoding=GB18030&path=' + path).then (result) ->
						thiz.set 'fileGrammar', result
					, (errors) ->
						thiz.set 'fileGrammar', errors.responseText
				).observes('grammar')
				aimlChanged: ( ->
					thiz = @
					path = @get 'aiml.path'
					if !path? then return
					#actually do stuff
					Ember.$.getJSON('/edit/file?encoding=UTF-8&path=' + path).then (result) ->
						thiz.set 'fileAIML', result
					, (errors) ->
						thiz.set 'fileAIML', errors.responseText
				).observes 'aiml'
				actions:
					saveGrammar: (grammar, file) ->
						$.ajax
							url: '/edit/upload'
							type: 'POST'
							data: JSON.stringify
								path: grammar.path
								encoding: 'GB18030'
								text: file
							dataType: 'json'
							contentType: 'application/json; charset=gb18030'
						.done (result) ->
							alert 'Grammar saved to server successfully!'
						.fail (response) ->
							alert response.responseText
						return false
					saveAIML: (aiml, file) ->
						$.ajax
							url: '/edit/upload'
							type: 'POST'
							data: JSON.stringify
								path: aiml.path
								encoding: 'UTF-8'
								text: file
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							alert 'AIML saved to server successfully!'
						.fail (response) ->
							alert response.responseText
						return false
					compile: (bot) ->
						thiz = @
						$.ajax
							url: '/bot/compile'
							type: 'POST'
							data: JSON.stringify
								bot: bot
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							alert 'Grammar compilation done for bot ' + bot
						.fail (response) ->
							alert 'Grammar compilation failed for bot ' + bot + ' ' + response.responseText
						return false
					reload: (bot) ->
						thiz = @
						$.ajax
							url: '/bot/reload'
							type: 'POST'
							data: JSON.stringify
								bot: bot
							dataType: 'json'
							contentType: 'application/json; charset=utf-8'
						.done (result) ->
							alert 'Bot reloaded successfully for ' + bot
							return true
						.fail (response) ->
							#thiz.send 'update', '[SYSTEM] Bot reloading failed for bot ' + bot + ' ' + response.responseText
							return false
						return false