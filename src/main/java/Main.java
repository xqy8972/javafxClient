import Handler.InitRenderHandler;
import Handler.LoginWindowHandler;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application {

	BorderPane root;

	Vertx vertx;

	Verticle verticle;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			this.root = new BorderPane();
			//final JsonObject result = new JsonObject();
			deploy().setHandler(ar->{
				if (ar.succeeded()){
					ar.result();
					//JsonObject config = verticle.config();
					JsonObject instance = new JsonObject();
					instance.put("host","dev-iot-dvlp.knowin.com");
					instance.put("port",80);
					//加载界面
					new LoginWindowHandler(root,primaryStage,vertx).initLoading();

					//new InitRenderHandler(root,primaryStage,vertx,instance).initLoading();

				}else {
					ar.cause();
				}
			});

//			primaryStage.setTitle("Device List");
//			primaryStage.setScene(new Scene(root,1280,800));
//			primaryStage.sizeToScene();
//			primaryStage.show();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	//部署Vert.x
	public Future<Void> deploy(){
		Promise<Void> promise = Promise.promise();
		this.vertx = Vertx.vertx();
		verticle = new Verticle();
		vertx.deployVerticle(verticle, ar->{
			if (ar.succeeded()){
				promise.complete();
			}else {
				ar.cause();
			}
		});
		return promise.future();
	}


}











































