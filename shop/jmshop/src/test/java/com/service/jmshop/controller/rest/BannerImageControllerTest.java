package com.service.jmshop.controller.rest;

import com.service.jmshop.utils.banner.dto.BannerImageDto;
import com.service.jmshop.utils.banner.service.BannerImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BannerImageControllerTest {
    @MockBean
    private BannerImageService bannerImageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findBannerImagesTest() {
        try {
            when(bannerImageService.findBannerImages(any())).thenReturn(Arrays.asList(BannerImageDto.builder().build()));
            mockMvc.perform(get("/banner-image").accept("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk());
            verify(bannerImageService, timeout(1)).findBannerImages(any());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
