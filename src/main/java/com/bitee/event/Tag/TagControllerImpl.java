package com.bitee.event.Tag;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class TagControllerImpl implements TagController{
    @Autowired
    TagService tagService;
    @Override
    public ResponseEntity<ApiResponse<Map<String,ArrayList<TagDto>>>> getTags() {
        return tagService.getTags();
    }
}
