package VerticleService;

import VerticleService.img.LoginServiceImpl;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public interface ClientService {

	static ClientService createLoginService(Vertx vertx, JsonObject config){
		return new LoginServiceImpl(vertx,config);
	}

	Future<JsonObject> getMessage();
}
