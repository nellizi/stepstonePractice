package com.likelion.stepstone.post;

import com.likelion.stepstone.post.model.PostEntity;
import com.likelion.stepstone.user.model.UserEntity;
import java.util.List;

public interface PostRepositoryCustom {
    List<PostEntity> getPostEntitiy(UserEntity userEntity);
}
