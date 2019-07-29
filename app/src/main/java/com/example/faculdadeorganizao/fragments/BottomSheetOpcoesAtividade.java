package com.example.faculdadeorganizao.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomAtividadeDAO;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.model.Disciplina;
import com.example.faculdadeorganizao.ui.Activity_CadastroAtividade;
import com.example.faculdadeorganizao.ui.Activity_DisciplinaEscolhida;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

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
    private LinearLayout bottomSheetContainer;
    private RoomDisciplinaDAO disciplinaDAO;
    private Disciplina disciplina;

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

    }

    private void conectaBd() {
        database = DataBasePrincipal.getInstance(getContext());
        atividadeDAO = database.getRoomAtividadeDAO();
        disciplinaDAO = database.getRoomDisciplinaDAO();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        conectaBd();

        encontrarElementosNaTela(v);
        configOnClickListeners();

        return v;
    }

    private void configOnClickListeners() {
        Atividade atividade = atividadeDAO.getAtividadeById(idAtividade);
        disciplina = disciplinaDAO.getDisciplina(atividade.getDisciplinaid());
        if (atividade.isAtividadeCompleta()) configButtonAtividadeIncompleta(atividade);
        else configButtonCompletarAtividade(atividade);

        configButtonEditAtividade();
        configButtonArquivarAtividade(atividade);
        configButtonDeletarAtividade(atividade);
        configButtonCompartilharAtividade(atividade);
    }

    private void configButtonAtividadeIncompleta(final Atividade atividade) {


        buttonCompletarAtividade.setText(R.string.atividadeIncompleta);
        buttonCompletarAtividade.setTextColor(getContext().getResources().getColor(R.color.color4));
        buttonCompletarAtividade.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_cancelar_completo, 0, 0, 0);
        buttonCompletarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atividade.setAtividadeCompleta(false);
                if(atividade.getNotaDaAtividade()!=0){
                    disciplina.setNota_obtida(disciplina.getNota_obtida()-atividade.getNotaTirada());
                    disciplinaDAO.updateDisciplina(disciplina);
                }

                atividade.setNotaTirada(0);
                atividadeDAO.updateAtividade(atividade);
                //////////////////////////////////////////

                getFragmentManager().beginTransaction().remove(BottomSheetOpcoesAtividade.this).commit();
                Activity_DisciplinaEscolhida activity = (Activity_DisciplinaEscolhida) getActivity();
                activity.onUpdate(2);
                activity.onUpdate(1);

            }
        });


    }

    private void configButtonCompartilharAtividade(Atividade atividade) {
        buttonCompartilharAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "compartilhar atividade", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void configButtonDeletarAtividade(final Atividade atividade) {
        buttonDeletarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Você tem certeza?");
                builder.setMessage("Remover tarefa?");

                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        atividadeDAO.deleteAtividade(atividade);

                        if (atividade.isAtividadeCompleta()) {
                            if (atividade.getNotaDaAtividade() != 0) {
                                disciplina.setNota_distribuida(disciplina.getNota_distribuida() - atividade.getNotaDaAtividade());
                                disciplina.setNota_obtida(disciplina.getNota_obtida() - atividade.getNotaTirada());
                                disciplinaDAO.updateDisciplina(disciplina);
                            }
                        } else {
                            if (atividade.getNotaDaAtividade() != 0) {
                                disciplina.setNota_distribuida(disciplina.getNota_distribuida() - atividade.getNotaDaAtividade());
                                disciplinaDAO.updateDisciplina(disciplina);



                            }
                        }

                        getFragmentManager().beginTransaction().remove(BottomSheetOpcoesAtividade.this).commit();
                        getDialog().dismiss();
                        Activity_DisciplinaEscolhida activity = (Activity_DisciplinaEscolhida) getActivity();
                        activity.onUpdate(2);
                        activity.onUpdate(1);



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

    private Atividade retornaAtividadeById() {
        return database.getRoomAtividadeDAO().getAtividadeById(idAtividade);
    }

    private void configButtonArquivarAtividade(Atividade atividade) {
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
        bottomSheetContainer = v.findViewById(R.id.bottsheetcontainter);
    }

    private void configButtonCompletarAtividade(final Atividade atividade) {
        if (atividade.getNotaDaAtividade() != 0) {
            buttonCompletarAtividade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    configDialogCompletarAtividade();
                }
            });
        } else {
            buttonCompletarAtividade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    atividade.setAtividadeCompleta(true);
                    atividadeDAO.updateAtividade(atividade);
                    Log.i("Entrou", "id Atividade" + atividade.getId_atividade());
                    Log.i("Entrou", "id Atividade atualizada" + atividadeDAO.getAtividadeById(atividade.getId_atividade()).getNomeAtividade());


                    getFragmentManager().beginTransaction().remove(BottomSheetOpcoesAtividade.this).commit();
                    Activity_DisciplinaEscolhida activity = (Activity_DisciplinaEscolhida) getActivity();
                    activity.onUpdate(2);

                }
            });

        }

    }

    private void configDialogCompletarAtividade() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dialog_disciplina_completa, null);
        alertDialog.setView(mView);

        AlertDialog dialog = alertDialog.create();

        configElementosDialogCompletarAtividade(mView, dialog);

        dialog.show();

    }

    private void configElementosDialogCompletarAtividade(View mView, AlertDialog dialog) {
        Atividade atividade = retornaAtividadeById();
        configConfirmarAtividadeListener(mView, atividade, dialog);

        configDefinirNotaObtidaSeekBar(mView, atividade);


    }

    private void configDefinirNotaObtidaSeekBar(View mView, final Atividade atividade) {

        final IndicatorSeekBar indicatorSeekBarNotaObtida = mView.findViewById(R.id.seekbarNotaTirada);
        indicatorSeekBarNotaObtida.setMax(atividade.getNotaDaAtividade());
        LinearLayout linearLayout = mView.findViewById(R.id.lineardialog);
        final TextView textView = mView.findViewById(R.id.nota_tirada);
        linearLayout.removeView(textView);
        indicatorSeekBarNotaObtida.getIndicator().setTopContentView(textView);
        indicatorSeekBarNotaObtida.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                double progressFloat = indicatorSeekBarNotaObtida.getProgressFloat();
                float notaArredondada = (float) arredondaValor(progressFloat);
                textView.setText(notaArredondada + "");
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }

    private double arredondaValor(Double notaAtividade) {

        if (notaAtividade - Math.floor(notaAtividade) < 0.33) {
            return Math.floor(notaAtividade);
        } else if (notaAtividade - Math.floor(notaAtividade) >= 0.66) {
            return Math.floor(notaAtividade) + 1;
        } else {
            return Math.floor(notaAtividade) + 0.5;
        }

    }

    private void configConfirmarAtividadeListener(final View mView, final Atividade atividade, final AlertDialog dialog) {
        TextView confirmAtividade = mView.findViewById(R.id.completar_atividade_nota);
        final IndicatorSeekBar indicatorSeekBar = mView.findViewById(R.id.seekbarNotaTirada);
        confirmAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Confirmar atividade", Toast.LENGTH_LONG).show();
                atualizaNotaAtividade(indicatorSeekBar, atividade);

                getFragmentManager().beginTransaction().remove(BottomSheetOpcoesAtividade.this).commit();
                dialog.dismiss();
                Activity_DisciplinaEscolhida activity = (Activity_DisciplinaEscolhida) getActivity();
                activity.onUpdate(2);
                activity.onUpdate(1);



            }
        });
    }

    private void atualizaNotaAtividade(IndicatorSeekBar indicatorSeekBar, Atividade atividade) {
        double progressFloat = indicatorSeekBar.getProgressFloat();
        float notaArredondada = (float) arredondaValor(progressFloat);


        atividade.setAtividadeCompleta(true);

        atividade.setNotaTirada(notaArredondada);

        atividadeDAO.updateAtividade(atividade);
        disciplina.setNota_obtida(disciplina.getNota_obtida() + notaArredondada);
        disciplinaDAO.updateDisciplina(disciplina);

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
