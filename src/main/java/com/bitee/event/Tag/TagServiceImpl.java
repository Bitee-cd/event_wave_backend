package com.bitee.event.Tag;

import com.bitee.event.dao.ApiResponse;
import com.bitee.event.dao.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    TagRepository tagRepository;
    @Override
    public ResponseEntity<ApiResponse<Map<String,ArrayList<TagDto>>>> getTags() {
        List<Tag> tags = tagRepository.findAll();
       Map<String,List<TagDto>> categorizedTags = new ArrayList<>(tags).stream()
                .collect(Collectors.groupingBy(
                        Tag::getCategory,
                        Collectors(tag-> {
                            TagDto tagDto = new TagDto();
                            tagDto.setId(tag.getId());
                            tagDto.setName(tag.getName());
                            return tagDto;
                        },Collectors.toList())
                ));
        return new ResponseEntity<>(ApiResponse.success("200","Tags retrieved successfully ",categorizedTags), HttpStatus.OK);
    }
}
