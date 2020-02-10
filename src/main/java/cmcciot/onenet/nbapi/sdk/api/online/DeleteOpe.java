package cmcciot.onenet.nbapi.sdk.api.online;

import org.json.JSONObject;

import cmcciot.onenet.nbapi.sdk.entity.CommonEntity;
import cmcciot.onenet.nbapi.sdk.utils.HttpSendCenter;
import okhttp3.Callback;

public class DeleteOpe extends BasicOpe {
	public DeleteOpe(String apiKey) {
		super(apiKey);
	}

	@Override
	public void operation(CommonEntity commonEntity, JSONObject body, Callback callback) {
		HttpSendCenter.deleteAsync(this.apiKey, commonEntity.toUrl(), callback);
	}

	@Override
	public JSONObject operation(CommonEntity commonEntity, JSONObject body) {
		return HttpSendCenter.delete(this.apiKey, commonEntity.toUrl());
	}

}
