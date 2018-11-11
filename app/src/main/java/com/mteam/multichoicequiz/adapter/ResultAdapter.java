package com.mteam.multichoicequiz.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mteam.multichoicequiz.R;
import com.mteam.multichoicequiz.common.Common;
import com.mteam.multichoicequiz.model.CurrentQuestion;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyHolder>{
    private List<CurrentQuestion>list;
    Context context;

    public ResultAdapter(List<CurrentQuestion> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result,viewGroup,false));
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        if(myHolder instanceof MyHolder){
            myHolder.tv_id_question.setText("Question "+list.get(i).getQuestionIndex()+1+"");
            if(list.get(i).getType()==Common.ANSWER_TYPE.WRONG_ANSWER){
                myHolder.tv_id_question.setBackground(context.getDrawable(R.drawable.grid_question_wrong_answer));
            }else if(list.get(i).getType()==Common.ANSWER_TYPE.CORRECT_ANSWER){
                myHolder.tv_id_question.setBackground(context.getDrawable(R.drawable.grid_question_right_answer));
            }else {
                myHolder.tv_id_question.setBackground(context.getDrawable(R.drawable.grid_question_no_answer));
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_id_question;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_id_question=itemView.findViewById(R.id.tv_id_question);

            tv_id_question.setOnClickListener(v -> {
                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Common.KET_BACK_RESULT).putExtra(Common.KEY_BACK_RESULT,list.get(getAdapterPosition()).getQuestionIndex()));
            });
        }
    }
}
