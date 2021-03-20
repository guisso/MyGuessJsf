package io.github.guisso.jakartaee8.myguessjsf;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Named
@RequestScoped
public class WillCrashBean {

    public String throwARandomException()
            throws ArithmeticException, ArrayIndexOutOfBoundsException {

        // Generates an arbitrary error
        if (Math.random() > .5) {
            int a[] = {0, 1, 2};
            System.out.println("a[9] = " + a[9]);
        } else {
            System.out.println("x = " + (9 / 0));
        }
        
        // Catch and process the exception
        try {
            // Exception!
            // int x = 1 / 0;
            
        } catch(ArithmeticException ex) {
            // Handles the execption, generates a log, ...
            // AdminLog.saveOnRemoteDatabase(...);
            
            // Sends the exception to another layers
            // throw ex;
        }

        return "index";
    }
    
    public String goToNonExistentPage() {
        // Developer fail...
        return "/WEB-INF/nonexistentpage";
    }
    
    public Long throwServerError() {
        return throwServerError();
    }

}
