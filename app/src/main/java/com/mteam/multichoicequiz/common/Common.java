package com.mteam.multichoicequiz.common;

import com.mteam.multichoicequiz.model.Category;
import com.mteam.multichoicequiz.model.CurrentQuestion;
import com.mteam.multichoicequiz.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static final int TOTAL_TIME = 20 * 60 * 1000;//20min
    public static Category categorySelected=new Category();
    public static List<Question> questionList=new ArrayList<>();
    public static List<CurrentQuestion> listAnswerSheet=new ArrayList<>();
    public static int right_answer=0;


    public  enum ANSWER_TYPE{
        NO_ANSWER,
        WRONG_ANSWER,
        CORRECT_ANSWER
    }
}
