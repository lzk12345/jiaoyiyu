package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TbManuscriptUrl class
 *
 * @author maochaoying
 * @date 2019/12/17
 */

public class TbManuscriptUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    private Integer status;

    private Integer assignmentId;

    private Integer manuscriptId;

    private String fileName;

    private String extendName;

    private BigDecimal fileSize;

    private Integer gaojianSign;

    private Integer isOrigin;

    public Integer getIsOrigin() {
        return isOrigin;
    }

    public void setIsOrigin(Integer isOrigin) {
        this.isOrigin = isOrigin;
    }

    public Integer getGaojianSign() {
        return gaojianSign;
    }

    public void setGaojianSign(Integer gaojianSign) {
        this.gaojianSign = gaojianSign;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getManuscriptId() {
        return manuscriptId;
    }

    public void setManuscriptId(Integer manuscriptId) {
        this.manuscriptId = manuscriptId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtendName() {
        return extendName;
    }

    public void setExtendName(String extendName) {
        this.extendName = extendName;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }
}
