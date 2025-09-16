//package com.hubpedro.bookstoreapi.application.serivce.service;
//
//
//import com.hubpedro.bookstoreapi.application.serivce.dto.BookDTO;
//import com.hubpedro.bookstoreapi.application.serivce.entity.Book;
//import com.hubpedro.bookstoreapi.application.serivce.mapper.BookMapper;
//import com.hubpedro.bookstoreapi.application.serivce.repository.BookRepository;
//import com.hubpedro.bookstoreapi.application.serivce.service.impl.BookServiceImpl;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
// public class BookServiceTest
//{
//
//    @Mock
//    private BookRepository bookRepository;
//
//    @Mock
//    private BookMapper bookMapper;
//
//    @InjectMocks
//    private BookServiceImpl bookService;
//
//    private Book book;
//    private BookDTO bookDTO;
//    private List<Book> bookList;
//    private Long testId;
//
//    @BeforeEach
//    void setUp()
//{
//        // Setup test data
//        testId = 1L;
//
//        book = new Book();
//        // Set id directly on the entity using reflection or direct field access if needed
//        // For test purposes, we'll mock the responses instead of relying on actual entity getter methods
//
//        bookDTO = new BookDTO();
//        bookDTO.setId(testId);
//
//        bookList = new ArrayList<>();
//        bookList.add(book);
//    }
//
//    @Test
//    void testSave()
//{
//        // Arrange
//        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(book);
//        when(bookRepository.save(any(Book.class))).thenReturn(book);
//        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);
//
//        // Act
//        BookDTO result = bookService.save(bookDTO);
//
//        // Assert
//        assertThat(result).isNotNull();
//        verify(bookRepository).save(any(Book.class));
//        verify(bookMapper).toDto(any(Book.class));
//    }
//
//    @Test
//    void testFindAll()
//{
//        // Arrange
//        List<BookDTO> dtoList = new ArrayList<>();
//        dtoList.add(bookDTO);
//
//        when(bookRepository.findAll()).thenReturn(bookList);
//
//        // Stub the individual entity mapping instead of the list mapping
//        // This correctly handles the stream().map() call in the service
//        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);
//
//        // Act
//        List<BookDTO> result = bookService.findAll();
//
//        // Assert
//        assertThat(result).isNotNull().hasSize(1);
//        verify(bookRepository).findAll();
//    }
//
//    @Test
//    void testFindOne()
//{
//        // Arrange
//        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
//        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDTO);
//
//        // Act
//        BookDTO result = bookService.findOne(testId);
//
//        // Assert
//        assertThat(result).isNotNull();
//        verify(bookRepository).findById(any());
//    }
//
//    @Test
//    void testDelete()
//{
//        // Arrange
//        doNothing().when(bookRepository).deleteById(any());
//
//        // Act
//        bookService.delete(testId);
//
//        // Assert
//        verify(bookRepository).deleteById(any());
//    }
//}
