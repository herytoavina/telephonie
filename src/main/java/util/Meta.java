/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author ASUS
 */
public class Meta {
    private String type ;
    private String status ;
    private String message ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if(status.compareTo("200")==0) {
            this.type = "success";
        }
        else {
            this.type = "error";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Meta() {
    }

    public Meta(String status,String message) {
        
        this.setStatus(status);
        this.message = message;
    }
    
}
