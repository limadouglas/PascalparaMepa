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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;


public class Conversor {
    ArrayList<String> tokensMepa = new ArrayList<String>();
    ArrayList<String> novoTokensMepa = new ArrayList<String>();
    ArrayList<String> variaveis = new ArrayList<String>();
    ArrayList<String> vetorBlocosAbertos = new ArrayList<String>();
    ArrayList<String> vetorBlocosFechados = new ArrayList<String>();
    boolean fecharSalto = false;

    BufferedReader lerArq;

    Conversor(File arq) throws FileNotFoundException {
        lerArq = new BufferedReader(new FileReader(arq));
    }

    public void gerarTokens() {
        String linha = "";
        String[] palavra = new String[0];

        try {
            linha = lerArq.readLine();
            while(linha != null){

                palavra = linha.split(" ");

                for(int i = 0; i < palavra.length; i++){
                    if(palavra[i] != "" && palavra[i] != " " && palavra[i] != "\n" && palavra[i] != "\r" && palavra[i].length()>0) {
                        palavrasReconhecidas(palavra[i]);
                    }
                }
                palavra = new String[0];
                linha = lerArq.readLine();
            }


        }catch(Exception e){
            System.out.println("Erro classe Conversor: " + e);
        }

    }


    void palavrasReconhecidas(String palavra){
        palavra  = palavra.toUpperCase();

        switch(palavra){
            case "PROGRAM" :  tokensMepa.add("INPP") ;break;
            case "READ"    :  tokensMepa.add("LEIT") ;break;
            case "WRITE"   :  tokensMepa.add("IMPR") ;break;
            case "AND"     :  tokensMepa.add("CONJ") ;break;
            case "OR"      :  tokensMepa.add("DISJ") ;break;
            case ">"       :  tokensMepa.add("CMMA");break;
            case "<"       :  tokensMepa.add("CMME");break;
            case "=="      :  tokensMepa.add("CMIG");break;
            case "!="      :  tokensMepa.add("CMDG");break;
            case "<="      :  tokensMepa.add("CMEG");break;
            case ">="      :  tokensMepa.add("CMAG");break;
            case ":="      :  tokensMepa.add("ARMZ");break;
            case "+"       :  tokensMepa.add("SOMA");break;
            case "-"       :  tokensMepa.add("SUBT");break;
            case "*"       :  tokensMepa.add("MULT");break;
            case "/"       :  tokensMepa.add("DIVI");break;
            case "VAR"     :
            case "INTEGER" :
            case "BOOLEAN" :
            case "CHAR"    :
            case "STRING"  :
            case "DOUBLE"  :
            case "BEGIN"   :
            case "END"     :
            case "WHILE"   :
            case "DO"      :
            case "IF"      :
            case "THEN"    :
            case "ELSE"    :
            case "."       :
            case ","       :
            case ";"       :
            case ":"       :
            default        :  tokensMepa.add(palavra);break;
        }
    }

