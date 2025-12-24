package kr.co.api.backend.voice.service;

import kr.co.api.backend.dto.ProductDTO;
import kr.co.api.backend.mapper.DepositMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepositResolveService {

    private final DepositMapper depositMapper;

    public Optional<String> resolveProductCode(String sttText) {
        String keyword = normalize(sttText);
        log.info("[ resolveService ] normalize keyword={}", keyword);

        List<ProductDTO> list = depositMapper.findDpstByName(keyword);

        if (list.size() == 1) {
            return Optional.of(list.get(0).getDpstId());
        }
        return Optional.empty();
    }

    private String normalize(String text) {
        return text
                .replaceAll("(가입(하고 싶어|할래|하고싶어)|알려줘|말해줘)", "")
                .replaceAll("(전기)", "정기")
                .trim();
    }
}
