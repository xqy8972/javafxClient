package Handler;

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
		Image image = new Image("/img/headerImg.png");
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
			String password = p_password.getText();



			if (password.equals("123")){
				JsonObject instance = new JsonObject();
				instance.put("host","dev-iot-dvlp.knowin.com");
				instance.put("port",80);

				Platform.runLater(()->{
					stage.close();
				});
				new InitRenderHandler(stage,vertx,instance).initLoading();
			}
		});

	}

}






















