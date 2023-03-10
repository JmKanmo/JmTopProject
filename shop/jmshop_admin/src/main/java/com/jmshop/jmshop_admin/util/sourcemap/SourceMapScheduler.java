package com.jmshop.jmshop_admin.util.sourcemap;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 하루에 한번 씩,
 */
@Component
@RequiredArgsConstructor
public class SourceMapScheduler {

    /**
     * // 다른 시간으로 테스트 해보면서 진행
     * // 매월 1일 0시 0분에 진행
     * 0 0 1 * *
     */
    @Scheduled(cron = "0 0 1 * *")
    public void removeOldSourceMap() {
        /**
         TODO
         WHATAP_S3_BUCKET 디렉토리 내에 모든 소스맵 파일에 대해 (현재시간 - 생성시간) 기준 30일(설정 값 지정 필요)이 넘어가는 소스맵 파일은 삭제하도록 한다
         **/
    }
}
