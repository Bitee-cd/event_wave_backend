package com.bitee.event.Tag;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Map;

public interface TagService {

    ResponseEntity<ApiResponse<Map<String,ArrayList<TagDto>>>> getTags();
}
