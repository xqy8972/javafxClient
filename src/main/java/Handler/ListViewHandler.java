package Handler;

import Utils.DecodeUtils;
import VerticleService.DeviceListService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ListViewHandler {
	ListView<String> listView;

	ObservableList<String> listDate;

	DeviceListService deviceService;

	public ListViewHandler(DeviceListService deviceService){
		this.deviceService = deviceService;
	}


	public Future<ListView> getListView(){
		Promise<ListView> promise = Promise.promise();
		deviceService.getMessage()
				.setHandler((res)->{
					if (res.succeeded()){
						//获取设备数据，加载设备列表
						JsonObject result = res.result();
						listDate = DecodeUtils.DecodeResult(result);
						listView = new ListView<>();
						listView.setPrefHeight(100);
						listView.setPrefWidth(100);
						listView.setItems(listDate);

						listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
							@Override
							public ListCell<String> call(ListView<String> param)
							{
								return new MyListView();
							}
						});
						promise.complete(listView);
					}else {
						res.cause();
					}
				});
		return promise.future();
	}

	class MyListView extends ListCell<String>{
		@Override
		protected void updateItem(String item, boolean b) {
			super.updateItem(item, b);

			if (item!=null){
				this.setText(item);
			}
		}
	}

}
