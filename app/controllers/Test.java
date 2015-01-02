package controllers;
import play.libs.F.*;
import play.mvc.*;

import com.fasterxml.jackson.databind.*;
import com.google.inject.Inject;

import core.storage.SessionStorage;

public class Test extends Controller {
	@Inject
	private SessionStorage ss;

	public Promise<Result> get(final String key) {
		return ss.get(key).map(
			new Function<Either<String, Throwable>, Result>() {
				public Result apply(Either<String, Throwable> result) {
					if (result.left!= null && result.left.isDefined()) return ok(result.left.get());
					if (result.right != null && result.right.isDefined()) return notFound(result.right.get().toString());
					return notFound();
				}
			}
		);
	}

	@BodyParser.Of(BodyParser.Json.class)
	public Promise<Result> set(final String key) {
		JsonNode json = request().body().asJson();
		String value = json.findPath(key).textValue();
		return ss.set(key, value).map(
			new Function<Either<String, Throwable>, Result>() {
				public Result apply(Either<String, Throwable> result) {
					if (result.left!= null && result.left.isDefined()) return ok(result.left.get());
					if (result.right != null && result.right.isDefined()) return internalServerError(result.right.get().toString());
					return internalServerError();
				}
			}
		);
	}
}
