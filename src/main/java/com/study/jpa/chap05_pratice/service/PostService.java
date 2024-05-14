package com.study.jpa.chap05_pratice.service;

import com.study.jpa.chap05_pratice.Repository.HashTagRepository;
import com.study.jpa.chap05_pratice.Repository.PostRepository;
import com.study.jpa.chap05_pratice.dto.PageDTO;
import com.study.jpa.chap05_pratice.dto.PageResponseDTO;
import com.study.jpa.chap05_pratice.dto.PostDetailResponseDTO;
import com.study.jpa.chap05_pratice.dto.PostListResponseDTO;
import com.study.jpa.chap05_pratice.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional // JPA 레파지토리는 트랜잭션 단위로 동작하기 때문에 작성해 주세요.
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    public PostListResponseDTO getPosts(PageDTO dto) {

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(
                dto.getPage() - 1,
                dto.getSize(),
                Sort.by("createDate").descending()
        );

        // 데이터베이스에서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);

        // 게시물 정보만 꺼내기
        List<Post> postList = posts.getContent();

        // 게시물 정보를 응답용 DTO의 형태에 맞게 변환
        List<PostDetailResponseDTO> detailList
                = postList.stream()
                .map(post -> new PostDetailResponseDTO(post))
                .collect(Collectors.toList());

        // DB에서 조회한 정보를 JSON 형태에 맞는 DTO로 변환
        // 페이지 구성 정보와 위에 있는 게시물 정보를 또다른 DTO로 한 번에 포장해서 리턴할 예정
        // -> PostListResponseDTO

        return PostListResponseDTO.builder()
                // 총 게시물의 수가 아니라 페이징에 의해 조회된 게시물의 개수
                .count(detailList.size())
                // JPA가 준 페이지 정보가 담긴 객체를 DTO에게 전달해서 그 쪽에서 알고리즘 돌리게 시킴
                .pageInfo(new PageResponseDTO(posts))
                .posts(detailList)
                .build();

    }
}
