package com.bitee.event.Tag;

import com.bitee.event.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

public interface TagService {

    ResponseEntity<ApiResponse<Map<TagCategory,ArrayList<Tag>>>> getTags();

    ResponseEntity<ApiResponse<Tag>> createTag(CreateTagRequestDto createTagRequestDto);

    ResponseEntity<ApiResponse<Tag>> editTag(Long tagId, CreateTagRequestDto editTagDto);

    ResponseEntity<ApiResponse<Tag>> getSingleTag(Long tagId);

    ResponseEntity<ApiResponse<String>> deleteSingleTag(Long tagId);
}
