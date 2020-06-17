package Handler.windows;

import Utils.RoundIcon;
import domain.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;


public class UserDataWindow extends Stage {

	private StackPane root = new StackPane();

	private Scene scene = new Scene(root);

	private User user;

	public UserDataWindow(Window author, User user){
		this.user = user;


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

		Label l1 = new Label("账号信息");
		l1.setFont(new Font(25));

		RoundIcon icon = new RoundIcon();
		Image image = new Image("/img/userheader.png");
		icon.resize(100,100);

		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(100);
		imageView.setFitHeight(100);


		Label l_id = new Label("id: "+user.getUid());
		l_id.setFont(new Font(17));
		Label l_username = new Label("昵称: "+user.getName());
		l_username.setFont(new Font(17));

		GridPane grid = new GridPane();
		grid.setHgap(48);
		grid.setVgap(15);
		grid.setPadding(new Insets(20));

		grid.add(l1, 2,0);
		grid.add(imageView, 2, 1);
		grid.add(l_id,2,3);
		grid.add(l_username,2,4);

		root.getChildren().add(grid);
		super.initModality(Modality.WINDOW_MODAL);
	}

}
