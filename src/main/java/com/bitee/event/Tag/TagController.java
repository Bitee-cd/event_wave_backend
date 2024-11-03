package com.bitee.event.Tag;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@RequestMapping("api/v1/tags")
public interface TagController {

    @GetMapping("")
    ResponseEntity<ApiResponse<Map<String,ArrayList<TagDto>>>> getTags();
}
