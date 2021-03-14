package com.example.easynotes.controller;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.News;
import com.example.easynotes.repository.NewsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */
@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    NewsRepository newsRepository;

    @GetMapping("/news")
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @PostMapping("/news")
    public News createNews(@Valid @RequestBody News news) {
        return newsRepository.save(news);
    }

    @GetMapping("/news/{id}")
    public News getNewsById(@PathVariable(value = "id") Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", newsId));
    }

    @PutMapping("/news/{id}")
    public News updateNews(@PathVariable(value = "id") Long newsId,
                                           @Valid @RequestBody News newsDetails) {

        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", newsId));

        news.setTitle(newsDetails.getTitle());
        news.setContent(newsDetails.getContent());

        News updatedNews = newsRepository.save(news);
        return updatedNews;
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable(value = "id") Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new ResourceNotFoundException("News", "id", newsId));

        newsRepository.delete(news);

        return ResponseEntity.ok().build();
    }
}
