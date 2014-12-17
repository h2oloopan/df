package storage;

import play.libs.F.*;
import play.libs.Akka;
import play.libs.F.Promise;

import com.typesafe.plugin.RedisPlugin;

import redis.clients.jedis.*;
import scala.concurrent.ExecutionContext;

public class RedisSessionStorage implements SessionStorage{
	//Make sure "session-context" is defined in application.conf
	private ExecutionContext context = Akka.system().dispatchers().lookup("session-context"); 
	@Override
	public Promise<Either<String, Throwable>> get(final String key) {
		return Promise.promise(new Function0<Either<String, Throwable>>() {
			public Either<String, Throwable> apply() {
				Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
				try {
					String result = j.get(key);
					if (result == null) {
						return Either.Right(new Throwable("Nothing matches provided key"));
					} else {
						return Either.Left(j.get(key));
					}
				} catch (Throwable t) {
					return Either.Right(t);
				} finally {
					play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
				}
			}
		}, context);
	}

	@Override
	public Promise<Either<String, Throwable>> set(final String key, final String value) {
		return Promise.promise(new Function0<Either<String, Throwable>>() {
			public Either<String, Throwable> apply() {
				Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
				try {
					j.set(key, value);
					return Either.Left(key);
				} catch (Throwable t) {
					return Either.Right(t);
				} finally {
					play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
				}
			}
		}, context);
		
	}

}
