package pl.witoldbrzezinski.libraryapp.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

  boolean existsByIsbn(String isbn);
}
