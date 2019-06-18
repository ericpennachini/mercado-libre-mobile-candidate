package ar.com.mercadolibre.productsearch.interfaces;

public interface ICustomActivity {
    /**
     * Obtiene los elementos visuales del layout del activity e inicializa
     * su comportamiento de ser necesario.
     */
    void initializeLayoutElements();

    /**
     * Inicia el comportamiento del activity a nivel visual y de código. Si el activity
     * realiza una tarea de fondo o si algunos elementos visuales deben ocultarse o mostrarse
     * mientras el activity está trabajando.
     */
    void startActivityWork();

    /**
     * Vuelve a mostrar los elementos visuales que se ocultan durante el trabajo de fondo que
     * realiza el activity. Salvo aquellos que no dependan solo del inicio y fin de dicho proceso.
     */
    void endActivityWork();

    /**
     * Muestra los elementos visuales que indican que no hay conexión a internet
     */
    void showNoConnectionStatus();

    /**
     * Oculta los elementos visuales que indican que no hay conexión a internet (indica que la
     * conexión se restableció).
     */
    void hideNoConnectionStatus();
}
