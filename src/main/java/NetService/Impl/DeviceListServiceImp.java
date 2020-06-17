package NetService.Impl;
import NetService.DeviceListService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

public class DeviceListServiceImp implements DeviceListService {

	private WebClient client;

	//设置最长超时时间
	private static final int DEFAULT_CONNECT_TIMEOUT = 3;
	//设置最长空余时间
	private static final int DEFAULT_IDLE_TIMEOUT = 3;

	private String token;

	public DeviceListServiceImp(Vertx vertx, JsonObject config, String token){
		this.token = token;
		System.out.println(token);
		WebClientOptions options = new WebClientOptions();
		options.setSsl(false);
		options.setUserAgent("controller/1.0");
		options.setConnectTimeout(config.getInteger("connectTimeout", DEFAULT_CONNECT_TIMEOUT) * 1000);
		options.setIdleTimeout(config.getInteger("idleTimeout", DEFAULT_IDLE_TIMEOUT));
		options.setKeepAlive(true);
		options.setDefaultHost(config.getString("host", "127.0.0.1"));
		options.setDefaultPort(config.getInteger("port", 8080));
		//创建客户端
		this.client = WebClient.create(vertx, options);
	}

	@Override
	public Future<JsonObject> getMessage() {
		Promise<JsonObject> promise = Promise.promise();
		client.getAbs("http://dev-iot-dvlp.knowin.com/simulator/instances")
				.bearerTokenAuthentication(token)
				.send(ar->{
					if (ar.succeeded()){
						HttpResponse<Buffer> result = ar.result();
						promise.complete(result.bodyAsJsonObject());
					}else{
						ar.failed();
					}
				});

		return promise.future();
	}
}
