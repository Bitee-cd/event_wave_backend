package com.bitee.event.TagTests;


import com.bitee.event.Tag.*;
import com.bitee.event.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.bitee.event.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagControllerImpl tagController;
    private Tag tag;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();

        tag = new Tag();
        tag.setCategory(TEST_TAG_CATEGORY);
        tag.setName(TEST_TAG_NAME);
    }

    @Test
    void getTags_ShouldReturnTags() throws Exception {
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(tag);

        Map<TagCategory, ArrayList<Tag>> tags = new HashMap<>();
        tags.put(TEST_TAG_CATEGORY, tagList);
        ResponseEntity<ApiResponse<Map<TagCategory,ArrayList<Tag>>>> response = new ResponseEntity<>(ApiResponse.success("200", "Success", tags), HttpStatus.OK);

        when(tagService.getTags()).thenReturn(response);

        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());

        verify(tagService, times(1)).getTags();
    }


    @Test
    void createTag_ShouldReturnCreatedTag() throws Exception {
        CreateTagRequestDto createTagRequestDto = new CreateTagRequestDto();
        createTagRequestDto.setTagCategory(TEST_TAG_CATEGORY);
        createTagRequestDto.setName(TEST_TAG_NAME);

        ResponseEntity<ApiResponse<Tag>> response = new ResponseEntity<>(ApiResponse.success("200","Success",tag),HttpStatus.CREATED);
        when(tagService.createTag(any(CreateTagRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/tags")
                        .contentType("application/json")
                        .content("{\"name\":\"newTag\",\"tagCategory\":\"FASHION\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(TEST_TAG_NAME));

        verify(tagService, times(1)).createTag(any(CreateTagRequestDto.class));
    }

    @Test
    void editTag_ShouldReturnUpdatedTag() throws Exception {
        CreateTagRequestDto createTagRequestDto = new CreateTagRequestDto();
        createTagRequestDto.setTagCategory(TEST_TAG_CATEGORY);
        createTagRequestDto.setName(TEST_TAG_NAME);

        ResponseEntity<ApiResponse<Tag>> response = new ResponseEntity<>(ApiResponse.success("200","Success",tag),HttpStatus.OK);
        when(tagService.editTag(eq(TEST_ID), any(CreateTagRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/tags/{tagId}", TEST_ID)
                        .contentType("application/json")
                        .content("{\"name\":\"updatedTag\",\"tagCategory\":\"FASHION\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(TEST_TAG_NAME));

        verify(tagService, times(1)).editTag(eq(TEST_ID), any(CreateTagRequestDto.class));
    }


    @Test
    void getSingleTag_ShouldReturnTag() throws Exception {

        ResponseEntity<ApiResponse<Tag>> response = new ResponseEntity<>(ApiResponse.success("200","Success",tag),HttpStatus.OK);
        when(tagService.getSingleTag(TEST_ID)).thenReturn(response);

        mockMvc.perform(get("/tags/{tagId}", TEST_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Success"))
                .andExpect(jsonPath("$.data.name").value(TEST_TAG_NAME));

        verify(tagService, times(1)).getSingleTag(TEST_ID);
    }

    @Test
    void deleteSingleTag_ShouldReturnSuccessMessage() throws Exception {
        Long tagId = 1L;
        ResponseEntity<ApiResponse<String>> response = new ResponseEntity<>(ApiResponse.success("200","Success",null),HttpStatus.OK);
        when(tagService.deleteSingleTag(tagId)).thenReturn(response);

        mockMvc.perform(delete("/tags/{tagId}", tagId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseMessage").value("Success"));
        verify(tagService, times(1)).deleteSingleTag(tagId);
    }
}
