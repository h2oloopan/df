define [], () ->
	return utils = 
		keys: (obj) ->
			if !Object.keys
				func = (obj) ->
					hasOwnProperty = Object.prototype.hasOwnProperty
					if typeof obj != 'object' and (typeof obj != 'function' or obj == null)
						return throw new TypeError 'Object.keys called on non-object'
					result = []
					for prop of obj
						if hasOwnProperty.call(obj, prop)
							result.push prop
					return result
				return func obj
			else
				return Object.keys obj
