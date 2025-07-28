/**
  * Copyright 2024 bejson.com
  */
package com.minigod.minigodinformation.dto.http;
import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2024-11-07 15:12:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Records {

    private int id;
    private int type;
    private Date time;
    private String custName;
    private List<String> files;
    private int status;
    private String title;
    private List<PdfFiles> pdfFiles;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setTime(Date time) {
         this.time = time;
     }
     public Date getTime() {
         return time;
     }

    public void setCustName(String custName) {
         this.custName = custName;
     }
     public String getCustName() {
         return custName;
     }

    public void setFiles(List<String> files) {
         this.files = files;
     }
     public List<String> getFiles() {
         return files;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setPdfFiles(List<PdfFiles> pdfFiles) {
         this.pdfFiles = pdfFiles;
     }
     public List<PdfFiles> getPdfFiles() {
         return pdfFiles;
     }

}