    public void converter() {
        int contadorVariaveis = 0;

        System.out.println("\n ******* MEPA ******* \n");

        for (int i = 0; i < tokensMepa.size(); i++) {

            // reconhecendo Program inicial.
            if (Objects.equals(tokensMepa.get(i), "INPP")) {
                novoTokensMepa.add("INPP");
            }

            // reconhecendo variaveis.
            if (Objects.equals(tokensMepa.get(i), "VAR")) {
                while (!Objects.equals(tokensMepa.get(i), "BEGIN")) {
                    if (verificarDeclaracao(tokensMepa.get(i))) {
                        contadorVariaveis++;
                        variaveis.add(tokensMepa.get(i));
                    }
                    i++;
                }
                novoTokensMepa.add("AMEM " + String.valueOf(contadorVariaveis));
            }

            // reconhecendo leitura.
            if (Objects.equals(tokensMepa.get(i), "LEIT")) {
                int indiceVariavel = -1;
                while (!Objects.equals(tokensMepa.get(i), ";")) {
                    indiceVariavel = verificarVariavelExistente(tokensMepa.get(i));
                    if (indiceVariavel != -1) {
                        novoTokensMepa.add("LEIT");
                        novoTokensMepa.add("ARMZ " + String.valueOf(indiceVariavel));
                    }
                    i++;
                }
            }


            //reconhecendo IMPRIMIR.
            if (Objects.equals(tokensMepa.get(i), "IMPR")) {
                int indiceVariavel = -1;
                while (!Objects.equals(tokensMepa.get(i), ";")) {
                    indiceVariavel = verificarVariavelExistente(tokensMepa.get(i));
                    if (indiceVariavel != -1) {
                        novoTokensMepa.add("CRVL " + String.valueOf(indiceVariavel));
                        novoTokensMepa.add("IMPR");
                    }
                    i++;
                }
            }

            // reconhecendo WHILE.
            if (Objects.equals(tokensMepa.get(i), "WHILE")) {
                vetorBlocosAbertos.add("L" + String.valueOf(vetorBlocosAbertos.size()));
                novoTokensMepa.add(vetorBlocosAbertos.get(vetorBlocosAbertos.size() - 1) + " NADA");
                while (!verificarComparacao(tokensMepa.get(i))) {
                    i++;
                } // ficar em loop enquanto não encontrar comparador
                reconhecerExpressaoComparacao(i);
                int aux = Integer.parseInt(vetorBlocosAbertos.get(vetorBlocosAbertos.size() - 1).substring(1, 2));
                novoTokensMepa.add("DSVF L" + String.valueOf(aux + 1));
                vetorBlocosFechados.add(vetorBlocosAbertos.get(vetorBlocosAbertos.size()-1));
            }

            // reconhecendo IF.
            if (Objects.equals(tokensMepa.get(i), "IF")) {

                //novoTokensMepa.add(vetorBlocos.get(vetorBlocos.size()-1) + " NADA");
                while (!verificarComparacao(tokensMepa.get(i))) {
                    i++;
                    if (i > 100000) break;
                } // ficar em loop enquanto não encontrar comparador
                reconhecerExpressaoComparacao(i);
                vetorBlocosAbertos.add("L" + String.valueOf(vetorBlocosAbertos.size()));
                vetorBlocosFechados.add(vetorBlocosAbertos.get(vetorBlocosAbertos.size()-1));
                //System.out.println(vetorBlocosAbertos.get(vetorBlocosAbertos.size() - 1).substring(1, 2)+"============");
                int aux = Integer.parseInt(vetorBlocosAbertos.get(vetorBlocosAbertos.size() - 1).substring(1, 2));
                novoTokensMepa.add("DSVF L" + String.valueOf(aux));

            }

            // reconhecendo atribuição, com ou sem expressão.
            if (Objects.equals(tokensMepa.get(i), "ARMZ")) {

                int indiceVariavelProx = verificarVariavelExistente(tokensMepa.get(i + 1));
                int indiceVariavelAnt = verificarVariavelExistente(tokensMepa.get(i - 1));

                if (!Objects.equals(tokensMepa.get(i + 2), ";")) {
                    while (!Objects.equals(tokensMepa.get(i), ";")) {

                        ArrayList<String> array = new ArrayList<String>();
                        ArrayList<String> arrayOrdenado = new ArrayList<String>();
                        i++;
                        while (!tokensMepa.get(i).equals(";")) {
                            array.add(tokensMepa.get(i));
                            i++;
                        }


                        for (int j = 0; j < array.size(); j++) {
                            if (Objects.equals(array.get(j), "MULT") || Objects.equals(array.get(j), "DIVI")) {

                                    if(j+1 < array.size()) {
                                        if (!verificarOperacoes(array.get(j + 1))) {
                                            arrayOrdenado.add(array.get(j + 1));
                                            array.remove(j + 1);
                                        }
                                    }
                                    if(array.size() >= 1) {
                                        if (!verificarOperacoes(array.get(j - 1))) {
                                            arrayOrdenado.add(array.get(j - 1));
                                            array.remove(j - 1);
                                            j--;
                                        }
                                    }

                                    arrayOrdenado.add(array.get(j));
                                    array.remove(j);

                                    j = 0;

                            }
                        }

                            for (int j = 0; j < array.size(); j++) {
                                if (array.size() > 1) {
                                    if (Objects.equals(array.get(j), "SOMA") || Objects.equals(array.get(j), "SUBT")) {
                                        if(j+1<array.size()) {
                                            if (!verificarOperacoes(array.get(j + 1))) {
                                                arrayOrdenado.add(array.get(j + 1));
                                                array.remove(j + 1);
                                            }
                                        }
                                        if(array.size() >= 1) {
                                            if (!verificarOperacoes(array.get(j - 1))) {
                                                arrayOrdenado.add(array.get(j - 1));
                                                array.remove(j - 1);
                                                j--;
                                            }
                                        }

                                        arrayOrdenado.add(array.get(j));
                                        array.remove(j);

                                        j = 0;
                                    }
                                }else{
                                    arrayOrdenado.add(array.get(j));
                                    array.remove(j);
                                }
                            }

                            for (int j = 0; j < arrayOrdenado.size(); j++) {
                                if (verificarVariavelExistente(arrayOrdenado.get(j)) != -1) {
                                    novoTokensMepa.add("CRVL " + String.valueOf(verificarVariavelExistente(arrayOrdenado.get(j))));
                                } else if (!verificarOperacoes(arrayOrdenado.get(j))) {
                                    novoTokensMepa.add("CRCT " + String.valueOf(arrayOrdenado.get(j)));
                                } else {
                                    novoTokensMepa.add(arrayOrdenado.get(j));
                                }
                            }
                        }
                    } else{
                        if (verificarVariavelExistente(tokensMepa.get(i + 1)) != -1) {
                            novoTokensMepa.add("CRVL " + String.valueOf(verificarVariavelExistente(tokensMepa.get(i + 1))));
                        } else {
                            novoTokensMepa.add("CRCT " + String.valueOf(tokensMepa.get(i + 1)));
                        }
                    }
                    novoTokensMepa.add("ARMZ " + String.valueOf(indiceVariavelAnt));
                    //  if(tokensMepa.get(i).equals(";"))i--;

                }

                if (Objects.equals(tokensMepa.get(i), "END;") || Objects.equals(tokensMepa.get(i), "ELSE") || (Objects.equals(tokensMepa.get(i), ";") && fecharSalto)) {
                    if (Objects.equals(tokensMepa.get(i), "END;") || Objects.equals(tokensMepa.get(i), "ELSE")) {
                        novoTokensMepa.add("DSVS " + vetorBlocosFechados.get(vetorBlocosFechados.size() - 1));
                        vetorBlocosFechados.remove(vetorBlocosFechados.size()-1);
                        //int aux = vetorBlocosAbertos.size();
                        vetorBlocosAbertos.add("L" + String.valueOf(vetorBlocosAbertos.size()));
                        vetorBlocosFechados.add(vetorBlocosAbertos.get(vetorBlocosAbertos.size()-1));
                        novoTokensMepa.add(vetorBlocosFechados.get(vetorBlocosFechados.size() - 1) + " NADA");


                    }

                    // reconhecendo ELSE.
                    if (Objects.equals(tokensMepa.get(i), "ELSE")) {


                        fecharSalto = true;
                    } else if ((Objects.equals(tokensMepa.get(i), ";") && fecharSalto)) { // problema aqui
                        novoTokensMepa.add(vetorBlocosFechados.get(vetorBlocosFechados.size() - 1) + " NADA");
                        vetorBlocosFechados.remove(vetorBlocosFechados.size()-1);
                        fecharSalto = false;
                    }
                }

                // reconhecendo final 'end.'.
                if (Objects.equals(tokensMepa.get(i), "END.")) {
                    novoTokensMepa.add("DMEM " + variaveis.size());
                    novoTokensMepa.add("PARA");
                }


            } // FINAL DO FOR.

            printar(novoTokensMepa);

        } // FINAL DO MÉTODO.


