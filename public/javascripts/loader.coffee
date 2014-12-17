require.config
	baseUrl: '/assets/javascripts/lib'
	ehbs:
		compile: true
		extension: '.html'
	paths:
		app: '/assets/javascripts/app'
		routes: '/assets/javascripts/routes'
		templates: '/assets/templates'
	shim:
		'bootstrap': ['jquery']
		'ember': ['handlebars', 'jquery']
		'ember-data': ['ember']
		'app': ['ember', 'ember-data', 'bootstrap']

require ['app'], (app) ->
	app.start()
		