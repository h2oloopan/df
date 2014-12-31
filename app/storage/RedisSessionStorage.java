package storage;

import play.libs.F.*;
import play.libs.Akka;
import play.libs.F.Promise;

import com.typesafe.plugin.RedisPlugin;

import redis.clients.jedis.*;
import scala.concurrent.ExecutionContext;

public class RedisSessionStorage implements SessionStorage{
	//Make sure "session-context" is defined in application.conf
	//private ExecutionContext context = Akka.system().dispatchers().lookup("session-context");
	//Will make this async once jedis supports async access
	@Override
	public String get(final String key) throws Throwable {
		Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
		try {
			String result = j.get(key);
			if (result == null) {
				throw new Throwable("Nothing matches provided key");
			} else {
				return j.get(key);
			}
		} catch (Throwable t) {
			throw t;
		} finally {
			play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
		}
	}

	@Override
	public String set(final String key, final String value) throws Throwable {
		Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
		try {
			j.set(key, value);
			return key;
		} catch (Throwable t) {
			throw t;
		} finally {
			play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
		}
	}

}
