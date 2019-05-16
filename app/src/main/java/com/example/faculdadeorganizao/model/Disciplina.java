package com.example.faculdadeorganizao.model;

public class Disciplina {

    private String nome_disciplina;
    private String nome_professor;
    private String nome_sala;
    private String status_disciplina;
    private int id_disciplina;



    public Disciplina(String nome_disciplina, String nome_sala ,String nome_professor, String status_disciplina) {
        this.nome_disciplina = nome_disciplina;
        this.nome_professor = nome_professor;
        this.nome_sala = nome_sala;
        this.status_disciplina = status_disciplina;
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

    public int getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(int id_disciplina) {
        this.id_disciplina = id_disciplina;
    }
}
