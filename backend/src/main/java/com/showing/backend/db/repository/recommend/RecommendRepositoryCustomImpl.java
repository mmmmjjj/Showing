package com.showing.backend.db.repository.recommend;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.showing.backend.api.response.PerformanceRes;
import com.showing.backend.db.entity.performance.*;
import com.showing.backend.db.entity.recommend.QRecommend;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RecommendRepositoryCustomImpl implements RecommendRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    QRecommend qRecommend = QRecommend.recommend;
    QPerformance qPerformance = QPerformance.performance;
    QSeason qSeason = QSeason.season;
    QStarPoint qStarPoint = QStarPoint.starPoint;

    /**
     * recommend 테이블에서 해당 공연의 추천 공연 리스트를 랜덤으로 count개 조회한다.
     * type이 0이라면 공연완료, 공연중, 공연예정 조회
     * type이 1이라면 공연중, 공연예정 조회
     */
    @Override
    public List<PerformanceRes> getTopCountRandomRecommendListByPerformanceId(int type, int count, List<Long> performanceIdList) {
        return jpaQueryFactory
                .select(Projections.constructor(PerformanceRes.class,
                        qPerformance.id.as("performanceId"),
                        qPerformance.performanceName.as("performanceName"),
                        qPerformance.performanceType.as("performanceType"),
                        qPerformance.lastSeasonId.as("lastSeasonId"),
                        qSeason.seasonImage.as("lastSeasonImage"),
                        qSeason.startDate.as("lastSeasonStartDate"),
                        qSeason.endDate.as("lastSeasonEndDate"),
                        qSeason.proceedFlag.as("lastSeasonProceedFlag"),
                        ExpressionUtils.as(
                                JPAExpressions.select(qStarPoint.rating.avg())
                                              .from(qStarPoint)
                                              .where(qStarPoint.performance.id.eq(qPerformance.id))
                                , "starPointAverage"
                        )
                ))
                .from(qRecommend)
                .join(qPerformance).on(qPerformance.id.eq(qRecommend.recPerformance.id))
                .join(qSeason).on(qSeason.id.eq(qPerformance.lastSeasonId))
                .where(qRecommend.performance.id.in(performanceIdList))
                .where(qSeason.proceedFlag.between(type, 3))
                .orderBy(qSeason.proceedFlag.desc())
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(count)
                .fetch();
    }

}