package com.bitee.event.TagTests;

import com.bitee.event.Otp.OtpServiceImpl;
import com.bitee.event.Tag.*;
import com.bitee.event.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static com.bitee.event.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;
    Tag tag;
    CreateTagRequestDto createTagRequestDto;
    @BeforeEach
    void setup(){
        createTagRequestDto = new CreateTagRequestDto();
        createTagRequestDto.setTagCategory(TEST_TAG_CATEGORY);
        createTagRequestDto.setName(TEST_TAG_NAME);

        tag = new Tag();
        tag.setCategory(TEST_TAG_CATEGORY);
        tag.setName(TEST_TAG_NAME);

    }

    @Test
    void getTags_ShouldReturnTagsSuccessfully() {

        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        when(tagRepository.findAll()).thenReturn(tagList);

        Map<TagCategory, ArrayList<Tag>> tagsResponse = new HashMap<>();
        tagsResponse.put(TEST_TAG_CATEGORY, tagList);

        ResponseEntity<ApiResponse<Map<TagCategory,ArrayList<Tag>>>> response = tagService.getTags();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Tags retrieved successfully", response.getBody().getResponseMessage());
        assertTrue(response.getBody().getData().containsKey(TEST_TAG_CATEGORY));
    }

    @Test
    void getTags_ShouldReturnEmptyWhenNoTags() {
        when(tagRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<ApiResponse<Map<TagCategory, ArrayList<Tag>>>> response = tagService.getTags();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Tags retrieved successfully", response.getBody().getResponseMessage());
        assertTrue(response.getBody().getData().isEmpty());
    }

    @Test
    void createTag_ShouldReturnSuccess() {
      
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        ResponseEntity<ApiResponse<Tag>> response = tagService.createTag(createTagRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("201", response.getBody().getResponseCode());
        assertEquals("Tag created successfully", response.getBody().getResponseMessage());
        assertEquals(TEST_TAG_NAME, response.getBody().getData().getName());
    }

    @Test
    void createTag_ShouldReturnBadRequestForInvalidCategory() {
        CreateTagRequestDto createTagRequestDto;
        createTagRequestDto = new CreateTagRequestDto();
        createTagRequestDto.setName(TEST_TAG_NAME);

        ResponseEntity<ApiResponse<Tag>> response = tagService.createTag(createTagRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("Invalid Tag Category", response.getBody().getResponseMessage());
    }

    @Test
    void editTag_ShouldReturnSuccess() {
        when(tagRepository.findById(TEST_ID)).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        ResponseEntity<ApiResponse<Tag>> response = tagService.editTag(TEST_ID, createTagRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Tag updated Successfully", response.getBody().getResponseMessage());
        assertEquals(TEST_TAG_NAME, response.getBody().getData().getName());
    }

    @Test
    void editTag_ShouldReturnBadRequestWhenTagNotFound() {
        when(tagRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<Tag>> response = tagService.editTag(TEST_ID, createTagRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("Tag not found", response.getBody().getResponseMessage());
    }
    @Test
    void getSingleTag_ShouldReturnSuccess() {
        
        when(tagRepository.findById(TEST_ID)).thenReturn(Optional.of(tag));

        ResponseEntity<ApiResponse<Tag>> response = tagService.getSingleTag(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Tag retrieved successfully", response.getBody().getResponseMessage());
        assertEquals(TEST_TAG_NAME, response.getBody().getData().getName());
    }

    @Test
    void getSingleTag_ShouldReturnBadRequestWhenTagNotFound() {
        when(tagRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<Tag>> response = tagService.getSingleTag(TEST_ID);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("Tag not found", response.getBody().getResponseMessage());
    }

    @Test
    void deleteSingleTag_ShouldReturnSuccess() {
        
        when(tagRepository.findById(TEST_ID)).thenReturn(Optional.of(tag));

        ResponseEntity<ApiResponse<String>> response = tagService.deleteSingleTag(TEST_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("200", response.getBody().getResponseCode());
        assertEquals("Tag deleted successfully", response.getBody().getResponseMessage());
    }

    @Test
    void deleteSingleTag_ShouldReturnBadRequestWhenTagNotFound() {
        when(tagRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponse<String>> response = tagService.deleteSingleTag(TEST_ID);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("400", response.getBody().getResponseCode());
        assertEquals("Tag not found", response.getBody().getResponseMessage());
    }

}
