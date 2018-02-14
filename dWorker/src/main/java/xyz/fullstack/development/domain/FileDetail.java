package xyz.fullstack.development.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fileName;

    private String processSuccessful;

    private String dateProcessed;

    private String error;

    private String source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProcessSuccessful() {
        return processSuccessful;
    }

    public void setProcessSuccessful(String processSuccessful) {
        this.processSuccessful = processSuccessful;
    }

    public String getDateProcessed() {
        return dateProcessed;
    }

    public void setDateProcessed(String dateProcessed) {
        this.dateProcessed = dateProcessed;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

