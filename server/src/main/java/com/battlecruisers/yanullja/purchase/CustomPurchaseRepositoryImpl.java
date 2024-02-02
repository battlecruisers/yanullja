package com.battlecruisers.yanullja.purchase;

import com.battlecruisers.yanullja.purchase.domain.Purchase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CustomPurchaseRepositoryImpl implements CustomPurchaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Purchase> findAllByMemberIdOrderByCreatedTimeDesc(
        Long memberId) {
        return entityManager.createQuery(
                "SELECT p FROM Purchase p " +
                    "JOIN FETCH p.reservation r " +
                    "JOIN FETCH r.room rm " +
                    "JOIN FETCH rm.place pl " +
                    "WHERE r.member.id = :memberId " +
                    "ORDER BY p.createdDate DESC", Purchase.class)
            .setParameter("memberId", memberId)
            .getResultList();
    }

}
