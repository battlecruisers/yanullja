package com.battlecruisers.yanullja.purchase;

import com.battlecruisers.yanullja.purchase.domain.Purchase;
import java.util.List;

public interface CustomPurchaseRepository {

    List<Purchase> findAllByMemberIdOrderByCreatedTimeDesc(Long memberId);

}
