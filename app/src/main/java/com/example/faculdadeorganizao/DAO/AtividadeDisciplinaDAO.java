package com.example.faculdadeorganizao.DAO;

import com.example.faculdadeorganizao.model.Atividade;

import java.util.ArrayList;

public class AtividadeDisciplinaDAO {
    private static final AtividadeDisciplinaDAO ourInstance = new AtividadeDisciplinaDAO();

    private ArrayList<Atividade> atividadeArrayList;
    public static int id_nova_atividade;


    public static AtividadeDisciplinaDAO getInstance() {
        return ourInstance;
    }

    private AtividadeDisciplinaDAO() {
        atividadeArrayList = new ArrayList<Atividade>();
    }

    public void adcAtividade(Atividade novaAtividade){
        novaAtividade.setId_disciplina(id_nova_atividade);
        atividadeArrayList.add(novaAtividade);
        id_nova_atividade ++;

    }

    public ArrayList<Atividade> getAtividadeArrayList() {
        return atividadeArrayList;
    }
}
