import java.io.File;
import java.io.FileNotFoundException;

public class Principal {
    public static void main(String[] args) throws FileNotFoundException {

        File arquivo = new File( System.getProperty("user.dir")+"/src/script.pas");
        System.out.println(arquivo);
        Conversor conversor = new Conversor(arquivo);
        conversor.gerarTokens();
        conversor.printar();
    }
}
