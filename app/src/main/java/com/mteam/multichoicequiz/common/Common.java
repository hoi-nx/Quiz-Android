package com.mteam.multichoicequiz.common;

import com.mteam.multichoicequiz.QuestionFragment;
import com.mteam.multichoicequiz.model.Category;
import com.mteam.multichoicequiz.model.CurrentQuestion;
import com.mteam.multichoicequiz.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Common {

    public static final int TOTAL_TIME = 20 * 60 * 1000;//20min
    public static Category categorySelected=new Category();
    public static List<Question> questionList=new ArrayList<>();
    public static List<CurrentQuestion> listAnswerSheet=new ArrayList<>();
    public static int right_answer=0;
    public static List<QuestionFragment> listFragment=new ArrayList<>();
    public static TreeSet<String> selected_values=new TreeSet<>();


    public  enum ANSWER_TYPE{
        NO_ANSWER,
        WRONG_ANSWER,
        CORRECT_ANSWER
    }
}
