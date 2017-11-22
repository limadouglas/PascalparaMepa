


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class Conversor {
ArrayList<String> tokensPascal = new ArrayList<String>();
ArrayList<String> tokensMepa = new ArrayList<String>();

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
            case "INTERGER":
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



