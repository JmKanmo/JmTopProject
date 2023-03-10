package com.jmshop.jmshop_admin.util.sourcemap;

import com.jmshop.jmshop_admin.util.AwsS3Util;
import com.jmshop.jmshop_admin.util.CompressUtil;
import com.jmshop.jmshop_admin.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class SourceMapUtil {
    private final AwsS3Util awsS3Util;
    private CompressUtil compressUtil;
    private CryptoUtil cryptoUtil;

    /**
     * 리스트 형태로 입력 받는 MultipartFile에 대해 병렬 처리 필요 ...
     * 조회 시에는 암호 값을 입력 받고, 암호화 된 소스맵에 대해 전부 암호를 해독 & SourceMapDTO 값을 반환하도록 한다.
     * 어떤식으로 개발될지는 모르겟으나 ... 우선은 조회 시에는 ... 모든 소스맵파일은 암호화된 상태로 반환...
     * 특정 고객이 복수선택한 소스맵 파일에 대하여 보기 기능을 제공...  결국 조회 시에 암호값을 입력 받도록 한다 ...
     * 각 고객마다 소스맵파일에 사용할 암호를 지정 ... 그렇다면 저장을 해야 하나? 아니면 특정 고객들마다 암호를 풀거나 할 필요 없이...
     * 해당 고객의 여러 정보를 이용해 암호를 만들 수도 있음... (다른 사람은 절대 못풀도록...) 혹은 2차비밀번호를 지정해야할 수도...
     * 만약, 2차 비밀번호를 까먹으면 ... ? (이러면 또 db에 저장을 해야할 수도 잇다 ... 그것만은.. ㅠㅠ)
     * 내 선택은... 고객의 accountID + other 정보를 이용해서... 조합합 비밀번호를 이용해 암호화&복호화를 진행하도록 한다...
     *
     * api의 경우는... 조회의 범위를 고려해서 특정 fullName dir 내에 sourceMap List를 반환하도록 한다... api에서 고객정보를 이용해 암호를 생성 ...
     * 이를 이용해 압축해제 & 복호화 및 SourceMap DTO 반환
     *
     * + 소스맵 분석 API 추가개발 하도록
     */
    public void uploadSourceMap(String fullName, InputStream inputStream) {

    }
}
