package com.example.faculdadeorganizao.model;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Disciplina implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id_disciplina;
    private String nome_disciplina;
    private String nome_sala;
    private String nome_professor;
    private String sobre_disciplina;
    private String status_disciplina;
    private boolean completa;
    private float nota_obtida=0;
    private float nota_distribuida = 0;
    private int limiteFaltas;
    private int faltas;

    public Disciplina() {
    }

    @Ignore
    public Disciplina(Long id_disciplina, String nome_disciplina, String nome_sala, String nome_professor, String sobre_disciplina, String status_disciplina, boolean completa, float nota_obtida, float nota_distribuida) {
        this.id_disciplina = id_disciplina;
        this.nome_disciplina = nome_disciplina;
        this.nome_sala = nome_sala;
        this.nome_professor = nome_professor;
        this.sobre_disciplina = sobre_disciplina;
        this.status_disciplina = status_disciplina;
        this.completa = completa;
        this.nota_obtida = nota_obtida;
        this.nota_distribuida = nota_distribuida;
    }


    @Ignore
    public Disciplina(String nome_disciplina, String nome_sala, String nome_professor, String sobre_disciplina, String status_disciplina, int faltasPerm) {
        this.nome_disciplina = nome_disciplina;
        this.nome_sala = nome_sala;
        this.nome_professor = nome_professor;
        this.sobre_disciplina = sobre_disciplina;
        this.status_disciplina = status_disciplina;
        this.limiteFaltas = faltasPerm;
    }



    public void setStatus_disciplina(String status_disciplina) {
        this.status_disciplina = status_disciplina;
    }

    public String getStatus_disciplina() {
        return status_disciplina;
    }

    public void setNome_disciplina(String nome_disciplina) {
        this.nome_disciplina = nome_disciplina;
    }

    public float getNota_distribuida() {
        return nota_distribuida;
    }

    public void setNota_distribuida(float nota_distribuida) {
        this.nota_distribuida = nota_distribuida;
    }
    public void adicionarNota(float nota){
        this.nota_distribuida+= nota;
    }

    public void setNome_professor(String nome_professor) {
        this.nome_professor = nome_professor;
    }

    public void setNome_sala(String nome_sala) {
        this.nome_sala = nome_sala;
    }

    public String getNome_disciplina() {
        return nome_disciplina;
    }

    public String getNome_professor() {
        return nome_professor;
    }

    public String getNome_sala() {
        return nome_sala;
    }

    public Long getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Long id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getSobre_disciplina() {
        return sobre_disciplina;
    }

    public void setSobre_disciplina(String sobre_disciplina) {
        this.sobre_disciplina = sobre_disciplina;
    }

    public boolean isCompleta() {
        return completa;
    }

    public void setCompleta(boolean completa) {
        this.completa = completa;
    }

    public float getNota_obtida() {
        return nota_obtida;
    }

    public void setNota_obtida(float nota_obtida) {
        this.nota_obtida = nota_obtida;
    }
    public int getLimiteFaltas() {
        return limiteFaltas;
    }

    public void setLimiteFaltas(int limiteFaltas) {
        this.limiteFaltas = limiteFaltas;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }
}
