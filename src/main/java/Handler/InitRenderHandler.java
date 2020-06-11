package Handler;
import VerticleService.DeviceListService;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InitRenderHandler {
	private BorderPane root;
	private Stage stage;
	private VBox left;
	private VBox right;
	private VBox top;
	private VBox button;
	private HBox header ;
	private Vertx vertx;
	private JsonObject instance;
	ListViewHandler listHandler;

	public InitRenderHandler(Stage stage, Vertx vertx, JsonObject instance){
		this.root = new BorderPane();
		this.stage = stage;
		this.vertx = vertx;
		this.instance = instance;

	}

	public void initLoading(){
		setHeader(root);
		addLeft(root);
		addRight(root);

		Platform.runLater(()->{
			stage.setResizable(false);
			stage.setTitle("Device List");
			stage.setScene(new Scene(root,1280,800));
			stage.sizeToScene();
			stage.show();
		});

	}

	//加载头部
	public void setHeader(BorderPane root){
		header = new HBox();
		header.setPrefHeight(100);
		header.setStyle("-fx-background-color: #fffdd1");
		root.setTop(header);
	}

	//加载左边
	public void addLeft(BorderPane root){

		listHandler = new ListViewHandler(DeviceListService.create(vertx,instance));
		ListView<String> emptyList = new ListView<>();
		emptyList.setPrefHeight(800);
		emptyList.setPrefWidth(300);
		root.setLeft(emptyList);

		listHandler.getListView().setHandler(ar->{
			ListView<String> list = ar.result();
			list.setPrefHeight(800);
			list.setPrefWidth(300);
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

		//加载右下
		button = new VBox();
		button.setPrefHeight(400);
		button.setFillWidth(true);
		button.setStyle("-fx-background-color: #ecebea");

		initDragSupport();
		top.setStyle("-fx-background-color: #f2f2ee");
		right.getChildren().addAll(top,button);
		right.setPrefHeight(800);
		root.setCenter(right);

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
