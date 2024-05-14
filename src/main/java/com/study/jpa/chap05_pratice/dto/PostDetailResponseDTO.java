package com.study.jpa.chap05_pratice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.study.jpa.chap05_pratice.entity.HashTag;
import com.study.jpa.chap05_pratice.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailResponseDTO {

    private String writer;
    private String title;
    private String content;
    private List<String> hashTags;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime regDate;

    // 엔터티를 DTO로 변환하는 생성자


    public PostDetailResponseDTO(Post post) {
        this.writer = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getCreateDate();
        this.hashTags = post.getHashTags()
                .stream()
                .map(HashTag::getTagName)
                .collect(Collectors.toList());
    }
}
