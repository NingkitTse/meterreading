package cmcciot.onenet.nbapi.sdk.entity.builder;

import cmcciot.onenet.nbapi.sdk.entity.CommonEntity;

public abstract class CommonEntityBuilder<T> {

    protected abstract CommonEntity getInstance();

    @SuppressWarnings("unchecked")
    public T setImei(String imei) {
        getInstance().imei = imei;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setObjId(Integer objId) {
        getInstance().objId = objId;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setObjInstId(Integer objInstId) {
        getInstance().objInstId = objInstId;
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setResId(Integer resId) {
        getInstance().resId = resId;
        return (T) this;
    }

}
