package aplicacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import padroescomportamentais.observer.PainelChamados;
import padroescriacao.singleton.Parametros;

@Configuration
public class Configuracao {

    @Bean
    public PainelChamados painelChamados() {
        return new PainelChamados();
    }

    @Bean
    public Parametros parametros(@Value("${app.nome:Central de Chamados}") String nome) {
        Parametros parametros = Parametros.getInstance();
        parametros.setNomeSistema(nome);
        return parametros;
    }
}
