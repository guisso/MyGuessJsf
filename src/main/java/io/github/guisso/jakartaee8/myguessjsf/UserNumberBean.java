package io.github.guisso.jakartaee8.myguessjsf;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * A session CDI managed bean that controls the business
 * logic of the application: draws a random number in a range
 * and generates actions and responses based on the user's 
 * guesses.
 * 
 * @author Luis Guisso <luis.guisso at ifnmg.edu.br>
 */
@Named
@SessionScoped
public class UserNumberBean implements Serializable {

    private final Integer random;
    private final Integer minimum;
    private final Integer maximum;
    private Integer guess;

    public UserNumberBean() {
        minimum = 1;
        maximum = 10;
        random = (int) (Math.random()
                * (maximum - minimum + 1))
                + minimum;
        System.out.println(">> Sorteado: " + random);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Integer getRandom() {
        return random;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Integer getGuess() {
        return guess;
    }

    public void setGuess(Integer guess) {
        this.guess = guess;
    }
    //</editor-fold>

    public String getResponse() {
        // Recupera contexto da aplicação
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        
        // Recupera fonte de recursos para internacionalização
        ResourceBundle resourceBundle = application.getResourceBundle(context, "i18n");

        if (random.equals(guess)) {
            // Destrói sessão
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            
            // Recupera e devolve resposta de sucesso
            return resourceBundle.getString("response.success");
        } else {
            
            // Recupera e devolve resposta de falha
            return resourceBundle.getString("response.fail");
        }
    }
}