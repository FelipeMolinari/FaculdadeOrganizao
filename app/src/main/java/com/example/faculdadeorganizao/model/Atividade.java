package com.example.faculdadeorganizao.model;

import java.util.Calendar;
import java.util.Date;

public class Atividade {

    private String nomeAtividade;
    private Calendar dataAtividade;
    private String descricaoAtividade;
    private int id_atividade;

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public void setDataAtividade(Calendar dataAtividade) {
        this.dataAtividade = dataAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public Calendar getDataAtividade() {
        return dataAtividade;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }

    public Atividade(String nomeAtividade, Calendar dataAtividade, String descricaoAtividade) {
        this.nomeAtividade = nomeAtividade;
        this.dataAtividade = dataAtividade;
        this.descricaoAtividade = descricaoAtividade;
    }

    public void setId_disciplina(int id_disciplina) {
        this.id_atividade = id_disciplina;
    }

}
