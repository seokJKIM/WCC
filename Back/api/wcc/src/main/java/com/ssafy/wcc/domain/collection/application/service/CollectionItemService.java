package com.ssafy.wcc.domain.collection.application.service;

import com.ssafy.wcc.domain.collection.db.entity.CollectionItem;

import java.util.List;
import java.util.Optional;

public interface CollectionItemService {
    List<CollectionItem> getList(Long id);

    boolean buy(Long memberId, int collectionId);

    boolean wear(Long memberId, int collectionId);
    int getPrice(int collectionId);
}
