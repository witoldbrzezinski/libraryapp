package pl.witoldbrzezinski.libraryapp.customer;

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
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private LocalDate birthDate;

  private String personalNumber;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
  private Set<BorrowEntity> borrows = new HashSet<>();

  private boolean isDeleted;

  private final String uuid = UUID.randomUUID().toString();

  @Version private Long version;

  public CustomerEntity(
      String firstName,
      String lastName,
      Gender gender,
      LocalDate birthDate,
      String personalNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.birthDate = birthDate;
    this.personalNumber = personalNumber;
  }

  public CustomerEntity(
      Long id,
      String firstName,
      String lastName,
      Gender gender,
      LocalDate birthDate,
      String personalNumber,
      boolean isDeleted,
      Long version) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.birthDate = birthDate;
    this.personalNumber = personalNumber;
    this.isDeleted = isDeleted;
    this.version = version;
  }

  public void addBorrow(BorrowEntity borrow) {
    borrow.setCustomer(this);
    borrows.add(borrow);
  }

  public void removeBorrow(BorrowEntity borrow) {
    borrow.setCustomer(null);
    borrows.remove(borrow);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CustomerEntity that = (CustomerEntity) o;
    return uuid.equals(that.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