    private void reconhecerExpressaoComparacao(int i) {

        if(verificarComparacao(tokensMepa.get(i))){

            if(!Objects.equals(tokensMepa.get(i + 2), "DO") && !Objects.equals(tokensMepa.get(i + 2), "THEN") && !Objects.equals(tokensMepa.get(i + 2), ")")) {
                while (!Objects.equals(tokensMepa.get(i), "DO") && !Objects.equals(tokensMepa.get(i), "THEN") && !Objects.equals(tokensMepa.get(i), ")")) {

                    if (verificarComparacao(tokensMepa.get(i))) {
                        if( verificarVariavelExistente(tokensMepa.get(i-1)) != -1 ){
                            novoTokensMepa.add("CRVL " + String.valueOf(verificarVariavelExistente(tokensMepa.get(i-1))));
                        } else{
                            novoTokensMepa.add("CRCT " + String.valueOf(tokensMepa.get(i-1)));
                        }

                        if(Objects.equals(tokensMepa.get(i + 2), ";")) {
                            if( verificarVariavelExistente(tokensMepa.get(i+1))  != -1 ){
                                novoTokensMepa.add("CRVL " + String.valueOf(verificarVariavelExistente(tokensMepa.get(i+1))));
                            } else{
                                novoTokensMepa.add("CRCT " + String.valueOf(tokensMepa.get(i+1)));
                            }
                            novoTokensMepa.add(tokensMepa.get(i));
                        }
                    }
                    i++;
                }
            } else {

                if( verificarVariavelExistente(tokensMepa.get(i-1))  != -1 ){
                    novoTokensMepa.add( "CRVL " + String.valueOf(verificarVariavelExistente(tokensMepa.get(i-1))) );
                } else {
                    novoTokensMepa.add( "CRCT " + String.valueOf(tokensMepa.get(i-1)) );
                }

                if( verificarVariavelExistente(tokensMepa.get(i+1))  != -1 ){
                    novoTokensMepa.add( "CRVL " + String.valueOf(verificarVariavelExistente(tokensMepa.get(i+1))) );
                } else {
                    novoTokensMepa.add( "CRCT " + String.valueOf(tokensMepa.get(i + 1)) );
                }

            }
            novoTokensMepa.add(tokensMepa.get(i));
        }

    }


