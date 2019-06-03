package com.cn.thinkx.cgb.model;

import java.util.Map;


public class ResponseParametersDTO {

    Map<String, Object> objectMap  ;
    Map<String, Object> commHeadDTOMap  ;
    Map<String, Object> signatureMap ;
    Map<String, Object> messageMap ;

    @Override
    public String toString() {
        return "ResponseParametersDTO{" +
                "objectMap=" + objectMap +
                ", commHeadDTOMap=" + commHeadDTOMap +
                ", signatureMap=" + signatureMap +
                ", messageMap=" + messageMap +
                '}';
    }

    public Map<String, Object> getObjectMap() {
        return objectMap;
    }

    public void setObjectMap(Map<String, Object> objectMap) {
        this.objectMap = objectMap;
    }

    public Map<String, Object> getCommHeadDTOMap() {
        return commHeadDTOMap;
    }

    public void setCommHeadDTOMap(Map<String, Object> commHeadDTOMap) {
        this.commHeadDTOMap = commHeadDTOMap;
    }

    public Map<String, Object> getSignatureMap() {
        return signatureMap;
    }

    public void setSignatureMap(Map<String, Object> signatureMap) {
        this.signatureMap = signatureMap;
    }

    public Map<String, Object> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String, Object> messageMap) {
        this.messageMap = messageMap;
    }
}
