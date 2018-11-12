package com.exercise.mltest;

import com.exercise.mltest.controller.DnaController;
import com.exercise.mltest.domain.Dna;
import com.exercise.mltest.domain.DnaStats;
import com.exercise.mltest.enumeration.DnaTypeEnum;
import com.exercise.mltest.service.DnaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for verify DNA Controller endpoints
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = DnaController.class, secure = false)
public class DnaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DnaService dnaService;

    @Test
    public void mutantValidationV1_mutantDna_shouldReturnSuccess() throws Exception {
        char[][] matrix = {{'A', 'A', 'A', 'A'}, {'C', 'C', 'C', 'C'}, {'T', 'T', 'A', 'T'}, {'A', 'G', 'A', 'A'}};
        when(dnaService.getMatrixDna(Mockito.any())).thenReturn(matrix);
        when(dnaService.isMutant(Mockito.any())).thenReturn(true);

        String mutantDnaJson = "{\"dna\":[\"AAAA\",\"CCCC\",\"TTAT\",\"AGAA\"]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dnas/mutant")
                .header("X-API-Version", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mutantDnaJson);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void mutantValidationV1_mutantDna_shouldReturnForbidden() throws Exception {
        char[][] matrix = {{'A', 'T', 'A', 'A'}, {'C', 'G', 'C', 'C'}, {'T', 'T', 'A', 'T'}, {'A', 'G', 'A', 'A'}};
        when(dnaService.getMatrixDna(Mockito.any())).thenReturn(matrix);
        when(dnaService.isMutant(Mockito.any())).thenReturn(false);

        String humanDnaJson = "{\"dna\":[\"ATAA\",\"CGCC\",\"TTAT\",\"AGAA\"]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dnas/mutant")
                .header("X-API-Version", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(humanDnaJson);

        mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
    }

    @Test
    public void mutantValidationV2_mutantDna_shouldReturnSuccess() throws Exception {
        String[] dnaArr = {"AAAA", "CCCC", "TTAT", "AGAA"};
        Dna dna = new Dna(dnaArr, DnaTypeEnum.MUTANT);
        char[][] matrix = {{'A', 'A', 'A', 'A'}, {'C', 'C', 'C', 'C'}, {'T', 'T', 'A', 'T'}, {'A', 'G', 'A', 'A'}};
        when(dnaService.getMatrixDna(Mockito.any())).thenReturn(matrix);
        when(dnaService.isMutant(Mockito.any())).thenReturn(true);
        when(dnaService.saveDna(Mockito.any())).thenReturn(dna);

        String mutantDnaJson = "{\"dna\":[\"AAAA\",\"CCCC\",\"TTAT\",\"AGAA\"]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dnas/mutant")
                .header("X-API-Version", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mutantDnaJson);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    public void mutantValidationV2_humanDna_shouldReturnForbidden() throws Exception {
        String[] dnaArr = {"ATAA", "CGCC", "TTAT", "AGAA"};
        Dna dna = new Dna(dnaArr, DnaTypeEnum.HUMAN);
        char[][] matrix = {{'A', 'T', 'A', 'A'}, {'C', 'G', 'C', 'C'}, {'T', 'T', 'A', 'T'}, {'A', 'G', 'A', 'A'}};
        when(dnaService.getMatrixDna(Mockito.any())).thenReturn(matrix);
        when(dnaService.isMutant(Mockito.any())).thenReturn(false);
        when(dnaService.saveDna(Mockito.any())).thenReturn(dna);

        String humanDnaJson = "{\"dna\":[\"AAAA\",\"CCCC\",\"TTAT\",\"AGAA\"]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dnas/mutant")
                .header("X-API-Version", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(humanDnaJson);

        mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
    }

    @Test
    public void mutantValidationV2_invalidDna_shouldReturnBadRequest() throws Exception {
        String[] dna = {"A", "CC", "TTAT"};
        when(dnaService.getMatrixDna(dna)).thenReturn(null);

        String invalidDnaJson = "{\"dna\":[\"A\",\"CC\",\"TTAT\"]}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/dnas/mutant")
                .header("X-API-Version", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidDnaJson);

        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

    @Test
    public void getExistsDnaStats_shouldReturnDnaStatsSuccess() throws Exception {
        DnaStats dnaStats = new DnaStats(40, 100);
        when(dnaService.getStats()).thenReturn(dnaStats);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/dnas/stats")
                .header("X-API-Version", 2)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").exists())
                .andExpect(jsonPath("$.count_human_dna").exists())
                .andExpect(jsonPath("$.ratio").exists())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(dnaStats.getMutantRatio()))
                .andDo(print());
    }

    @Test
    public void getNonExistsDnaStats_shouldReturnForbidden() throws Exception {
        when(dnaService.getStats()).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/dnas/stats")
                .header("X-API-Version", 2)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
    }

}
