package com.showing.backend.api.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceRes {

    @ApiModelProperty(name = "공연 Id", example = "1")
    Long performanceId;

    @ApiModelProperty(name = "공연 이름", example = "절벽")
    String performanceName;

    @ApiModelProperty(name = "공연 종류", example = "0", notes = "1: 뮤지컬, 2: 연극")
    int performanceType;

    @ApiModelProperty(name = "공연의 마지막 시즌 Id", example = "1")
    Long lastSeasonId;

    @ApiModelProperty(name = "공연의 마지막 시즌 포스터 이미지", example = "http://playdb...")
    String lastSeasonImage;

    @ApiModelProperty(name = "공연의 마지막 시즌 시작일", example = "2022-03-04")
    LocalDate lastSeasonStartDate;

    @ApiModelProperty(name = "공연의 마지막 시즌 종료일", example = "2022-03-31")
    LocalDate lastSeasonEndDate;

    @ApiModelProperty(name = "현재 러닝 여부", example = "2022-03-04", notes = "0: 공연완료, 1: 공연중, 2: 공연예정")
    int lastSeasonProceedFlag;

    @ApiModelProperty(name = "공연의 평균 별점", example = "3.7")
    double starPointAverage;

}
