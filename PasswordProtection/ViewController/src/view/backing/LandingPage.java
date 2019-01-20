package view.backing;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.output.RichActiveOutputText;

 import javax.faces.context.ExternalContext;  
 import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletRequest;  
 import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;

import weblogic.servlet.security.ServletAuthentication;  


public class LandingPage {
    private RichForm f1;
    private RichDocument d1;
    private RichActiveOutputText aot1;
    private RichPanelStretchLayout psl1;
    private RichButton b1;
    private RichPanelGroupLayout pgl1;
    private RichOutputFormatted of1;


    public void setF1(RichForm f1) {
        this.f1 = f1;
    }

    public RichForm getF1() {
        return f1;
    }

    public void setD1(RichDocument d1) {
        this.d1 = d1;
    }

    public RichDocument getD1() {
        return d1;
    }

    public void setAot1(RichActiveOutputText aot1) {
        this.aot1 = aot1;
    }

    public RichActiveOutputText getAot1() {
        return aot1;
    }

    public void setPsl1(RichPanelStretchLayout psl1) {
        this.psl1 = psl1;
    }

    public RichPanelStretchLayout getPsl1() {
        return psl1;
    }

    public void setB1(RichButton b1) {
        this.b1 = b1;
    }

    public RichButton getB1() {
        return b1;
    }

    public void setPgl1(RichPanelGroupLayout pgl1) {
        this.pgl1 = pgl1;
    }

    public RichPanelGroupLayout getPgl1() {
        return pgl1;
    }
    public void logout(ActionEvent ae){
                 FacesContext context = FacesContext.getCurrentInstance();
                 ExternalContext ectx = context.getExternalContext();  
                 String url = ectx.getRequestContextPath() + "/faces/Login.jsf"; 
                 HttpSession session = (HttpSession)ectx.getSession(false);  
                 session.invalidate();  
                   
                 HttpServletRequest request = (HttpServletRequest)ectx.getRequest();  
//                 ServletAuthentication.invalidateAll(request);  
//                 ServletAuthentication.killCookie(request);  
                 ServletAuthentication.logout(request);  
                   
                 try{  
                    ectx.redirect(url); 
                 }  
                 catch(Exception e){  
                   e.printStackTrace();  
                 }  
                 context.responseComplete();  
                   
                 return;  
               }

    public void setOf1(RichOutputFormatted of1) {
        this.of1 = of1;
    }

    public RichOutputFormatted getOf1() {
        return of1;
    }
}
