package com.mteam.multichoicequiz;

import com.mteam.multichoicequiz.model.CurrentQuestion;

public interface IQuestion {
    CurrentQuestion getSelectAnswer();
    void showCorrectAnswer();
    void disableAnswer();
    void resetQuestion();

}