    // verifica a parte de declaração de variais, retorna true caso o token seja diferente de pontuações tipos de variaveis.
    private boolean verificarDeclaracao(String token){
        switch (token){
            case ";"       : return false;
            case "."       : return false;
            case ","       : return false;
            case ":"       : return false;
            case "VAR"     : return false;
            case "INTEGER" : return false;
            case "BOOLEAN" : return false;
            case "CHAR"    : return false;
            case "STRING"  : return false;
            case "DOUBLE"  : return false;
            default        : return true;
        }
    }

    private int verificarVariavelExistente(String token){
        for(int i = 0; i < variaveis.size(); i++){
            if( Objects.equals(token, variaveis.get(i)) ){
                return i;
            }
        }

        return -1;


    }

    private boolean verificarOperacoes(String token){
        switch(token) {
            case "SOMA": return true;

            case "SUBT": return true;

            case "MULT": return true;

            case "DIVI": return true;

            default    : return false;
        }
    }

    public boolean verificarComparacao(String token){
        switch (token){
            case "CONJ"     :  return true;
            case "DISJ"     :  return true;
            case "CMMA"     :  return true;
            case "CMME"     :  return true;
            case "CMIG"     :  return true;
            case "CMDG"     :  return true;
            case "CMEG"     :  return true;
            case "CMAG"     :  return true;
            default         :  return false;
        }
    }


    void printar(ArrayList<String> tokens){
        for(int i = 0; i < tokens.size(); i++){
            System.out.println(tokens.get(i));
        }
    }

}


/*

PROGRAM TESTE ;
    VAR N , K : INTEGER ;
        F1 , F2 , F3 : INTEGER ;
BEGIN
    READ ( N ) ;
    F1 := 0 ; F2 := 1 ; K := 1 ;
    WHILE K <= N DO
    BEGIN
        F3 := F1 + F2 ;
        F1 := F2 ;
        F2 := F3 ;
        K := K + 1 ;
    END;

    WRITE ( N , F1 ) ;

END.



PROGRAM TESTE ;
VAR A , B , C , D : INTEGER ;
BEGIN
      READ ( A , B ) ;
      C := A + B ;
      WHILE ( C >= 0 ) DO
      BEGIN
             C := C - 1 ;
             IF ( C >= A ) THEN
                   D := D + C * 2 ;
            ELSE
                   D := D + C * 3 ;
     END;
    WRITE( A , B , D ) ;
END.


*/

