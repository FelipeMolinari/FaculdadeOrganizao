package com.example.faculdadeorganizao.DAO;

import com.example.faculdadeorganizao.model.Disciplina;

import java.util.ArrayList;

public class ListaDisciplinaDAO {
    private static final ListaDisciplinaDAO ourInstance = new ListaDisciplinaDAO();

    public ArrayList<Disciplina> listaDisciplinas;
    public static int id_nova_disciplina;

    public static ListaDisciplinaDAO getInstance() {
        return ourInstance;
    }


    private ListaDisciplinaDAO() {
        listaDisciplinas = new ArrayList<>();
        id_nova_disciplina = 0;
    }

    public void adcDisciplina(Disciplina nova_disciplina)
    {
        nova_disciplina.setId_disciplina(id_nova_disciplina);
        listaDisciplinas.add(nova_disciplina);
        id_nova_disciplina ++;
    }

    public ArrayList<Disciplina> retornaLista(){
        return  listaDisciplinas;
    }



}
