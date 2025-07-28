package com.minigod.zero.trade.hs.resp;

import java.io.Serializable;

/**
 *
 */
public class GrmField implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer fieldId;
	private String fieldCode;
	private String fieldName;
	private String dictionaryCode;
	private String fieldType;
	private Integer fieldDeci = 0;
	private String extFieldCode;

    public Integer getFieldId () {
        return fieldId;
    }

    public void setFieldId (Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldCode () {
        return fieldCode;
    }

    public void setFieldCode (String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName () {
        return fieldName;
    }

    public void setFieldName (String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDictionaryCode () {
        return dictionaryCode;
    }

    public void setDictionaryCode (String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    public String getFieldType () {
        return fieldType;
    }

    public void setFieldType (String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getFieldDeci () {
        return fieldDeci;
    }

    public void setFieldDeci (Integer fieldDeci) {
        this.fieldDeci = fieldDeci;
    }

    public String getExtFieldCode () {
        return extFieldCode;
    }

    public void setExtFieldCode (String extFieldCode) {
        this.extFieldCode = extFieldCode;
    }
}
