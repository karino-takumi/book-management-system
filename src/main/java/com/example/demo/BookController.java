package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    
    //データベースへ保存するためのメソッド
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    //トップページへの遷移
    @RequestMapping("/")
    public ModelAndView showAllBooks(ModelAndView mv) {
        List<Book> allBooks = bookService.getAllBooks();
        mv.addObject("books", allBooks);
        mv.setViewName("index"); 
        return mv;
    }
    
    //検索結果を別ページで表示させるためのメソッド
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(name = "id", required = false) String id, ModelAndView mv) {
        List<Book> bookList = bookService.searchBooks(id);
        mv.addObject("books", bookList);
        mv.setViewName("book_search");
        return mv;
    }
  
    //編集画面へ遷移するためのメソッド
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editBook(@RequestParam(name = "id") Long id,
    		ModelAndView mv) {
        Book book = bookService.getBookById(id);
        mv.addObject("book", book);
        mv.addObject("originalStatus", book.getStatus());
        mv.setViewName("book_edit");
        return mv;
    }
    
    //編集内容を保存するためのメソッド
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateBook(@ModelAttribute Book updatedBook, 
    		@RequestParam("originalStatus") String originalStatus, 
    		ModelAndView mv) {
        updatedBook.setStatus(originalStatus);
        bookService.updateBook(updatedBook);
        // 更新後の本のリストを取得
        List<Book> bookList = bookService.getAllBooks();
        mv.addObject("books", bookList);
        mv.setViewName("index");

        return mv;
    }
    
    //書籍を削除するためのメソッド
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(name = "id") Long id, ModelAndView mv) {
        String resultMessage = bookService.deleteBookById(id);

        // 削除結果に基づいたメッセージを追加
        mv.addObject("message", resultMessage);

        // 削除後に書籍一覧を再取得して表示
        List<Book> bookList = bookService.getAllBooks();
        mv.addObject("books", bookList);

        // リダイレクト先を指定
        mv.setViewName("book_delete_result");
        return mv;
    }
    
    //履歴を取得して表示させるためのメソッド
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ModelAndView showBorrowHistory(ModelAndView mv) {
        List<BorrowHistory> historyList = bookService.getBorrowHistory();
        mv.addObject("historyList", historyList);
        mv.setViewName("borrow_history");
        return mv;
    }
    
    //貸し出し返却画面へ遷移するためのメソッド
    @RequestMapping(value = "/loanReturn")
    public ModelAndView loanReturn(ModelAndView mv) {
        List<Book> books = bookService.getAllBooks();
        mv.addObject("books", books);
        mv.setViewName("book_loan_return");
        return mv;
    }
    
    //貸し出しメソッド
    @RequestMapping(value = "/borrow/{id}", method = RequestMethod.POST)
    public ModelAndView borrowBook(@PathVariable("id") Long bookId,
    		ModelAndView mv) {
        bookService.borrowBook(bookId);
        List<Book> bookList = bookService.getAllBooks();
        mv.addObject("books", bookList);
        mv.setViewName("index");

        return mv;
        
        }

    //返却メソッド
    @RequestMapping(value = "/return/{id}", method = RequestMethod.POST)
    public ModelAndView returnBook(@PathVariable("id") Long bookId,
    		ModelAndView mv) {
    	bookService.returnBook(bookId);
        List<Book> bookList = bookService.getAllBooks();
        mv.addObject("books", bookList);
        mv.setViewName("index");

        return mv;
    }

    
    //新規登録画面へ遷移させるためのメソッド
    @RequestMapping(value = "/add")
    public ModelAndView add(ModelAndView mv) {

    mv.setViewName("book_add");
    return mv;
    }
    
    //新規登録をするためのメソッド
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "isbn") String isbn,
            ModelAndView mv
    ) {
        bookService.insertBook(title, author, isbn);

        mv.addObject("books", bookService.getAllBooks());
        mv.setViewName("index");
        return mv;
    }
}
