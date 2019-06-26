package com.example.faculdadeorganizao.database.dao;

import com.example.faculdadeorganizao.model.Atividade;

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

    @Query("SELECT * FROM Atividade WHERE disciplinaid= :IdDisciplina ")
    List<Atividade> getListaAtividadeDaDisciplina(Long IdDisciplina);

    @Query("SELECT * FROM Atividade WHERE id_atividade = :IdAtividade")
    Atividade getAtividadeById(Long IdAtividade);

    @Update
    void updateAtividade(Atividade atividade);

    @Delete
    void deleteAtividade(Atividade atividade);
}
