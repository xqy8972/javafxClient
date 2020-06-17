package VerticleService;

import VerticleService.img.DeviceListServiceImp;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public interface DeviceListService {

	static DeviceListService create(Vertx vertx, JsonObject config){
		return new DeviceListServiceImp(vertx,config);
	}

	Future<JsonObject> getMessage();
}
