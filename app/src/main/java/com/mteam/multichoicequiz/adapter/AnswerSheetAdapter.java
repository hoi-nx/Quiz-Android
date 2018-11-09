package com.mteam.multichoicequiz.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mteam.multichoicequiz.R;
import com.mteam.multichoicequiz.common.Common;
import com.mteam.multichoicequiz.model.CurrentQuestion;

import java.util.List;

public class AnswerSheetAdapter extends RecyclerView.Adapter<AnswerSheetAdapter.MyHolder> {

    private List<CurrentQuestion>list;

    public AnswerSheetAdapter(List<CurrentQuestion> list) {
        this.list=list;

    }

    @NonNull
    @Override
    public AnswerSheetAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_grid_answer_sheet,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerSheetAdapter.MyHolder myHolder, int i) {

        CurrentQuestion currentQuestion=list.get(i);
        if(currentQuestion.getType()==Common.ANSWER_TYPE.NO_ANSWER){
            myHolder.view.setBackgroundResource(R.drawable.grid_question_no_answer);
        }else if(currentQuestion.getType()==Common.ANSWER_TYPE.CORRECT_ANSWER){
            myHolder.view.setBackgroundResource(R.drawable.grid_question_right_answer);
        }else {
            myHolder.view.setBackgroundResource(R.drawable.grid_question_wrong_answer);
        }


    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        View view;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.question_answer);
        }
    }
}
