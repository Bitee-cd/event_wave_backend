package com.bitee.event.Tag;

import com.bitee.event.dao.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    @Override
    public ResponseEntity<ApiResponse<Map<TagCategory, ArrayList<Tag>>>> getTags() {
        List<Tag> tags = tagRepository.findAll();
        Map<TagCategory, ArrayList<Tag>> categorizedTags = tags.stream()
                .collect(Collectors.groupingBy(
                        Tag::getCategory,
                        Collectors.collectingAndThen(Collectors.toList(), ArrayList::new)
                ));
        return new ResponseEntity<>(ApiResponse.success("200", "Tags retrieved successfully ", categorizedTags), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<Tag>> createTag(CreateTagDto createTagDto) {
        try {
            TagCategory category = TagCategory.valueOf(String.valueOf(createTagDto.getTagCategory()));
            Tag tag = new Tag();
            tag.setName(createTagDto.getName());
            tag.setCategory(category);
            tagRepository.save(tag);
            return new ResponseEntity<>(ApiResponse.success("201", "Tag created successfully", tag), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid Tag Category", null), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Tag>> editTag(Long tagId, CreateTagDto editTagDto) {
        try {
            Optional<Tag> optionalTag = tagRepository.findById(tagId);

            if (optionalTag.isEmpty()) {
                return new ResponseEntity<>(ApiResponse.error("400", "Tag not found", null), HttpStatus.BAD_REQUEST);
            }

            Tag existingTag = optionalTag.get();
            existingTag.setCategory(editTagDto.getTagCategory());
            existingTag.setName(editTagDto.getName());
            tagRepository.save(existingTag);

            return new ResponseEntity<>(ApiResponse.success("200", "Tag updated Successfully", existingTag), HttpStatus.OK);

        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(ApiResponse.error("400", "Invalid Tag Category", null), HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<ApiResponse<Tag>> getSingleTag(Long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Tag not found", null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ApiResponse.success("200","Tag retrieved successfully",optionalTag.get()),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> deleteSingleTag(Long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error("400", "Tag not found", null), HttpStatus.BAD_REQUEST);
        }
        tagRepository.delete(optionalTag.get());
        return new ResponseEntity<>(ApiResponse.success("200","Tag deleted successfully",null),HttpStatus.OK);
    }
}
