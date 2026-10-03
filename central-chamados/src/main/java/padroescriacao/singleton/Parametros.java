package padroescriacao.singleton;

public class Parametros {

    private Parametros() {}

    private static final Parametros instance = new Parametros();

    public static Parametros getInstance() {
        return instance;
    }

    private String nomeSistema = "Central de Chamados";

    public String getNomeSistema() {
        return nomeSistema;
    }

    public void setNomeSistema(String nomeSistema) {
        if (nomeSistema == null || nomeSistema.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do sistema deve ser informado");
        }
        this.nomeSistema = nomeSistema.trim();
    }
}
