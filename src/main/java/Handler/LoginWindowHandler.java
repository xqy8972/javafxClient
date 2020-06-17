package Handler;

import Handler.windows.ErrorWindow;
import NetService.Impl.LoginServiceImpl;
import NetService.LoginService;
import domain.User;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

public class LoginWindowHandler {

	private BorderPane root;

	private Stage stage;

	private Vertx vertx;

	//private JsonObject instance;

	public LoginWindowHandler(BorderPane root, Stage stage, Vertx vertx) {
		this.root = root;
		this.stage = stage;
		this.vertx = vertx;
	}

	public void initLoading(){

		Label l_username = new Label("账号: ");
		Label l_password = new Label("密码: ");
		Button login = new Button("登陆");

		TextField t_username = new TextField();
		PasswordField p_password = new PasswordField();

		BorderPane root = new BorderPane();

		HBox uHBox = new HBox();
		uHBox.getChildren().addAll(l_username,t_username);
		uHBox.setAlignment(Pos.CENTER);

		HBox pHBox = new HBox();
		pHBox.getChildren().addAll(l_password,p_password);
		pHBox.setAlignment(Pos.CENTER);
		pHBox.setPadding(new Insets(8));

		HBox btnHBox = new HBox();
		login.setMinWidth(201);
		HBox spare = new HBox();
		spare.setPrefWidth(40);
		btnHBox.getChildren().addAll(spare,login);
		btnHBox.setAlignment(Pos.CENTER);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(uHBox,pHBox,btnHBox);

		HBox header = new HBox();
		header.setPrefHeight(100);
		Image image = new Image("/img/header.png");
		ImageView imageView = new ImageView(image);
		header.setAlignment(Pos.CENTER);
		header.getChildren().add(imageView);
		header.setPadding(new Insets(50));

		root.setTop(header);
		root.setCenter(vBox);
		vBox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root);
		scene.getStylesheets().add(LoginWindowHandler.class.getResource("/Login.css").toExternalForm());

		Platform.runLater(()->{
			stage.setScene(scene);
			stage.setWidth(600);
			stage.setHeight(400);
			stage.setResizable(false);
			stage.setTitle("登陆");
			stage.show();
		});

		login.setOnAction((e)->{
			String username = t_username.getText();
			String password = p_password.getText();
			String md5_password = DigestUtils.md5Hex(password);

			JsonObject instance = new JsonObject();
			instance.put("host","dev-Iot-account.knowin.com");
			instance.put("port",80);

			LoginService loginService = new LoginServiceImpl(vertx,instance);

			loginService.getMessage(username, md5_password).setHandler(ar->{
				if (ar.succeeded()){
					Map<String, Object> map = ar.result();
					String msg = (String)map.get("msg");
					if("ok".equals(msg)){
						JsonObject mainInstance = new JsonObject();
						mainInstance.put("host","dev-iot-dvlp.knowin.com");
						mainInstance.put("port",80);

						Platform.runLater(()->{
							stage.close();
						});

						User user = (User)map.get("user");
						String token = (String)map.get("token");
						InitRenderHandler initRenderHandler = new InitRenderHandler(stage, vertx, mainInstance, user, token);
						initRenderHandler.initLoading();
					}else{
						Platform.runLater(()->{
							ErrorWindow errorWindow = new ErrorWindow(stage);
							errorWindow.resize(300,100);
							errorWindow.setResizable(false);
							errorWindow.show();
							errorWindow.centerInParent();
						});
					}
				}else{
					System.out.println(ar.cause());
				}

			});
		});
	}
}






















