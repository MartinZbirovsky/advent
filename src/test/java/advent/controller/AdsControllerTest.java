package advent.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class AdsControllerTest {
    /*@Autowired
    private MockMvc mvc;

    @Test
    void getAds() throws Exception {
        this.mvc.perform(get("/api/ads/{id}", "35"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Harry"));
    }*/
}