# book-management-system 書籍管理システム
このプロジェクトは、書籍を管理するための簡易的なウェブアプリケーションです。
書籍の追加や検索、削除などをすることができます。

## 機能

- 書籍の追加と削除
- 書籍情報の編集
- 書籍の検索と表示
- 貸し出しと返却、履歴の確認

## 技術スタック

- フロントエンド: HTML
- バックエンド: Java
- データベース: SQL
- 開発ツール: SpringToolSuite4 MySQL

## ディレクトリ構造

book-management-system/
|-- src/
|   |-- main/
|   |   |-- java/com/example/demo
|   |   |   |-- Application.java
|   |   |   |-- Book.java
|   |   |   |-- BookController.java
|   |   |   |-- BookRepository.java
|   |   |   |-- BookService.java
|   |   |   |-- BorrowHistory.java
|   |   |   |-- BorrowHistoryRepository.java
|   |-- resources
|   |   |-- templates
|   |   |   |-- book_add.html
|   |   |   |-- book_delete_result.html
|   |   |   |-- book_edit.html
|   |   |   |-- book_loan_return.html
|   |   |   |-- book_search.html
|   |   |   |-- borrow_history.html
|   |   |   |-- index.html
|-- README.md
