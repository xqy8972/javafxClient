package NetService;

import NetService.Impl.DeviceListServiceImp;
import domain.User;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public interface DeviceListService {

	static DeviceListService create(Vertx vertx, JsonObject config, String token){
		return new DeviceListServiceImp(vertx,config,token);
	}

	Future<JsonObject> getMessage();
}
