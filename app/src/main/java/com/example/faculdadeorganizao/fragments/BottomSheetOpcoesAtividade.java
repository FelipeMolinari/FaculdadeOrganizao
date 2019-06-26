package com.example.faculdadeorganizao.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomAtividadeDAO;
import com.example.faculdadeorganizao.ui.Activity_CadastroAtividade;
import com.example.faculdadeorganizao.ui.Activity_DisciplinaEscolhida;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class BottomSheetOpcoesAtividade extends BottomSheetDialogFragment {

    //Elementos Tela
    private TextView buttonCompletarAtividade;
    private TextView buttonEditarAtividade;
    private TextView buttonArquivarAtividade;
    private TextView buttonDeletarAtividade;
    private TextView buttonCompartilharAtividade;

    private RoomAtividadeDAO atividadeDAO;
    private DataBasePrincipal database;

    private long idAtividade;

    public BottomSheetOpcoesAtividade() {
    }

    public static BottomSheetOpcoesAtividade newInstance(Long idAtividade) {
        BottomSheetOpcoesAtividade fragment = new BottomSheetOpcoesAtividade();
        Bundle args = new Bundle();
        args.putSerializable("Atividade", idAtividade);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idAtividade = (getArguments() != null ? getArguments().getLong("Atividade") : 1);
        conectaBd();
    }

    private void conectaBd() {
        database = DataBasePrincipal.getInstance(getContext());
        atividadeDAO = database.getRoomAtividadeDAO();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        encontrarElementosNaTela(v);
        configOnClickListeners();

        return v;
    }

    private void configOnClickListeners() {
        configButtonCompletarAtividade();
        configButtonEditAtividade();
        configButtonArquivarAtividade();
        configButtonDeletarAtividade();
        configButtonCompartilharAtividade();
    }

    private void configButtonCompartilharAtividade() {
        buttonCompartilharAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "compartilhar atividade", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void configButtonDeletarAtividade() {
        buttonDeletarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Você tem certeza?");
                builder.setMessage("Remover tarefa?");

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        atividadeDAO.deleteAtividade(database.getRoomAtividadeDAO().getAtividadeById(idAtividade));
                        getFragmentManager().beginTransaction().remove(BottomSheetOpcoesAtividade.this).commit();
                        getDialog().dismiss();
                        Activity_DisciplinaEscolhida activity = (Activity_DisciplinaEscolhida) getActivity();
                        activity.onUpdate();



                    }
                });
                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "NO", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    private void configButtonArquivarAtividade() {
        buttonArquivarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "arquivar atividade", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void encontrarElementosNaTela(View v) {
        buttonCompletarAtividade = v.findViewById(R.id.completar_atividade_id);
        buttonEditarAtividade = v.findViewById(R.id.editar_atividade_id);
        buttonArquivarAtividade = v.findViewById(R.id.arquivar_atividade_id);
        buttonDeletarAtividade = v.findViewById(R.id.deletar_atividade_id);
        buttonCompartilharAtividade = v.findViewById(R.id.compartilhar_atividade_id);
    }

    private void configButtonCompletarAtividade() {
        buttonCompletarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "completar atividade", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configButtonEditAtividade() {
        buttonEditarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_CadastroAtividade.class);
                intent.putExtra("AtividadeId", idAtividade);
                startActivity(intent);

            }
        });
    }


}
