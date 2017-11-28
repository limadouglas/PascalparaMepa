// Douglas Henrique --- 551066
// Matheus Ferreira --- 547980

/* OBSERVAÇÕES

    O script em pascal tem que ter todos os tokens separados por um espaço,
    exemplo correto  : a := 1 + 1 ;
    exemplo incorreto: a:=1+1;

    As ultimas linhas deste arquivo possuem dois codigos em pascal testados neste exemplo.

    todos os exercicios do trabalho foram implementados!

    o codigo mepa é mostrado no console e não em um arquivo separado!

 */

import java.io.File;
import java.io.FileNotFoundException;

public class Principal {
    public static void main(String[] args) throws FileNotFoundException {

        File arquivo = new File( System.getProperty("user.dir")+"/src/script.pas");
        System.out.println(arquivo);
        Conversor conversor = new Conversor(arquivo);
        conversor.gerarTokens();
        conversor.converter();
    }
}
