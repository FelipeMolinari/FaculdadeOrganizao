package com.example.faculdadeorganizao.model;

public class Atividade {

    private String nomeAtividade;
    private String dataAtividade;
    private String descricaoAtividade;
    private int id_atividade;
    private int colorAtividade;

    public Atividade(String nomeAtividade, int colorAtividade, String descricaoAtividade, String dataAtividade) {
        this.nomeAtividade = nomeAtividade;
        this.dataAtividade = dataAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.colorAtividade = colorAtividade;
    }

    public Atividade() {

    }


    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public void setDataAtividade(String dataAtividade) {
        this.dataAtividade = dataAtividade;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public String getDataAtividade() {
        return dataAtividade;
    }

    public String getDescricaoAtividade() {
        return descricaoAtividade;
    }


    public void setId_disciplina(int id_disciplina) {
        this.id_atividade = id_disciplina;
    }

    public int getColorAtividade() {
        return colorAtividade;
    }

    public void setColorAtividade(int colorAtividade) {
        this.colorAtividade = colorAtividade;
    }
}
