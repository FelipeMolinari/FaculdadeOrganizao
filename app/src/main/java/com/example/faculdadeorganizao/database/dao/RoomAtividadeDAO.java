package com.example.faculdadeorganizao.database.dao;

import com.example.faculdadeorganizao.model.Atividade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RoomAtividadeDAO {

    @Insert
    void adcAtividade(Atividade novaAtividade);

    @Query("SELECT * FROM Atividade WHERE disciplinaid =:IdDisciplina ORDER BY date(dataFormata)")
    List<Atividade> getListaAtividadeDaDisciplina(Long IdDisciplina);

    @Query("SELECT * FROM Atividade WHERE id_atividade = :IdAtividade")
    Atividade getAtividadeById(Long IdAtividade);

    @Update
    void updateAtividade(Atividade atividade);

    @Delete
    void deleteAtividade(Atividade atividade);


    @Query("SELECT * FROM Atividade WHERE disciplinaid =:id_disciplina ORDER BY date(dataFormata) DESC")
    List<Atividade> retornaOrdenado(Long id_disciplina);

    @Query ("SELECT * FROM (SELECT * FROM Atividade WHERE disciplinaid =:id_disciplina) WHERE NOT atividadeCompleta")
    List<Atividade> retornaAtividadesNaoCompletas(Long id_disciplina);

    @Query("SELECT * FROM (SELECT * FROM Atividade WHERE disciplinaid =:id_disciplina) WHERE notaDaAtividade!=0")
    List<Atividade> retornaAtividadesAvaliativas(Long id_disciplina);
}
