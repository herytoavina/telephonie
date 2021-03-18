/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class BuilderResponse {
    private  Meta META;
    private List DATA;

    public Meta getMETA() {
        return META;
    }

    public void setMETA(Meta META) {
        this.META = META;
    }

    public List getDATA() {
        return DATA;
    }

    public void setDATA(ArrayList DATA) {
        this.DATA = DATA;
    }

    public BuilderResponse() {
    }

    public BuilderResponse(Meta META, List DATA) {
        this.META = META;
        this.DATA = DATA;
    }
    
    
    
}
