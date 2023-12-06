package com.example.demo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    //全書籍の情報を取得する
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    //書籍の情報を保存するためのメソッド
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }
    
 // 書籍の状態を更新するメソッド
    public void updateBook(Book updatedBook) {
       bookRepository.save(updatedBook);
   }

   //全書籍のidを取得するメソッド
   public Book getBookById(Long id) {
       return bookRepository.findById(id).orElse(null);
   }
    
    //検索結果を表示させるためのメソッド
    public List<Book> searchBooks(String id) {
        List<Book> bookList = new ArrayList<>();

        try {
            if (id == null || id.trim().isEmpty()) {
                bookList = bookRepository.findAll();
            } else if (id.chars().allMatch(Character::isDigit)) {
                Long parsedId = Long.parseLong(id);
                Book book = bookRepository.findById(parsedId).orElse(null);
                if (book != null) {
                    bookList.add(book);
                } else {
                    // 書籍が見つからなかった場合のメッセージを追加
                    throw new IllegalArgumentException("書籍が見つかりませんでした");
                }
            } else {
                // もし数字以外の形式のIDが渡された場合のエラーハンドリング
                throw new IllegalArgumentException("Invalid ID format");
            }
        } catch (NumberFormatException e) {
            // 数字に変換できなかった場合のエラーハンドリング
            throw new IllegalArgumentException("Invalid ID format");
        }

        return bookList;
    }

    //書籍の情報を削除するためのメソッド
    public String deleteBookById(Long id) {
        try {
            // データベースから指定されたIDの書籍を削除
            bookRepository.deleteById(id);
            return "書籍の削除に成功しました。";
        } catch (EmptyResultDataAccessException e) {
            // 削除対象が存在しない場合
            return "指定された書籍が見つかりません。";
        } catch (Exception e) {
            // その他の例外が発生した場合は削除に失敗したと判断
            return "書籍の削除に失敗しました。";
        }
    }
    
    //貸し出し履歴を取得するメソッド
    public List<BorrowHistory> getBorrowHistory() {
        return borrowHistoryRepository.findAll();
    }
    
  //貸し出し処理を行うためのメソッド
    public void borrowBook(Long bookId) {
        Book book = getBookById(bookId);

        if (book != null && book.getStatus().equals("AVAILABLE")) {
            book.setStatus("BORROWED");
            bookRepository.save(book);

            BorrowHistory history = new BorrowHistory();
            history.setBook(book);
            history.setBorrowDate(LocalDateTime.now());
            borrowHistoryRepository.save(history);
        }
    }

    //返却処理を行うためのメソッド
    public void returnBook(Long bookId) {
    	Book book = getBookById(bookId);

        if (book != null && book.getStatus() .equals("BORROWED")) {
            book.setStatus("AVAILABLE");
            bookRepository.save(book);

            BorrowHistory history = new BorrowHistory();
            history.setBook(book);
            history.setReturnDate(LocalDateTime.now());
            borrowHistoryRepository.save(history);
            
        }
    }
    
    //新規登録をするためのメソッド
    public void insertBook(String title, String author, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn); 
        book.setStatus("AVAILABLE");
        bookRepository.save(book);
    }

	
}
