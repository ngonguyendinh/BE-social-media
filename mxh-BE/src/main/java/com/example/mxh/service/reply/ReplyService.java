package com.example.mxh.service.reply;

import com.example.mxh.form.FormCreateReply;
import com.example.mxh.form.FormUpdateReply;
import com.example.mxh.model.reply.Reply;
import com.example.mxh.model.user.User;

public interface ReplyService {
    Reply create(FormCreateReply reply, User user, int commentId);
    Reply update(FormUpdateReply reply, User user, int commentId);

}
