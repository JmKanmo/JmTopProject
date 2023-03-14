package com.jmshop.jmshop_admin.util.sourcemap;

import com.jmshop.jmshop_admin.config.SourceMapConfig;
import com.jmshop.jmshop_admin.dto.domain.SourceMapAnalysisDto;
import com.jmshop.jmshop_admin.dto.domain.SourceMapDto;
import com.jmshop.jmshop_admin.dto.error.type.SourceMapException;
import com.jmshop.jmshop_admin.util.AwsS3Util;
import com.jmshop.jmshop_admin.util.CompressUtil;
import com.jmshop.jmshop_admin.util.CryptoUtil;
import com.jmshop.jmshop_admin.util.JsonUtil;
import com.jmshop.jmshop_admin.util.sourcemap.mapping.SourceMapping;
import com.jmshop.jmshop_admin.util.sourcemap.mapping.SourceMap;
import com.jmshop.jmshop_admin.util.sourcemap.mapping.SourceMapImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 리스트 형태로 입력 받는 MultipartFile 에 대해 병렬 처리 필요 ...
 * 조회 시에는 암호 값을 입력 받고, 암호화 된 소스맵에 대해 전부 암호를 해독 & SourceMapDTO 값을 반환하도록 한다.
 * 어떤식으로 개발될지는 모르겟으나 ... 우선은 조회 시에는 ... 모든 소스맵파일은 암호화된 상태로 반환...
 * 특정 고객이 복수선택한 소스맵 파일에 대하여 보기 기능을 제공...  결국 조회 시에 암호값을 입력 받도록 한다 ...
 * 각 고객마다 소스맵파일에 사용할 암호를 지정 ... 그렇다면 저장을 해야 하나? 아니면 특정 고객들마다 암호를 풀거나 할 필요 없이...
 * 해당 고객의 여러 정보를 이용해 암호를 만들 수도 있음... (다른 사람은 절대 못풀도록...) 혹은 2차비밀번호를 지정해야할 수도...
 * 만약, 2차 비밀번호를 까먹으면 ... ? (이러면 또 db에 저장을 해야할 수도 잇다 ... 그것만은.. ㅠㅠ)
 * 내 선택은... 고객의 accountID + password 정보를 (암호화&복호화) 해서 이용해서... 조합합 비밀번호를 이용해 암호화&복호화를 진행하도록 한다...
 * <p>
 * api의 경우는... 조회의 범위를 고려해서 특정 fullName dir 내에 sourceMap List를 반환하도록 한다... api에서 고객정보를 이용해 암호를 생성 ...
 * 이를 이용해 압축해제 & 복호화 및 SourceMap DTO 반환
 * 페이지네이션 고려(서버측 페이지네이션 라이브러리 활용
 * <p>
 * + 소스맵 분석 API 추가개발 하도록
 * <p>
 * read의 경우는 와탭 소스코드 내에서는 aws s3 조회로 인한 불필요한 비용 발생을 줄이기 위해 되도록 캐싱 적용하도록 ...
 * 캐싱 적용 시, 소스맵 + 소스맵 매핑 객체도 같이 캐싱하도록 ...
 * 캐싱 적용 시, 파일 사이즈도 계산 ... 전체 프로젝트 당 256MB로 계산... (매번 업로드 할때마다 전체 s3 pcode당 디렉토리 파일 사이즈 계싼..?)_
 * aws s3 get folder size: https://itecnote.com/tecnote/java-calculate-s3-objectfolder-size-in-java/
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SourceMapUtil {
    private final AwsS3Util awsS3Util;
    private final CompressUtil compressUtil;
    private final CryptoUtil cryptoUtil;
    private final SourceMapConfig sourceMapConfig;

    private final int TOTAL_MAX_SIZE = 256 * 1024 * 1024;
    private final int SINGLE_MAX_SIZE = 50 * 1024 * 1024;

    private ConcurrentHashMap<Long, Long> fileSizeMap = new ConcurrentHashMap<>(); // 캐싱을 어떤식으로 할지 고민 ...

    public void uploadSourceMap(long pcode, String version, String host, List<MultipartFile> multipartFiles) throws SourceMapException {
        try {
            List<byte[]> compressedBytesList = new ArrayList<>();
            long totalFileSize = 0;

            for (MultipartFile multipartFile : multipartFiles) {
                if (multipartFile.getOriginalFilename().isEmpty()) {
                    throw new SourceMapException("잘못된 파일 형식 입니다.");
                }

                byte[] encryptedBytes = cryptoUtil.encrypt(multipartFile.getInputStream().readAllBytes(), sourceMapConfig.getSecretKey().getBytes());
                byte[] compressedBytes = compressUtil.compressData(encryptedBytes);
                totalFileSize += compressedBytes.length;
                compressedBytesList.add(compressedBytes);
            }

            if (totalFileSize > SINGLE_MAX_SIZE * multipartFiles.size()) {
                throw new SourceMapException("단일파일 크기는 50mb를 초과할 수 없습니다.");
            } else if ((fileSizeMap.get(pcode) == null ? 0 : fileSizeMap.get(pcode)) + totalFileSize > TOTAL_MAX_SIZE) {
                throw new SourceMapException("프로젝트당 전체 업로드 파일 크기는 256MB를 넘을 수 없습니다.");
            }

            for (int i = 0; i < multipartFiles.size(); i++) {
                String fullName = pcode + "/" + version + "/" + host + "/" + multipartFiles.get(i).getOriginalFilename();
                awsS3Util.uploadSourceMap(fullName, compressedBytesList.get(i));
            }
            fileSizeMap.put(pcode, totalFileSize);
        } catch (Exception exception) {
            log.error("SourceMapUtil:uploadSourceMap: exception:", exception);
            throw new SourceMapException("소스맵 업로드 중에 에러가 발생하였습니다." + exception);
        }
    }

    public List<SourceMapDto> readSourceMapDtoList(String dir) throws SourceMapException {
        try {
            List<SourceMapDto> sourceMapDtoList = new ArrayList<>();

            for (byte[] sourceMapBytes : awsS3Util.readSourceMapList(dir)) {
                byte[] deCompressedSourceMapBytes = compressUtil.deCompressData(sourceMapBytes);
                byte[] decryptedSourceMapBytes = cryptoUtil.decrypt(deCompressedSourceMapBytes, sourceMapConfig.getSecretKey().getBytes());
                sourceMapDtoList.add(SourceMapDto.from(JsonUtil.getJsonObject(new String(decryptedSourceMapBytes))));
            }
            return sourceMapDtoList;
        } catch (Exception exception) {
            log.error("SourceMapUtil:readSourceMapDtoList: exception:", exception);
            throw new SourceMapException("소스맵 조회 중에 에러가 발생하였습니다." + exception);
        }
    }

    private String readSourceMap(String dir) throws SourceMapException {
        try {
            byte[] sourceMapBytes = awsS3Util.readSourceMap(dir);
            byte[] deCompressedSourceMapBytes = compressUtil.deCompressData(sourceMapBytes);
            byte[] decryptedSourceMapBytes = cryptoUtil.decrypt(deCompressedSourceMapBytes, sourceMapConfig.getSecretKey().getBytes());
            String sourceMap = new String(decryptedSourceMapBytes);
            return sourceMap;
        } catch (Exception exception) {
            log.error("SourceMapUtil:readSourceMapDto: exception:", exception);
            throw new SourceMapException("소스맵 조회 중에 에러가 발생하였습니다." + exception);
        }
    }

    /**
     * 2가지 방법중에 하나로 조회
     * 1. 해당 파일 디렉토리...(파일명 포함)를 전달 받아서 이를 조회 및 조회한 데이터로 분석... (GET에 적합)
     * 2. Multipart형태의 파일 데이터를 전달 받아서 해당 파일의 컨텐츠를 통해... (POST API 개발..)
     * 나중 상황... (캐싱)까지 고려해서 1번으로 하는게 적합할 것 같다.
     */
    public SourceMapAnalysisDto analysisSourceMap(String dir, int line, int column) throws SourceMapException {
        try {
            String sourceMapStr = readSourceMap(dir);
            SourceMap sourceMap = new SourceMapImpl(sourceMapStr);
            SourceMapping sourceMapping = sourceMap.getMapping(line, column);
            SourceMapDto sourceMapDto = SourceMapDto.from(JsonUtil.getJsonObject(sourceMapStr));
            SourceMapAnalysisDto sourceMapAnalysisDto = SourceMapAnalysisDto.from(sourceMapping, sourceMapDto.getSourceMap());
            return sourceMapAnalysisDto;
        } catch (Exception exception) {
            log.error("SourceMapUtil:analysisSourceMap: exception:", exception);
            throw new SourceMapException("소스맵 분석 중에 에러가 발생하였습니다." + exception);
        }
    }

    /**
     * 추후에 캐싱으로 대체 ... (캐싱 구조에 대해 고민)
     */
    public int getFileSizeByDir(String dir) throws SourceMapException {
        try {
            return awsS3Util.getS3FileSizeByDir(dir);
        } catch (Exception e) {
            log.error("SourceMapUtil:getFileSizeByDir: exception:", e);
            throw new SourceMapException("소스맵 경로 내 파일 크기를 구하는 중에 에러가 발생하였습니다." + e);
        }
    }
}
