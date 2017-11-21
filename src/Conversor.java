


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class Conversor {
ArrayList<String> tokens = new ArrayList<String>();
ArrayList<String> tokensMepa = new ArrayList<String>();

    BufferedReader lerArq;

    Conversor(File arq) throws FileNotFoundException {
        lerArq = new BufferedReader(new FileReader(arq));
    }

    public void gerarTokens() {
        String linha = "";
        String[] palavra = new String[0];

        try {
            while(linha != null){
                linha = lerArq.readLine();
                palavra = linha.split(" ");

                for(int i = 0; i < palavra.length; i++){
                    if(palavra[i] != " " && palavra[i] != "\n" && palavra[i] != "\r" && palavra[i].length()>0) {
                        tokens.add(palavra[i]);
                    }

                }

                palavra = new String[0];
            }


            printar();
            // if( palavrasReconhecidas(palavra) ){
            //  palavra = "";
            //}

        }catch(Exception e){
            System.out.println("Erro classe Conversor: " + e);
        }

    }


    void palavrasReconhecidas(String palavra){
        palavra  = palavra.toUpperCase();

        switch(palavra){
            case "PROGRAM" :  tokensMepa.add("INPP"); break;
            case "VAR"     :  tokensMepa.add(palavra);break;
            case "INTERGER":  break;
            case "BOOLEAN" :  break;
            case "CHAR"    :  break;
            case "STRING"  :  break;
            case "DOUBLE"  :  break;
            case "BEGIN"   :  break;
            case "END"     :  break;
            case "READ"    :  break;
            case "WHILE"   :  break;
            case "WRITE"   :  break;
            case "DO"      :  break;
            case "IF"      :  break;
            case "THEN"    :  break;
            case "ELSE"    :  break;
            case "."       :  break;
            case ","       :  break;
            case ";"       :  break;
            case ">"       :  break;
            case "<"       :  break;
            case "="       :  break;
            case "<="      :  break;
            case ">="      :  break;
            case ":="      :  break;
            case "+"       :  break;
            case "-"       :  break;
            case "*"       :  break;
            case "/"       :  break;
            case ":"       :  break;
            case " "       :  break;
            case "\n"      :  break;
            default        :  break;

        }



    }


    void printar(){
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



