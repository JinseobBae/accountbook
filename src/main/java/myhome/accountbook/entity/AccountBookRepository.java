package myhome.accountbook.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

    Optional<AccountBook> findByName(String name);
}
