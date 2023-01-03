package pl.witoldbrzezinski.libraryapp.book;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusHandler {

  private final BookRepository bookRepository;

  @Scheduled(cron = "0 0 1 * * *")
  void setExpiredBooksStatus() {
    List<BookEntity> books = bookRepository.findAll();
    for (BookEntity book : books) {
      if (book.getEndOfLastBorrow().isBefore(LocalDate.now())) {
        book.setStatus(Status.EXPIRED);
        bookRepository.save(book);
      }
    }
  }
}
