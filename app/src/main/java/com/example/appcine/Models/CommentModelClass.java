package com.example.appcine.Models;

import android.content.ClipData;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.example.appcine.Database.AppDatabase;
import com.example.appcine.Database.Entities.CommentEntity;
import com.example.appcine.R;

import java.util.ArrayList;
import java.util.List;

public class CommentModelClass {

    private String content, uname;
    private Long userID;

    public CommentModelClass(String comment, String s, int res, Long id) {

    }

    public CommentModelClass(String content, String userName, Long userID) {
        this.content = content;
        this.uname = userName;
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String userName) {
        this.uname = uname;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public static ArrayList<CommentModelClass> listComments(Context context){
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        List<CommentEntity> commentEntities = database.commentDAO().getAll();
        ArrayList<CommentModelClass> comments = new ArrayList<CommentModelClass>();
        comments.clear();
        for(int i = 0; i<commentEntities.size(); i++){
            CommentEntity comment = commentEntities.get(i);
            int res = R.drawable.boy;
            comments.add(new CommentModelClass(comment.getComment(),comment.getuName(),res,comment.getId()));
        }
        return comments;
    }
}
