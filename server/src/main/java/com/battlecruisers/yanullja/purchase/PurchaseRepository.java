package com.battlecruisers.yanullja.purchase;

import com.battlecruisers.yanullja.purchase.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>,
    CustomPurchaseRepository {


}
