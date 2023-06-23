package pl.witoldbrzezinski.libraryapp.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import pl.witoldbrzezinski.libraryapp.borrow.BorrowEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String isbn;

  private String title;

  private String author;

  @Enumerated(EnumType.STRING)
  private Genre genre;

  private String index;

  @Enumerated(EnumType.STRING)
  private Status status;

  private LocalDate endOfLastBorrow;

  @OneToMany(mappedBy = "book", cascade = CascadeType.MERGE)
  private Set<BorrowEntity> borrows = new HashSet<>();

  private boolean isDeleted;

  private final String uuid = UUID.randomUUID().toString();

  @Version private Long version;

  public BookEntity(
      Long id,
      String isbn,
      String title,
      String author,
      Genre genre,
      String index,
      Status status,
      boolean isDeleted,
      Long version) {
    this.id = id;
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.index = index;
    this.status = status;
    this.isDeleted = isDeleted;
    this.version = version;
  }

  public BookEntity(
      Long id,
      String isbn,
      String title,
      String author,
      Genre genre,
      String index,
      Status status,
      LocalDate endOfLastBorrow,
      boolean isDeleted,
      Long version) {
    this.id = id;
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.index = index;
    this.status = status;
    this.endOfLastBorrow = endOfLastBorrow;
    this.isDeleted = isDeleted;
    this.version = version;
  }

  public void addBorrow(BorrowEntity borrow) {
    borrow.setBook(this);
    borrows.add(borrow);
  }

  public void removeBorrow(BorrowEntity borrow) {
    borrow.setBook(null);
    borrows.remove(borrow);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookEntity that = (BookEntity) o;
    return Objects.equals(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
