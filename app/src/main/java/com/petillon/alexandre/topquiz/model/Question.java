package com.petillon.alexandre.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Collections;

/**
 * Created by Alexandre Pétillon on 18/06/2018.
 */
public class Question implements Parcelable{
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        if (choiceList.size() < 2) {
        } if ((answerIndex < 0) || (answerIndex >= choiceList.size())) {
        } else {
            mQuestion = question;
            mChoiceList = choiceList;
            mAnswerIndex = answerIndex;
        }
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setChoiceList(List<String> choiceList) {
        mChoiceList = choiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        mAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mQuestion);
        parcel.writeList(mChoiceList);
        parcel.writeInt(mAnswerIndex);
    }

    public static final Parcelable.Creator<Question> CREATOR
            = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source)
        {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size)
        {
            return new Question[size];
        }
    };

    public Question(Parcel in) {
        mQuestion = in.readString();
        in.readList(mChoiceList, String.class.getClassLoader());
        mAnswerIndex = in.readInt();
    }
}
