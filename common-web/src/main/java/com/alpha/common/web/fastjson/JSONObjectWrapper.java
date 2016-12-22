package com.alpha.common.web.fastjson;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chenwen on 16/10/10.
 */
public class JSONObjectWrapper {
    private JSONObject jsonObject;

    public JSONObjectWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }

}
