package com.example.faculdadeorganizao.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    private String dataFormata;//YYYY-MMM-DD
    private String descricaoAtividade;
    private int colorAtividade;
    private float notaDaAtividade =0;
    private float notaTirada=0;
    private boolean atividadeCompleta;




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

    public void setAtividadeCompleta(boolean atividadeCompleta) {
        this.atividadeCompleta = atividadeCompleta;
    }

    public boolean isAtividadeCompleta() {
        return atividadeCompleta;
    }



    public String getDataFormata() {
        return dataFormata;
    }

    public void setDataFormata(String dataFormata) {
        this.dataFormata = dataFormata;
    }

}
