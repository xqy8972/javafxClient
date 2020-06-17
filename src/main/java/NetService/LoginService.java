package NetService;

import NetService.Impl.LoginServiceImpl;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.Map;

public interface LoginService {

	static LoginService createLoginService(Vertx vertx, JsonObject config){
		return new LoginServiceImpl(vertx,config);
	}

	Future<Map<String, Object>> getMessage(String username, String password);

}
