package com.umc.refit.domain.dto.clothe;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetClosetListResponseDto {

    private Long id;
    private String imageUrl;

    private Integer targetCnt;
    private Integer count;

    private Integer cntPerMonth;
    private Integer cntPerWeek;
    /**
     * 목표 기간 남았을 때 : D-00
     * 목표 기간 지났을 때 : D+00
     * 목표 기간 설정 안했을 때 : -7777
     * 달성 완료 했을 때 : +7777
     */
    private Integer remainedDay;
}
