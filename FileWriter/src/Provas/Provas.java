
package Provas;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Provas {
    
    public static void add_gabs() throws IOException {
        ArrayList<String> alunos = new ArrayList();
        ArrayList<String> respostas = new ArrayList();
        
        String materia = JOptionPane.showInputDialog(null,"De qual matéria é o teste ?", "Matéria", 3);
        materia = materia.toLowerCase();
        String caminho = materia.toUpperCase() + "/" + materia + "_oficial.txt";
        String caminho2 = materia.toUpperCase() + "/" + materia + "_respostas.txt";
        File gab_materia = new File(caminho);
        File gab_alunos = new File(caminho2);

        if(gab_alunos.exists()) {
            JOptionPane.showMessageDialog(null, "A turma já foi pontuada nessa matéria.", "Atençao", 1);   
            return;
        }
        else {
            new File(materia.toUpperCase()).mkdir();
        }
        
        if(!gab_materia.exists()){
            JOptionPane.showMessageDialog(null, "O gabarito desse teste ainda não foi adicionado.", "Atençao", 1);
            String gabarito = JOptionPane.showInputDialog(null, "Insira o gabarito oficial do teste(Sem espaços em branco, somente V ou F):", "Gabarito", 1).toUpperCase();      
            Arq.write(gabarito, caminho);
        }
        
        int Nalunos = Integer.parseInt(JOptionPane.showInputDialog(null,"São quantos alunos ?", "Menu", 3));
        
        for(int i=1; i<=Nalunos; i++) {
            alunos.add(JOptionPane.showInputDialog(null, "Nome do aluno "+i+":", null, 3));
        }
        
        JOptionPane.showMessageDialog(null, "Agora coloque as respostas de cada um(Sem espaços; 10 questões)!", "Atenção", 1);
    
        for(String a: alunos) {
            respostas.add(JOptionPane.showInputDialog(null, "Respostas de "+a+":", null, 3).toUpperCase());
        }
        
        
        //Escreve no arquivo 
        
        String conteudo = "";
        int indice = 0;
        
        for(String a: alunos) {
            conteudo +=  respostas.get(indice) + "\t" + a + "\n";
            indice += 1;
        }
        
        Arq.write(conteudo, caminho2);
        
        ArrayList<String> alunostmp = alunos;   
        ArrayList<String> respostastmp = respostas;  
        
        
        String oficial = Arq.read(caminho).replace("\n", "");
        
        if(oficial.equals("Error")) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao ler o arquivo", "Erro", 0);
            return;
        }
        
        int pontos = 0;
        
        try {
            for(int i = 0; i<=Nalunos-1; i++) {
                if(respostas.get(i).equals("FFFFFFFFFF") || respostas.get(i).equals("VVVVVVVVVV")){
                    respostas.set(i, "0");
                }
                else {
                    for(int p=0; p<=9; p++) {
                        if(respostas.get(i).charAt(p) == oficial.charAt(p)) {
                            pontos += 1;
                        }
                    }
                    respostas.set(i, String.valueOf(pontos));
                    pontos = 0;
                }
            }
        }
        catch(StringIndexOutOfBoundsException se) {
            JOptionPane.showMessageDialog(null, "A PROVA TEM 10 QUESTÕES! Insira 10 V ou F.", "Erro", 0);
            return;
        }
        
        for (int i = 0; i < alunos.size(); i++) {
            for (int j = alunos.size() - 1; j > i; j--) {
                if (alunos.get(i).compareToIgnoreCase(alunos.get(j)) > 0) {

                    String tmp = alunos.get(i);
                    alunostmp.set(i, alunos.get(j));
                    alunostmp.set(j, tmp);
                    String aux = respostastmp.get(i);
                    respostastmp.set(i, respostas.get(j));
                    respostastmp.set(j, aux);
                }
            }
        }
        
        respostas = respostastmp;
        indice = 0;
        conteudo = "";
        
        for(String a: alunostmp) {
            conteudo +=  respostas.get(indice) + " pontos" + "\t" + a + "\n";
            indice += 1;
        }
        
        Arq.write(conteudo, materia.toUpperCase() + "/" + materia + "_alfabetica.txt");
        
        
        
        for (int i = 1; i < Nalunos; i++) {
            for (int j = 0; j < i; j++) {
                if (Integer.parseInt(respostas.get(i)) >  Integer.parseInt(respostas.get(j))) {
                    int temp = Integer.parseInt(respostas.get(i));
                    respostas.set(i, respostas.get(j));
                    respostas.set(j,String.valueOf(temp));
                    alunostmp.set(i, alunostmp.get(j));
                    alunostmp.set(j, alunostmp.get(i));
                }
            }
        }
        
        indice = 0;
        int media = 0;
        conteudo = "";
        
        for(String a: alunostmp) {
            conteudo +=  respostas.get(indice) + " pontos" + "\t" + a + "\n";
            media += Integer.parseInt(respostas.get(indice));
            indice += 1;
        }
        
        conteudo += "\n\nMédia da turma: " + media/Nalunos;
        
        
        Arq.write(conteudo, materia.toUpperCase() + "/" + materia + "_media.txt");
        
        JOptionPane.showMessageDialog(null, "Tudo Pronto!", null, 1);

    }
    
    public static void ver_cami() {
        String materia = JOptionPane.showInputDialog(null,"De qual matéria é o teste ?", "Matéria", 3);
        materia = materia.toLowerCase();
        
        File arquivo = new File(materia.toUpperCase() + "/" + materia + "_oficial.txt");
        
        if(!arquivo.exists()) {
            JOptionPane.showMessageDialog(null, "Gabarito não registrado!", "Erro", 0);
            return;
        }
  
        JOptionPane.showMessageDialog(null, "O gabarito oficial está em :\n\n"+arquivo.getAbsolutePath(), null, 1);   
    }
    
    public static void ver_gabs() throws IOException{
        String materia = JOptionPane.showInputDialog(null,"De qual matéria é o teste ?", "Matéria", 3);
        materia = materia.toLowerCase();
        String arquivo = materia.toUpperCase() + "/" +materia+"_alfabetica.txt";
        File f = new File(arquivo);
        String data;
        
        if(!f.exists()) {
            JOptionPane.showMessageDialog(null, "Gabarito não registrado!", "Erro", 0);
            return;
        }
        
        data = Arq.read(arquivo);
        
        StringBuilder Tbuilder;
        String[] notas = data.split("\n");
        data = "";
        
        for(String nota: notas) {
            Tbuilder = new StringBuilder(nota);
            if(nota.charAt(0) == '1' || nota.charAt(1) == '0') {
                Tbuilder = Tbuilder.insert(9, "       ");
            }
            else {
                Tbuilder = Tbuilder.insert(8, "          ");
            }
            data += Tbuilder.toString() + "\n";
        }
        
        JOptionPane.showMessageDialog(null, data, "Resultado", 1);
    }
        
    public static void main(String[] args) throws IOException {
        
        while(true) {
            int escolha = Integer.parseInt(JOptionPane.showInputDialog(null,"O que deseja fazer ?\n\n"
                                                            + "1-Visualizar Pontuação\n"
                                                            + "2-Adicionar Gabarito\n"
                                                            + "3-Visualizar localização do gabarito oficial\n\n"
                                                            + "0-Sair", "Menu", 3));

            if(escolha == 1) {
                ver_gabs();
            }
            else if(escolha == 2) {
                add_gabs();
            }
            else if(escolha == 3) {
                ver_cami();
            }
            else if(escolha == 0) {
                break;
            }

        }
    }
}
