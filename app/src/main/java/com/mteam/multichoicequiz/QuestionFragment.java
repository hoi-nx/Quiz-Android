package com.mteam.multichoicequiz;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mteam.multichoicequiz.common.Common;
import com.mteam.multichoicequiz.model.CurrentQuestion;
import com.mteam.multichoicequiz.model.Question;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class QuestionFragment extends Fragment implements IQuestion{
    TextView tv_question_txt;
    private CheckBox checkBoxA,checkBoxB,checkBoxC,checkBoxD;
    private FrameLayout frameLayout;
    private int questionIndex;
    private Question question;
    private ProgressBar progressBar;


    public QuestionFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionIndex=getArguments().getInt("index",-1);
        question=Common.questionList.get(questionIndex);
        if(question!=null){
            progressBar=getView().findViewById(R.id.progess);
            if(question.getIsImageQuestion()==1){
                ImageView imageView=getView().findViewById(R.id.img_question);
                Picasso.get().load(question.getQuestionImage()).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

            }else if(question.getIsImageQuestion()==0){

            }
        }

        tv_question_txt=getView().findViewById(R.id.tv_question_text);
        checkBoxA=getView().findViewById(R.id.chk_answerA);

        checkBoxA.setText(question.getAnswerA());
        checkBoxA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.selected_values.add(checkBoxA.getText().toString());
                }else {
                    Common.selected_values.remove(checkBoxA.getText().toString());
                }

            }
        });
        checkBoxB=getView().findViewById(R.id.chk_answerB);
        checkBoxB.setText(question.getAnswerA());
        checkBoxB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.selected_values.add(checkBoxB.getText().toString());
                }else {
                    Common.selected_values.remove(checkBoxB.getText().toString());
                }

            }
        });
        checkBoxC=getView().findViewById(R.id.chk_answerC);
        checkBoxC.setText(question.getAnswerA());
        checkBoxC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.selected_values.add(checkBoxC.getText().toString());
                }else {
                    Common.selected_values.remove(checkBoxC.getText().toString());
                }

            }
        });
        checkBoxD=getView().findViewById(R.id.chk_answerD);
        checkBoxD.setText(question.getAnswerD());
        checkBoxD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Common.selected_values.add(checkBoxD.getText().toString());
                }else {
                    Common.selected_values.remove(checkBoxD.getText().toString());
                }

            }
        });

        frameLayout=getView().findViewById(R.id.layout_frame);

    }

    @Override
    public CurrentQuestion getSelectAnswer() {
        return null;
    }

    @Override
    public void showCorrectAnswer() {

    }

    @Override
    public void disableAnswer() {
        checkBoxA.setEnabled(false);
        checkBoxB.setEnabled(false);
        checkBoxC.setEnabled(false);
        checkBoxD.setEnabled(false);

    }
}
