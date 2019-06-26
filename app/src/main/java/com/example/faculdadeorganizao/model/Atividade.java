package com.example.faculdadeorganizao.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.*;

@Entity
public class Atividade {

    @PrimaryKey(autoGenerate = true)
    private Long id_atividade;

    @ForeignKey(entity = Disciplina.class
            , parentColumns = "id_disciplina",
            childColumns = "disciplinaid",
            onUpdate = CASCADE,
            onDelete = CASCADE)
    private long disciplinaid;


    private String nomeAtividade;
    private String dataAtividade;
    private String descricaoAtividade;
    private int colorAtividade;
    private float notaDaAtividade;
    private float notaTirada;


    public float getNotaDaAtividade() {
        return notaDaAtividade;
    }

    public void setNotaDaAtividade(float notaDaAtividade) {
        this.notaDaAtividade = notaDaAtividade;
    }

    public float getNotaTirada() {
        return notaTirada;
    }

    public void setNotaTirada(float notaTirada) {
        this.notaTirada = notaTirada;
    }

    @Ignore
    public Atividade(Long id_atividade, long disciplinaid, String nomeAtividade, String dataAtividade, String descricaoAtividade, int colorAtividade, float notaDaAtividade, float notaTirada) {
        this.id_atividade = id_atividade;
        this.disciplinaid = disciplinaid;
        this.nomeAtividade = nomeAtividade;
        this.dataAtividade = dataAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.colorAtividade = colorAtividade;
        this.notaDaAtividade = notaDaAtividade;
        this.notaTirada = notaTirada;
    }

    @Ignore
    public Atividade(Long id_atividade, int disciplinaid, String nomeAtividade, String dataAtividade, String descricaoAtividade, int colorAtividade) {
        this.id_atividade = id_atividade;
        this.disciplinaid = disciplinaid;
        this.nomeAtividade = nomeAtividade;
        this.dataAtividade = dataAtividade;
        this.descricaoAtividade = descricaoAtividade;
        this.colorAtividade = colorAtividade;
    }

    @Ignore
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


    public Long getId_atividade() {
        return id_atividade;
    }

    public void setId_atividade(Long id_atividade) {
        this.id_atividade = id_atividade;
    }

    public Long getDisciplinaid() {
        return disciplinaid;
    }

    public void setDisciplinaid(Long disciplinaid) {
        this.disciplinaid = disciplinaid;
    }

    public int getColorAtividade() {
        return colorAtividade;
    }

    public void setColorAtividade(int colorAtividade) {
        this.colorAtividade = colorAtividade;
    }
}
