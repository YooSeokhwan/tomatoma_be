package com.tomatoma.controller;

import com.tomatoma.dto.CategoryDTO;
import com.tomatoma.dto.ResponseDTO;
import com.tomatoma.entity.Category;
import com.tomatoma.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * GET /api/categories
     * Get all food categories
     */
    @GetMapping
    public ResponseEntity<ResponseDTO<List<CategoryDTO>>> getAllCategories() {
        log.info("Fetching all categories");

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> dtos = categories.stream()
                .map(c -> CategoryDTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .color(c.getColor())
                        .icon_emoji(c.getIcon_emoji())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseDTO.success(dtos));
    }

}
