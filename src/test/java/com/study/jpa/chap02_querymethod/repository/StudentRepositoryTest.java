package com.study.jpa.chap02_querymethod.repository;

import com.study.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    // @BeforeAll : 해당 테스트 클래스를 초기화 할 때 딱 한번 수행되는 메서드 (static으로 선언해야 함)
    @BeforeEach // 각각의 테스트 메서드 실행 직전에 수행되는 메서드
    void insertData() {
        Student s1 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("수학과")
                .build();
        Student s2 = Student.builder()
                .name("언년이")
                .city("부산시")
                .major("수학교육과")
                .build();
        Student s3 = Student.builder()
                .name("대길이")
                .city("한양도성")
                .major("체육과")
                .build();

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 조회해야 한다.")
    void testFindByName() {
        // given
        String name ="춘식이";
        // when
        List<Student> students = studentRepository.findByName(name);
        // then
        assertEquals(1, students.size());

        System.out.println("students.get(0) = " + students.get(0));
    }



}