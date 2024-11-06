package com.bitee.event.Tag;

import com.bitee.event.dao.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Map;

public interface TagService {

    ResponseEntity<ApiResponse<Map<TagCategory,ArrayList<Tag>>>> getTags();

    ResponseEntity<ApiResponse<Tag>> createTag(CreateTagDto createTagDto);

    ResponseEntity<ApiResponse<Tag>> editTag(Long tagId, CreateTagDto editTagDto);

    ResponseEntity<ApiResponse<Tag>> getSingleTag(Long tagId);

    ResponseEntity<ApiResponse<String>> deleteSingleTag(Long tagId);
}
