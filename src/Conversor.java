// Douglas Henrique de Souza Lima --- 551066

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
    ArrayList<String> vetorBlocos = new ArrayList<String>();

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



            //System.out.println("Tokens Pascal: *********** ");
            //printar(tokensPascal);
            System.out.println("Tokens Mepa  : *********** ");
            printar(tokensMepa);


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

    public void converter(){
        int contadorVariaveis = 0;

        System.out.println("Tokens NOVO MEPA  : ******************* ");

        for(int i=0; i < tokensMepa.size(); i++){
            System.out.println("******"+tokensMepa.get(i)+"******");


            // reconhecendo Program inicial.
            if(Objects.equals(tokensMepa.get(i), "INPP")){
                novoTokensMepa.add("INPP");
            }

            // reconhecendo variaveis.
             if(Objects.equals(tokensMepa.get(i), "VAR")){
                 while(!Objects.equals(tokensMepa.get(i), "BEGIN")){
                     if(verificarDeclaracao(tokensMepa.get(i))) {
                         contadorVariaveis++;
                         variaveis.add(tokensMepa.get(i));
                     }
                     i++;
                 }
                 novoTokensMepa.add("AMEM " + String.valueOf(contadorVariaveis) );
                 printar(novoTokensMepa);
             }

             // reconhecendo leitura.
             if(Objects.equals(tokensMepa.get(i), "LEIT")){
                 novoTokensMepa.add("LEIT");
                 int indiceVariavel=-1;
                 while(!Objects.equals(tokensMepa.get(i), ";")) {
                     indiceVariavel = verificarVariavelExistente(tokensMepa.get(i));
                     if(indiceVariavel != -1) {
                         novoTokensMepa.add("ARMZ " + String.valueOf(indiceVariavel));
                     }
                     i++;
                 }
             }


             //reconhecendo escrita.
            if(Objects.equals(tokensMepa.get(i), "IMPR")){
                int indiceVariavel=-1;
                while(!Objects.equals(tokensMepa.get(i), ";")) {
                    indiceVariavel = verificarVariavelExistente(tokensMepa.get(i));
                    if(indiceVariavel != -1) {
                        novoTokensMepa.add("CRVL " + String.valueOf(indiceVariavel));
                        novoTokensMepa.add("IMPR");
                    }
                    i++;
                }
            }

            // reconhecendo while.
            if(Objects.equals(tokensMepa.get(i), "WHILE")){
                vetorBlocos.add("L" + String.valueOf(vetorBlocos.size()));
                novoTokensMepa.add(vetorBlocos.get(vetorBlocos.size()-1) + " NADA");
                reconhecerExpressao(i);
                int aux = Integer.parseInt(vetorBlocos.get(vetorBlocos.size()-1).substring(1, 2));
                novoTokensMepa.add("DSVS L" + String.valueOf(aux+1));
            }

            if(Objects.equals(tokensMepa.get(i), "END;")){
                novoTokensMepa.add("DSVS " + vetorBlocos.get(vetorBlocos.size()-1));
                vetorBlocos.add("L" + String.valueOf(vetorBlocos.size()));
                novoTokensMepa.add(vetorBlocos.get(vetorBlocos.size()-1) + " NADA");
            }


            // reconhecendo atribuição, com ou sem expressão.
            if(Objects.equals(tokensMepa.get(i), "ARMZ")){
                int indiceVariavelProx = verificarVariavelExistente(tokensMepa.get(i+1));
                int indiceVariavelAnt = verificarVariavelExistente(tokensMepa.get(i-1));

                if(!Objects.equals(tokensMepa.get(i + 2), ";")) {
                    while (!Objects.equals(tokensMepa.get(i), ";")) {

                        if (verificarOperacoes(tokensMepa.get(i))) {
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
                    if( verificarVariavelExistente(tokensMepa.get(i+1))  != -1 ){
                        novoTokensMepa.add("CRVL " + String.valueOf(verificarVariavelExistente(tokensMepa.get(i+1))));
                    } else{
                        novoTokensMepa.add("CRCT " + String.valueOf(tokensMepa.get(i+1)));
                    }
                }
                novoTokensMepa.add("ARMZ " + String.valueOf(indiceVariavelAnt));
            }

            // reconhecendo final 'end.'.
            if(Objects.equals(tokensMepa.get(i), "END.")){
                novoTokensMepa.add("DMEM " + variaveis.size());
                novoTokensMepa.add("PARA");
            }


        } // FINAL DO FOR.

        printar(novoTokensMepa);

    } // FINAL DO MÉTODO.


    private void reconhecerExpressao(int i){

        if(Objects.equals(tokensMepa.get(i), "ARMZ")){
            int indiceVariavelProx = verificarVariavelExistente(tokensMepa.get(i+1));
            int indiceVariavelAnt = verificarVariavelExistente(tokensMepa.get(i-1));

            if(!Objects.equals(tokensMepa.get(i + 2), ";")) {
                while (!Objects.equals(tokensMepa.get(i), ";")) {

                    if (verificarOperacoes(tokensMepa.get(i))) {
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
                if( verificarVariavelExistente(tokensMepa.get(i+1))  != -1 ){
                    novoTokensMepa.add("CRVL " + String.valueOf(verificarVariavelExistente(tokensMepa.get(i+1))));
                } else{
                    novoTokensMepa.add("CRCT " + String.valueOf(tokensMepa.get(i+1)));
                }
            }
            novoTokensMepa.add("ARMZ " + String.valueOf(indiceVariavelAnt));
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



    void printar(ArrayList<String> tokens){
        for(int i = 0; i < tokens.size(); i++){
            System.out.println(tokens.get(i));
        }
    }

}





/*
Program teste;
var a, b, c, d: integer;
begin
       read(a,b);
      c := a+b;
       while (c >= 0) do
      begin
             c:= c-1;
             if (c >= a) then
                   d := d + c*2;
            else
                   d:=d + c*3;
     end
    write( a, b, d);
end.
*/



