package com.cn.thinkx.cgb.model;

import java.io.Serializable;


public class RequestParametersDTO<T> implements Serializable {

    private static final long serialVersionUID = -2863753961300138523L;

    private CommHeadDTO commHeadDTO;
    private SignatureDTO signatureDTO;
    private T object;
    public RequestParametersDTO() {
    }

    public RequestParametersDTO(T t){
        object = t;
    }
    public SignatureDTO getSignatureDTO() {
        return signatureDTO;
    }

    public void setSignatureDTO(SignatureDTO signatureDTO) {
        this.signatureDTO = signatureDTO;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }


    public CommHeadDTO getCommHeadDTO() {
        return commHeadDTO;
    }

    public void setCommHeadDTO(CommHeadDTO commHeadDTO) {
        this.commHeadDTO = commHeadDTO;
    }

    @Override
    public String toString() {
        return "RequestParametersDTO{" +
                "commHeadDTO=" + commHeadDTO +
                ", signatureDTO=" + signatureDTO +
                ", object=" + object +
                '}';
    }
}
