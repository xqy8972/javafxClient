package Utils;

import cn.geekcity.xiot.spec.codec.vertx.instance.DeviceCodec;
import cn.geekcity.xiot.spec.instance.Device;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DecodeUtils {

	static ObservableList<String> listDate;

	public static ObservableList<String> DecodeResult(JsonObject result){
		JsonArray datas = result.getJsonArray("data");

		List<Map<String, String>> descriptions = datas.stream().filter(x -> x instanceof JsonObject)
				.map(x -> DeviceCodec.decode(((JsonObject) x).getJsonObject("content")))
				.map(Device::description)
				.collect(Collectors.toList());

		listDate = FXCollections.observableArrayList();
		for (Map<String, String> map : descriptions){
			if (map.containsKey("en-US")){
				listDate.add(map.get("en-US"));
			}
		}

		return listDate;
	}
}
