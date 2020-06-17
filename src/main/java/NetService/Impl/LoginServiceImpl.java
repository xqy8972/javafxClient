package NetService.Impl;

import NetService.LoginService;
import domain.User;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

import java.util.HashMap;
import java.util.Map;

public class LoginServiceImpl implements LoginService {

	private WebClient client;

	//设置最长超时时间
	private static final int DEFAULT_CONNECT_TIMEOUT = 3;
	//设置最长空余时间
	private static final int DEFAULT_IDLE_TIMEOUT = 3;

	public LoginServiceImpl(Vertx vertx, JsonObject config){
		WebClientOptions options = new WebClientOptions();
		options.setSsl(false);
		options.setUserAgent("controller/1.0");
		options.setConnectTimeout(config.getInteger("connectTimeout", DEFAULT_CONNECT_TIMEOUT) * 1000);
		options.setIdleTimeout(config.getInteger("idleTimeout", DEFAULT_IDLE_TIMEOUT));
		options.setKeepAlive(true);
		options.setDefaultHost(config.getString("host", "127.0.0.1"));
		options.setDefaultPort(config.getInteger("port", 8080));
		options.setFollowRedirects(false);
		//创建客户端
		this.client = WebClient.create(vertx, options);
	}

	@Override
	public Future<Map<String, Object>> getMessage(String username, String password) {
		Promise<Map<String, Object>> promise = Promise.promise();

		JsonObject jsonObject = new JsonObject();
		jsonObject.put("username",username);
		jsonObject.put("password",password);
		client.postAbs("https://dev-iot-account.knowin.com/v1/login")
				.sendJsonObject(jsonObject,ar->{
					if (ar.failed()){
						ar.cause().printStackTrace();
						promise.fail("LoginService_getMessage error: " + ar.cause());
						return;
					}
					JsonObject o = ar.result().bodyAsJsonObject();
					String msg = o.getString("msg");

					Map<String,Object> map = new HashMap<>();
					if ("ok".equals(msg)){
						JsonObject data = o.getJsonObject("data");
						String token = data.getString("token");

						User user = new User();
						user.setName(data.getString("name"))
								.setEmail(data.getString("email"))
								.setUid(data.getInteger("uid"))
								.setAvatar(data.getString("avatar"));

						map.put("user",user);
						map.put("token",token);
						map.put("msg","ok");
					}else{
						map.put("msg","error");
						map.put("description", o.getString("description"));
					}
					promise.complete(map);
				});

		return promise.future();
	}
}
















