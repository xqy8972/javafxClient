package Handler.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ErrorWindow extends Stage {

	StackPane root = new StackPane();
	Scene scene = new Scene(root);

	public ErrorWindow(Window author){

		initOwner(author);

		initLayout();

		initStyle(StageStyle.DECORATED);

		setScene(scene);

		sizeToScene();
	}

	public void resize(double w, double h){
		root.setPrefWidth(w);
		root.setPrefHeight(h);
		sizeToScene();
	}

	public void relocate(double x, double y)
	{
		this.setX(x);
		this.setY(y);
	}

	public void centerInParent(){

		Window owner = getOwner();

		double px = owner.getX(), py=owner.getY();
		double pw = owner.getWidth(), ph=owner.getHeight();

		double dx = (pw-getWidth())/2;
		double dy = (ph-getHeight())/2;

		this.setX(dx+px);
		this.setY(dy+py);
	}

	private void initLayout(){
		Button button = new Button("确认");
		Label label = new Label("输入的账号或密码有误！");
		label.setFont(new Font(18));
		root.getChildren().addAll(label,button);
		StackPane.setAlignment(label, Pos.TOP_CENTER);
		StackPane.setAlignment(button, Pos.BOTTOM_CENTER);
		root.setPadding(new Insets(20));
		button.setOnAction((e)->{
			close();
		});
	}

}




















