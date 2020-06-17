package Handler;
import Handler.windows.UserDataWindow;
import NetService.DeviceListService;
import Utils.RoundIcon;
import domain.User;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InitRenderHandler {
	private BorderPane root;
	private Stage stage;
	private VBox left;
	private VBox right;
	private VBox top;
	private VBox button;
	private HBox header;
	private Vertx vertx;
	private JsonObject instance;
	private User user;
	private String token;
	private ListViewHandler listHandler;
	private ContextMenu contextMenu;

	public InitRenderHandler(Stage stage, Vertx vertx, JsonObject instance, User user, String token){
		this.root = new BorderPane();
		this.stage = stage;
		this.vertx = vertx;
		this.instance = instance;
		this.user = user;
		this.token = token;
		this.contextMenu = new ContextMenu();
	}

	public void initLoading(){
		createPopWindow();
		Platform.runLater(()->{
			setHeader(root);
			addLeft(root);
			addRight(root);
			stage.setResizable(false);
			stage.setTitle("Device List");
			stage.setScene(new Scene(root,1280,800));
			stage.sizeToScene();
			stage.show();

		});
	}

	public void setHeader(BorderPane root){

		header = new HBox();
		header.setPadding(new Insets(15));
		Label username = new Label(user.getName());
		username.setFont(new Font(18));
		username.setStyle("-fx-text-base-color: #616360");
		username.setTextFill(Color.web("#616360"));

		Image image = new Image("/img/userheader2.png");

		RoundIcon roundIcon = new RoundIcon();
		roundIcon.setImage(image);
		roundIcon.setPrefWidth(48);
		roundIcon.setPrefHeight(48);

		Image headImg = new Image("/img/header.png");
		ImageView imageView = new ImageView(headImg);
		double h = headImg.getHeight();
		double w = headImg.getWidth();
		imageView.setFitHeight(h*0.7);
		imageView.setFitWidth(w*0.7);

		Label s = new Label();
		s.setMinWidth(790);

		final Separator sepHor = new Separator();
		sepHor.setValignment(VPos.BOTTOM);
		sepHor.setPrefHeight(3);


		//弹出窗口事件
		roundIcon.setOnMouseEntered((e)->{
			if (!contextMenu.isShowing()){
				contextMenu.show(roundIcon, e.getScreenX(), e.getScreenY());
			}
		});

		Label spare1 = new Label();
		spare1.setMinWidth(5);
		Label spare2 = new Label();
		spare2.setMinWidth(20);

		header.getChildren().addAll(imageView,s, roundIcon,spare1,username,spare2);
		header.setAlignment(Pos.TOP_RIGHT);
		header.setPrefHeight(70);
		header.setStyle("-fx-background-color:rgba(255,129,0,0)");
		header.setAlignment(Pos.TOP_LEFT);

		VBox vBox = new VBox();
		vBox.getChildren().addAll(header,sepHor);
		root.setTop(vBox);
	}

	public void addLeft(BorderPane root){

		System.out.println(token);

		listHandler = new ListViewHandler(DeviceListService.create(vertx,instance,token));
		ListView<String> emptyList = new ListView<>();
		emptyList.setPrefHeight(800);
		emptyList.setPrefWidth(300);
		emptyList.setStyle("-fx-border-color: rgb(127,127,127);"+
				"-fx-border-width: 1.5;");
		root.setLeft(emptyList);

		listHandler.getListView().setHandler(ar->{
			ListView<String> list = ar.result();
			list.setPrefHeight(800);
			list.setPrefWidth(300);
			list.setStyle("-fx-border-color: rgb(127,127,127);"+
					"-fx-border-width: 1.5;");
			left = new VBox();
			left.getChildren().addAll(list);
			Platform.runLater(()->{
				root.setLeft(left);
			});
		});
	}

	//加载右边
	public void addRight(BorderPane root){
		right = new VBox();
		//加载右上
		top = new VBox();
		top.setPrefHeight(400);
		top.setFillWidth(true);
		top.setStyle("-fx-background-color: #f2f2ee");


		final Separator sepHor = new Separator();
		sepHor.setValignment(VPos.BOTTOM);
		sepHor.setPrefHeight(3);

		//加载右下
		button = new VBox();
		button.setPrefHeight(400);
		button.setFillWidth(true);
		button.setStyle("-fx-background-color: #ecebea");

		initDragSupport();
		top.setStyle("-fx-background-color: #f2f2ee");
		right.getChildren().addAll(top,sepHor,button);
		right.setPrefHeight(800);
		root.setCenter(right);

	}

	private void createPopWindow(){

		MenuItem item1 = new MenuItem("个人中心");
		item1.setOnAction((e)->{
			UserDataWindow userDataWindow = new UserDataWindow(stage,user);
			userDataWindow.resize(320, 300);
			userDataWindow.setResizable(false);
			userDataWindow.setTitle("个人信息");
			userDataWindow.show();
		});

		MenuItem item2 = new MenuItem("权限");

		MenuItem item3 = new MenuItem("退出登陆");
		item3.setOnAction((e)->{
			stage.close();
			LoginWindowHandler loginWindowHandler = new LoginWindowHandler(root,stage,vertx);
			loginWindowHandler.initLoading();
		});

		SeparatorMenuItem s1 = new SeparatorMenuItem();
		SeparatorMenuItem s2 = new SeparatorMenuItem();
		contextMenu.setMaxWidth(200);
		contextMenu.setHeight(100);
		contextMenu.getItems().addAll(item1, s1, item2, s2, item3);
	}



	////////////////////// 拖动窗口支持 ///////////////////////////

	private boolean dragging = false;
	private boolean resizingHeight = false;
	private double topHeight=0, buttonHeight=0;
	private double startY = 0;
	private void initDragSupport(){
		root.setOnMouseMoved((e)->{
			boolean r = isResizingHeight(e.getY());
			if (r){
				root.setCursor(Cursor.V_RESIZE);
			}else{
				root.setCursor(Cursor.DEFAULT);
			}
		});
		root.setOnMousePressed((e)->{
			topHeight = top.getHeight();
			buttonHeight = button.getHeight();
			startY = e.getY();
		});
		root.setOnDragDetected((e)->{
			if(e.getButton() == MouseButton.PRIMARY){
				dragging = true;
				resizingHeight = isResizingHeight(startY);
			}
		});
		root.setOnMouseDragged((e)->{
			if (!dragging)
				return;
			double dy = e.getY()-startY;
			if (resizingHeight){
				double newTopHeight = topHeight + dy ;
				double newButtonHeight = buttonHeight-dy;

				if(newTopHeight<600 && newTopHeight>100){
					top.setPrefHeight(newTopHeight);
					button.setPrefHeight(newButtonHeight);
					stage.sizeToScene();
				}
			}
		});
		root.setOnMouseReleased((e)->{
			dragging = false;
			resizingHeight = false;
		});
	}
	private boolean isResizingHeight(Double y){
		double h = top.getHeight() + header.getHeight();
		return y>h-2 && y<h+2;
	}
}
