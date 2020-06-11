import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class Verticle extends AbstractVerticle {

	@Override
	public void start(Promise<Void> startPromise) throws Exception {
			startPromise.complete();
	}


	@Override
	public void stop(Promise<Void> stopPromise) throws Exception {

	}
}
