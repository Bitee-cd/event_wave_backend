package com.bitee.event.Tag;

import com.bitee.event.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class TagControllerImpl implements TagController {
    @Autowired
    TagService tagService;

    @Override
    public ResponseEntity<ApiResponse<Map<TagCategory, ArrayList<Tag>>>> getTags() {
        return tagService.getTags();
    }

    @Override
    public ResponseEntity<ApiResponse<Tag>> createTag(CreateTagRequestDto createTagRequestDto) {
        return tagService.createTag(createTagRequestDto);
    }

    @Override
    public ResponseEntity<ApiResponse<Tag>> editTag(Long tagId, CreateTagRequestDto createTagRequestDto) {
        return tagService.editTag(tagId, createTagRequestDto);
    }

    @Override
    public ResponseEntity<ApiResponse<Tag>> getSingleTag(Long tagId) {
        return tagService.getSingleTag(tagId);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteSingleTag(Long tagId) {
        return tagService.deleteSingleTag(tagId);
    }
}
