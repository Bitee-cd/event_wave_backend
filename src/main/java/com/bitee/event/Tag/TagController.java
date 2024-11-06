package com.bitee.event.Tag;

import com.bitee.event.dao.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RequestMapping("api/v1/tags")
public interface TagController {

    @GetMapping("")
    ResponseEntity<ApiResponse<Map<TagCategory,ArrayList<Tag>>>> getTags();

    @PostMapping("")
    ResponseEntity<ApiResponse<Tag>> createTag(@Valid @RequestBody CreateTagDto createTagDto);

    @PutMapping("/{tagId}")
    ResponseEntity<ApiResponse<Tag>> editTag( @Valid @PathVariable Long tagId,@Valid @RequestBody CreateTagDto createTagDto);

    @GetMapping("/{tagId}")
    ResponseEntity<ApiResponse<Tag>> getSingleTag(@Valid @PathVariable Long tagId);

    @DeleteMapping("/{tagId}")
    ResponseEntity<ApiResponse<String>> deleteSingleTag(@Valid @PathVariable Long tagId);
}
