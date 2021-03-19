package io.github.guisso.jakartaee8.myguessjsf;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * A session CDI managed bean that controls the business logic of the
 * application: draws a random number in a range and generates actions and
 * responses based on the user's guesses.
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

    // Language specified by user
    private String lang;

    // To disable guesses already done
    private boolean[] disabled;

    public UserNumberBean() {

        // Default language
        lang = "pt-BR";

        minimum = 1;
        maximum = 10;
        random = (int) (Math.random()
                * (maximum - minimum + 1))
                + minimum;

        // Default state for all guess buttons: false
        disabled = new boolean[maximum - minimum + 1];

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
        disabled[guess - minimum] = true;
        this.guess = guess;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean[] getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean[] disabled) {
        this.disabled = disabled;
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

    public String defineLang(String lang) {
        this.setLang(lang);

        // Null return refreshes the page
        // without navigation to another destination
        return null;
    }

    public String getPreviousView() {
        // Expression Language: #{header['referer'])}
        String referer = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestHeaderMap()
                .get("referer");
        int pos1 = referer.lastIndexOf('/');
        int pos2 = referer.lastIndexOf(".xhtml");
        return referer.substring(pos1, pos2);
    }
    
    public List<Integer> getRange() {
        return IntStream
                .rangeClosed(minimum, maximum)
                .boxed()
                .collect(Collectors.toList());
    }

}
