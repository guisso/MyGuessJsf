package io.github.guisso.jakartaee8.myguessjsf;

import java.io.Serializable;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Named
@RequestScoped
public class ErrorBean 
        implements Serializable {

    public String processError() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
        
        StringBuilder reqString = new StringBuilder(">> Request map:\n");
        
        requestMap.entrySet().forEach(
                e -> reqString.append(
                        e.getKey()
                        + ":"
                        + e.getValue()
                        + "\n"
                ));
        
        System.out.println(reqString.toString());

        Integer httpStatusCode = (Integer) requestMap.get("javax.servlet.error.status_code");
        System.out.println(">> HTTP status code: " + httpStatusCode);

        String exceptionDescription = (String) requestMap.get("javax.servlet.error.message");
        System.out.println(">> Message: " + exceptionDescription);

        String requestUri = (String) requestMap.get("javax.servlet.error.request_uri");
        System.out.println(">> Request URI: " + requestUri);

        String servletName = (String) requestMap.get("javax.servlet.error.servlet_name");
        System.out.println(">> Servlet name: " + servletName);

        Throwable exception = (Throwable) requestMap.get("javax.servlet.error.exception");
        System.out.println(">> Throwable: " + exception);

        // Registrar exceção no log, 
        // enviar um email para o administrador
        // ou outras providências junto ao sistema.
        
        return exceptionDescription;
    }

}
