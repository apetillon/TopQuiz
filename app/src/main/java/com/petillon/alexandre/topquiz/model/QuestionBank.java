package com.petillon.alexandre.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alexandre Pétillon on 18/06/2018.
 */
public class QuestionBank implements Parcelable{
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList);
        mNextQuestionIndex = 0;
    }

    public Question getQuestion() {
        if (mNextQuestionIndex >= mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }
        return mQuestionList.get(mNextQuestionIndex++);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(mQuestionList);
        parcel.writeInt(mNextQuestionIndex);
    }

    public static final Parcelable.Creator<QuestionBank> CREATOR
            = new Parcelable.Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel source)
        {
            return new QuestionBank(source);
        }

        @Override
        public QuestionBank[] newArray(int size)
        {
            return new QuestionBank[size];
        }
    };

    public QuestionBank(Parcel in) {
        in.readList(this.mQuestionList, Question.class.getClassLoader());
        this.mNextQuestionIndex = in.readInt();
    }
}